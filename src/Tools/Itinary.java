/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Eleonore
 */
public class Itinary {
    List  ListePlageHoraire;
    //Liste de liste de livraison
    //Une liste de livraison comprend : Heure de début, heure de fin, liste des livraison
    
    
    public Itinary(){
        ListePlageHoraire = new ArrayList();
    }
    
    public List getListPlageHoraire(){
        return ListePlageHoraire; 
    }
    
    public void setDelivery(List ListLivraisons ){
     //Ajoute la liste de livraison pour chaque plage horaire
      ListePlageHoraire.add(ListLivraisons);
    }
}
