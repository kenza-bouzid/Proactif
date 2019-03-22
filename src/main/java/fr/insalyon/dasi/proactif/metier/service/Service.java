/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import fr.insalyon.dasi.proactif.dao.ClientDao;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.util.GeoTest;
import fr.insalyon.dasi.proactif.util.Message;
import java.text.ParseException;
import java.util.Random;

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
    public static void connexion(String mail, String mdp) {

    }

    public static void deconnexion() {

    }

    public static void demandeIntervention( ) {
        
    }

    public static void affecterEmployer() {

    }

}
