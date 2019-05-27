/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import static projectai.Principal.Movements.MOVE_UP;
import static projectai.Principal.Movements.MOVE_RIGHT;
import static projectai.Principal.Movements.MOVE_DOWN;
import static projectai.Principal.Movements.MOVE_LEFT;
import static projectai.Principal.map;
import static projectai.Principal.tree;

/**
 *
 * @author jesus
 */
public class UIListener extends UI{	
    public static void addComponentListener(JFrame frame) {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                int sizeW = frame.getWidth();
                int sizeH = frame.getHeight() - 40;
                int size;
                
                if (sizeW < (50 * (map.getWidth())) || sizeH < (50 * (map.getHeight()) + 40)) {
                    sizeW = 50 * map.getWidth();
                    sizeH = 50 * map.getHeight() + 40;
                    frame.setSize(sizeW, sizeH);
                }
                
                size = sizeW < sizeH ? sizeW : sizeH;
                
                map.setCellSize(size - (size / map.getSize()));
                
                ImageManipulator.setImage(map.getMap(), LBL);
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
                
                if (op.isAplicable(map.getSelectedPlayer(), nextState) && !map.isFinished()) {
                    map.setCurrentState(nextState);
                    ImageManipulator.setImage(map.getMap(), UI.LBL);
                }
                if (map.getCurrentState().equals(map.getFinalState())) {
                	map.setFinish(true);
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                        	JDialog frame_Tree = new TreeFrame(Search.getBackTrackingTree(map.getInitialState(), map.getFinalState()), frame);
                        }
                    });
                    
                    JOptionPane.showMessageDialog(frame, "You Win!");
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
