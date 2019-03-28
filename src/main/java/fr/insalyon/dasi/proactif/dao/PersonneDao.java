/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Personne;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author utilisateur
 */
public class PersonneDao {

    public static void persist(Personne p) {
        JpaUtil.obtenirEntityManager().persist(p);
    }

    public static Personne findByEMail(String mail, String mdp) {
        String jpql = "select p from Personne p where p.adresseElec = :mail and p.mdp = :mdp";
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        query.setParameter("mail", mail);
        query.setParameter("mdp", mdp);
        List<Personne> results = query.getResultList();
        Personne foundEntity = null;
        if (!results.isEmpty()) {
            foundEntity = results.get(0);
        }
        if (results.size() > 1) {
            for (Personne result : results) {
                if (result != foundEntity) {
                    throw new NonUniqueResultException();
                }
            }
        }
        return foundEntity;
    }

    public static Personne merge(Personne p) {
        return JpaUtil.obtenirEntityManager().merge(p);
    }

}
