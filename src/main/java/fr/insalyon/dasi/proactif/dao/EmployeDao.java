/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Employe;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author dhamidovic
 */
public class EmployeDao {
    
    public static void persist(Employe c) {
        JpaUtil.obtenirEntityManager().persist(c);
    }
    
    public static Employe find(Long id) {
        return JpaUtil.obtenirEntityManager().find(Employe.class, id);
    }
    
     public static List<Employe> listPersonne ()
    {
        String jpql = "select e from Employe e";
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        return (List<Employe>)query.getResultList();   
    }
}
