/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import com.google.maps.model.LatLng;
import javax.persistence.Basic;
import javax.persistence.Column;

/**
 * Classe représentant les personnes de notre application.
 * Elle représente les employés et les clients.
 * Elle est abstract.
 * @author Kenza Bouzid
 * @author David Hamidovic
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personne implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   
    private String civilite;
    private String nom;
    private String prenom;
    @Basic
    private java.sql.Date dateNaissance;
    private String adresse;
    private LatLng coord;
    private String numTel;
    @Column(unique = true, nullable = false)
    private String adresseElec;
    private String mdp;

    public Personne() {
    }
/**
 * Constructeur de Personne.
 * @param civilite civilite de la Personne.
 * @param nom nom de la Personne.
 * @param prenom prénom de la Personne.
 * @param dateNaissance date de naissance de la Personne.
 * @param adresse adresse de la Personne.
 * @param numTel numéro de téléphone de la Personne.
 * @param adresseElec adresse mail de la Personne.
 * @param mdp mot de passe de la Personne.
 */
    public Personne(String civilite, String nom, String prenom, String dateNaissance, String adresse, String numTel, String adresseElec, String mdp) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = java.sql.Date.valueOf(dateNaissance);
        this.adresse = adresse;
        this.numTel = numTel;
        this.adresseElec = adresseElec;
        this.mdp = mdp;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public java.sql.Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(java.sql.Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LatLng getCoord() {
        return coord;
    }

    public void setCoord(LatLng coord) {
        this.coord = coord;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getAdresseElec() {
        return adresseElec;
    }

    public void setAdresseElec(String adresseElec) {
        this.adresseElec = adresseElec;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
/**
 * Override de la méthode equals. 
 * @param object l'object sur lequel on veut tester l'égualité.
 * @return true ou false si les objets sont égaux ou non.
 */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Intervention)) {
            return false;
        }
        Personne other = (Personne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
}
