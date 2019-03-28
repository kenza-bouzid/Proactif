
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.modele;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Classe représentant les interventions de notre application.
 * Elle est abstract.
 * @author Kenza Bouzid
 * @author David Hamidovic
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Intervention implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long numIntervention;
    private String description;
    private boolean reussie;
    private boolean enCours;
    @Basic
    private java.sql.Timestamp dateDebut;
    @Basic
    private java.sql.Timestamp dateFin;
    private String commentaire;
    @ManyToOne
    @JoinColumn(name = "EMPLOYE_ID", referencedColumnName = "id")
    private Employe employeAffecte;
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "id")
    private Client monClient;
/**
 * Constructeur de la classe Intervention.
 * @param description Description du client de l'intervention demandé.
 */
    public Intervention(String description) {
        this.description = description;
        this.dateDebut = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        this.enCours = false;
    }

    public Intervention() {
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Employe getEmployeAffecte() {
        return employeAffecte;
    }

    public void setEmployeAffecte(Employe employeAffecte) {
        this.employeAffecte = employeAffecte;
    }

    public Client getMonClient() {
        return monClient;
    }

    public void setMonClient(Client monClient) {
        this.monClient = monClient;
    }

    public boolean isReussie() {
        return reussie;
    }

    public void setReussie(boolean reussie) {
        this.reussie = reussie;
    }

    public boolean isEnCours() {
        return enCours;
    }

    public void setEnCours(boolean enCours) {
        this.enCours = enCours;
    }

    public Long getNumIntervention() {
        return numIntervention;
    }

    public void setNumIntervention(Long numIntervention) {
        this.numIntervention = numIntervention;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public Time getTimeDebut() {
        return new Time(this.getDateDebut().getTime());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numIntervention != null ? numIntervention.hashCode() : 0);
        return hash;
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
        Intervention other = (Intervention) object;
        if ((this.numIntervention == null && other.numIntervention != null) || (this.numIntervention != null && !this.numIntervention.equals(other.numIntervention))) {
            return false;
        }
        return true;
    }
}
