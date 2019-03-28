/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.util.DebugLogger;
import java.sql.Time;
import java.util.List;
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

    public static List<Employe> listerEmployesDisponibles(Time date) {
        List<Employe> results = null;
        try {
            String jpql = "select e from Employe e where e.estEnIntervention=0 and e.debutTravail<:date and e.finTravail>:date";
            Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
            query = query.setParameter("date", date, TemporalType.TIME);
            results = query.getResultList();
            if (results.isEmpty()) {
                results = null;
            }
        } catch (Exception e) {
            DebugLogger.log("Attention exception", e);
        }
        return results;
    }

    public static Employe merge(Employe e) {
        return JpaUtil.obtenirEntityManager().merge(e);
    }
}
