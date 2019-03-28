/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Client;

/**
 *
 * @author dhamidovic
 */
public class ClientDao {

    public static void persist(Client c) {
        JpaUtil.obtenirEntityManager().persist(c);
    }
    public static Client merge(Client c) {
        return JpaUtil.obtenirEntityManager().merge(c);
    }
}
