/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import fr.insalyon.dasi.proactif.util.Notif;
import fr.insalyon.dasi.proactif.dao.ClientDao;
import fr.insalyon.dasi.proactif.dao.EmployeDao;
import fr.insalyon.dasi.proactif.dao.InterventionDao;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.dao.PersonneDao;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import fr.insalyon.dasi.proactif.metier.modele.Personne;
import fr.insalyon.dasi.proactif.util.DebugLogger;
import fr.insalyon.dasi.proactif.util.GeoTest;
import fr.insalyon.dasi.proactif.util.Message;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import javax.persistence.RollbackException;

public class Service {

    public Service() {
    }

    public static boolean inscrireClient(Client c) throws ParseException {
        boolean reussie = false;
        try {
            JpaUtil.creerEntityManager();
            if ((Client) PersonneDao.findByEmail(c.getAdresseElec()) == null) {
                JpaUtil.ouvrirTransaction();
                c.setCoord(GeoTest.getLatLng(c.getAdresse()));
                ClientDao.persist(c);
                if ((Client) PersonneDao.findByEmailMdp(c.getAdresseElec(), c.getMdp()) != null) {
                    JpaUtil.validerTransaction();
                    DebugLogger.log("Inscription réussie!");
                    Message.envoyerMail("contact@proact.if", c.getAdresseElec(), "Bienvenue chez PROACT'IF", "Bonjour" + c.getPrenom() + ", nous vous confirmons votre inscription au service PROACT'IFG. Votre numéro de client est : " + c.getId());
                    reussie = true;
                } else {
                    JpaUtil.annulerTransaction();
                    DebugLogger.log("Echec de l'inscription!");
                    Message.envoyerMail("contact@proact.if", c.getAdresseElec(), "Bienvenue chez PROACT'IF", "Bonjour" + c.getPrenom() + ", votre inscription au service PROACT'IF a malencontreusement échoué...Merci de recommencer ultérieurement.");
                }
            } else {
                DebugLogger.log("Echec de l'inscription, cette adresse email correspond déjà à un autre utilisateur");
            }
            JpaUtil.fermerEntityManager();
        } catch (RollbackException e) {
            DebugLogger.log("Rollback Exception d'inscription", e);
        }
        return reussie;
    }

    public static int envoyerCodeConfirmation(String mail, String num) {
        int code = 0;
        try {
            JpaUtil.creerEntityManager();
            if (PersonneDao.findByEmailNum(mail, num) != null) {
                int lower = 1000;
                int higher = 9999;
                code = (int) (Math.random() * (higher - lower)) + lower;
                Message.envoyerNotification(num, "Votre code de confirmation est: " + code);
                JpaUtil.fermerEntityManager();
            }
        } catch (Exception e) {
            DebugLogger.log("Attention exception lors de l'envoi du code de confirmation ", e);
        }
        return code;
    }

    public static Client findClient(String mail, String num) {
        Client c = null;
        try {
            JpaUtil.creerEntityManager();
            c = (Client) PersonneDao.findByEmailNum(mail, num);
            JpaUtil.fermerEntityManager();
        } catch (Exception e) {
            DebugLogger.log("Attention exception pour trouver le client (mail,num): ", e);
        }
        return c;
    }

    public static void updateMdp(Personne c, String mdp) {
        try {
            c.setMdp(mdp);
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            PersonneDao.merge(c);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (RollbackException e) {
            DebugLogger.log("Attention exception pour trouver le client (mail,num): ", e);
        }
    }

    public static Personne connexion(String mail, String mdp) {
        Personne p = null;
        try {
            JpaUtil.creerEntityManager();
            p = PersonneDao.findByEmailMdp(mail, mdp);
            JpaUtil.fermerEntityManager();
        } catch (Exception e) {
            DebugLogger.log("Attention exception lors de la connexion ", e);
        }
        if (p != null) {
            DebugLogger.log("Connexion réussie !");
        } else {
            DebugLogger.log("Echec de la connexion!");
        }
        return p;
    }

    public static boolean demandeIntervention(Intervention i, Client c) {

        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            i.setMonClient(c);
            c.addHistoInterventions(i);
            List<Employe> listeEmploye = EmployeDao.listerEmployesDisponibles(i.getTimeDebut());

            Double duree = Double.MAX_VALUE;

            Employe employeAffecte = null;

            if ((listeEmploye != null) && !(listeEmploye.isEmpty())) {

                for (Employe e : listeEmploye) {

                    Double dureeDistanceVelo = GeoTest.getTripDurationByBicycleInMinute(e.getCoord(), c.getCoord());

                    if (duree > dureeDistanceVelo) {
                        duree = dureeDistanceVelo;
                        employeAffecte = e;
                    }
                }
                if (employeAffecte != null) {
                    employeAffecte.setEstEnIntervention(true);
                    i.setEnCours(true);
                }
            }

            i.setEmployeAffecte(employeAffecte);
            InterventionDao.persist(i);
            ClientDao.merge(c);
            if (employeAffecte != null) {
                employeAffecte.getTabBord().add(i);
                EmployeDao.merge(employeAffecte);
                Notif.envoyerNotifEmploye(i);
                Message.envoyerNotification(c.getNumTel(), "Votre demande d'intervention numéro (#" + i.getNumIntervention() + ") a été prise en compte, vous receverez une notification vous informant de son déroulement une fois achevée. \n Nous vous remercions pour votre confiance! ");
            } else {
                Message.envoyerNotification(c.getNumTel(), "Votre demande d'intervention numéro (#" + i.getNumIntervention() + ") n'a malheureusement pas été approuvée, aucun emploi n'est disponible à l'instant, merci de réessayer ultérieurement! ");
            }
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();

        } catch (RollbackException e) {
            DebugLogger.log("Rollback Exception demande d'intervention", e);
        }
        return i.isEnCours();
    }

    public static void cloturerIntervention(Intervention i, String commentaire, boolean Status) {
        if (i.isEnCours()) {
            try {
                i.setDateFin(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                i.setCommentaire(commentaire);
                i.setReussie(Status);
                i.setEnCours(false);
                Employe e = i.getEmployeAffecte();
                e.setEstEnIntervention(false);
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
                EmployeDao.merge(e);
                InterventionDao.merge(i);
                JpaUtil.validerTransaction();
                JpaUtil.fermerEntityManager();
                Notif.envoyerNotifClient(i);
            } catch (RollbackException e) {
                DebugLogger.log("Rollback Exception clôturer intervention", e);
            }
        } else {
            DebugLogger.log("Cette intervention ne peut pas être clôturée, celle ci a déjà été clôturé ou pas du tout entammé!");
        }
    }

    public static void initialisationEmploye() throws ParseException {

        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            Employe e = new Employe("08:00:00", "18:00:00", "Mr", "Jeanne", "Julien", "1996-09-25", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0689743698", "emp1@gmail.com", "159");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            Employe e1 = new Employe("10:00:00", "14:00:00", "Mr", "Jeanne", "Bob", "1994-10-15", "37 Avenue Jean Capelle Est, Villeurbanne", "0669892316", "emp2@gmail.com", "175");
            e1.setCoord(GeoTest.getLatLng(e1.getAdresse()));
            EmployeDao.persist(e1);
            Employe e2 = new Employe("08:00:00", "23:45:00", "Mr", "Loic", "Laura", "1992-04-02", "61 Avenue Roger Salengro, Villeurbanne", "0689746315", "emp3@gmail.com", "195");
            e2.setCoord(GeoTest.getLatLng(e2.getAdresse()));
            EmployeDao.persist(e2);
            Employe e3 = new Employe("06:00:00", "23:59:00", "Mme", "Cavagna", "Lea", "1994-10-14", "15 Avenue Roger Salengro, Villeurbanne", "0634897512", "emp4@gmail.com", "164");
            e3.setCoord(GeoTest.getLatLng(e3.getAdresse()));
            EmployeDao.persist(e3);
            Employe e4 = new Employe("11:00:00", "23:59:00", "Mme", "Paquet", "Louise", "1998-04-01", "20 Avenue Albert Einstein, Villeurbanne", "03987463158", "emp5@gmail.com", "179");
            e4.setCoord(GeoTest.getLatLng(e4.getAdresse()));
            EmployeDao.persist(e4);
            Employe e5 = new Employe("16:30:00", "23:59:00", "Mr", "Genest", "Marc", "1998-06-03", "37 Avenue Jean Capelle Est, Villeurbanne", "03698745216", "emp6@gmail.com", "167");
            e5.setCoord(GeoTest.getLatLng(e5.getAdresse()));
            EmployeDao.persist(e5);
            Employe e6 = new Employe("08:00:00", "02:00:00", "Mr", "Lelouard", "Pierre", "1997-09-03", "20 Avenue Jean Capelle Ouest, Villeurbanne", "07956314289", "emp7@gmail.com", "495");
            e6.setCoord(GeoTest.getLatLng(e6.getAdresse()));
            EmployeDao.persist(e6);
            Employe e7 = new Employe("10:00:00", "23:59:00", "Mr", "Hamidovic", "Martin", "1994-07-01", "7 Avenue Jean Capelle Est, Villeurbanne", "06479515697", "emp8@gmail.com", "369");
            e7.setCoord(GeoTest.getLatLng(e7.getAdresse()));
            EmployeDao.persist(e7);
            Employe e8 = new Employe("16:00:00", "23:59:00", "Mr", "Scotto", "David", "1970-04-27", "21 Avenue Albert Einstein, Villeurbanne", "02698756325", "emp9@gmail.com", "258");
            e8.setCoord(GeoTest.getLatLng(e8.getAdresse()));
            EmployeDao.persist(e8);
            Employe e9 = new Employe("12:00:00", "23:00:00", "Mr", "Tandereau", "Nathan", "1964-05-07", "69 Avenue Roger Salengro, Villeurbanne", "06479951463", "emp10@gmail.com", "147");
            e9.setCoord(GeoTest.getLatLng(e9.getAdresse()));
            EmployeDao.persist(e9);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (RollbackException e) {
            DebugLogger.log("RollBack Exception lors de l'initialisation", e);
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
        }
    }

    public static void initialisationClient() throws ParseException {
        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            Client c = new Client("Mr", "Jeanne", "Patrick", "1996-09-25", "15 Avenue Jean Capelle Ouest, Villeurbanne", "0689743698", "cli1@gmail.com", "298");
            c.setCoord(GeoTest.getLatLng(c.getAdresse()));
            ClientDao.persist(c);
            c = new Client("Mr", "Laporte", "Bob", "1997-10-15", "20 Avenue Jean Capelle Est, Villeurbanne", "0669892316", "cli2@gmail.com", "164");
            c.setCoord(GeoTest.getLatLng(c.getAdresse()));
            ClientDao.persist(c);
            c = new Client("Mr", "Loic", "Laura", "1996-04-02", "16 Avenue Roger Salengro, Villeurbanne", "0689746315", "cli3@gmail.com", "123");
            c.setCoord(GeoTest.getLatLng(c.getAdresse()));
            ClientDao.persist(c);
            c = new Client("Mme", "Cavagna", "Lea", "1993-10-14", "21 Avenue Jean Capelle Ouest, Villeurbanne", "0634897512", "cli4@gmail.com", "145");
            c.setCoord(GeoTest.getLatLng(c.getAdresse()));
            ClientDao.persist(c);

            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (RollbackException e) {
            DebugLogger.log("RollBack Exception lors de l'initialisation", e);
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
        }
    }

    public static List<Intervention> RecupererInterventionsDuJour(Employe e) {
        List<Intervention> interventionDuJour = null;
        try {
            JpaUtil.creerEntityManager();
            interventionDuJour = InterventionDao.RecupererInterventionsDuJour(e);
            JpaUtil.fermerEntityManager();
        } catch (Exception ex) {
            DebugLogger.log("Attentionn exception lors de la récupération des interventions du jour ", ex);
        }

        return interventionDuJour;
    }

    public static List<Intervention> HistoriqueClientParType(String type, Client c) {
        List<Intervention> historiqueClient = null;
        try {

            JpaUtil.creerEntityManager();
            historiqueClient = InterventionDao.HistoriqueClientParType(type, c);
            JpaUtil.fermerEntityManager();
        } catch (Exception ex) {
            DebugLogger.log("Attentionn exception lors de la récupération de l'histo client trié par type ", ex);
        }

        return historiqueClient;
    }

    public static List<Intervention> HistoriqueClientParDate(String date, Client c) throws ParseException {
        List<Intervention> historiqueClient = null;
        try {
            JpaUtil.creerEntityManager();
            historiqueClient = InterventionDao.HistoriqueClientParDate(date, c);
            JpaUtil.fermerEntityManager();
        } catch (ParseException e) {
            DebugLogger.log("Attentionn exception lors de la récupération de l'histo client trié par type ", e);
        }
        return historiqueClient;
    }

    public static List<Intervention> HistoriqueClientParTypeEtDate(String type, String date, Client c) throws ParseException {
        List<Intervention> historiqueClient = null;
        try {
            JpaUtil.creerEntityManager();
            historiqueClient = InterventionDao.HistoriqueClientParTypeEtDate(type, date, c);
            JpaUtil.fermerEntityManager();
        } catch (ParseException e) {
            DebugLogger.log("Attentionn exception lors de la récupération de l'histo client trié par type et par date ", e);
        }
        return historiqueClient;
    }

    public static void updateProfil(String civilite, String nom, String prenom, String dateNaissance, String adresse, String numTel, String adresseElec, Personne p) {

        try {
            p.setCivilite(civilite);
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setDateNaissance(java.sql.Date.valueOf(dateNaissance));
            p.setAdresse(adresse);
            p.setCoord(GeoTest.getLatLng(adresse));
            p.setNumTel(numTel);
            p.setAdresseElec(adresseElec);
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            PersonneDao.merge(p);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (RollbackException e) {
            DebugLogger.log("RollBack Exception lors de la mise a jour du profil", e);
        }
    }
}
