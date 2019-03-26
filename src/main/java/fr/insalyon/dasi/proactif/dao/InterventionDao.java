/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author utilisateur
 */
public class InterventionDao {

    public static void persist(Intervention i) {
        JpaUtil.obtenirEntityManager().persist(i);
    }

    public static Intervention merge(Intervention i) {
        return JpaUtil.obtenirEntityManager().merge(i);
    }

    public static List<Intervention> RecupererInterventionsDuJour(Employe e) {
        String jpql = "select i from Intervention i where i.employeAffecte =:e and i.dateDebut>:date1 and i.dateFin<:date2";
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        Timestamp date1 = new Timestamp(c1.getTime().getTime());
        Timestamp date2 = new Timestamp(c2.getTime().getTime());
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        query = query.setParameter("date1", date1, TemporalType.DATE);
        query = query.setParameter("date2", date2, TemporalType.DATE);
        query = query.setParameter("e", e);
        List<Intervention> results = query.getResultList();
        if (results == null || results.isEmpty()) {
            return null;
        }
        return results;
        }
    
    public static List<Intervention> HistoriqueClient(String typeIntervention, Client c ){
        String jpql;
        List<Intervention> HistoriqueClient=null;
                
        switch(typeIntervention)
        {
            case "incident":
                jpql="select i from Incident i where i.client=:c";
                break;
            case "animal":
                jpql="select a from Animal a where a.client=:c";
                break;
            case "livraison":
                jpql="select l from Livraison l where l.client=:c";
                break;
            default:
                jpql="";
                
        }
        
        if(!jpql.equals(""))
        {
            Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
            query = query.setParameter("c", c);
            HistoriqueClient = query.getResultList();
            if (HistoriqueClient.isEmpty()) {
                HistoriqueClient=null;
            }
        }
        
        return HistoriqueClient;
    }
    
}
