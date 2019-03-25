/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Intervention;

/**
 *
 * @author utilisateur
 */
public class InterventionDao {

    public static void persist(Intervention i) {
        JpaUtil.obtenirEntityManager().persist(i);
    }

    public static Intervention merge(Intervention i) {
        return JpaUtil.obtenirEntityManager().merge(i);
    }
}
