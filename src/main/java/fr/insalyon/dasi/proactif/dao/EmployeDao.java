/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Employe;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author dhamidovic
 */
public class EmployeDao {

    public static void persist(Employe c) {
        JpaUtil.obtenirEntityManager().persist(c);
    }

    public static Employe findByEMail(String mail, String mdp) {
        String jpql = "select e from Employe e where e.adresseElec = :mail and e.mdp = :mdp";
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        query.setParameter("mail", mail);
        query.setParameter("mdp", mdp);
        List<Employe> results = query.getResultList();
        Employe foundEntity = null;
        if (!results.isEmpty()) {
            foundEntity = results.get(0);
        }
        if (results.size() > 1) {
            for (Employe result : results) {
                if (result != foundEntity) {
                    throw new NonUniqueResultException();
                }
            }
        }
        return foundEntity;
    }

    public static List<Employe> listPersonne() {
        String jpql = "select e from Employe e";
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        return (List<Employe>) query.getResultList();
    }

    public static List<Employe> listerEmployesDisponibles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
