/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Personne;
import fr.insalyon.dasi.proactif.metier.service.Service;
import java.text.ParseException;
/**
 *
 * @author dhamidovic
 */
public class Test {

    public static void main(String args[]) throws ParseException {
        
        JpaUtil.init();

        Service s = new Service();
        Service.Initialisation();
        Service.inscrireClient("test@gmail.com", "123", "M", "Dupont", " Grégoire","02/06/1998", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0658974316");
        Service.envoyerCodeConfirmation("test@gmail.com","0658974316" );
        
        Personne p = Service.connexion("test@gmail.com","123"); 
        if (p instanceof Client)
        {
            System.out.println("Vous etes connecté");
        }
        
        JpaUtil.destroy();
        
       
        

    }

}
