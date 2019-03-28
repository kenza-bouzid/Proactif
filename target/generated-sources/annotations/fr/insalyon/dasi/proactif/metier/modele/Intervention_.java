package fr.insalyon.dasi.proactif.metier.modele;

import fr.insalyon.dasi.proactif.metier.modele.Client;
import fr.insalyon.dasi.proactif.metier.modele.Employe;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-28T09:18:33")
@StaticMetamodel(Intervention.class)
public abstract class Intervention_ { 

    public static volatile SingularAttribute<Intervention, Timestamp> dateDebut;
    public static volatile SingularAttribute<Intervention, Long> numIntervention;
    public static volatile SingularAttribute<Intervention, Boolean> reussie;
    public static volatile SingularAttribute<Intervention, String> description;
    public static volatile SingularAttribute<Intervention, Client> client;
    public static volatile SingularAttribute<Intervention, Timestamp> dateFin;
    public static volatile SingularAttribute<Intervention, Employe> employeAffecte;
    public static volatile SingularAttribute<Intervention, String> commentaire;
    public static volatile SingularAttribute<Intervention, Boolean> enCours;

}