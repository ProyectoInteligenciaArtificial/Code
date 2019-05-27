package projectai;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class TreeFrame extends JDialog {
	TreeFrame(Tree<Node> tree, JFrame frame) {
		super(frame, "Tree");
		
		setLayout(new FlowLayout());
        JLabel lbl_tree = new JLabel();
        BufferedImage img = tree.paint();
        ImageManipulator.setImage(tree.paint(), lbl_tree);
        JScrollPane scroolPane_Tree = new JScrollPane(lbl_tree);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.floor(screenSize.getWidth() * 0.5);
        int height = (int) Math.floor(screenSize.getHeight() * 0.9);
        scroolPane_Tree.setPreferredSize(new Dimension(width, height));;
        add(scroolPane_Tree);
        
        pack();
        setVisible(true);
	}
}
