/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Incident;
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
        Service.Initialisation();

        Client c = new Client ("M", "Dupont","Grégoire" ,"1998-06-02","7 Avenue Jean Capelle Ouest, Villeurbanne" , "0658974316", "test@gmail.com", "123" ); 
        Service.inscrireClient(c);
        //Service.envoyerCodeConfirmation("test@gmail.com","0658974316" );
        
        Personne p = Service.connexion("test@gmail.com","123"); 
        if (p instanceof Client)
        {
            System.out.println("Vous etes connecté");
        }
        Incident i = new Incident ("gyomklhjvh"); 
        System.out.println(Service.demandeIntervention(c, i));
        
        
        JpaUtil.destroy();
        
       
        

    }

}
