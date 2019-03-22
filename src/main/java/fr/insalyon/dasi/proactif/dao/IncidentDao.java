/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Animal;
import fr.insalyon.dasi.proactif.metier.modele.Incident;

/**
 *
 * @author utilisateur
 */
public class IncidentDao {
    
    public static void persist(Animal a) {
        JpaUtil.obtenirEntityManager().persist(a);
    }

    public static void persist(Incident i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
