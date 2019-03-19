/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.modele;

import com.google.maps.model.LatLng;
import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import javax.persistence.Entity;

/**
 *
 * @author dhamidovic
 */
@Entity
public class Employe extends Personne implements Serializable {

    private Time debutTravail; 
    private Time finTravail; 
    private boolean estEnIntervention ; 

    public Time getDebutTravail() {
        return debutTravail;
    }

    public void setDebutTravail(Time debutTravail) {
        this.debutTravail = debutTravail;
    }

    public Time getFinTravail() {
        return finTravail;
    }

    public void setFinTravail(Time finTravail) {
        this.finTravail = finTravail;
    }

    public boolean isEstEnIntervention() {
        return estEnIntervention;
    }

    public void setEstEnIntervention(boolean estEnIntervention) {
        this.estEnIntervention = estEnIntervention;
    }

    public Employe(Time debutTravail, Time finTravail, String civilite,
           String nom, String prenom, String dateNaissance, LatLng coord, 
           String numTel, String adresseElec, String mdp) throws ParseException {
        super(civilite, nom, prenom, dateNaissance, coord, numTel, adresseElec, mdp);
        this.debutTravail = debutTravail;
        this.finTravail = finTravail;
    }

    public Employe() {
    }
    
 
}
