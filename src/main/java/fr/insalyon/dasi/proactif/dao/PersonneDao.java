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
 * Classe intéragissant avec la base de donnés pour 
 * les objets de type Personne.
 * @author Kenza Bouzid
 * @author David Hamidovic
 */
public class PersonneDao {

 /**
 * Obtient un entity manager et persist la Personne.
 * @param p l'Intervention à persister.
 */
    public static void persist(Personne p) {
        JpaUtil.obtenirEntityManager().persist(p);
    }
 /**
 * Obtient un entity manager et merge la Personne.
 * @param p la Personne qu'on merge.
 * @return On retourne la nouvelle instance de la Personne
 * qui vient d'être merge.
 */
    public static Personne merge(Personne p) {
        return JpaUtil.obtenirEntityManager().merge(p);
    }
/**
 * La méthode cherche si une Personne ayant le String mail comme
 * adresse électronique dans la base de données. 
 * On effectue une requête jpql. Si le resultat de la requête est 
 * vide on retourne null, sinon on renvoie la Personne trouvé.
 * @param mail le mail qu'on utilise pour chercher une Personne
 * dans la base de données.
 * @return La Personne ou null si on n'a pas de correspondance.
 */
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
    
/**
 * La méthode cherche si une Personne ayant les String mail comme
 * adresse Electronique et mdp comme mot de passe dans la base de données.
 * On effectue une requête jpql. Si le resultat de la requête est vide
 * on retourne null, sinon on renvoie la Personne trouvé.
 * @param mail le mail qu'on utilise pour chercher une Personne
 * dans la base de données.
 * @param mdp le mot de passe qu'on utilise pour chercher une Personne
 * dans la base de données.
 * @return La Personne ou null si on n'a pas de correspondance.
 */
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
/**
 * La méthode cherche si une Personne ayant les String mail comme
 * adresse Electronique et num comme numéro de téléphone dans la base de
 * données.On effectue une requête jpql. Si le resultat de la requête est
 * vide on retourne null, sinon on renvoie la Personne trouvé.
 * @param mail le mail qu'on utilise pour chercher une Personne
 * dans la base de données.
 * @param num le numéro de téléphone qu'on utilise pour chercher une Personne
 * dans la base de données.
 * @return La Personne ou null si on n'a pas de correspondance.
 */
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
