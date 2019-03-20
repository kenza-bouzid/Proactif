/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;

/**
 *
 * @author utilisateur
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Intervention implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long numIntervention;
    private String description ; 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDebut; 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateFin ; 

    public Intervention(String description, Date dateDebut, Date dateFin) {
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Intervention() {
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numIntervention  != null ? numIntervention.hashCode() : 0);
        return hash;
    }

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