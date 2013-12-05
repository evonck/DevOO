/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Tools.PlanReader;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.Viewer;
import org.xml.sax.SAXException;

/**
 *
 * @author Aleks
 * WILL EXTEND 
 */
public class MainWindow{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        
        Graph graph = new SingleGraph("Tutorial 1");
        
        
       
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet", "url('./map_style.css')");
        
        //graph.addAttribute("ui.class", "test");
        
        PlanReader planReader = new PlanReader("plan.xml");
        try {
            planReader.process();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<PlanReader.Node> nodes = planReader.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            Node aNode = graph.addNode(String.valueOf(nodes.get(i).getId()));
            aNode.addAttribute("xy", nodes.get(i).getX(), nodes.get(i).getY());
        }
        List<PlanReader.Edge> edges = planReader.getEdges();
        for (int i = 0; i < edges.size(); i++) {
            try {
                Edge edge = graph.addEdge(String.valueOf(i), String.valueOf(edges.get(i).getNodeIdL()), String.valueOf(edges.get(i).getNodeIdR()), true);
                //edge.addAttribute("ui.label", (int)edges.get(i).getweight());
                if(edges.get(i).getweight() > 110) {
                    edge.addAttribute("ui.class", "boulevard" );    
                } else if(edges.get(i).getweight() < 80) {
                    
                } else {
                    edge.addAttribute("ui.class", "avenue" );    
                }
            } catch(Exception e) {
                
            }
        }
        
        Viewer v = graph.display(false);
        v.getDefaultView().setMouseManager(new PlanMouseManager());
                
        
        
        
        
    }
    
}
