/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.modele;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Classe représentant les clients de notre application.
 * La classe hérite de Personne.
 * @author Kenza Bouzid
 * @author David Hamidovic
 */
@Entity

public class Client extends Personne implements Serializable {

    @OneToMany//(mappedBy="monClient")
    private List<Intervention> histoInterventions;

    public Client() {
        super(); 
    }
    
    public Client(String civilite, String nom, String prenom, String dateNaissance,
            String adresse, String numTel, String adresseElec, String mdp){
        super(civilite, nom, prenom, dateNaissance, adresse, numTel, adresseElec, mdp);
    }
    public List<Intervention> getHistoInterventions() {
        return histoInterventions;
    }

    public void setHistoInterventions(List<Intervention> histoInterventions) {
        this.histoInterventions = histoInterventions;
    }
    public boolean addHistoInterventions(Intervention i) {
        return this.histoInterventions.add(i);
    }

}
