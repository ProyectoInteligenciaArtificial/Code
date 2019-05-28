package projectai;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class ColorChooser extends JFrame implements ActionListener { 
    	
    JButton b;    
    Container c; //Color
    Color color;
    
    ColorChooser(){    
        c=getContentPane();    
        c.setLayout(new FlowLayout());         
        b=new JButton("color");    
        b.addActionListener(this);
        c.add(b);    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {    
        Color initialcolor=Color.RED;    
        color = JColorChooser.showDialog(this,"Selecciona un color",initialcolor);
    }
} 