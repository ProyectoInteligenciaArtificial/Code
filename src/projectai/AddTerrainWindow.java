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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static projectai.Principal.map;

/**
 *
 * @author jesus
 */
class AddTerrainWindow extends JDialog {
    private final JPanel listPane;
    private final JLabel lbl_colorPrev;
    
    public static AddTerrainWindow main(JFrame frame, boolean b) {
        AddTerrainWindow atw = null;
        EventQueue.invokeLater(() -> {
            AddTerrainWindow atw1 = new AddTerrainWindow(frame, b);
        });
        return atw;
    }

    public AddTerrainWindow(JFrame frame, boolean b) {
        super(frame, b);
        listPane = new JPanel();
        listPane.setLayout(new FlowLayout());
        lbl_colorPrev = new JLabel();
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setBounds(frame.getBounds().x + 25, frame.getBounds().y + 25, 500, 200);
        
        addComponents();
        pack();
        setVisible(true);
    }
    
    private void addComponents() {
        addNameChooser();
        addColorChooser();
        add(listPane);
        add(lbl_colorPrev);
    }
    
    private void addNameChooser() {
        JPanel nameChooserPanel = new JPanel();
        
        JLabel lbl_name = new JLabel();
        lbl_name.setText("Nombre");
        
        JComboBox comboBox_terrainID = new JComboBox();
        
        for (Terrain t : map.getTerrains()) {
            comboBox_terrainID.addItem(t.getId());
        }
        
        nameChooserPanel.setLayout(new BoxLayout(nameChooserPanel, BoxLayout.PAGE_AXIS));
        nameChooserPanel.add(lbl_name);
        nameChooserPanel.add(comboBox_terrainID);
        listPane.add(nameChooserPanel);
    }
    
    private void addColorChooser() {
        JButton btn_color = new JButton();
        btn_color.setText("Color");
        
        btn_color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setColor(ae);
            }
        });
        
        listPane.add(btn_color);
    }
    
    private void setColor(ActionEvent ae) {
        ColorChooser ch=new ColorChooser();
        ch.actionPerformed(ae);
        ImageManipulator.setImage(ch.color.getRGB(), lbl_colorPrev);
        pack();
    }
}
