/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.modele;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author dhamidovic
 */
@Entity
public class Employe extends Personne implements Serializable {

    @OneToMany
    
    private List<Intervention> tabBord;

    public List<Intervention> getTabBord() {
        return tabBord;
    }

    public void setTabBord(List<Intervention> tabBord) {
        this.tabBord = tabBord;
    }

    @Basic
    private Time debutTravail;
    @Basic
    private Time finTravail;
    private boolean estEnIntervention;

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

    public Employe(String debut, String fin, String civilite,
            String nom, String prenom, String dateNaissance, String adresse,
            String numTel, String adresseElec, String mdp) throws ParseException {
        super(civilite, nom, prenom, dateNaissance, adresse, numTel, adresseElec, mdp);
        debutTravail = java.sql.Time.valueOf(debut);
        finTravail =  java.sql.Time.valueOf(fin);
        
        this.estEnIntervention = false;
    }

    public Employe() {
        super();
    }

    @Override
    public String toString() {
        return super.toString()+"Employe{" + "tabBord=" + tabBord + ", debutTravail=" + debutTravail + ", finTravail=" + finTravail + ", estEnIntervention=" + estEnIntervention + '}';
    }




}
