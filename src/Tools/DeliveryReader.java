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
               String heureDebut;
               String heureFin;
               try{
               heureDebut = attributes.getValue("heureDebut");
                heureFin = attributes.getValue("heureFin");
               }
               catch(Exception e){
                   //erreur, le contenu de id n'est pas un entier
                            throw new SAXException(e);
               }
                HeureBegin= ParseTime(heureDebut);
                HeureEnd = ParseTime(heureFin);
                ListLivraison.add(HeureBegin);   
                ListLivraison.add(HeureEnd);   
           }
           else if(qName.equals("Livraison")){
               try{
                int idLivraison = Integer.parseInt(attributes.getValue("id"));
                int nClient = Integer.parseInt(attributes.getValue("client"));
                int adresseLivraison = Integer.parseInt(attributes.getValue("adresse"));
                DeliveryNode newLivraison = new DeliveryNode(adresseLivraison,idLivraison,nClient);
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
            if (qName.equals("Plage")) {
           Itineraire.setDelivery(ListLivraison);
           ListLivraison = new ArrayList();
            }
        }
    }

       private static Calendar ParseTime(String heureDebut){
           
           Calendar heure=Calendar.getInstance(); ;
           String delim = ":";
           String[] time = heureDebut.split(delim);
           heure.set(0, 0, 0, Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
           return heure;
       }
}


    
    
           
    
    

