/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.TileObserver;
import java.awt.image.WritableRenderedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author jesus
 */

public class ProjectAI {
    protected static Map map = new Map();
    
    public interface Movements {
        public static final Operator MOVE_UP = new Operator(Operator.MOVE_INT_UP);
        public static final Operator MOVE_RIGHT = new Operator(Operator.MOVE_INT_RIGHT);
        public static final Operator MOVE_DOWN = new Operator(Operator.MOVE_INT_DOWN);
        public static final Operator MOVE_LEFT = new Operator(Operator.MOVE_INT_LEFT);
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        // TODO code application logic here
        
        map.loadMap(new FileReader("mapFile"));
        
        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.red);
        g2d.fill(new Ellipse2D.Float(0, 0, 50, 50));
        g2d.dispose();
        
        map.addPlayer(new Player("test", image));
        map.getSelectedPlayer().addWeight(new Weight(0, 2));
        map.getSelectedPlayer().addWeight(new Weight(5, 1));
        
        map.setInitialState(new State('A', 1));
        map.setFinalState(new State(map.getWidth(), map.getHeight()));
        map.setCellSize(50);
        
        UI.show();
    }
    
}
