/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static projectai.ProjectAI.Movements.*;
import static projectai.ProjectAI.map;

/**
 *
 * @author jesus
 */
public class UI {
    public static final JLabel LBL = new JLabel();
    private static JFrame frame;

    public static void show() {
        frame = new JFrame("Map");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(500, 500);
        
        ImageManipulator.setImage(map.getMap());
        
        frame.add(LBL);
        
        frame.setVisible(true);
        
        UIListener.addComponentListener(frame);
        UIListener.addKeyListener(frame);
    }
}
