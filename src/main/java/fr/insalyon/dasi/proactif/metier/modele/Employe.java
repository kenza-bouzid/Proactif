/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.modele;

import com.google.maps.model.LatLng;
import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

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

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date debutTravail;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date finTravail;
    private boolean estEnIntervention;

    public Date getDebutTravail() {
        return debutTravail;
    }

    public Date getFinTravail() {
        return finTravail;
    }

    public void setDebutTravail(Date debutTravail) {
        this.debutTravail = debutTravail;
    }

    public void setFinTravail(Date finTravail) {
        this.finTravail = finTravail;
    }

    public boolean isEstEnIntervention() {
        return estEnIntervention;
    }

    public void setEstEnIntervention(boolean estEnIntervention) {
        this.estEnIntervention = estEnIntervention;
    }

    public Employe(String debut, String fin, String civilite,
            String nom, String prenom, String dateNaissance, LatLng coord,
            String numTel, String adresseElec, String mdp) throws ParseException {
        super(civilite, nom, prenom, dateNaissance, coord, numTel, adresseElec, mdp);
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        try{
             this.debutTravail = format.parse(debut);
            this.finTravail = format.parse(fin);  
        }
        catch (ParseException e )
        {
            e.printStackTrace();
        }
       
        this.estEnIntervention = false; 
    }

    public Employe() {
    }

}
