/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Query;

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
    
    public static void RecupererInterventionsDuJour(Employe e){
        String jpql = "select i from Intervention i where i.employeAffecte =:e and i.dateDebut>:date1 and i.dateFin<:date2";
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c2.set(Calendar.HOUR_OF_DAY,23);
        c2.set(Calendar.MINUTE, 59);
        Timestamp date1 = new java.sql.Timestamp(c1.getTime().getTime());
        Timestamp date2 = new java.sql.Timestamp(c2.getTime().getTime());
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        query  = query.setParameter("date1", date1);
        query  = query.setParameter("date2", date2);
        query  = query.setParameter("e", e);
        if(e==null)
        {
            System.out.println("employee vide");
        }
        List<Intervention> results = query.getResultList();
        if (results.isEmpty()) {
            results = null;
        }
        if(results!=null)
        {
            for(Intervention ii : results)
            {
                System.out.println("aaaaaaaaaaaaaaaaaa");
            }
        }else{
            System.out.println("vide");
        }
        
        
        
        
       

    }
}
