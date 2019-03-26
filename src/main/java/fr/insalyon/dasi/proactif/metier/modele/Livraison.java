
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.modele;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import javax.persistence.Entity;
/**
 *
 * @author utilisateur
 */
@Entity
public class Livraison extends Intervention implements Serializable {
    
    private String objet ; 
    private String entreprise; 

    public Livraison(String objet, String entreprise, String description) throws ParseException {
        super(description);
        this.objet = objet;
        this.entreprise = entreprise;
    }

    public Livraison() {
    }

    @Override
    public String toString() {
        return "Livraison{" + "objet=" + objet + ", entreprise=" + entreprise + '}';
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }
    
    
}