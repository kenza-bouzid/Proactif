/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Personne;
import fr.insalyon.dasi.proactif.util.DebugLogger;
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
    
    public static Personne merge(Personne p) {
        return JpaUtil.obtenirEntityManager().merge(p);
    }

    public static Personne findByEmail(String mail) {
        Personne foundEntity = null;
        try {
            String jpql = "select p from Personne p where p.adresseElec = :mail";
            Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
            query.setParameter("mail", mail);
            List<Personne> results = query.getResultList();

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
        } catch (NonUniqueResultException e) {
            DebugLogger.log("Attention exception", e);
        }
        return foundEntity;
    }
    public static Personne findByEmailMdp(String mail, String mdp) {
        Personne foundEntity = null;
        try {
            String jpql = "select p from Personne p where p.adresseElec = :mail and p.mdp = :mdp";
            Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
            query.setParameter("mail", mail);
            query.setParameter("mdp", mdp);
            List<Personne> results = query.getResultList();

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
        } catch (NonUniqueResultException e) {
            DebugLogger.log("Attention exception", e);
        }
        return foundEntity;
    }

     public static Personne findByEmailNum(String mail, String num) {
        Personne foundEntity = null;
        try {
            String jpql = "select p from Personne p where p.adresseElec = :mail and p.numTel = :num";
            Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
            query.setParameter("mail", mail);
            query.setParameter("num", num);
            List<Personne> results = query.getResultList();
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
        } catch (NonUniqueResultException e) {
            DebugLogger.log("Attention exception", e);
        }
        return foundEntity;
    }
}
