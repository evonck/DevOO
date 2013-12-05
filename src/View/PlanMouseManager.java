/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import java.awt.event.MouseEvent;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;

/**
 *
 * @author Aleks
 */
public class PlanMouseManager extends DefaultMouseManager{

    public PlanMouseManager() {
        super();
    }

    @Override
    protected void elementMoving(GraphicElement element, MouseEvent event) {
    
    }
}
