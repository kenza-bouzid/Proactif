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


/**
 *
 * @author dhamidovic
 */
public class Service {

    public Service() {

    }
    
    public static void inscrireClient(String mail, String mdp, String civilite, 
                               String nom, String prenom, String dateNaissance, 
                               String adresse, String numTel ) throws ParseException {
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        Client c = new Client(civilite, nom, prenom, dateNaissance, GeoTest.getLatLng(adresse), numTel, mail, mdp);
        ClientDao.persist(c);
        Message.envoyerMail("contact@proact.if", mail, "Bienvenue chez PROACT'IF", "Bonjour"+c.getPrenom()+", nous vous confirmons votre inscription au service PROACT'IFG. Votre numéro de client est : "+c.getId());

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    
    public static void connexion()
    {
        /* */
    }
    
    public static void deconnexion()
    {
        
    }
    
    public static void demanderIntervention()
    {
        
    }
    
    public static void  affecterEmployer ()
    {
        
    }
   
    
    
    
    
}
