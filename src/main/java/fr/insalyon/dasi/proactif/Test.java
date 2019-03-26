/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.modele.Animal;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.metier.modele.Incident;
import fr.insalyon.dasi.proactif.metier.modele.Livraison;
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
        //
        Personne p = Service.connexion("test@gmail.com","123"); 
        if (p instanceof Client)
        {
            System.out.println((Client)p);
        }
        Incident i = new Incident ("mon voisin m'a signalé un incident");
        Animal a = new Animal ("pug","Il faudrait sortir Bruno au park en face de chez moi"); 
        Livraison l = new Livraison("colis","DPD","blablabla");
        Incident i2  = new Incident("blabla2");
        System.out.println(Service.demandeIntervention(c, i));
        System.out.println(Service.demandeIntervention(c, a));
        System.out.println(Service.demandeIntervention(c, l));
        
        Service.envoyerNotifEmploye(i);
        Service.cloturerIntervention(i, "Tout bon", true);
        Service.envoyerNotifClient(i);
        
        Service.envoyerNotifEmploye(a);
        Service.cloturerIntervention(a, "Tout bon", true);
        Service.envoyerNotifClient(a);
        
        Service.envoyerNotifEmploye(l);
        Service.cloturerIntervention(l, "Tout bon", false);
        Service.envoyerNotifClient(l);
        
        /*NullPointerException ? why
        Service.envoyerNotifEmploye(i2);
        Service.cloturerIntervention(i2, "Tout bon", true);
        Service.envoyerNotifClient(i2);*/
        
        
        Client e = (Client) Service.connexion("test@gmail.com", "123");
        
        System.out.println(e);
        Service.HistoriqueClientParTypeEtDate("livraison", "26/03/2019",e);
        Service.RecupererInterventionsDuJour((Employe) Service.connexion("emp9@gmail.com", "258"));
        JpaUtil.destroy();
     
       
        

    }

}
