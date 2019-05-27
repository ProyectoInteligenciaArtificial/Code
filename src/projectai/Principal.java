package projectai;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Principal extends JFrame {
	private File name;
	private JPanel contentPane;
    public static Map map = new Map();
    public static Tree<Node> tree;
        
    private final JFrame frame = this;
        
    public interface Movements {
    	public static final Operator MOVE_UP = new Operator(Operator.MOVE_INT_UP);
        public static final Operator MOVE_RIGHT = new Operator(Operator.MOVE_INT_RIGHT);
        public static final Operator MOVE_DOWN = new Operator(Operator.MOVE_INT_DOWN);
        public static final Operator MOVE_LEFT = new Operator(Operator.MOVE_INT_LEFT);
    }


	/**
	 * Launch the application.
	 */
	 private static final JLabel lbl = new JLabel();

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.runtime.version"));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	

	public Principal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Button button_1 = new Button("Cargar mapa");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
                            try {
                                map.loadMap(new FileReader((new Chooser()).ChooseMap()));
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
                            try {
            					Select frame = new Select();
            					frame.setVisible(true);
            				} catch (Exception err) {
            					err.printStackTrace();
            				}
                            dispose();
			}

		});
		button_1.setBounds(208, 28, 126, 22);
		contentPane.add(button_1);
		
		Button button = new Button("Salir");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button.setBounds(208, 118, 126, 22);
		contentPane.add(button);
	}
}
