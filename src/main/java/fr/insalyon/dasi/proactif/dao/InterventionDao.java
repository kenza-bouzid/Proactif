/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import fr.insalyon.dasi.proactif.util.DebugLogger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * Classe intéragissant avec la base de donnés pour 
 * les objets de type Intervention.
 * @author Kenza Bouzid
 * @author David Hamidovic
 */
public class InterventionDao {

 /**
 * Obtient un entity manager et persist l'Intervention.
 * @param i l'Intervention à persister.
 */
    public static void persist(Intervention i) {
        JpaUtil.obtenirEntityManager().persist(i);
    }

 /**
 * Obtient un entity manager et merge l'Intervention.
 * @param i l'Intervention qu'on merge.
 * @return On retourne la nouvelle instance d'Intervention
 * qui vient d'être merge.
 */
    public static Intervention merge(Intervention i) {
        return JpaUtil.obtenirEntityManager().merge(i);
    }
/**
 * La méthode vise à obtenir toutes les interventions d'aurjourd'hui. 
 * On fait une requête jpql pour récuperer les interventions du jour
 * concernant l'Employe e en paramètre. De ce fait on crée deux 
 * Timestamp un pour indiquer le commencement du jour et l'autre pour 
 * indiquer la fin de celui-ci et on utilise ces objets pour faire la 
 * requête. Si rien n'est trouvé on retourne null et la liste sinon.
 * @param e L'Employe dont on veut les interventions du jour
 * @return La liste des interventions du jour ou null si rien est 
 * trouvé
 */
    public static List<Intervention> RecupererInterventionsDuJour(Employe e) {
        List<Intervention> results = null;
        try {
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
            results = query.getResultList();

        } catch (Exception ex) {
            DebugLogger.log("Attention Exception", ex);
        }

        if (results == null || results.isEmpty()) {
            return null;
        }
        return results;
    }
/**
 * La méthode récupère la liste des Interventions d'un client d'un type 
 * donné. La méthode récupère le String typeIntervention et séléctionne
 * une requête jpql suivant sa valeur. Elle effectue cette requête et 
 * fait un return.
 * @param typeIntervention String correspondant au type d'Intervention voulu
 * @param c Le Client dont on veut les interventions
 * @return La liste d'intervention d'un type donné, null si la liste est vide
 * ou le String typeIntervention n'est pas pertinant
 */
    public static List<Intervention> HistoriqueClientParType(String typeIntervention, Client c) {
        List<Intervention> HistoriqueClient = null;
        try {
            String jpql;
            switch (typeIntervention) {
                case "incident":
                    jpql = "select i from Incident i where i.monClient=:c";
                    break;
                case "animal":
                    jpql = "select a from Animal a where a.monClient=:c";
                    break;
                case "livraison":
                    jpql = "select l from Livraison l where l.monClient=:c";
                    break;
                default:
                    jpql = "";
            }
            if (!jpql.equals("")) {
                Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
                query = query.setParameter("c", c);
                HistoriqueClient = query.getResultList();
                if (HistoriqueClient.isEmpty()) {
                    HistoriqueClient = null;
                }
            }
        } catch (Exception ex) {
            DebugLogger.log("Attention Exception", ex);
        }
        return HistoriqueClient;
    }
/**
 La méthode récupère la liste des Interventions d'un client d'un type 
 * donné et d'une date donné. La méthode récupère le String typeIntervention et
 * séléctionne une requête jpql suivant sa valeur. On crée ensuite deux
 * Timestamp un pour indiquer le commencement du jour et l'autre pour 
 * indiquer la fin de celui-ci  à partir du String date et on utilise ces
 * objets pour effectuer la requête dans la base de données. 
 * @param typeIntervention String correspondant au type d'Intervention voulu
 * @param date String correspondant a la date voulu pour la recherche des Interventions
 * @param c Le Client dont on veut les interventions
 * @return La liste d'intervention d'un type donné et d'une date donné, null si la liste est vide
 * ou le String typeIntervention n'est pas pertinant
 * @throws ParseException 
 */
    public static List<Intervention> HistoriqueClientParTypeEtDate(String typeIntervention, String date, Client c) throws ParseException {

        List<Intervention> HistoriqueClient = null;
        try {
            String jpql;
            switch (typeIntervention) {
                case "incident":
                    jpql = "select i from Incident i where i.monClient=:c and i.dateDebut>:date1 and i.dateFin<:date2";
                    break;
                case "animal":
                    jpql = "select a from Animal a where a.monClient=:c and a.dateDebut>:date1 and a.dateFin<:date2";
                    break;
                case "livraison":
                    jpql = "select l from Livraison l where l.monClient=:c and l.dateDebut>:date1 and l.dateFin<:date2";
                    break;
                case "intervention":
                    jpql = "select i from Intervention i where i.monClient =:c and i.dateDebut>:date1 and i.dateFin<:date2";
                    break;
                default:
                    jpql = "";
            }
            if (!jpql.equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                try {
                    cal1.setTime(sdf.parse(date));
                    cal2.setTime(sdf.parse(date));

                } catch (ParseException e) {
                    DebugLogger.log("Attention ParseException", e);
                }
                cal2.set(Calendar.HOUR_OF_DAY, 23);
                cal2.set(Calendar.MINUTE, 59);
                cal2.set(Calendar.SECOND, 59);
                Timestamp date1 = new Timestamp(cal1.getTime().getTime());
                Timestamp date2 = new Timestamp(cal2.getTime().getTime());
                Query query = JpaUtil.obtenirEntityManager().createQuery(jpql);
                query = query.setParameter("date1", date1, TemporalType.TIMESTAMP);
                query = query.setParameter("date2", date2, TemporalType.TIMESTAMP);
                query = query.setParameter("c", c);
                HistoriqueClient = query.getResultList();
                if (HistoriqueClient.isEmpty()) {
                    HistoriqueClient = null;
                }
            }
        } catch (Exception ex) {
            DebugLogger.log("Attention Exception", ex);
        }
        return HistoriqueClient;
    }

    public static List<Intervention> HistoriqueClientParDate(String date, Client c) throws ParseException {
        return HistoriqueClientParTypeEtDate("intervention", date, c);
    }
}
