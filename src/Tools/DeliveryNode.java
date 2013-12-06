/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;

/**
 *
 * @author Eleonore
 */
/* Classe qui définnit une livraison descendant de la classe node*/
public class DeliveryNode extends Node {
   private int id;
   private int Nclient;
   private int DeliveryAdress;
   
   
   public DeliveryNode(int deliveryAdress,int Id,int nclient){
       super(deliveryAdress);
       id=Id;
       Nclient=nclient;
       DeliveryAdress = deliveryAdress;
   }
   
   public int getId(){
       return id;
   }

    public int getNclient() {
        return Nclient;
    }

    public int getDeliveryAdress() {
        return DeliveryAdress;
    }
   
   
}
