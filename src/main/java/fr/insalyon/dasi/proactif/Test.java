/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif;

import fr.insalyon.dasi.proactif.dao.InterventionDao;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.modele.Animal;
import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Employe;
import fr.insalyon.dasi.proactif.metier.modele.Incident;
import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import fr.insalyon.dasi.proactif.metier.modele.Livraison;
import fr.insalyon.dasi.proactif.metier.modele.Personne;
import fr.insalyon.dasi.proactif.metier.service.Service;
import fr.insalyon.dasi.proactif.util.DebugLogger;
import fr.insalyon.dasi.proactif.util.Saisie;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author dhamidovic
 */
public class Test {

    public static Client clientCourant;
    public static Employe employeConnecte;

    public static void main(String args[]) throws ParseException {

        JpaUtil.init();

        menuPrincipal(); 

        
        JpaUtil.destroy();

    }

    public static void menuPrincipal() throws ParseException {
        /*------------Initialisation-------------*/
        System.out.println("--Bienvenue sur Proactif--");
        System.out.println("Initialisation de l'application");
        Service.initialisationEmploye();
        Service.initialisationClient();
        
        int choix = 0;
        System.out.println("--Bienvenue sur la demo Proact'If version Beta--");
        System.out.println("Nous vous invitons à tester nos différents services !");
        System.out.println("Pour une version de tests interactive: tapez 1 ");
        System.out.println("Pour un test global de tous les services à la fois : tapez 2");
        System.out.println("Pour quitter le programme : tapez 3");
        choix = Saisie.lireInteger("Inqiquer votre choix: ", Arrays.asList(1, 2, 3));
        switch (choix) {
            case 1:
                testInteractif();
                break;
            case 2:
                testGlobal(); 
                break;
            case 3:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public static void testInteractif() throws ParseException {
        int choix = 0;
        System.out.println("--Bien jouer, vous avez choisi la version interactive--");
        System.out.println("Veuillez choir le service que vous voulez tester!");
        System.out.println("Pour tester le service d'inscription: tapez 1 ");
        System.out.println("Pour tester le service de connexion : tapez 2");
        System.out.println("Pour quitter le programme : tapez 3 ");
        choix = Saisie.lireInteger("Inqiquer votre choix: ", Arrays.asList(1, 2, 3));
        switch (choix) {
            case 1:
                testInscription(); 
                break;
            case 2:
                testConnexion();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                break;
        }
    }
    public static void testGlobal() throws ParseException{
        
        Saisie.lireChaine("Inscription de Dupont");
        Client c = new Client("M", "Dupont", "Grégoire", "1998-06-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0658974316", "Dupont@gmail.com", "123");
        Service.inscrireClient(c);
        Saisie.lireChaine("Inscription d'Asimov");
        Client c1 = new Client("M", "Asimov", "Isaac", "1998-06-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0699123422", "Asimov@gmail.com", "123");
        boolean inscription1 = Service.inscrireClient(c1);
        Saisie.lireChaine("Inscription de Dick");
        Client c2 = new Client("M", "Dick", "Philip", "1982-06-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0611223344", "Dick@gmail.com", "123");
        Service.inscrireClient(c2);
        Saisie.lireChaine("Inscription de Kubrick");
        Client c3 = new Client("M", "Kubrick", "Stanley", "1954-03-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0611223345", "Kubrick@gmail.com", "123");
        Service.inscrireClient(c3);
        Saisie.lireChaine("Inscription de Zemeckis");
        Client c4 = new Client("M", "Zemeckis", "Robert", "1932-03-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0612223345", "Zemeckis@gmail.com", "123");
        Service.inscrireClient(c4);
        Saisie.lireChaine("Inscription de Baranger");
        Client c5 = new Client("M", "Baranger", "François", "1978-03-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0613223345", "Baranger@gmail.com", "123");
        Service.inscrireClient(c5);
        Saisie.lireChaine("Inscription d'Astier");
        Client c6 = new Client("M", "Astier", "Alexandre", "1969-03-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0613223945", "Astier@gmail.com", "123");
        Service.inscrireClient(c6);
        Saisie.lireChaine("Inscription de LeRobot");
        Client c7 = new Client("M", "LeRobot", "Wall-E", "2011-03-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0613423945", "LeRobot@gmail.com", "123");
        Service.inscrireClient(c7);
        Saisie.lireChaine("Inscription de Vert");
        Client c8 = new Client("M", "Vert", "Thé", "2011-03-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0613424945", "Vert@gmail.com", "123");
        Service.inscrireClient(c8);
        Saisie.lireChaine("Inscription de Vert (son frere qui a la meme adresse mail)");
        Client c9 = new Client("M", "Vert", "Soleil", "2011-03-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0613424945", "Vert@gmail.com", "123");
        Service.inscrireClient(c9);
        Saisie.lireChaine("Tentative de connexion avec mauvais mdp");
        
   
        Service.connexion("Dupont@gmail.com", "122");
        Saisie.lireChaine("Tentative de connexion avec le bon mdp");
        Personne p = Service.connexion("Dupont@gmail.com", "123");
        
        c=(Client)p;
        Incident i1 = new Incident("Problèmes de fantôme");
        Saisie.lireChaine("demande intervention");
        Service.demandeIntervention(i1, c);
        Saisie.lireChaine("clotûrer intervention");
        Service.cloturerIntervention(i1,"c'est tout bon", true);
        Saisie.lireChaine("demande intervention");
        Animal i2 = new Animal("pug", "Problèmes de santé");
        Service.demandeIntervention(i2,c);
        Saisie.lireChaine("clotûrer intervention");
        Service.cloturerIntervention(i2, "Votre chien est mort", false);
        Saisie.lireChaine("demande intervention 1");
        Animal i3 = new Animal("pug", "Promenez mon chien svp");
        Service.demandeIntervention(i3,c1);
        Saisie.lireChaine("demande intervention 2");
        Livraison i4= new Livraison("colis","amazon","Récuperer mon colis svp");
        Service.demandeIntervention(i4, c);
        Saisie.lireChaine("clotûrer intervention 2");
        Service.cloturerIntervention(i4,"Tout est bon",true);
        Saisie.lireChaine("clotûrer intervention 1");
        Service.cloturerIntervention(i3, "Votre chien n'est pas mort", true);
        Saisie.lireChaine("demande intervention");
        Livraison i5 = new Livraison("colis","DPD","Livraison");
        Service.demandeIntervention(i5, c);
        Saisie.lireChaine("clotûrer intervention");
        Service.cloturerIntervention(i5,"Tout est ni-ckel",true);
        
        Incident i10 = new Incident("Problèmes de fantôme");
        Incident i11 = new Incident("Problèmes de fantôme");
        Incident i12 = new Incident("Problèmes de fantôme");
        Incident i13 = new Incident("Problèmes de fantôme");
        Incident i14 = new Incident("Problèmes de fantôme");
        Incident i15 = new Incident("Problèmes de fantôme");
        Incident i16 = new Incident("Problèmes de fantôme");
        Incident i17 = new Incident("Problèmes de fantôme");
        Incident i18 = new Incident("Problèmes de fantôme");
        Incident i19 = new Incident("Problèmes de fantôme");
        Incident i20 = new Incident("Problèmes de fantôme");
        
        Saisie.lireChaine("demande intervention");
        Service.demandeIntervention(i10, c);
        Saisie.lireChaine("demande intervention");
        Service.demandeIntervention(i11, c1);
        Saisie.lireChaine("demande intervention");
        Service.demandeIntervention(i12, c2);
        Saisie.lireChaine("demande intervention");
        Service.demandeIntervention(i13, c3);
        Saisie.lireChaine("demande intervention");
        Service.demandeIntervention(i14, c4);
        Saisie.lireChaine("demande intervention");
        Service.demandeIntervention(i15, c5);
        Saisie.lireChaine("demande intervention");
        Service.demandeIntervention(i16, c6);
        Saisie.lireChaine("demande intervention");
        Service.demandeIntervention(i17, c7);
        Saisie.lireChaine("demande intervention");
        Service.demandeIntervention(i18, c8);
        Employe e10 = i1.getEmployeAffecte();
        //Personne p10 = Service.connexion("emp6@gmail.com", "167");
        //Employe e10 = (Employe)p10;
        List<Intervention> intervention = Service.RecupererInterventionsDuJour(e10);
        Saisie.lireChaine("récuperer intervention du jour de Employe 6");
        if(intervention!=null)
        {
            for(Intervention iii : intervention)
            {
              System.out.println("Intervention :  "+iii);  
            }
            
        }else{
            System.out.println("dadada2");
        }
        Saisie.lireChaine("récuperer intervention incident de Dupont");
        intervention =Service.HistoriqueClientParType("incident",c);
        
        if(intervention!=null)
        {
            for(Intervention iii : intervention)
            {
              System.out.println("Intervention :  "+iii);  
            }
            
        }else{
            System.out.println("Requête vide");
        }
        
        Saisie.lireChaine("récuperer intervention livraison de Dupont");
        intervention =Service.HistoriqueClientParType("livraison",c);
        
        if(intervention!=null)
        {
            for(Intervention iii : intervention)
            {
              System.out.println("Intervention :  "+iii);  
            }
            
        }else{
            System.out.println("Requête vide");
        }
        
        Saisie.lireChaine("récuperer intervention animal de Dupont");
        intervention =Service.HistoriqueClientParType("animal",c);
        
        if(intervention!=null)
        {
            for(Intervention iii : intervention)
            {
              System.out.println("Intervention :  "+iii);  
            }
            
        }else{
            System.out.println("Requête vide");
        }
        
        Saisie.lireChaine("récuperer interventions d'aujourd'hui de Dupont");
        intervention =Service.HistoriqueClientParDate("28/03/2019",c);
        
        if(intervention!=null)
        {
            for(Intervention iii : intervention)
            {
              System.out.println("Intervention :  "+iii);  
            }
            
        }else{
            System.out.println("Requête vide");
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try {
            cal1.setTime(sdf.parse("22/03/2019"));
            cal2.setTime(sdf.parse("22/03/2019"));

        } catch (ParseException e) {
            DebugLogger.log("Attention ParseException", e);
        }
        cal2.set(Calendar.HOUR_OF_DAY, 15);
        cal2.set(Calendar.MINUTE, 33);
        cal2.set(Calendar.SECOND, 23);
        cal1.set(Calendar.HOUR_OF_DAY, 13);
        cal1.set(Calendar.MINUTE, 23);
        cal1.set(Calendar.SECOND, 00);
        Timestamp date1 = new Timestamp(cal1.getTime().getTime());
        Timestamp date2 = new Timestamp(cal2.getTime().getTime());
        i1.setDateDebut(date1);
        i1.setDateFin(date2);
        Intervention itmp;
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        itmp = InterventionDao.merge(i1);
        i1=(Incident)itmp;
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        
        Saisie.lireChaine("récuperer interventions d'aujourd'hui de Dupont (Intervention i1 placé le 22/03/2019)");
        intervention =Service.HistoriqueClientParDate("28/03/2019",c);

        if(intervention!=null)
        {
            for(Intervention iii : intervention)
            {
              System.out.println("Intervention :  "+iii);  
            }

        }else{
            System.out.println("Requête vide");
        }

        Saisie.lireChaine("récuperer interventions de Dupont 22/03/2019");
        intervention =Service.HistoriqueClientParDate("22/03/2019",c);

        if(intervention!=null)
        {
            for(Intervention iii : intervention)
            {
              System.out.println("Intervention :  "+iii);  
            }

        }else{
            System.out.println("Requête vide");
        }
        
        Saisie.lireChaine("récuperer interventions animal du  22/03/2019");
        intervention =Service.HistoriqueClientParTypeEtDate("animal","22/03/2019",c);

        if(intervention!=null)
        {
            for(Intervention iii : intervention)
            {
              System.out.println("Intervention :  "+iii);  
            }

        }else{
            System.out.println("Requête vide");
        }
        
        Saisie.lireChaine("récuperer interventions de Dupont incident du  22/03/2019");
        intervention =Service.HistoriqueClientParTypeEtDate("incident","22/03/2019",c);

        if(intervention!=null)
        {
            for(Intervention iii : intervention)
            {
              System.out.println("Intervention :  "+iii);  
            }

        }else{
            System.out.println("Requête vide");
        }
        
        Saisie.lireChaine("Données de Dupont");
        System.out.println("id=" + c.getId() + ", civilite=" + c.getCivilite() + ", nom=" + c.getNom() + ", prenom=" + c.getPrenom() + ", dateNaissance=" + c.getDateNaissance() + ", adresse=" + c.getAdresse() + ", coord=" + c.getCoord() + ", numTel=" + c.getNumTel() + ", adresseElec=" + c.getAdresseElec() + ", mdp=" + c.getMdp());
    
        Service.updateProfil("Mme", "Dupont", "Patrick", "1993-03-02", "3 rue des pommiers, Joue-Les-Tours", "0799234213", "Dupontpont@gmail.com", c);
        
        Personne pp = Service.connexion("Dupontpont@gmail.com","123");
        c=(Client)pp;
        
        Saisie.lireChaine("Données de Dupont après modif2");
        System.out.println("id=" + c.getId() + ", civilite=" + c.getCivilite() + ", nom=" + c.getNom() + ", prenom=" + c.getPrenom() + ", dateNaissance=" + c.getDateNaissance() + ", adresse=" + c.getAdresse() + ", coord=" + c.getCoord() + ", numTel=" + c.getNumTel() + ", adresseElec=" + c.getAdresseElec() + ", mdp=" + c.getMdp());
        /*
            
        /*
        Personne p = Service.connexion("test@gmail.com", "469");
        if (p instanceof Client) {
            System.out.println((Client) p);
        }
        Incident i = new Incident("mon voisin m'a signalé un incident");
        Animal a = new Animal("pug", "Il faudrait sortir Bruno au park en face de chez moi");
        Livraison l = new Livraison("colis", "DPD", "blablabla");
        Incident i2 = new Incident("blabla2");
        System.out.println(Service.demandeIntervention(i, c));
        System.out.println(Service.demandeIntervention(a, c));
        System.out.println(Service.demandeIntervention(l, c));

        Service.cloturerIntervention(i, "Tout bon", true);

        Service.cloturerIntervention(a, "Tout bon", true);

        Service.cloturerIntervention(l, "Tout bon", false);

        System.out.println(Service.demandeIntervention(i2, c));
        Service.cloturerIntervention(i2, "Tout bon", true);

        Client e = (Client) Service.connexion("test@gmail.com", "469");

        System.out.println(e);
        //List<Intervention> ll = Service.HistoriqueClientParTypeEtDate("livraison", "28/03/2019", e);
        List<Intervention> ll = Service.HistoriqueClientParDate("28/03/2019", e);
        ll.forEach((m) -> {
            System.out.println(m);
        });

        System.out.println("fini histo");

        List<Intervention> lll = Service.RecupererInterventionsDuJour((Employe) Service.connexion("emp1@gmail.com", "159"));
        lll.forEach((m) -> {
            System.out.println(m);
        });*/
    }

    public static void testInscription() throws ParseException {

        /*------------Test Inscription-------------*/
        System.out.println("----Inscrivez vous----");
        System.out.println("----- Veuillez saisir vos données personnelles -----");
        String civilite = Saisie.lireChaine("Civilité : ");
        String prenom = Saisie.lireChaine("Prenom : ");
        String nom = Saisie.lireChaine("Nom : ");
        String dateNaiss = Saisie.lireChaine("Date de naissance : (yyyy-mm-dd) : ");
        String adresse = Saisie.lireChaine("Adresse : ");
        String numTel = Saisie.lireChaine("Numéro de téléphone : ");
        String email = Saisie.lireChaine("Adresse email : ");
        String mdp = Saisie.lireChaine("Mot de passe : ");

        Client courant = new Client(civilite, nom, prenom, dateNaiss, adresse, numTel, email, mdp);
        Service.inscrireClient(courant);

        /*------------Test Echec Inscription mail déja utilisé -------------*/
        System.out.println("Test echec inscription: mail déja utilisé par un autre client ");
        Client c1 = new Client("M", "Dupont", "Grégoire", "1998-06-02", "7 Avenue Jean Capelle Ouest, Villeurbanne", "0658974316", "test@gmail.com", "123");
        Client c2 = new Client("Mm", "Dupont", "Grégoire", "1998-06-02", "8 Avenue Jean Capelle Ouest, Villeurbanne", "0658974316", "test@gmail.com", "123");
        Service.inscrireClient(c1);
        Service.inscrireClient(c2);

    }

    public static void testConnexion() {

        /*------------Test Connexion -------------*/
        String email;
        String mdp;
        String num;
        System.out.println("----Connectez vous----");
        email = Saisie.lireChaine("Adresse email : ");
        int oublie = Saisie.lireInteger("Tapez 1 si vous avez oublie votre mdp 0 sinon : ", Arrays.asList(0, 1));
        if (oublie == 0) {
            mdp = Saisie.lireChaine("Mot de passe : ");
        } else {
            num = Saisie.lireChaine("Veuillez saisir votre numero de telephone \n vous receverez un message pour confirmer votre identite: ");
            Client c = Service.findClient(email, num);
            if (c != null) {
                System.out.println("Vous ne faites pas partie de nos clients, vous ne pouvez pas modifier votre mot de passe");
                return;
            } else {
                int code = Service.envoyerCodeConfirmation(email, num);
                int code2 = Saisie.lireInteger("Veuillez saisr le code que vous avez recu: ");
                if (code == code2) {
                    mdp = Saisie.lireChaine("Veuillez saisir votre nouveau mot de passe: ");
                    Service.updateMdp(c, mdp);
                }
                System.out.println("----Connectez vous----");
                email = Saisie.lireChaine("Adresse email : ");
                mdp = Saisie.lireChaine("Mot de passe : ");
            }
        }

        Personne p = Service.connexion(email, mdp);
        if (p instanceof Client) {
            Client c = (Client) p;
            System.out.println("Bienvenue sur votre profil Proactif");
            acceuilClient(c);
        }
        if (p instanceof Employe) {
            Employe e = (Employe) p;
            acceuilEmploye(e);
        }
    }

    public static void acceuilClient(Client c) {
        int sousmenu = 0;
        boolean arret = false;
        while (!arret) {
            System.out.println("--Bienvenue sur Proactif--");
            System.out.println("Pour demander une interevntion : tapez 1");
            System.out.println("Pour consulter l'historique de vos interventions : tapez 2");
            System.out.println("Pour consulter votre profil : tapez 3");
            System.out.println("Pour vous déconnecter: tapez 4");
            sousmenu = Saisie.lireInteger("Inqiquer votre choix: ", Arrays.asList(1, 2, 3, 4));
            switch (sousmenu) {
                case 1:
                    menuIntervention(c);
                    break;
                case 2:
                    afficherProfil(c);
                    break;
                case 3:
                    afficherHisto(c);
                    break;
                case 4:
                    
                    break;
                default:
                    break;
            }
        }
    }

    public static void menuIntervention(Client c) {
        int choix = 0;
        System.out.println("---Portail demande d'intervention---");
        System.out.println("Veuillez choisir le type d'interevntion");
        System.out.println("Pour une intervention de type Animal, tapez 1");
        System.out.println("Pour une intervention de type Livraison, tapez 2");
        System.out.println("Pour une intervention de type Incident, tapez 3");
        choix = Saisie.lireInteger("Inqiquer votre choix: ", Arrays.asList(1, 2, 3));
        String description;
        switch (choix) {
            case 1:
                description = Saisie.lireChaine("Description : ");
                String animal = Saisie.lireChaine("Animal: ");
                Animal a = new Animal(animal, description);
                Service.demandeIntervention(a, c);
                break;
            case 2:
                description = Saisie.lireChaine("Description : ");
                String objet = Saisie.lireChaine("Objet: ");
                String entreprise = Saisie.lireChaine("Entreprise: ");
                Livraison l = new Livraison(objet, entreprise, description);
                Service.demandeIntervention(l, c);
                break;
            case 3:
                description = Saisie.lireChaine("Description : ");
                Incident i = new Incident(description);
                Service.demandeIntervention(i, c);
                break;
            default:
                break;
        }
    }

    public static void afficherProfil(Personne c) {
        System.out.print("Client{civilite=" + c.getCivilite() + "\n nom=" + c.getNom() + "\n prenom=" + c.getPrenom()
                + "\n dateNaissance=" + c.getDateNaissance() + "\n adresse=" + c.getAdresse() + "\n numTel=" + c.getNumTel() + "\n adresseElec=" + c.getAdresseElec() + '}');
        System.out.println("Pour modifier vos informations personnelles, tapez 1");
        System.out.println("Pour modifier votre mot de passe, tapez 2");
        int choix = Saisie.lireInteger("Indiquer votre choix: ", Arrays.asList(1, 2));

        switch (choix) {
            case 1:
                System.out.println("----- Veuillez saisir vos informations personnelles de nouveau -----");
                String civilite = Saisie.lireChaine("Civilité : ");
                String prenom = Saisie.lireChaine("Prenom : ");
                String nom = Saisie.lireChaine("Nom : ");
                String dateNaiss = Saisie.lireChaine("Date de naissance : (yyyy-mm-dd) : ");
                String adresse = Saisie.lireChaine("Adresse : ");
                String numTel = Saisie.lireChaine("Numéro de téléphone : ");
                String email = Saisie.lireChaine("Adresse email : ");
                Service.updateProfil(civilite, nom, prenom, dateNaiss, adresse, numTel, email, c);

                break;
            case 2:
                String mdp = Saisie.lireChaine("Saisir nouveau mdp: ");
                Service.updateMdp(c, mdp);
                break;
            default:
                break;
        }
    }

    public static void afficherHisto(Client c) {
        for (Intervention i : c.getHistoInterventions()) {
            afficherIntervention(i);
        }

    }

    public static void afficherIntervention(Intervention i) {
        if (i instanceof Animal) {
            Animal a = (Animal) i;
            System.out.println("Animal{" + "numIntervention=" + a.getNumIntervention() + " /n animal=" + a.getAnimal() + "/n description=" + a.getDescription() + "/n reussie=" + a.isReussie() + "\n commentaire=" + a.getCommentaire() + '}');
        } else if (i instanceof Livraison) {
            Livraison l = (Livraison) i;
            System.out.println("Livarison{" + "numIntervention=" + l.getNumIntervention() + " /n objet=" + l.getObjet() + "\n Entreprise" + l.getEntreprise() + "/n description=" + l.getDescription() + "/n reussie=" + l.isReussie() + "\n commentaire=" + l.getCommentaire() + '}');
        } else if (i instanceof Incident) {
            Incident inc = (Incident) i;
            System.out.println("Incident{" + "numIntervention=" + inc.getNumIntervention() + "/n description=" + inc.getDescription() + "/n reussie=" + inc.isReussie() + "\n commentaire=" + inc.getCommentaire() + '}');
        }
    }

    public static void afficherTableauBord(Employe e) {
        for (Intervention i : Service.RecupererInterventionsDuJour(e)) {
            afficherIntervention(i);
        }
    }

    public static void acceuilEmploye(Employe e) {
        int sousmenu;
        boolean arret = false;
        while (!arret) {
            System.out.println("--Bienvenue sur votre portail employe Proact'If--");
            System.out.println("Pour afficher votre intervention courante: tapez 1");
            System.out.println("Pour cloturer votre intervention courante: tapez 2");
            System.out.println("Pour consulter votre tableau de bord: tapez 3");
            System.out.println("Pour vous déconnecter: tapez 4");
            sousmenu = Saisie.lireInteger("Indiquer votre choix: ", Arrays.asList(1, 2, 3, 4));
            switch (sousmenu) {
                case 1:
                    Intervention current = e.getInterventionCourante();
                    if (current == null) {
                        System.out.println("Vous n'avez pas d'intervention courante");
                    } else {
                        afficherIntervention(e.getInterventionCourante());
                    }
                    break;
                case 2:
                    System.out.println("--Alors cette intervention?--");
                    String commentaire = Saisie.lireChaine("Votre commentaire: ");
                    int status = Saisie.lireInteger("tapez 1 si vous avez réussi l'intervetion, 0 sinon", Arrays.asList(0, 1));
                    Service.cloturerIntervention(e.getInterventionCourante(), commentaire, status == 1);
                    break;
                case 3:
                    afficherTableauBord(e);
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
    }

}

/*    public void menu() {
        int sousmenu = 0;
        boolean arret = false;
        while (!arret) {

            System.out.println("--Bienvenue sur Proactif--");
            System.out.println("Pour vous connecter : tapez 1");
            System.out.println("Pour vous inscrire  : tapez 2");
            System.out.println("Pour quitter : tapez 0");
            sousmenu = Saisie.lireInteger("Inqiquer votre choix: ");
            switch (sousmenu) {
                case 1: {
                    int choix = 0;
                    System.out.println("---Portail de connexion---");
                    System.out.println("Pour revenir sur le menu précédent : tapez -1 ");
                    System.out.println("Pour quitter : tapez 9");
                    System.out.println("Pour continuer : tapez 1");

                    choix = Saisie.lireInteger("Inqiquer votre choix: ");
                    switch (choix) {
                        case 1:
                            String mail = "";
                            String mdp;
                            Personne p;
                            int i = 0;
                            do {
                                if (i > 0) {
                                    System.out.println("Votre identifiant ou mot de passe est incorrect");
                                }
                                if (i < 2) {
                                    System.out.println("Merci de remplir les champs suivants");
                                    mail = Saisie.lireChaine("Adresse mail: ");
                                    mdp = Saisie.lireChaine("Mot de passe: ");
                                    p = Service.connexion(mail, mdp);
                                    i++;
                                }
                                if (i >= 2) {
                                    System.out.println("Vous avez oublie votre mot de passe? : tapez 1");
                                    
                                    String num = Saisie.lireChaine("Saisir votre numero de telephone pour recevoir un code de confirmation: ");
                                    int code1 = Service.envoyerCodeConfirmation(mail, num);

                                    int code2 = Saisie.lireInteger("Saisir votre code de confirmation: ");
                                    if (code1 == code2) {
                                        String mdp1 = Saisie.lireChaine("Saisir votre nouveau mot de passe: ");
                                        Client c = Service.findClient(mail, num);
                                        Service.updateMdp(c, mdp1);
                                        System.out.println("Merci de remplir les champs suivants pour vous connecter");
                                        mail = Saisie.lireChaine("Adresse mail: ");
                                        mdp = Saisie.lireChaine("Mot de passe: ");
                                        p = Service.connexion(mail, mdp);

                                    }
                                }

                            } while (p == null && i >= 4);

                            if (p instanceof Client) {
                                clientCourant = (Client) p;
                            } else if (p instanceof Employe) {
                                employeConnecte = (Employe) e;
                            }

                            break;
                        case 2:
                            System.out.println("Sous menu 1-2");
                            break;
                        case 9:
                            arret = true;
                            break;
                        default:
                            System.out.println("entrez un choix entre 1 et 2");
                            break;
                    }
                }
                break;
                case 2:
                    String civilite = civilite;
                    String nom = nom;
                    String prenom = prenom;
                    String dateNaissance = dateNaissance;
                    String adresse = adresse;
                    String coord = coord;
                    String numTel = numTel;
                    String adresseElec = adresseElec;
                    String mdp = mdp;
                    System.out.println("Sous menu 2");
                    break;
                case 3:
                    System.out.println("Sous menu 2");
                    break;
                case 9:
                    arret = true;
                    break;
                default:
                    System.out.println("entrez un choix entre 1 et 3");
                    break;
            }
        }
    }


 */
