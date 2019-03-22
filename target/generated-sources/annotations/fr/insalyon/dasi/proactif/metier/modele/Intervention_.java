package fr.insalyon.dasi.proactif.metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-22T15:05:18")
@StaticMetamodel(Intervention.class)
public abstract class Intervention_ { 

    public static volatile SingularAttribute<Intervention, Date> dateDebut;
    public static volatile SingularAttribute<Intervention, Long> numIntervention;
    public static volatile SingularAttribute<Intervention, String> description;
    public static volatile SingularAttribute<Intervention, Date> dateFin;

}