package fr.insalyon.dasi.proactif.metier.modele;

import java.sql.Time;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-20T00:32:02")
@StaticMetamodel(Employe.class)
public class Employe_ extends Personne_ {

    public static volatile SingularAttribute<Employe, Time> finTravail;
    public static volatile SingularAttribute<Employe, Boolean> estEnIntervention;
    public static volatile SingularAttribute<Employe, Time> debutTravail;

}