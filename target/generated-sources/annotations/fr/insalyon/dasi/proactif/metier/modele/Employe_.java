package fr.insalyon.dasi.proactif.metier.modele;

import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-22T16:41:26")
@StaticMetamodel(Employe.class)
public class Employe_ extends Personne_ {

    public static volatile SingularAttribute<Employe, Date> finTravail;
    public static volatile SingularAttribute<Employe, Boolean> estEnIntervention;
    public static volatile ListAttribute<Employe, Intervention> tabBord;
    public static volatile SingularAttribute<Employe, Date> debutTravail;

}