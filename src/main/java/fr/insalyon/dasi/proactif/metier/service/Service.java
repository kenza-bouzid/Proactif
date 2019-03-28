/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import fr.insalyon.dasi.proactif.util.Notif;
import fr.insalyon.dasi.proactif.dao.ClientDao;
import fr.insalyon.dasi.proactif.dao.EmployeDao;
import fr.insalyon.dasi.proactif.dao.InterventionDao;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.dao.PersonneDao;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import fr.insalyon.dasi.proactif.metier.modele.Personne;
import fr.insalyon.dasi.proactif.util.DebugLogger;
import fr.insalyon.dasi.proactif.util.GeoTest;
import fr.insalyon.dasi.proactif.util.Message;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import javax.persistence.RollbackException;
/**
 * Classe représentant les services fournis par
 * l'application.
 * @author Kenza Bouzid
 * @author David Hamidovic
 */
public class Service {

    
    public Service() {
    }

    /**
     * Méthode qui permet d'inscrire le client.
     * Récupère un client passé en paramètre et recherche si son adresse mail 
     * est déjà dans la base de données Si l’email n’est pas utilisé la méthode
     * créé le client dans la base de données et envoie un mail pour confirmer
     * l’inscription.Si l’inscription échoue, la méthode envoie un mail pour
     * mettre signaler l'échec de l’inscription.
     * @param c le Client à inscrire
     * @return true si l'inscription est réussie, false
     * sinon
     * @throws ParseException 
     */
    public static boolean inscrireClient(Client c) throws ParseException {
        boolean reussie = false;
        try {
            JpaUtil.creerEntityManager();
            if ((Client) PersonneDao.findByEmail(c.getAdresseElec()) == null) {
                JpaUtil.ouvrirTransaction();
                c.setCoord(GeoTest.getLatLng(c.getAdresse()));
                ClientDao.persist(c);
                if ((Client) PersonneDao.findByEmailMdp(c.getAdresseElec(), c.getMdp()) != null) {
                    JpaUtil.validerTransaction();
                    DebugLogger.log("Inscription réussie!");
                    Message.envoyerMail("contact@proact.if", c.getAdresseElec(), "Bienvenue chez PROACT'IF", "Bonjour " + c.getPrenom() + ", nous vous confirmons votre inscription au service PROACT'IFG. Votre numéro de client est : " + c.getId());
                    reussie = true;
                } else {
                    JpaUtil.annulerTransaction();
                    DebugLogger.log("Echec de l'inscription!");
                    Message.envoyerMail("contact@proact.if", c.getAdresseElec(), "Bienvenue chez PROACT'IF", "Bonjour " + c.getPrenom() + ", votre inscription au service PROACT'IF a malencontreusement échoué...Merci de recommencer ultérieurement.");
                }
            } else {
                DebugLogger.log("Echec de l'inscription, cette adresse email correspond déjà à un autre utilisateur");
            }
            JpaUtil.fermerEntityManager();
        } catch (RollbackException e) {
            DebugLogger.log("Rollback Exception d'inscription", e);
        }
        return reussie;
    }

/**
 * Méthode qui permet d'envoyer un code pour une réinitialisation
 * de mot de passe. Vérifie dans la base de données si un client
 * ou un employé correspond au mail et au numéro de téléphone en
 * paramètre et envoie un code si c’est le cas. Renvoie le code
 * généré ou renvoie un code égal à zéro s'il n'y a pas de 
 * correspondance dans la base de données.
 * @param mail le mail associé au compte du client
 * @param num le numéro du client
 * @return le code ou 0 si cela a echoué
 */
    public static int envoyerCodeConfirmation(String mail, String num) {
        int code = 0;
        try {
            JpaUtil.creerEntityManager();
            if (PersonneDao.findByEmailNum(mail, num) != null) {
                int lower = 1000;
                int higher = 9999;
                code = (int) (Math.random() * (higher - lower)) + lower;
                Message.envoyerNotification(num, "Votre code de confirmation est: " + code);
                JpaUtil.fermerEntityManager();
            }
        } catch (Exception e) {
            DebugLogger.log("Attention exception lors de l'envoi du code de confirmation ", e);
        }
        return code;
    }

/**
 * Méthode qui permet de trouver une personne grâce
 * à son mail et son numéro. Cherche le client dans la 
 * base de données grâce à l’e-mail et le numéro 
 * de portable passés en paramètre. 
 * @param mail le mail associé au compte du client
 * @param num le numéro du client
 * @return La personne trouvée ou null si la requête ne
 * renvoie rien
 */
    public static Personne findPersonneByMailNum(String mail, String num) {
        Personne c = null;
        try {
            JpaUtil.creerEntityManager();
            c =  PersonneDao.findByEmailNum(mail, num);
            JpaUtil.fermerEntityManager();
        } catch (Exception e) {
            DebugLogger.log("Attention exception pour trouver le client (mail,num): ", e);
        }
        return c;
    }

/**
 * Méthode qui permet de changer le mot de passe d'une Personne
 * passé en paramètre. Récupère une personne et un String, 
 * puis change le mot de passe du string pour enfin 
 * le mettre à jour dans la base de données.
 * @param c la Personne qui veut une mise à jour de mot de
 * passe 
 * @param mdp le nouveau mot de passe de la personne    
 */    
    public static void updateMdp(Personne c, String mdp) {
        try {
            c.setMdp(mdp);
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            PersonneDao.merge(c);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (RollbackException e) {
            DebugLogger.log("Attention exception pour trouver le client (mail,num): ", e);
        }
    }

/**
 * Méthode permettant de récupérer une Personne à partir
 * de son mail et son mot de passe. Récupère les String e-mail
 * et mot de passe passés en paramètres. Si une personne
 * renvoie la personne, sinon renvoie null.
 * @param mail le mail de la Personne à connecter
 * @param mdp le mot de passe de la Personne à connecter
 * @return la Personne connecté ou null si personne ne correspond
 * dans la base de données
 */    
    public static Personne connexion(String mail, String mdp) {
        Personne p = null;
        try {
            JpaUtil.creerEntityManager();
            p = PersonneDao.findByEmailMdp(mail, mdp);
            JpaUtil.fermerEntityManager();
        } catch (Exception e) {
            DebugLogger.log("Attention exception lors de la connexion ", e);
        }
        if (p != null) {
            DebugLogger.log("Connexion réussie !");
        } else {
            DebugLogger.log("Echec de la connexion!");
        }
        return p;
    }

/**
 * Méthode permettant de faire une demande d'intervention. 
 * Associe le Client à l’Intervention et ajoute l’Intervention
 * au Client.Récupère la liste des employés disponibles, 
 * c’est à dire, pas en intervention et actuellement au travail. 
 * A partir de cette liste, recherche l’Employé le plus proche de 
 * chez le Client. Si l’Employé n’est pas vide, alors l’Employé
 * est mis en intervention,et on affecte l’Employé à l’Intervention.
 * Même si aucun Employé n’est trouvé on met à jour la base de
 * données, on garde ainsi les Interventions acceptées et 
 * refusées. On envoie une notification au client pour 
 * indiquer l’échec ou la réussite de la demande.
 * @param i l'Intervention
 * @param c le Client qui demande l'intervention
 * @return true si la demande a réussie, false sinon
 */
    public static boolean demandeIntervention(Intervention i, Client c) {

        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            i.setMonClient(c);
            c.addHistoInterventions(i);
            List<Employe> listeEmploye = EmployeDao.listerEmployesDisponibles(i.getTimeDebut());

            Double duree = Double.MAX_VALUE;

            Employe employeAffecte = null;

            if ((listeEmploye != null) && !(listeEmploye.isEmpty())) {

                for (Employe e : listeEmploye) {

                    Double dureeDistanceVelo = GeoTest.getTripDurationByBicycleInMinute(e.getCoord(), c.getCoord());

                    if (duree > dureeDistanceVelo) {
                        duree = dureeDistanceVelo;
                        employeAffecte = e;
                    }
                }
                if (employeAffecte != null) {
                    employeAffecte.setEstEnIntervention(true);
                    i.setEnCours(true);
                }
            }

            i.setEmployeAffecte(employeAffecte);
            InterventionDao.persist(i);
            ClientDao.merge(c);
            if (employeAffecte != null) {
                employeAffecte.getTabBord().add(i);
                EmployeDao.merge(employeAffecte);
                Notif.envoyerNotifEmploye(i);
                Message.envoyerNotification(c.getNumTel(), "Votre demande d'intervention numéro (#" + i.getNumIntervention() + ") a été prise en compte, vous receverez une notification vous informant de son déroulement une fois achevée. \n Nous vous remercions pour votre confiance! ");
            } else {
                Message.envoyerNotification(c.getNumTel(), "Votre demande d'intervention numéro (#" + i.getNumIntervention() + ") n'a malheureusement pas été approuvée, aucun emploi n'est disponible à l'instant, merci de réessayer ultérieurement! ");
            }
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();

        } catch (RollbackException e) {
            DebugLogger.log("Rollback Exception demande d'intervention", e);
        }
        return i.isEnCours();
    }

/**
 * Méthode permettant de clôturer une intervention en cours.
 * Ajoute un commentaire à l’intervention, indique grâce au
 * boolean si elle est réussie ou ratée et change l’état
 * "enCours" à faux. Change l’état de l’Employé pour indiquer
 * qu’il n’est plus en intervention. Met à jour la base de données.
 * @param i l'Intervention
 * @param commentaire le commentaire de l'employé sur l'intervention
 * @param Status le statut de l'intervention, réussi ou échoué
 */
    public static void cloturerIntervention(Intervention i, String commentaire, boolean Status) {
        if (i.isEnCours()) {
            try {
                i.setDateFin(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                i.setCommentaire(commentaire);
                i.setReussie(Status);
                i.setEnCours(false);
                Employe e = i.getEmployeAffecte();
                e.setEstEnIntervention(false);
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
                EmployeDao.merge(e);
                InterventionDao.merge(i);
                JpaUtil.validerTransaction();
                JpaUtil.fermerEntityManager();
                Notif.envoyerNotifClient(i);
            } catch (RollbackException e) {
                DebugLogger.log("Rollback Exception clôturer intervention", e);
            }
        } else {
            DebugLogger.log("Cette intervention ne peut pas être clôturée, celle ci a déjà été clôturé ou pas du tout entammé!");
        }
    }
/**
 * Méthode permettant d'initialiser 10 employés dans la base
 * de données. Initialise et persiste un à un dans la base 
 * de données les employés.
 * @throws ParseException 
 */
    public static void initialisationEmploye() throws ParseException {

        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            Employe e = new Employe("08:00:00", "18:00:00", "Mr", "Jeanne", "Julien", "1996-09-25", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0689743698", "emp1@gmail.com", "159");
            e.setCoord(GeoTest.getLatLng(e.getAdresse()));
            EmployeDao.persist(e);
            Employe e1 = new Employe("10:00:00", "14:00:00", "Mr", "Jeanne", "Bob", "1994-10-15", "37 Avenue Jean Capelle Est, Villeurbanne", "0669892316", "emp2@gmail.com", "175");
            e1.setCoord(GeoTest.getLatLng(e1.getAdresse()));
            EmployeDao.persist(e1);
            Employe e2 = new Employe("08:00:00", "23:45:00", "Mr", "Loic", "Laura", "1992-04-02", "61 Avenue Roger Salengro, Villeurbanne", "0689746315", "emp3@gmail.com", "195");
            e2.setCoord(GeoTest.getLatLng(e2.getAdresse()));
            EmployeDao.persist(e2);
            Employe e3 = new Employe("06:00:00", "23:59:00", "Mme", "Cavagna", "Lea", "1994-10-14", "15 Avenue Roger Salengro, Villeurbanne", "0634897512", "emp4@gmail.com", "164");
            e3.setCoord(GeoTest.getLatLng(e3.getAdresse()));
            EmployeDao.persist(e3);
            Employe e4 = new Employe("11:00:00", "23:59:00", "Mme", "Paquet", "Louise", "1998-04-01", "20 Avenue Albert Einstein, Villeurbanne", "03987463158", "emp5@gmail.com", "179");
            e4.setCoord(GeoTest.getLatLng(e4.getAdresse()));
            EmployeDao.persist(e4);
            Employe e5 = new Employe("16:30:00", "23:59:00", "Mr", "Genest", "Marc", "1998-06-03", "37 Avenue Jean Capelle Est, Villeurbanne", "03698745216", "emp6@gmail.com", "167");
            e5.setCoord(GeoTest.getLatLng(e5.getAdresse()));
            EmployeDao.persist(e5);
            Employe e6 = new Employe("08:00:00", "02:00:00", "Mr", "Lelouard", "Pierre", "1997-09-03", "20 Avenue Jean Capelle Ouest, Villeurbanne", "07956314289", "emp7@gmail.com", "495");
            e6.setCoord(GeoTest.getLatLng(e6.getAdresse()));
            EmployeDao.persist(e6);
            Employe e7 = new Employe("10:00:00", "23:59:00", "Mr", "Hamidovic", "Martin", "1994-07-01", "7 Avenue Jean Capelle Est, Villeurbanne", "06479515697", "emp8@gmail.com", "369");
            e7.setCoord(GeoTest.getLatLng(e7.getAdresse()));
            EmployeDao.persist(e7);
            Employe e8 = new Employe("16:00:00", "23:59:00", "Mr", "Scotto", "David", "1970-04-27", "21 Avenue Albert Einstein, Villeurbanne", "02698756325", "emp9@gmail.com", "258");
            e8.setCoord(GeoTest.getLatLng(e8.getAdresse()));
            EmployeDao.persist(e8);
            Employe e9 = new Employe("12:00:00", "23:00:00", "Mr", "Tandereau", "Nathan", "1964-05-07", "69 Avenue Roger Salengro, Villeurbanne", "06479951463", "emp10@gmail.com", "147");
            e9.setCoord(GeoTest.getLatLng(e9.getAdresse()));
            EmployeDao.persist(e9);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (RollbackException e) {
            DebugLogger.log("RollBack Exception lors de l'initialisation", e);
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
        }
    }

/**
 * Méthode permettant d'initialiser 4 client dans la base
 * de données. Initialise et persiste un à un dans la base 
 * de données les clients.
 * @throws ParseException 
 */    
    public static void initialisationClient() throws ParseException {
        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            Client c = new Client("Mr", "Jeanne", "Patrick", "1996-09-25", "15 Avenue Jean Capelle Ouest, Villeurbanne", "0689743698", "cli1@gmail.com", "298");
            c.setCoord(GeoTest.getLatLng(c.getAdresse()));
            ClientDao.persist(c);
            c = new Client("Mr", "Laporte", "Bob", "1997-10-15", "20 Avenue Jean Capelle Est, Villeurbanne", "0669892316", "cli2@gmail.com", "164");
            c.setCoord(GeoTest.getLatLng(c.getAdresse()));
            ClientDao.persist(c);
            c = new Client("Mr", "Loic", "Laura", "1996-04-02", "16 Avenue Roger Salengro, Villeurbanne", "0689746315", "cli3@gmail.com", "123");
            c.setCoord(GeoTest.getLatLng(c.getAdresse()));
            ClientDao.persist(c);
            c = new Client("Mme", "Cavagna", "Lea", "1993-10-14", "21 Avenue Jean Capelle Ouest, Villeurbanne", "0634897512", "cli4@gmail.com", "145");
            c.setCoord(GeoTest.getLatLng(c.getAdresse()));
            ClientDao.persist(c);

            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (RollbackException e) {
            DebugLogger.log("RollBack Exception lors de l'initialisation", e);
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
        }
    }
/**
 * Méthode permettant d'avoir toutes les interventions d'aujourd'hui
 * d'un employé. Fait une requête jpql pour récupérer les
 * interventions du jour d’un employé donné. Renvoie null 
 * si la requête n’a rien récupéré et renvoie la liste sinon.
 * @param e l'Employé
 * @return la liste d'interventions ou null si la liste est
 * vide
 */
    public static List<Intervention> RecupererInterventionsDuJour(Employe e) {
        List<Intervention> interventionDuJour = null;
        try {
            JpaUtil.creerEntityManager();
            interventionDuJour = InterventionDao.RecupererInterventionsDuJour(e);
            JpaUtil.fermerEntityManager();
        } catch (Exception ex) {
            DebugLogger.log("Attentionn exception lors de la récupération des interventions du jour ", ex);
        }

        return interventionDuJour;
    }

/**
 * Méthode permettant de récuperer les interventions
 * d'un client en fonction du type d'intervention.
 * Récupère le String en paramètre pour déterminer quelle
 * requête jpql effectuer. Effectue la requête
 * correspondante au String. Renvoie null si la
 * requête est vide ou si le String ne correspond à 
 * aucun type d’intervention, renvoie la liste
 * d’intervention sinon. 
 * @param type le type d'Intervention
 * @param c le Client
 * @return la liste d'interventions ou null si la liste
 * est vide
 */
    public static List<Intervention> HistoriqueClientParType(String type, Client c) {
        List<Intervention> historiqueClient = null;
        try {

            JpaUtil.creerEntityManager();
            historiqueClient = InterventionDao.HistoriqueClientParType(type, c);
            JpaUtil.fermerEntityManager();
        } catch (Exception ex) {
            DebugLogger.log("Attentionn exception lors de la récupération de l'histo client trié par type ", ex);
        }

        return historiqueClient;
    }

/**
 * Méthode permettant de récuperer les interventions
 * d'un client en fonction de la date passé en 
 * paramètre. Effectue une requête jpql sélectionnant
 * les interventions le jour correspondant au String
 * en paramètre. Renvoie null si la requête est vide
 * ou si le String ne correspond à aucun type
 * d’intervention, sinon renvoie la liste d’intervention.
 * @param date le jour au format dd/MM/yyyy
 * @param c le Client
 * @return la liste d'intervention ou null si la 
 * liste est vide
 * @throws ParseException 
 */    
    public static List<Intervention> HistoriqueClientParDate(String date, Client c) throws ParseException {
        List<Intervention> historiqueClient = null;
        try {
            JpaUtil.creerEntityManager();
            historiqueClient = InterventionDao.HistoriqueClientParDate(date, c);
            JpaUtil.fermerEntityManager();
        } catch (ParseException e) {
            DebugLogger.log("Attentionn exception lors de la récupération de l'histo client trié par type ", e);
        }
        return historiqueClient;
    }
/**
 * Méthode permettant de récuperer les interventions
 * d'un client en fonction de la date passé en 
 * paramètre et du type passé en paramètre.
 * Récupère les Strings en paramètre correspondants
 * à la date et au type d’intervention pour déterminer
 * quelle requête jpql effectuer Effectue la requête
 * Renvoie null si la requête est vide ou si le String
 * ne correspond à aucun type d’intervention,
 * sinon renvoie la liste d’intervention.
 * @param type le type de l'intervention
 * @param date le jour au format dd/MM/yyyy
 * @param c le Client
 * @return la liste d'intervention ou null si la 
 * liste est vide
 * @throws ParseException 
 */
    public static List<Intervention> HistoriqueClientParTypeEtDate(String type, String date, Client c) throws ParseException {
        List<Intervention> historiqueClient = null;
        try {
            JpaUtil.creerEntityManager();
            historiqueClient = InterventionDao.HistoriqueClientParTypeEtDate(type, date, c);
            JpaUtil.fermerEntityManager();
        } catch (ParseException e) {
            DebugLogger.log("Attentionn exception lors de la récupération de l'histo client trié par type et par date ", e);
        }
        return historiqueClient;
    }
/**
 * Méthode permettant de mettre à jour les 
 * infos d'une Personne.Récupère les paramètres
 * en entrée et modifie la personne. Change la
 * personne correspondante dans la base de données
 * @param civilite la civilité de la Personne
 * @param nom le nom de la Personne
 * @param prenom le prénom de la Personne
 * @param dateNaissance la date de naissance 
 * de la Personne
 * @param adresse l'adresse postale de la Personne
 * @param numTel le numéro de téléphone de la Personne
 * @param adresseElec l'adresse mail de la Personne
 * @param p la Personne
 */
    public static void updateProfil(String civilite, String nom, String prenom, String dateNaissance, String adresse, String numTel, String adresseElec, Personne p) {

        try {
            p.setCivilite(civilite);
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setDateNaissance(java.sql.Date.valueOf(dateNaissance));
            p.setAdresse(adresse);
            p.setCoord(GeoTest.getLatLng(adresse));
            p.setNumTel(numTel);
            p.setAdresseElec(adresseElec);
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            PersonneDao.merge(p);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (RollbackException e) {
            DebugLogger.log("RollBack Exception lors de la mise a jour du profil", e);
        }
    }

/**
 * Méthode permettant de changer les horaires de travail
 * d'un Employé. Récupère les paramètres en entrée et
 * modifie l’employé. Change l’employé correspondante
 * dans la base de données
 * @param dateDebut l'heure de début au format hh:mm:ss
 * @param dateFin l'heure de fin au format hh:mm:ss
 * @param e l'employé
 */    
    public static void updateHoraire(String dateDebut, String dateFin, Employe e) {
        try{
            e.setDebutTravail(java.sql.Time.valueOf(dateDebut));
            e.setDebutTravail(java.sql.Time.valueOf(dateFin));
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            EmployeDao.merge(e);
            
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        }
        catch (RollbackException ex)
        {
            DebugLogger.log("RollBack Exception lors des horaires de travail", ex);
        }
    }

}
