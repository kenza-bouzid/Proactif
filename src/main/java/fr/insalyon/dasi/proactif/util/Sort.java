/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.util;

import fr.insalyon.dasi.proactif.metier.modele.Intervention;
import java.util.List;

/**
 *
 * @author DHamidovic
 * @author kbouzid
 */
public class Sort {
    
    /**
     *
     * @param l
     */
    public static void sortHistoIntervention(List<Intervention> l) {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < l.size(); ++i) {
                if (l.get(i).getDateDebut().before(l.get(i + 1).getDateDebut())) {
                    Intervention temp = l.get(i);
                    l.set(i, l.get(i + 1));
                    l.set(i + 1, temp);
                    sorted = false;
                }
            }
        }
    }
    
}
