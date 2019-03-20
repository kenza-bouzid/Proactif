/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Animal;
/**
 *
 * @author utilisateur
 */
public class AnimalDao {

    public static void persist(Animal a) {
        JpaUtil.obtenirEntityManager().persist(a);
    }

}
