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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        c1.set(Calendar.SECOND, 0);
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        c2.set(Calendar.SECOND, 59);
        Timestamp date1 = new Timestamp(c1.getTime().getTime());
        Timestamp date2 = new Timestamp(c2.getTime().getTime());
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        query = query.setParameter("date1", date1, TemporalType.TIMESTAMP);
        query = query.setParameter("date2", date2, TemporalType.TIMESTAMP);
        query = query.setParameter("e", e);
        List<Intervention> results = query.getResultList();
        if (results == null || results.isEmpty()) {
            return null;
        }
        return results;
        }
    
    public static List<Intervention> HistoriqueClientParType(String typeIntervention, Client c ){
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
    
    public static List<Intervention> HistoriqueClientParDate(String date, Client c ) throws ParseException{
        String jpql = "select i from Intervention i where i.client =:c and i.dateDebut>:date1 and i.dateFin<:date2";
        List<Intervention> HistoriqueClient=null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try{
            cal1.setTime(sdf.parse(date));
            cal2.setTime(sdf.parse(date));
            
        }catch(ParseException e){
            e.printStackTrace();
        }
        cal2.set(Calendar.HOUR_OF_DAY,23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND,59);
        Timestamp date1 = new Timestamp(cal1.getTime().getTime());
        Timestamp date2 = new Timestamp(cal2.getTime().getTime());
        Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
        query = query.setParameter("date1", date1,TemporalType.TIMESTAMP);
        query = query.setParameter("date2", date2,TemporalType.TIMESTAMP);
        query = query.setParameter("c", c);
        HistoriqueClient = query.getResultList();
        if (HistoriqueClient.isEmpty()) {
            HistoriqueClient=null;
        }
        return HistoriqueClient;
    }
    
    public static List<Intervention> HistoriqueClientParTypeEtDate(String typeIntervention, String date, Client c ) throws ParseException{
        String jpql;
        List<Intervention> HistoriqueClient=null;
                
        switch(typeIntervention)
        {
            case "incident":
                jpql="select i from Incident i where i.client=:c and i.dateDebut>:date1 and i.dateFin<:date2";
                break;
            case "animal":
                jpql="select a from Animal a where a.client=:c and a.dateDebut>:date1 and a.dateFin<:date2";
                break;
            case "livraison":
                jpql="select l from Livraison l where l.client=:c and l.dateDebut>:date1 and l.dateFin<:date2";
                break;
            default:
                jpql="";
                
        }
        
        
        if(!jpql.equals(""))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            try{
                cal1.setTime(sdf.parse(date));
                cal2.setTime(sdf.parse(date));

            }catch(ParseException e){
                e.printStackTrace();
            }
            cal2.set(Calendar.HOUR_OF_DAY,23);
            cal2.set(Calendar.MINUTE, 59);
            cal2.set(Calendar.SECOND,59);
            Timestamp date1 = new Timestamp(cal1.getTime().getTime());
            Timestamp date2 = new Timestamp(cal2.getTime().getTime());
            Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
            query = query.setParameter("date1", date1,TemporalType.TIMESTAMP);
            query = query.setParameter("date2", date2,TemporalType.TIMESTAMP);
            query = query.setParameter("c", c);
            HistoriqueClient = query.getResultList();
            if (HistoriqueClient.isEmpty()) {
                HistoriqueClient=null;
            }
        }
        
        if(HistoriqueClient!=null)
        {
            for(Intervention i : HistoriqueClient)
            {
                System.out.println(i);
            }
        }else{
            System.out.println("aaaaaaaaaaaaaaaa");
        }
        
        return HistoriqueClient;
    }
    
}
