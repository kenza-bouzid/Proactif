
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;


/**
 *
 * @author utilisateur
 */
@Entity
public class Incident extends Intervention implements Serializable {

    public Incident(String description ) {
        super(description);
    }

    public Incident() {
    }

}