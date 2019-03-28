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
 * Classe intéragissant avec la base de donnés pour 
 * les objets de type Employe.
 * @author Kenza Bouzid
 * @author David Hamidovic
 */
public class EmployeDao {

 /**
 * Obtient un entity manager et persist l'Employe.
 * @param e l'Employe à persister
 */
    public static void persist(Employe e) {
        JpaUtil.obtenirEntityManager().persist(e);
    }
/**
 * La méthode vise à obtenir tout les employés disponibles
 * à l'heure indiquer par le paramètre date. On fait une requête jpql en 
 * cherchant les employés qui ne sont pas en intervention et étant au travail 
 * lors de l'heure de la demande. Si personne n'est trouvé la liste vaut null.
 * @param date l'heure à laquelle les employés doivent être disponible
 * @return La liste des employés s'il y a au moins un employé
 * disponible, null sinon.
 */
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
 /**
 * Obtient un entity manager et merge l'Employe.
 * @param e l'Employe qu'on merge
 */
    public static Employe merge(Employe e) {
        return JpaUtil.obtenirEntityManager().merge(e);
    }
}
