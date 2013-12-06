/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import Tools.DeliveryNode;
import Tools.Itinary;
/**
 *
 * @author Eleonore
 */
public class DeliveryReader {
    
    private DeliveryHandler mDeliveryHandler = new DeliveryHandler();
    private File mFile;
    
     public DeliveryReader(String filePath) {
        SAXParserFactory fabrique = SAXParserFactory.newInstance();
        mFile = new File(filePath);

        
    }
    
    public void process() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory fabrique = SAXParserFactory.newInstance();
        SAXParser parseur = fabrique.newSAXParser();
        parseur.parse(mFile, mDeliveryHandler);
        
    }
    
    public Itinary getitinary(){
        return mDeliveryHandler.Itineraire;
    }
    
public static class DeliveryHandler extends DefaultHandler{
    private Itinary Itineraire;
    private List ListLivraison;
    private Calendar  HeureBegin;
    private Calendar HeureEnd;
           
           
    public DeliveryHandler(){
        super();
            this.ListLivraison = new ArrayList();
            Itineraire = new Itinary();
    }
   
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //Entrepot pas encore gérée la classe n'as pas encore été implementer
         int entrepotadresse;
           if (qName.equals("Entrepot")) {
               try{
               entrepotadresse= Integer.parseInt(attributes.getValue("adresse"));
               }
                  
               catch(Exception e){
                            //erreur, le contenu de id n'est pas un entier
                            throw new SAXException(e);
                    }
           }
           else if(qName.equals("Plage")){
               //Quand le parseur trouve un noeud Plage il recupére les valeurs d'heure de débuet et de fin de crénaux sous forme de string
               String heureDebut;
               String heureFin;
               
               try{
               heureDebut = attributes.getValue("heureDebut");
                heureFin = attributes.getValue("heureFin");
                //Parse le string pour le mettre sous forme de Calendar
                HeureBegin= ParseTime(heureDebut);
                HeureEnd = ParseTime(heureFin);
                //Ajoute les heures de début et de fin dans les deux premiéres case de la liste 
                ListLivraison.add(HeureBegin);   
                ListLivraison.add(HeureEnd);   
           
               }
               catch(Exception e){
                   //erreur, le contenu de id n'est pas un entier
                            throw new SAXException(e);
               }
               }
           else if(qName.equals("Livraison")){
               try{
                   //Récupére les valeurs de id, client et adresse dans le fichier XML
                int idLivraison = Integer.parseInt(attributes.getValue("id"));
                int nClient = Integer.parseInt(attributes.getValue("client"));
                int adresseLivraison = Integer.parseInt(attributes.getValue("adresse"));
                //Crée une nouvelle livraison,avec les valeurs trouver
                DeliveryNode newLivraison = new DeliveryNode(adresseLivraison,idLivraison,nClient);
                //Ajoute la livrasion à la liste de livraison
                ListLivraison.add(newLivraison);        
               }
               catch(Exception e){
                            //erreur, le contenu de id n'est pas un entier
                            throw new SAXException(e);
                    }
           }
              
           }
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            //Lorsque le parseur trouve une baslise fermante Plage
            if (qName.equals("Plage")) {
                //Ajoute la liste de livraison à l'éléments Itinearaire
           Itineraire.setDelivery(ListLivraison);
           //Créée une nouvelle liste de livraison pour la plage horaire suivant(A voir de la mettre avant pour eviter de crée une liste de livraison dans le vide)
           ListLivraison = new ArrayList();
            }
        }
    }

       private static Calendar ParseTime(String heureDebut){
           //Parse le string heure en Calendar
           Calendar heure=Calendar.getInstance(); ;
           String delim = ":";
           //Découpe le string heureDebut à chaque ":"
           String[] time = heureDebut.split(delim);
           //Définit l'heure de l'éléments Calendar
           heure.set(0, 0, 0, Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
           return heure;
       }
}


    
    
           
    
    

