/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Client;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
/**
 *
 * @author dhamidovic
 */
public class ClientDao {
    
    public static void persist(Client c) {
        JpaUtil.obtenirEntityManager().persist(c);
    }
    
    public static Client findByEMail(String mail , String mdp) {
        String jpql = "select c from Client c where c.adresseElec = :mail and c.mdp = :mdp"; 
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        query.setParameter("mail", mail);
        query.setParameter("mdp", mdp); 
        List<Client> results = query.getResultList();
        Client foundEntity = null;
        if (!results.isEmpty()) {
            foundEntity = results.get(0);
        }
        if (results.size() > 1) {
            for (Client result : results) {
                if (result != foundEntity) {
                    throw new NonUniqueResultException();
                }
            }
        }
        return foundEntity;
    }
    
     public static List<Client> listPersonne ()
    {
        String jpql = "select c from Client c";
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        return (List<Client>)query.getResultList();   
    }
    
    public static Client findByEmailNum (String mail , String num)
    {
        String jpql = "select c from Client c where c.adresseElec = :mail and c.numTel = :num"; 
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        query.setParameter("mail", mail);
        query.setParameter("num", num); 
        List<Client> results = query.getResultList();
        Client foundEntity = null;
        if (!results.isEmpty()) {
            foundEntity = results.get(0);
        }
        if (results.size() > 1) {
            for (Client result : results) {
                if (result != foundEntity) {
                    throw new NonUniqueResultException();
                }
            }
        }
        return foundEntity;
    }
    
    public static Client merge(Client c) {
        return JpaUtil.obtenirEntityManager().merge(c);
    }
}
