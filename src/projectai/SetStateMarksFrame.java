/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static projectai.Principal.map;

/**
 *
 * @author jesus
 */
public class SetStateMarksFrame extends JFrame{
    JPanel leftPane;
    JLabel lbl;
    
    int selectedPlayer;
    State initialState = new State();
    State finalState = new State();
    
    static void main() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SetStateMarksFrame frame = new SetStateMarksFrame();
                frame.setVisible(true);
            }
        });
    }
    
    SetStateMarksFrame() {
        leftPane = new JPanel();
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        addComponents();
        pack();
    }

    private void addComponents() {
        addLeftPane();
        addPreviewPane();
    }

    private void addLeftPane() {
        addSelectPlayer();
        addInitialStatePane();
        addFinalStatePane();
        addPreviewButton();
        addFinishButton();
        addBackButton();
        add(leftPane);
    }

    private void addBackButton() {
    	JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Select frame = new Select();
					frame.setVisible(true);
				} catch (Exception err) {
					err.printStackTrace();
				}
                dispose();
			}
		});
		leftPane.add(btnRegresar );
	}

	private void addPreviewPane() {
        lbl = new JLabel("Preview");
        add(lbl);
    }
    
    private void addSelectPlayer() {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
        
        JLabel lbl_player = new JLabel("Ser");
        playerPanel.add(lbl_player);
        
        final JComboBox comboBox_Player = new JComboBox();
        map.getPlayers().forEach((Player p) -> {
            comboBox_Player.addItem(p.getName());
        });
        comboBox_Player.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                selectedPlayer = comboBox_Player.getSelectedIndex();
            }
        });
        playerPanel.add(comboBox_Player);
        
        selectedPlayer = comboBox_Player.getSelectedIndex();
        
        leftPane.add(playerPanel);
    }

    private void addInitialStatePane() {
        JPanel initialStatePanel = new JPanel();
        initialStatePanel.setLayout(new BoxLayout(initialStatePanel, BoxLayout.PAGE_AXIS));
        
        JLabel lbl_title = new JLabel("Estado Inicial");
        initialStatePanel.add(lbl_title);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        
        final String [] column = {null};
        final Integer [] row = {null};
        
        JComboBox comboBox_Column = new JComboBox();
        for (int i = 0; i < map.getWidth(); i++) {
            comboBox_Column.addItem((char) ('A' + i));
        }
        
        JComboBox comboBox_Row = new JComboBox();
        for (int i = 1; i <= map.getHeight(); i++) {
            comboBox_Row.addItem(i);
        }
        
        comboBox_Column.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                column[0] = comboBox_Column.getSelectedItem().toString();
                row[0] = (Integer) comboBox_Row.getSelectedItem();
                setInitialState(column[0], row[0]);
            }
        });
        bottomPanel.add(comboBox_Column);
        
        comboBox_Row.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                column[0] = comboBox_Column.getSelectedItem().toString();
                row[0] = (Integer) comboBox_Row.getSelectedItem();
                setInitialState(column[0], row[0]);
            }
        });
        bottomPanel.add(comboBox_Row);
        
        setInitialState(comboBox_Column.getSelectedItem().toString(), (Integer) comboBox_Row.getSelectedItem());
        
        initialStatePanel.add(bottomPanel);
        leftPane.add(initialStatePanel);
    }
    
    private void addFinalStatePane() {
        JPanel finalStatePanel = new JPanel();
        finalStatePanel.setLayout(new BoxLayout(finalStatePanel, BoxLayout.PAGE_AXIS));
        
        JLabel lbl_title = new JLabel("Estado final");
        finalStatePanel.add(lbl_title);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        
        final String [] column = {null};
        final Integer [] row = {null};
        
        JComboBox comboBox_Column = new JComboBox();
        for (int i = 0; i < map.getWidth(); i++) {
            comboBox_Column.addItem((char) ('A' + i));
        }
        
        JComboBox comboBox_Row = new JComboBox();
        for (int i = 1; i <= map.getHeight(); i++) {
            comboBox_Row.addItem(i);
        }
        
        comboBox_Column.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                column[0] = comboBox_Column.getSelectedItem().toString();
                row[0] = (Integer) comboBox_Row.getSelectedItem();
                setFinalState(column[0], row[0]);
            }
        });
        bottomPanel.add(comboBox_Column);
        
        comboBox_Row.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                column[0] = comboBox_Column.getSelectedItem().toString();
                row[0] = (Integer) comboBox_Row.getSelectedItem();
                setFinalState(column[0], row[0]);
            }
        });
        bottomPanel.add(comboBox_Row);
        
        setFinalState(comboBox_Column.getSelectedItem().toString(), (Integer) comboBox_Row.getSelectedItem());
        
        finalStatePanel.add(bottomPanel);
        leftPane.add(finalStatePanel);
    }
    
    private void addPreviewButton() {
        JButton btn_Preview = new JButton("Preview");
        btn_Preview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setImage();
            }
        });
        leftPane.add(btn_Preview);
    }

    private void addFinishButton() {
        JButton btn_Finish = new JButton("Terminar");
        btn_Finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                finish();
            }
        });
        leftPane.add(btn_Finish);
    }
    
    private void setInitialState(String c, Integer r) {
        initialState = new State(c.charAt(0), r);
    }
    
    private void setFinalState(String c, Integer r) {
        finalState = new State(c.charAt(0), r);
    }

    private void setImage() {
        try {
            lbl.setText("");
            map.setSelectedPlayer(selectedPlayer);
            map.setInitialState(initialState);
            map.setFinalState(finalState);
        } catch (RuntimeException rte) {
            JOptionPane.showMessageDialog(this, rte);
        }
        ImageManipulator.setImage(map.getMapPrev(map.stateReachable(initialState), map.stateReachable(finalState)), lbl);
        pack();
    }
    
    private void finish() {
        try {
            UI.show();
            setVisible(false);
            map.setSelectedPlayer(selectedPlayer);
            map.setInitialState(initialState);
            map.setFinalState(finalState);
            map.setCurrentState(initialState);
            dispose();
        } catch (RuntimeException rte) {
            JOptionPane.showMessageDialog(this, rte);
        }
    }
}
