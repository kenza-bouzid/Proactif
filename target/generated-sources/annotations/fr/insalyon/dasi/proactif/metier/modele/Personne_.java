package fr.insalyon.dasi.proactif.metier.modele;

import com.google.maps.model.LatLng;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-22T09:42:52")
@StaticMetamodel(Personne.class)
public abstract class Personne_ { 

    public static volatile SingularAttribute<Personne, LatLng> coord;
    public static volatile SingularAttribute<Personne, Date> dateNaissance;
    public static volatile SingularAttribute<Personne, String> adresseElec;
    public static volatile SingularAttribute<Personne, String> mdp;
    public static volatile SingularAttribute<Personne, Long> id;
    public static volatile SingularAttribute<Personne, String> nom;
    public static volatile SingularAttribute<Personne, String> prenom;
    public static volatile SingularAttribute<Personne, String> numTel;
    public static volatile SingularAttribute<Personne, String> civilite;

}