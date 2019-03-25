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
import fr.insalyon.dasi.proactif.metier.modele.Animal;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.metier.modele.Incident;
import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import fr.insalyon.dasi.proactif.metier.modele.Livraison;
import fr.insalyon.dasi.proactif.metier.modele.Personne;
import fr.insalyon.dasi.proactif.util.GeoTest;
import fr.insalyon.dasi.proactif.util.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            Message.envoyerMail("contact@proact.if", c.getAdresseElec(), "Bienvenue chez PROACT'IF", "Bonjour" + c.getPrenom() + ", nous vous confirmons votre inscription au service PROACT'IFG. Votre numéro de client est : " + c.getId());
        } else {
            Message.envoyerMail("contact@proact.if", c.getAdresseElec(), "Bienvenue chez PROACT'IF", "Bonjour" + c.getPrenom() + ", votre inscription au service PROACT'IF a malheureusement échoué...Merci de recommencer ultérieurement.");
        }
        JpaUtil.validerTransaction();
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
    public static void updateMdp (Client c , String mdp)
    {
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
                i.setStatus(true);
            }
        }
        i.setEmployeAffecte(employeAffecte);
        InterventionDao.persist(i);
        ClientDao.merge(c);
        if (employeAffecte != null) {
            employeAffecte.getTabBord().add(i);
            EmployeDao.merge(employeAffecte);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return i.isStatus();
    }

    public static void envoyerNotifEmploye(Intervention i) {
        String type="";
        if (i instanceof Animal) {
            Animal a = (Animal) i;
            type = "Animal pour "+a.getAnimal() ; 
        } else if (i instanceof Incident) {
            type = "Incident";
        } else if (i instanceof Livraison) {
            Livraison l = (Livraison)i; 
            type = "Livraison de la part de l'entreprise: "+ ((Livraison) i).getEntreprise()+" pour l'objet: "+((Livraison) i).getObjet();
        }
        String date = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(i.getTimeDebut());
        Message.envoyerNotification(i.getEmployeAffecte().getNumTel(), "Intervention "+type+" le " + date+ " pour " + i.getClient().getPrenom() + " " + i.getClient().getNom() + " (#" + i.getClient().getId() + "), "+ i.getClient().getAdresse() + ". <<" + i.getDescription() + ">>. " + "Trajet:" + GeoTest.getFlightDistanceInKm(i.getEmployeAffecte().getCoord(), i.getClient().getCoord())+" Km.");
    }
    
    public static void envoyerNotifClient(Intervention i) {
        String dateDebut = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(i.getTimeDebut());
        String dateFin = new SimpleDateFormat("HH:mm").format(i.getTimeDebut());
        Message.envoyerNotification(i.getClient().getNumTel(), "Votre demande du "+dateDebut+" a été clôturée à "+dateFin+ ". "+i.getCommentaire());
    }
    
    

    public static void cloturerIntervention(Intervention i, String commentaire, boolean Status) {
        i.setDateFin(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
        i.setCommentaire(commentaire);
        i.setStatus(Status);
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        InterventionDao.merge(i);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
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
            e = new Employe("08:00:00", "20:00:00", "Mr", "Loic", "Laura", "1992-04-02", "61 Avenue Roger Salengro, Villeurbanne", "0689746315", "emp3@gmail.com", "195");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("06:00:00", "22:00:00", "Mme", "Cavagna", "Lea", "1994-10-14", "15 Avenue Roger Salengro, Villeurbanne", "0634897512", "emp4@gmail.com", "164");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("11:00:00", "16:00:00", "Mme", "Paquet", "Louise", "1998-04-01", "20 Avenue Albert Einstein, Villeurbanne", "03987463158", "emp5@gmail.com", "179");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("16:30:00", "2:00:00", "Mr", "Genest", "Marc", "1998-06-03", "37 Avenue Jean Capelle Est, Villeurbanne", "03698745216", "emp6@gmail.com", "167");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("08:00:00", "02:00:00", "Mr", "Lelouard", "Pierre", "1997-09-03", "20 Avenue Jean Capelle Ouest, Villeurbanne", "07956314289", "emp7@gmail.com", "495");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("10:00:00", "19:00:00", "Mr", "Hamidovic", "Martin", "1994-07-01", "7 Avenue Jean Capelle Est, Villeurbanne", "06479515697", "emp8@gmail.com", "369");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            e = new Employe("16:00:00", "23:45:00", "Mr", "Scotto", "David", "1970-04-27", "21 Avenue Albert Einstein, Villeurbanne", "02698756325", "emp9@gmail.com", "258");
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

    public static void RecupererInterventionsDuJour(Employe e){
        JpaUtil.creerEntityManager();
        InterventionDao.RecupererInterventionsDuJour(e);
        JpaUtil.fermerEntityManager();
    
    }
}
