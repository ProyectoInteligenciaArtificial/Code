/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static projectai.Principal.map;

/**
 *
 * @author jesus
 */

public class ProjectAI {
    
    public interface Movements {
        public static final Operator MOVE_UP = new Operator(Operator.MOVE_INT_UP);
        public static final Operator MOVE_RIGHT = new Operator(Operator.MOVE_INT_RIGHT);
        public static final Operator MOVE_DOWN = new Operator(Operator.MOVE_INT_DOWN);
        public static final Operator MOVE_LEFT = new Operator(Operator.MOVE_INT_LEFT);
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		// TODO code application logic here
        
        EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                        try {
                            Principal frame = new Principal();
                            frame.setVisible(true);
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
        });
        
    	/*
        map.loadMap(new FileReader("Mapas/LaberintoProyecto3.txt"));
        
        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.red);
        g2d.fill(new Ellipse2D.Float(0, 0, 50, 50));
        g2d.dispose();
        
        map.getTerrains().get(0).setName("agua");
        map.getTerrains().get(0).setImage(Color.BLUE.getRGB(), 50);
        map.getTerrains().get(1).setName("tierra");
        map.getTerrains().get(1).setImage(Color.DARK_GRAY.getRGB(), 50);
        
        map.addPlayer(new Player("test", image));
        map.getSelectedPlayer().addWeight(new Weight(0, map.getTerrains().get(1).getId()));
        
        map.setInitialState(new State('B', 2));
        map.setFinalState(new State('H', 15));
        map.setCellSize(50);
        
        UI.show();
        */
    }
    
}
