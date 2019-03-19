/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Livraison;
/**
 *
 * @author utilisateur
 */
public class LivraisonDao {
    
    public static void persist(Livraison l) {
        JpaUtil.obtenirEntityManager().persist(l);
    }
}
