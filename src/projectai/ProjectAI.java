/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

/**
 *
 * @author jesus
 */

import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ProjectAI {
    private static final JLabel lbl = new JLabel();
    
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        // TODO code application logic here        
        JFrame frame = new JFrame("test");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(500, 500);
        
        Map map = new Map();
        map.loadMap(new FileReader("mapFile"));
        
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                map.setCellSize(frame.getHeight() < frame.getWidth() ? frame.getHeight() : frame.getWidth());
                BufferedImage img = map.getMap();        
                ImageIcon icon = new ImageIcon(img);
                lbl.setIcon(icon);
            }
        });
        
        frame.add(lbl);
        
        frame.setVisible(true);
    }
    
}
