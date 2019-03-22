/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import fr.insalyon.dasi.proactif.dao.ClientDao;
import fr.insalyon.dasi.proactif.dao.EmployeDao;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.dao.PersonneDao;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.metier.modele.Personne;
import fr.insalyon.dasi.proactif.util.GeoTest;
import fr.insalyon.dasi.proactif.util.Message;
import java.text.ParseException;
/**
 *
 * @author dhamidovic
 */
public class Service {

    public Service() {

    }

    public static void inscrireClient(String mail, String mdp, String civilite,
            String nom, String prenom, String dateNaissance,
            String adresse, String numTel) throws ParseException {

        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        Client c = new Client(civilite, nom, prenom, dateNaissance, GeoTest.getLatLng(adresse), numTel, mail, mdp);
        ClientDao.persist(c);
        if (ClientDao.findByEMail(mail, mdp) != null) {
            Message.envoyerMail("contact@proact.if", mail, "Bienvenue chez PROACT'IF", "Bonjour" + c.getPrenom() + ", nous vous confirmons votre inscription au service PROACT'IFG. Votre numéro de client est : " + c.getId());
        } else {
            Message.envoyerMail("contact@proact.if", mail, "Bienvenue chez PROACT'IF", "Bonjour" + c.getPrenom() + ", votre inscription au service PROACT'IF a malheureusement échoué...Merci de recommencer ultérieurement.");
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }

    public static int envoyerCodeConfirmation (String mail, String num)
    {    
        JpaUtil.creerEntityManager();
        if (ClientDao.findByEmailNum(mail, num)!= null)
        {
           int lower = 1000; int higher = 9999;
           int code = (int)(Math.random() * (higher-lower)) + lower ; 
           Message.envoyerNotification(num,"Votre code de confirmation est: "+code ); 
           JpaUtil.fermerEntityManager();
           return code; 
        }
        else 
        {
            JpaUtil.fermerEntityManager();
            return 0 ;
        }
    }
    
    public static Personne connexion(String mail, String mdp) {
        JpaUtil.creerEntityManager();
        Personne p = PersonneDao.findByEMail(mail, mdp) ; 
        JpaUtil.fermerEntityManager();
        return p; 
    }
    
    public static boolean employeEnIntervention (Employe e) 
    {
        
        
        return false; 
    }
    
    public static void Initialisation () throws ParseException
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        Employe e = new Employe ("08:00:00","18:00:00","Mr","Jeanne" , "Julien", "25/09/1996", GeoTest.getLatLng("7 Avenue Jean Capelle Ouest, Villeurbanne"), "0689743698", "emp1@gmail.com", "159");
        EmployeDao.persist(e);
        e = new Employe ("10:00:00","14:00:00","Mr","Jeanne" , "Bob", "15/10/1994", GeoTest.getLatLng("37 Avenue Jean Capelle Est, Villeurbanne"), "0669892316", "emp2@gmail.com", "175");
        EmployeDao.persist(e);
        e = new Employe ("08:00:00","20:00:00","Mr","Loic" , "Laura", "04/02/1992", GeoTest.getLatLng("61 Avenue Roger Salengro, Villeurbanne"), "0689746315", "emp3@gmail.com", "195");
        EmployeDao.persist(e);
        e = new Employe ("06:00:00","22:00:00","Mme","Cavagna" , "Lea", "14/10/1994", GeoTest.getLatLng("15 Avenue Roger Salengro, Villeurbanne"), "0634897512", "emp4@gmail.com", "164");
        EmployeDao.persist(e);
        e = new Employe ("11:00:00","16:00:00","Mme","Paquet" , "Louise", "27/04/1998", GeoTest.getLatLng("20 Avenue Albert Einstein, Villeurbanne"), "03987463158", "emp5@gmail.com", "179");
        EmployeDao.persist(e);
        e = new Employe ("16:30:00","2:00:00","Mr","Genest" , "Marc", "28/08/1998", GeoTest.getLatLng("37 Avenue Jean Capelle Est, Villeurbanne"), "03698745216", "emp6@gmail.com", "167");
        EmployeDao.persist(e);
        e = new Employe ("08:00:00","20:00:00","Mr","Lelouard" , "Pierre", "29/04/1997", GeoTest.getLatLng("20 Avenue Jean Capelle Ouest, Villeurbanne"), "07956314289", "emp7@gmail.com", "495");
        EmployeDao.persist(e);
        e = new Employe ("10:00:00","19:00:00","Mr","Hamidovic" , "Martin", "16/04/1994", GeoTest.getLatLng("7 Avenue Jean Capelle Est, Villeurbanne"), "06479515697", "emp8@gmail.com", "369");
        EmployeDao.persist(e);
        e = new Employe ("16:00:00","23:45:00","Mr","Scotto" , "David", "27/04/1970", GeoTest.getLatLng("21 Avenue Albert Einstein, Villeurbanne"), "02698756325", "emp9@gmail.com", "258");
        EmployeDao.persist(e);
        e = new Employe ("12:00:00","23:00:00","Mr","Tandereau" , "Nathan", "27/05/1964", GeoTest.getLatLng("69 Avenue Roger Salengro, Villeurbanne"), "06479951463", "emp10@gmail.com", "147");
        EmployeDao.persist(e);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        
    }

    public static void deconnexion() {

    }

    public static void demandeIntervention( ) {
        
    }

    public static void affecterEmployer() {

    }

}
