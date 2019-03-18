/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static projectai.ProjectAI.Movements.MOVE_DOWN;
import static projectai.ProjectAI.Movements.MOVE_LEFT;
import static projectai.ProjectAI.Movements.MOVE_RIGHT;
import static projectai.ProjectAI.Movements.MOVE_UP;
import static projectai.ProjectAI.map;
import static projectai.UI.LBL;

/**
 *
 * @author jesus
 */
public class UIListener {
    public static void addComponentListener(JFrame frame) {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                int sizeW = frame.getWidth();
                int sizeH = frame.getHeight() + 40;
                int size;
                
                if (sizeW < (50 * (map.getWidth())) || sizeH < (50 * (map.getHeight()) + 40)) {
                    sizeW = 50 * map.getWidth();
                    sizeH = 50 * map.getHeight() + 40;
                    frame.setSize(sizeW, sizeH);
                }
                
                size = sizeW < sizeH ? sizeW : sizeH;
                
                map.setCellSize(size - (size / map.getSize()));
                
                ImageManipulator.setImage(map.getMap());
            }
        });
    }
    
    public static void addKeyListener(JFrame frame) {
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent ke) {
                Operator op;
                State nextState;
                
                switch (ke.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        op = MOVE_UP;
                        break;
                    case KeyEvent.VK_RIGHT:
                        op = MOVE_RIGHT;
                        break;
                    case KeyEvent.VK_DOWN:
                        op = MOVE_DOWN;
                        break;
                    case KeyEvent.VK_LEFT:
                        op = MOVE_LEFT;
                        break;
                    default:
                        return;
                }
                
                nextState = op.getNextState(map.getCurrentState());
                if (op.isAplicable(map.getSelectedPlayer(), nextState)) {
                    map.setCurrentState(nextState);
                    
                    if (map.getCurrentState().equals(map.getFinalState())) {
                        System.out.println("Success!");
                    }
                    
                    BufferedImage img = map.getMap();
                
                    ImageIcon icon = new ImageIcon(img);
                    LBL.setIcon(icon);
                }
            }

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
    }
}
