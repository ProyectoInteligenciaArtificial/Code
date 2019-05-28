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

import static projectai.Principal.map;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
//from w w w  . j a  v  a2  s.  c  om
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Animation implements Runnable {
    Timer timer;
    Animation a;
    boolean shutdown;
    
    Animation() {
        this.a = this;
        this.shutdown = false;
        SwingUtilities.invokeLater(this);
    }
  
    public void stop() {
        shutdown = true;
    }

    @Override
    public void run() {
        JFrame gui = UI.frame;

        final AnimatedImage tile = new AnimatedImage();

        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	if (!shutdown) {
            		tile.paintImage();
            		if (shutdown = tile.stop) {
            			ImageManipulator.setImage(map.getMap(), UI.LBL);
            			EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                            	if (UI.backTracking.isSelected()) {
                            		JDialog frame_Tree = new TreeFrame(Search.getBackTrackingTree(map.getInitialState(), map.getFinalState()), UI.frame);	
                            	} else {
                            		JDialog frame_Tree = new TreeFrame(Search.getAStarTree(map.getInitialState(), map.getFinalState()), UI.frame);
                            	}
                            }
                        });
                        
                        JOptionPane.showMessageDialog(UI.frame, "You Win!");
            		}
            	}
            }
        };

        timer = new Timer(100, listener);
        timer.start();

        JOptionPane.showMessageDialog(null, gui);
        timer.stop();
    }
};

class AnimatedImage {
    private ArrayList<State> route;
    protected boolean stop;
    private int i = 0;
    
    AnimatedImage() {
    	map.setFinish(false);
        if (UI.auto.isSelected()) {
        	if (UI.AStar.isSelected()) {
        		route = Search.getAStarRoute(map.getInitialState(), map.getFinalState());
        	} else {
        		route = Search.getBackTrackingRoute(map.getInitialState(), map.getFinalState());
        	}
        } else {
        	stop = true;
        }
    }
    
    public void paintImage() {
    	if (i == route.size()) {
    		map.setFinish(true);
    		if (UI.backTracking.isSelected()) {
    			map.setSolution(Search.getBackTrackingSolution(map.getInitialState(), map.getFinalState()));
    		} else if (UI.AStar.isSelected()) {
    			map.setSolution(Search.getAStarSolution(map.getInitialState(), map.getFinalState()));
    		}
        	stop = true;
        	return;
        }
        
        map.setCurrentState(route.get(i++));
	    ImageManipulator.setImage(map.getMap(), UI.LBL);
    }
}