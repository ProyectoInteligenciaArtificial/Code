package projectai;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import static projectai.Principal.map;

public class Select extends JFrame {
    
        public static int size = map.getCellSize();
        public int item = -1;
	public int itemP = -1;
        private int id=-1;
        
        public static JLabel lbl_player = new JLabel();
        private JLabel lblColor = new JLabel("Color: Not set.");
        public static BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

	private JPanel contentPane = new JPanel();
	private JTextField text_nombre;
        
	private static JComboBox comboBox;
	private static JComboBox comboBox_setCamino;
        private static JComboBox comboBox_Personaje;
        
        private static final TerrainDataTable terrainData = new TerrainDataTable();       
        private static final PlayerDataTable playerData = new PlayerDataTable();
        
        private static JScrollPane scrollPaneTerrains;
        private static JScrollPane scrollPanePlayers;

	static ArrayList<Integer> Numbers = new ArrayList<>();
	static ArrayList<Integer> Numbers2 = new ArrayList<>();
	static ArrayList<Integer> Numbers3 = new ArrayList<>();
	
	
	public static final JLabel LBL = new JLabel();
	
	static ArrayList<Container> conta = new ArrayList<>();
	static ArrayList<Integer> ids = new ArrayList<>();
	static ArrayList<String> nombres = new ArrayList<>();
	File nameFile;
	public static Color colorM = null;
	public static Color colorP = null;

	public static File fileName = null;
	public String nombreM = null;
	public String nombreP = null;
        

	public double Peso = 0;
	static boolean veri = true;
        
        private final JFrame frame = this;
        private JTextField text_peso;
        private JTextField textPlayer;
        
    	public static Map mapi = new Map();



	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
        
        private void createTerrainsTable() {
            JLabel lbl_terrainsTableTitle = new JLabel();
            lbl_terrainsTableTitle.setText("Terrenos");
            lbl_terrainsTableTitle.setBounds(650, 25, 300, 30);
            contentPane.add(lbl_terrainsTableTitle);
            
            terrainData.clear();
            JTable terrainsTable = terrainData.add(map.getTerrains());
            terrainsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    if (terrainsTable.getSelectedRow() > -1) {
                        comboBox.setSelectedIndex(terrainsTable.getSelectedRow());
                        text_nombre.setText(terrainsTable.getValueAt(terrainsTable.getSelectedRow(), 1).toString());
                        Integer rgb = map.getTerrains().get(terrainsTable.getSelectedRow()).getColorRGB();
                        lblColor.setBackground(new Color(rgb == null ? 0 : rgb));
                    }
                }
            });
            
            scrollPaneTerrains = new JScrollPane(terrainsTable);
            scrollPaneTerrains.setBounds(650, 55, 300, 170);
            contentPane.add(scrollPaneTerrains);
        }
        
        private void createPlayersTable() {
            JLabel lbl_playersTableTitle = new JLabel();
            lbl_playersTableTitle.setText("Seres");
            lbl_playersTableTitle.setBounds(650, 225, 300, 30);
            contentPane.add(lbl_playersTableTitle);
            
            playerData.clear();
            JTable playersTable = playerData.add(map.getPlayers());
            playersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    if (playersTable.getSelectedRow() > -1) {
                        String name = playersTable.getValueAt(id = playersTable.getSelectedRow(), 0).toString();
                        comboBox_Personaje.setSelectedItem(name);
                        Object camino;
                        if ((camino = playersTable.getValueAt(id, 1)) != null) {
                            comboBox_setCamino.setSelectedItem(camino);
                            text_peso.setText(playersTable.getValueAt(id, 2).toString());
                        }
                        lbl_player.setBackground(new Color(map.getPlayer(playersTable.getValueAt(id, 0).toString()).getImage().getRGB(size/2, size/2)));
                    }
                }
            });
            
            scrollPanePlayers = new JScrollPane(playersTable);
            scrollPanePlayers.setBounds(650, 255, 300, 170);
            contentPane.add(scrollPanePlayers);
            
            JButton btn_deletePlayer = new JButton();
            btn_deletePlayer.setText("Borrar Ser");
            btn_deletePlayer.setBounds(650, 395, 150, 30);
            contentPane.add(btn_deletePlayer);
            
            JButton btn_deleteWeight = new JButton();
            btn_deleteWeight.setText("Borrar Costo");
            btn_deleteWeight.setBounds(800, 395, 150, 30);
            contentPane.add(btn_deleteWeight);
        }

	public Select() {
            
            createTerrainsTable();
            createPlayersTable();
		
		//Container cont;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 480);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setBounds(177, 105, 1, 1);
		contentPane.add(list);
		
		text_nombre = new JTextField();
		text_nombre.setColumns(10);
		text_nombre.setBounds(346, 52, 86, 20);
		contentPane.add(text_nombre);
                
                comboBox = new JComboBox();
                comboBox_setCamino = new JComboBox();
                comboBox_Personaje = new JComboBox();
		
		//JComboBox comboBox = new JComboBox();
//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////COPY/////////////////////////////////////////////////////////
/////////////////////////////////////DOS/////////////////////////////////////////////////////////

                for (Terrain t : map.getTerrains()) {
                    comboBox.addItem(t.getName());
                }
                item = (int)comboBox.getSelectedIndex();
                
		comboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                    	if (comboBox.getItemCount() < 1) {
                    		return;
                    	}
                        item = (int)comboBox.getSelectedIndex();
                        Terrain t = map.getTerrains().get(item);
                        if (t.getImage() != null) {
                            Color c = new Color(t.getImage().getRGB(0, 0));
                            lblColor.setText("");
                            lblColor.setBackground(c);
                        }
                        else {
                            lblColor.setText("Color: Not set.");
                            colorM = null;
                        }
                        if (t.getName() != null) {
                            text_nombre.setText(t.getName());
                        }
                        else {
                            text_nombre.setText("");
                        }
			}
                }
		);
		
///////////////////////
		
		
		comboBox.setMaximumRowCount(100);
		comboBox.setBounds(25, 51, 86, 22);
		contentPane.add(comboBox);
                
		lblColor.setBounds(177, 85, 150, 36);
		contentPane.add(lblColor);
		
		JButton btnColor = new JButton("Color");
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ColorChooser ch=new ColorChooser(); 
				ch.actionPerformed(e);
                                if (ch.color != null) {
                                    lblColor.setText("");
                                    lblColor.setBackground(ch.color);
				
                                    colorM = ch.color;
                                }
				
				
			}
		});
		btnColor.setBounds(171, 51, 91, 23);
		contentPane.add(btnColor);
		//////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////COPY/////////////////////////////////////////////////////////
		/////////////////////////////////////Uno/////////////////////////////////////////////////////////
		JButton btnAgregar = new JButton("AgregarCamino");
                
                btnAgregar.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
                            if (colorM == null) {
                                JOptionPane.showMessageDialog(frame, "Select a color.");
                                return;
                            }
                            
                            if (map.getTerrains().stream().anyMatch((Terrain t) -> {
                                return t.getId() != map.getTerrains().get(item).getId() && ((t.getColorRGB() != null && t.getColorRGB().equals(colorM.getRGB())) || (t.getName() != null && t.getName().equals(text_nombre.getText())));
                            })) {
                                JOptionPane.showMessageDialog(frame, "No puede haber dos terrenos con el mismo nombre color.");
                                return;
                            }
				addTerrain(text_nombre.getText(), colorM.getRGB());
                                terrainData.clear();
                                scrollPaneTerrains = new JScrollPane(terrainData.add(map.getTerrains()));
                                playerData.clear();
                                scrollPanePlayers = new JScrollPane(playerData.add(map.getPlayers()));
				lblColor.setForeground(Color.BLACK);
				
				comboBox.removeAllItems();
				comboBox_setCamino.removeAllItems();
				for (Terrain t : map.getTerrains()) {
					comboBox.addItem(t.getName());
					comboBox_setCamino.addItem(t.getName());
				}
			}
		});
		btnAgregar.setBounds(458, 51, 142, 23);
		contentPane.add(btnAgregar);
		
		JButton btnTerminar = new JButton("Siguiente");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            if (map.getTerrains().stream().anyMatch((Terrain t) -> (t.getImage() == null || t.getName() == null))) {
                                JOptionPane.showMessageDialog(frame, "Nombre todos los terrenos y agregales una imagen antes de continuar.");
                                return;
                            }
                            if (map.getPlayers().isEmpty()) {
                                JOptionPane.showMessageDialog(frame, "Agregue al menos a un ser antes de continuar.");
                                return;
                            }
                            if (map.getPlayers().stream().anyMatch((Player p) -> (p.getWeights().isEmpty()))) {
                                JOptionPane.showMessageDialog(frame, "Agregue al menos a un ser antes de continuar.");
                                return;
                            }
                            
                            frame.setVisible(false);
                            frame.dispose();
                            SetStateMarksFrame.main();
			}
		});
		btnTerminar.setBounds(509, 368, 91, 23);
		contentPane.add(btnTerminar);
		
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception err) {
					err.printStackTrace();
				}
				map.reset();
                dispose();
			}
		});
		btnRegresar.setBounds(400, 368, 91, 23);
		contentPane.add(btnRegresar );
		
		JLabel lblCamino = new JLabel("Camino");
		lblCamino.setBounds(25, 20, 46, 14);
		contentPane.add(lblCamino);
		
		JLabel lblColorCamino = new JLabel("Color Camino");
		lblColorCamino.setBounds(177, 20, 86, 14);
		contentPane.add(lblColorCamino);
		
		JLabel lblNombeCamino = new JLabel("Nombe camino");
		lblNombeCamino.setBounds(346, 20, 86, 14);
		contentPane.add(lblNombeCamino);
		
		JLabel lblPersonaje_1 = new JLabel("Personaje");
		lblPersonaje_1.setBounds(25, 132, 86, 14);
		contentPane.add(lblPersonaje_1);
		
		JLabel lblCamino_1 = new JLabel("Camino");
		lblCamino_1.setBounds(140, 222, 86, 14);
		contentPane.add(lblCamino_1);
		
		//JComboBox comboBox_6 = new JComboBox();
		comboBox_setCamino.setMaximumRowCount(100);
		comboBox_setCamino.setBounds(140, 242, 86, 22);
		contentPane.add(comboBox_setCamino);
		
		
		
//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////HERE/////////////////////////////////////////////////////////////////
/////////////////////////////DOS/////////////////////////////////////////////////////////////////
		for (Terrain t : map.getTerrains()) {
                    comboBox_setCamino.addItem(t.getName());
                }
		item = 0;
                comboBox_setCamino.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        item = (int)comboBox_setCamino.getSelectedIndex();
                    }
                }
		);
		JLabel lblPeso = new JLabel("Peso");
		lblPeso.setBounds(249, 222, 86, 14);
		contentPane.add(lblPeso);
		
		JLabel lblColorPlayer = new JLabel("Color: Not set.");
		lblColorPlayer.setBounds(140, 172, 150, 36);
		contentPane.add(lblColorPlayer);
                
                JLabel lblPersonajeAddWeight = new JLabel("Personaje");
                lblPersonajeAddWeight.setBounds(25, 212, 86, 36);
                contentPane.add(lblPersonajeAddWeight);
                
                comboBox_Personaje.setBounds(25, 242, 74, 36);
                contentPane.add(comboBox_Personaje);
                
                text_peso = new JTextField();
		//text_peso.setColumns(10);
		text_peso.setBounds(249, 242, 86, 20);
		contentPane.add(text_peso);
                
                JButton button_agregarPeso = new JButton("Agregar Peso");
                button_agregarPeso.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if (comboBox_Personaje.getItemCount() == 0) {
                            JOptionPane.showMessageDialog(frame, "Primero añade al menos un ser.");
                            return;
                        }
                        if (!text_peso.getText().matches("^[0-9]+\\.?[0-9]{0,2}$")) {
                            JOptionPane.showMessageDialog(frame, "'" + text_peso.getText() + "'" + "no es un costo valido");
                            return;
                        }
                        
                        map.getPlayer((String)comboBox_Personaje.getSelectedItem()).addWeight(new Weight(Double.parseDouble(text_peso.getText()), map.getTerrains().get((int) comboBox_setCamino.getSelectedIndex())));
                        
                        playerData.clear();
                        scrollPanePlayers = new JScrollPane(playerData.add(map.getPlayers()));
                    }
                });
                button_agregarPeso.setBounds(450, 242, 150, 30);
                contentPane.add(button_agregarPeso);
                
                JButton button_borrarPeso = new JButton("Borrar Peso");
                button_borrarPeso.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if (playerData.getTable().getValueAt(id, 1) != null) {
                            map.getPlayer(playerData.getTable().getValueAt(id, 0).toString()).removeWeight((int) playerData.getTable().getValueAt(id, 1));
                        
                            playerData.clear();
                            scrollPanePlayers = new JScrollPane(playerData.add(map.getPlayers()));
                        }
                    }
                });
                button_borrarPeso.setBounds(450, 272, 150, 30);
                contentPane.add(button_borrarPeso);
                
		lbl_player = new JLabel();
		lbl_player.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_player.setBounds(346, 309, size, size);
		contentPane.add(lbl_player);
		

		
///////////////////////////////////////////////////////////////////////////////////////
/////////////////////////HERE/////////////////////////////////////////////////////////
/////////////////////////UNO/////////////////////////////////////////////////////////
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		JButton button_AgregarPersonaje = new JButton("Agregar Ser");
		button_AgregarPersonaje.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        if (colorP == null) {
                            JOptionPane.showMessageDialog(frame, "Select a color.");
                            return;
                        }
                        if (map.getTerrains().stream().anyMatch((Terrain t) -> {
                            return t.getColorRGB() != null && t.getColorRGB() == colorP.getRGB();
                        })) {
                            JOptionPane.showMessageDialog(frame, "El color del personaje debe ser diferente al de los terrenos.");
                            return;
                        }
                            
                        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = img.createGraphics();
                        g2d.setColor(colorP);
                        g2d.fill(new Ellipse2D.Double(0, 0, size, size));
                        g2d.dispose();
                            
                        try {
                            map.addPlayer(new Player(textPlayer.getText(), img));
                        } catch (RuntimeException rte) {
                            JOptionPane.showMessageDialog(frame, rte.getMessage());
                        }
                        
                        comboBox_Personaje.removeAllItems();
                        map.getPlayers().stream().forEach((p) -> {
                            comboBox_Personaje.addItem(p.getName());
                        });
                        playerData.clear();
                        scrollPanePlayers = new JScrollPane(playerData.add(map.getPlayers()));
                    }
		});
		button_AgregarPersonaje.setBounds(450, 144, 150, 30);
		contentPane.add(button_AgregarPersonaje);
		
		JButton button_borrarPersonaje = new JButton();
                button_borrarPersonaje.setText("Borrar Ser");
                button_borrarPersonaje.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if (id == -1) {
                            return;
                        }
                        System.out.println(playerData.getTable().getValueAt(id, 0).toString() + "?");
                        map.removePlayer(playerData.getTable().getValueAt(id, 0).toString());
                        
                        playerData.clear();
                        scrollPanePlayers = new JScrollPane(playerData.add(map.getPlayers()));

                        comboBox_Personaje.removeAllItems();
                        map.getPlayers().stream().forEach((p) -> {
                            comboBox_Personaje.addItem(p.getName());
                        });
                    }
                });
                button_borrarPersonaje.setBounds(450, 174, 150, 30);
                contentPane.add(button_borrarPersonaje);
		
		JLabel lbl_ColorPersonaje = new JLabel("Color Personaje");
		lbl_ColorPersonaje.setBounds(140, 132, 102, 14);
		contentPane.add(lbl_ColorPersonaje);
		
		
		
		JButton button_colorPlayer = new JButton("Color");
		button_colorPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent i) {

				ColorChooser ch=new ColorChooser();
				ch.actionPerformed(i);
                                
                                BufferedImage img = new BufferedImage(size/2, size/2, BufferedImage.TYPE_INT_RGB);
                                
                                Graphics2D g2d = img.createGraphics();
                                g2d.setColor(colorP = ch.color);
                                g2d.fill(new Ellipse2D.Double(0, 0, size, size));
                                g2d.dispose();
                                
                                ImageManipulator.setImage(img, lbl_player);
			}
		});
		button_colorPlayer.setBounds(140, 145, 91, 23);
		contentPane.add(button_colorPlayer);
		
		textPlayer = new JTextField();
		textPlayer.setColumns(10);
		textPlayer.setBounds(25, 146, 86, 20);
		contentPane.add(textPlayer);
		
		
	}
        
        private void addTerrain(String name, int rgb) {
            try {
                map.getTerrains().get(item).setName(name);
                map.getTerrains().get(item).setImage(rgb, size);
                
            }
            catch (RuntimeException rte) {
                JOptionPane.showMessageDialog(this, rte.getMessage());
            }
        }
}