/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static projectai.Search.operators;

import static projectai.Principal.map;

/**
 *
 * @author jesus
 */
public class UI {
    protected static final JLabel LBL = new JLabel();
    protected static JFrame frame;
    private static JDialog optionPane;
    private static int index = -1;
    
    protected static JCheckBox auto = new JCheckBox("Auto");
    protected static JRadioButton backTracking = new JRadioButton("Back Tracking");
    protected static JRadioButton AStar = new JRadioButton("A*");
    
    private static JPanel rightPanel;
    private static JPanel autoPanel;
    private static JPanel orderSelectorPanel;
    private static OperatorsDataTable opDT = new OperatorsDataTable();
    private static JScrollPane scrollPaneOperators;
    
    private static JTable opTable;
    
    protected static Animation animate;
   
    public static void show() {
        
        optionPane = new JDialog(frame, "Options");
    	
    	frame = new JFrame("Map");
        rightPanel = new JPanel();
        autoPanel = new JPanel();
        autoPanel.setLayout(new BoxLayout(autoPanel, BoxLayout.PAGE_AXIS));
    	        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(250, 250);
    	        
    	addComponents();
        addListeners();
        
        frame.setVisible(true);
        frame.pack();
        
        optionPane.pack();
        optionPane.setBounds(frame.getBounds().x+ frame.getBounds().width, frame.getBounds().y, optionPane.getWidth(), optionPane.getHeight());

        frame.setVisible(true);
    }

    private static void addComponents() {
        addMap();
        addOptionPane();
    }

    private static void addListeners() {
        UIListener.addComponentListener(frame);
        UIListener.addKeyListener(frame);
    }

    private static void addMap() {
        ImageManipulator.setImage(map.getMap(), LBL);
        frame.add(LBL);
    }
    
    private static void addOptionPane() {
    	addRightPane();
    	addAutoPane();
    	optionPane.add(autoPanel);
        optionPane.setVisible(true);
    }

    private static void addRightPane() {
        addCellInformationPane();
        addRestartsButton();
        addChangeMapButton();
        addExitButton();
        autoPanel.add(rightPanel);
    }

    private static void addCellInformationPane() {
        JPanel informationPane = new JPanel();
        informationPane.setLayout(new BoxLayout(informationPane, BoxLayout.PAGE_AXIS));
        
        JLabel lbl_Information = new JLabel("Informacion");
        informationPane.add(lbl_Information);
        
        JPanel cellPositionPane = new JPanel();
        cellPositionPane.setLayout(new FlowLayout());
        
        final String [] column = {null};
        final Integer [] row = {null};
        
        JComboBox comboBox_Column = new JComboBox();
        for (int i = 0; i < map.getWidth(); i++) {
            comboBox_Column.addItem((char) ('A' + i));
        }
        cellPositionPane.add(comboBox_Column);
        
        JComboBox comboBox_Row = new JComboBox();
        for (int i = 1; i <= map.getHeight(); i++) {
            comboBox_Row.addItem(i);
        }
        cellPositionPane.add(comboBox_Row);
        informationPane.add(cellPositionPane);
        
        column[0] = comboBox_Column.getSelectedItem().toString();
        row[0] = (Integer) comboBox_Row.getSelectedItem();
        
        final JLabel lbl_InformationFound = new JLabel(getInformation(column[0], row[0]));
        informationPane.add(lbl_InformationFound);
        
        comboBox_Column.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                column[0] = comboBox_Column.getSelectedItem().toString();
                row[0] = (Integer) comboBox_Row.getSelectedItem();
                lbl_InformationFound.setText(getInformation(column[0], row[0]));
            }
        });
        
        comboBox_Row.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                column[0] = comboBox_Column.getSelectedItem().toString();
                row[0] = (Integer) comboBox_Row.getSelectedItem();
                lbl_InformationFound.setText(getInformation(column[0], row[0]));
            }
        });
        
        rightPanel.add(informationPane);
    }

    private static void addRestartsButton() {
        JButton btn_restart = new JButton("Reiniciar");
        btn_restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                frame.setVisible(false);
                frame.dispose();
                optionPane.setVisible(false);
                optionPane.dispose();
                for (ArrayList<Cell> ar : map.getCells()) {
                    for (Cell c : ar) {
                        c.resetVisitCounter();
                    }
                }
                map.resetVisitCounter();
                map.setCurrentState(map.getInitialState());
                map.setFinish(false);
                SetStateMarksFrame.main();
            }
        });
        rightPanel.add(btn_restart);
    }

    private static void addChangeMapButton() {
        JButton btn_changeMap = new JButton("Cambiar mapa");
        btn_changeMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    FileReader fr = new FileReader((new Chooser()).ChooseMap());
                    map.reset(fr);
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                } catch (RuntimeException rte) {
                    JOptionPane.showMessageDialog(frame, rte.getMessage());
                    return;
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(frame, "Unknown error...");
                    return;
                }
                frame.setVisible(false);
                frame.dispose();
                optionPane.setVisible(false);
                optionPane.dispose();
                try {
					Select frame = new Select();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
        rightPanel.add(btn_changeMap);
    }

    private static void addExitButton() {
        JButton btn_exit = new JButton("Salir");
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                frame.setVisible(false);
                frame.dispose();
                optionPane.setVisible(false);
                optionPane.dispose();
            }
        });
        rightPanel.add(btn_exit);
    }
    
    private static void addAutoPane() {
    	autoPanel.setLayout(new BoxLayout(autoPanel, BoxLayout.PAGE_AXIS));
    	addCheckBox();
    	addRadioButtons();
    	addOrderSelector();
    	addPlayButton();
    }
    
    private static void addCheckBox() {
    	autoPanel.add(auto);
    }
    
    private static void addRadioButtons() {
    	backTracking.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (backTracking.isSelected()) {
					AStar.setSelected(false);
				} else {
					AStar.setSelected(true);
				}
			}
    	});
    	
    	AStar.addChangeListener(new ChangeListener() {
    		
    		@Override
    		public void stateChanged(ChangeEvent e) {
    			if (AStar.isSelected()) {
    				backTracking.setSelected(false);
    			} else {
    				backTracking.setSelected(true);
    			}
    		}
    	});
    	
    	autoPanel.add(backTracking);
    	autoPanel.add(AStar);
    }
    
    private static void addOrderSelector() {
    	orderSelectorPanel = new JPanel();
    	orderSelectorPanel.setLayout(new FlowLayout());
    	addDataTable();
    	addChangeButtons();
    	autoPanel.add(orderSelectorPanel);
    }
    
    private static void addPlayButton() {
    	JButton btn_Play = new JButton("Play");
    	btn_Play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				map.resetVisitCounter();
				if (animate != null) {
	                animate.stop();
	            }
	            animate = new Animation();
			}
    	});
    	
    	autoPanel.add(btn_Play);
    }
    
    private static void addDataTable() {
    	opDT.clear();
    	JTable opTable = opDT.add(operators);;
    	
    	opTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                index = opTable.getSelectedRow();
            }
        });
    	
    	scrollPaneOperators = new JScrollPane(opTable);
    	orderSelectorPanel.add(scrollPaneOperators);
    }
    
    private static void addChangeButtons() {
    	JButton btn_Up = new JButton("UP");
    	JButton btn_Down = new JButton("DOWN");
    	
    	btn_Up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (index != -1 && index > 0) {
					int i = index-1;
					Operator aux = operators[index];
					operators[index] = operators[index-1];
					operators[index-1] = aux;
					opDT.clear();
					opTable = opDT.add(operators);
					Search.orderChanged = true;
					index = i;
				}
			}
    	});
    	
    	btn_Down.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (index != -1 && index < 3) {
					int i = index+1;
					Operator aux = operators[index];
					operators[index] = operators[index+1];
					operators[index+1] = aux;
					opDT.clear();
					opTable = opDT.add(operators);
					Search.orderChanged = true;
					index = i;
				}
			}
    	});
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    	
    	panel.add(btn_Up);
    	panel.add(btn_Down);
    	
    	orderSelectorPanel.add(panel);
    }
    
    public static String getInformation(String c, Integer r) {
        return map.getTerrain(new State(c.charAt(0), r)).getName();
    }
}
