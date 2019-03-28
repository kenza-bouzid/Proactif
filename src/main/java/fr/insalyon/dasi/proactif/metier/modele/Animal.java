
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
public class Animal extends Intervention implements Serializable {

    private String animal ; 

    public Animal(String animal, String description) {
        super(description);
        this.animal = animal;
    }

    public Animal() {
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
    
}