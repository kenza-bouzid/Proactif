/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Client;
import java.util.List;
import javax.persistence.Query;
/**
 *
 * @author dhamidovic
 */
public class ClientDao {
    
    public static void persist(Client c) {
        JpaUtil.obtenirEntityManager().persist(c);
    }
    
    public static Client find(Long id) {
        return JpaUtil.obtenirEntityManager().find(Client.class, id);
    }
    
     public static List<Client> listPersonne ()
    {
        String jpql = "select c from Client c";
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        return (List<Client>)query.getResultList();   
    }
     
}