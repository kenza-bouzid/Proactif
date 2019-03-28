/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.util;

import fr.insalyon.dasi.proactif.metier.modele.Animal;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Incident;
import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import fr.insalyon.dasi.proactif.metier.modele.Livraison;
import fr.insalyon.dasi.proactif.util.GeoTest;
import fr.insalyon.dasi.proactif.util.Message;
import java.text.SimpleDateFormat;

/**
 *
 * @author utilisateur
 */
public class Notif {
    
    public static void envoyerNotifEmploye(Intervention i) {
        String type = "";
        if (i instanceof Animal) {
            Animal a = (Animal) i;
            type = "Animal pour " + a.getAnimal();
        } else if (i instanceof Incident) {
            type = "Incident";
        } else if (i instanceof Livraison) {
            Livraison l = (Livraison) i;
            type = "Livraison de la part de l'entreprise: " + ((Livraison) i).getEntreprise() + " pour l'objet: " + ((Livraison) i).getObjet();
        }
        String date = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(i.getTimeDebut());
        Client c = i.getMonClient(); 
        Message.envoyerNotification(i.getEmployeAffecte().getNumTel(), "Intervention " + type + " le " + date + " pour " + c.getPrenom() + " " + c.getNom() + " (#" + c.getId() + "), " + c.getAdresse() + ". <<" + i.getDescription() + ">>. " + "Trajet:" + GeoTest.getFlightDistanceInKm(i.getEmployeAffecte().getCoord(), c.getCoord()) + " Km.");
    }

    public static void envoyerNotifClient(Intervention i) {
        String dateDebut = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(i.getTimeDebut());
        String dateFin = new SimpleDateFormat("HH:mm").format(i.getTimeDebut());
        String status = "Echec"; 
        if (i.isReussie())
        {
            status = "Réussite"; 
        }
        Message.envoyerNotification(i.getMonClient().getNumTel(), status + ": votre demande du " + dateDebut + " a été clôturée à " + dateFin + ". " + i.getCommentaire());
    }
}
