/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Client;

/**
 * Classe intéragissant avec la base de donnés pour 
 * les objets de type Client.
 * @author Kenza Bouzid
 * @author David Hamidovic
 */
public class ClientDao {
/**
 * Obtient un entity manager et persist le Client.
 * @param c le Client à persister.
 */
    public static void persist(Client c) {
        JpaUtil.obtenirEntityManager().persist(c);
    }
    
 /**
 * Obtient un entity manager et merge le Client.
 * @param c le Client qu'on merge.
 * @return On retourne la nouvelle instance du client
 * qui vient d'être merge.
 */
    public static Client merge(Client c) {
        return JpaUtil.obtenirEntityManager().merge(c);
    }
}
