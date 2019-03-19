/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.modele;

import com.google.maps.model.LatLng;
import java.io.Serializable;
import java.text.ParseException;
import javax.persistence.Entity;

/**
 *
 * @author dhamidovic
 */
@Entity

public class Client extends Personne implements Serializable{
    
    public Client() {
    }

    public Client(String civilite, String nom, String prenom, String dateNaissance, 
            LatLng coord, String numTel, String adresseElec, String mdp) throws ParseException {
        super(civilite, nom, prenom, dateNaissance, coord, numTel, adresseElec, mdp);
    }

}
