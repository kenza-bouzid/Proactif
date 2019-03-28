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
 * Classe représentant les employés de notre application.
 * La classe hérite de Personne.
 * @author Kenza Bouzid
 * @author David Hamidovic
 */
@Entity
public class Employe extends Personne implements Serializable {

    @OneToMany//(mappedBy="employeAffecte")
    private List<Intervention> tabBord;
    @Basic
    private Time debutTravail;
    @Basic
    private Time finTravail;
    private boolean estEnIntervention;
    
/**
 * Constructeur de la classe Employe.
 * @param debut heure de début de son travail.
 * @param fin heure de fin de son travail.
 * @param civilite civilite de l'Employe.
 * @param nom nom de l'Employe.
 * @param prenom prénom de l'Employe.
 * @param dateNaissance date de naissance de l'Employe.
 * @param adresse adresse de l'Employe.
 * @param numTel numéro de téléphone de l'Employe.
 * @param adresseElec adresse mail de l'Employe.
 * @param mdp mot de passe de l'Employe.
 * @throws ParseException 
 */    
    public Employe(String debut, String fin, String civilite,
            String nom, String prenom, String dateNaissance, String adresse,
            String numTel, String adresseElec, String mdp) throws ParseException {
        super(civilite, nom, prenom, dateNaissance, adresse, numTel, adresseElec, mdp);
        debutTravail = java.sql.Time.valueOf(debut);
        finTravail = java.sql.Time.valueOf(fin);

        this.estEnIntervention = false;
    }

    public Employe() {
        super();
    }
    
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
    public List<Intervention> getTabBord() {
        return tabBord;
    }

    public void setTabBord(List<Intervention> tabBord) {
        this.tabBord = tabBord;
    }

/**
 * Retourne l'intervention courante.
 * La méthode retourne l'intervention courante si l'Employe
 * est en intervention. Sinon la méthode retourne null.
 * @return L'intervention courante ou null si il n'y en
 * a pas.
 */
    public Intervention getInterventionCourante() {
        for (Intervention i : tabBord) {
            if (i.isEnCours()) {
                return i;
            }
        }
        return null;
    }
}
