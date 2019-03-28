/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import fr.insalyon.dasi.proactif.dao.ClientDao;
import fr.insalyon.dasi.proactif.dao.EmployeDao;
import fr.insalyon.dasi.proactif.dao.InterventionDao;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.dao.PersonneDao;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import fr.insalyon.dasi.proactif.metier.modele.Personne;
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

    public static void inscrireClient(Client c) throws ParseException {

        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        c.setCoord(GeoTest.getLatLng(c.getAdresse()));
        ClientDao.persist(c);
        if (ClientDao.findByEMail(c.getAdresseElec(), c.getMdp()) != null) {

            JpaUtil.validerTransaction();
            Message.envoyerMail("contact@proact.if", c.getAdresseElec(), "Bienvenue chez PROACT'IF", "Bonjour" + c.getPrenom() + ", nous vous confirmons votre inscription au service PROACT'IFG. Votre numéro de client est : " + c.getId());
        } else {
            JpaUtil.annulerTransaction();
            Message.envoyerMail("contact@proact.if", c.getAdresseElec(), "Bienvenue chez PROACT'IF", "Bonjour" + c.getPrenom() + ", votre inscription au service PROACT'IF a malheureusement échoué...Merci de recommencer ultérieurement.");
        }

        JpaUtil.fermerEntityManager();
    }

    public static int envoyerCodeConfirmation(String mail, String num) {
        JpaUtil.creerEntityManager();
        if (ClientDao.findByEmailNum(mail, num) != null) {
            int lower = 1000;
            int higher = 9999;
            int code = (int) (Math.random() * (higher - lower)) + lower;
            Message.envoyerNotification(num, "Votre code de confirmation est: " + code);
            JpaUtil.fermerEntityManager();
            return code;
        } else {
            JpaUtil.fermerEntityManager();
            return 0;
        }
    }
    
    public static Client findClient (String mail , String num)
    {
        JpaUtil.creerEntityManager();
        Client c = ClientDao.findByEmailNum(mail, num) ; 
        JpaUtil.fermerEntityManager();
        return c ; 
    }

    public static void updateMdp(Client c, String mdp) {
        c.setMdp(mdp);
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        ClientDao.merge(c);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }

    public static Personne connexion(String mail, String mdp) {
        JpaUtil.creerEntityManager();
        Personne p = PersonneDao.findByEMail(mail, mdp);
        JpaUtil.fermerEntityManager();
        return p;
    }

    public static boolean demandeIntervention(Client c, Intervention i) throws ParseException {
        i.setClient(c);
        c.addHistoInterventions(i);

        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        List<Employe> listeEmploye = EmployeDao.listerEmployesDisponibles(i.getTimeDebut());

        Double duree = Double.MAX_VALUE;

        Employe employeAffecte = null;

        if ((listeEmploye != null) && !(listeEmploye.isEmpty())) {

            for (Employe e : listeEmploye) {
                System.out.println(e);
                Double dureeDistanceVelo = GeoTest.getTripDurationByBicycleInMinute(e.getCoord(), c.getCoord());
                System.out.println(dureeDistanceVelo);
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
            SousService.envoyerNotifEmploye(i);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return i.isEnCours();
    }



    public static void cloturerIntervention(Intervention i, String commentaire, boolean Status) {
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
        SousService.envoyerNotifClient(i);
    }

    public static void Initialisation() throws ParseException {

        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            Employe e = new Employe("08:00:00", "18:00:00", "Mr", "Jeanne", "Julien", "1996-09-25", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0689743698", "emp1@gmail.com", "159");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("10:00:00", "14:00:00", "Mr", "Jeanne", "Bob", "1994-10-15", "37 Avenue Jean Capelle Est, Villeurbanne", "0669892316", "emp2@gmail.com", "175");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("08:00:00", "23:45:00", "Mr", "Loic", "Laura", "1992-04-02", "61 Avenue Roger Salengro, Villeurbanne", "0689746315", "emp3@gmail.com", "195");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("06:00:00", "23:59:00", "Mme", "Cavagna", "Lea", "1994-10-14", "15 Avenue Roger Salengro, Villeurbanne", "0634897512", "emp4@gmail.com", "164");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("11:00:00", "23:59:00", "Mme", "Paquet", "Louise", "1998-04-01", "20 Avenue Albert Einstein, Villeurbanne", "03987463158", "emp5@gmail.com", "179");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("16:30:00", "23:59:00", "Mr", "Genest", "Marc", "1998-06-03", "37 Avenue Jean Capelle Est, Villeurbanne", "03698745216", "emp6@gmail.com", "167");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("08:00:00", "02:00:00", "Mr", "Lelouard", "Pierre", "1997-09-03", "20 Avenue Jean Capelle Ouest, Villeurbanne", "07956314289", "emp7@gmail.com", "495");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("10:00:00", "23:59:00", "Mr", "Hamidovic", "Martin", "1994-07-01", "7 Avenue Jean Capelle Est, Villeurbanne", "06479515697", "emp8@gmail.com", "369");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("16:00:00", "23:59:00", "Mr", "Scotto", "David", "1970-04-27", "21 Avenue Albert Einstein, Villeurbanne", "02698756325", "emp9@gmail.com", "258");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("12:00:00", "23:00:00", "Mr", "Tandereau", "Nathan", "1964-05-07", "69 Avenue Roger Salengro, Villeurbanne", "06479951463", "emp10@gmail.com", "147");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (ParseException | RollbackException e) {
            JpaUtil.annulerTransaction();
        }
    }

    public static List<Intervention> RecupererInterventionsDuJour(Employe e) {
        JpaUtil.creerEntityManager();
        List<Intervention> InterventionDuJour = InterventionDao.RecupererInterventionsDuJour(e);
        JpaUtil.fermerEntityManager();
        return InterventionDuJour;
    }

    public static List<Intervention> HistoriqueClientParType(String type, Client c) {
        JpaUtil.creerEntityManager();
        List<Intervention> HistoriqueClient = InterventionDao.HistoriqueClientParType(type, c);
        JpaUtil.fermerEntityManager();
        return HistoriqueClient;
    }

    public static List<Intervention> HistoriqueClientParDate(String date, Client c) throws ParseException {
        JpaUtil.creerEntityManager();
        List<Intervention> HistoriqueClient = InterventionDao.HistoriqueClientParDate(date, c);
        JpaUtil.fermerEntityManager();
        return HistoriqueClient;
    }

    public static List<Intervention> HistoriqueClientParTypeEtDate(String type, String date, Client c) throws ParseException {
        JpaUtil.creerEntityManager();
        List<Intervention> HistoriqueClient = InterventionDao.HistoriqueClientParTypeEtDate(type, date, c);
        JpaUtil.fermerEntityManager();
        return HistoriqueClient;
    }

    public static void updateProfil(String civilite, String nom, String prenom, Date dateNaissance, String adresse, String numTel, String adresseElec, Client c) {
        c.setCivilite(civilite);
        c.setNom(nom);
        c.setPrenom(prenom);
        c.setDateNaissance(dateNaissance);
        c.setAdresse(adresse);
        c.setCoord(GeoTest.getLatLng(adresse));
        c.setNumTel(numTel);
        c.setAdresseElec(adresseElec);
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        ClientDao.merge(c);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    public static Intervention getInterventionCourante (Employe e ){
        for (Intervention i : e.getTabBord())
        {
            if (i.isEnCours())
                return i ; 
        }
        return null; 
    }
}
