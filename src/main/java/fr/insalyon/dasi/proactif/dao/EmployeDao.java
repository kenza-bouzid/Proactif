/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Employe;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author dhamidovic
 */
public class EmployeDao {

    public static void persist(Employe e) {
        JpaUtil.obtenirEntityManager().persist(e);
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

    public static List<Employe> listerEmployesDisponibles(Time date) {
        String jpql = "select e from Employe e where e.estEnIntervention=0 and e.debutTravail<:date and e.finTravail>:date";
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        query  = query.setParameter("date", date,TemporalType.TIME);
        List<Employe> results = query.getResultList();
        if (results.isEmpty()) {
            results = null;
        }
        return results;
    }

    public static Employe merge(Employe e) {
        return JpaUtil.obtenirEntityManager().merge(e);
    }
}
