package gui.window;


import gui.Circuit.Components.guiComponente;
import gui.Circuit.Components.guiLoadComponente;
import gui.facade.Facade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.MaskFormatter;

import dataAccess.ReadProperties;
import dataAccess.Readxml;
import dataAccess.SerialAccess;
import dataAccess.generarXml;
import logic.TablaDeVerdad.SimpleTable1;
import logic.lista.List;
import logic.lista.Node;


@SuppressWarnings("serial")
public class Home extends MainClass implements MenuListener, ActionListener, Runnable, MouseListener, KeyListener{
	
	private Graphics g;
	private boolean running = true;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	public static Facade facade;
	private JLabel bg;
	private JButton StartButton;
	private JPanel background,textPanel;
	private static JPanel paintPanel;
	private JPanel panel0,panel1,panelGO;
	private JMenuBar bar;
	private JMenu Archivo,Editar;
	private JMenuItem salir,nuevo,guardar,cargar,arduino,tabla;
	@SuppressWarnings("rawtypes")
	private JList componentMenu;
	private Icon AND_img,NAND_img,OR_img,NOR_img,NOT_img,XOR_img,XNOR_img,WIRE_img,WIRE2_img,WIRE3_img,light_img,Switch_img;
	private static Canvas canvasBG;
	private Font FontA,FontB;
	private double X1,X2,Y1,Y2;
	private static guiLoadComponente circuitoCargado;
	private static List<guiComponente> listaComponentesGUI= new List<>();
	private static guiComponente GUIComponenteTemporal;
	public static String strComponenteTemporal,strComponenteTemporalAnterior;
	private int contadorElementosGUI = 0;
	
	@SuppressWarnings("unused")
	private static SpringLayout LApaintPanel;
	
	//TODO
	private static boolean settingConnection = false;
	private static boolean changeFlagHome = false;
	private static String StringConecctingFalse = "Not Setting Connection";
	private static String StringConecctingTrue = "Setting Connection";
	private static String ChangingInputFalse = "Not Changing Input";
	private static String ChangingInputTrue = "Changing Input";
	private static JLabel ConecctingLabel, ChangingLabel;
	
	public static SerialAccess serialComunicationsInput;
	
	//Constructor de la clase
	public Home(String titulo) {
		super(titulo);
		run();
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void init(){
		ReadProperties.getInstance();
		
		facade = new Facade();
		//######################################## BARRA DE OPCIONES  ##################################################################
		//************************ FILE **************************
		Archivo = new JMenu("File");
		
		nuevo = new JMenuItem("Nuevo");
		nuevo.addActionListener(this);
		
		guardar = new JMenuItem("Guardar");
		guardar.addActionListener(this);
		
		cargar = new JMenuItem("Cargar");
		cargar.addActionListener(this);
		
		arduino = new JMenuItem("Conectarse a Arduino");
		arduino.addActionListener(this);
		
		tabla = new JMenuItem("Generar Tabla");
		tabla.addActionListener(this);
		
		salir = new JMenuItem("Salir");
		salir.addActionListener(this);
		
		
		Archivo.add(nuevo);
		Archivo.add(guardar);
		Archivo.add(cargar);
		Archivo.add(new JSeparator());
		Archivo.add(arduino);
		Archivo.add(tabla);
		Archivo.add(new JSeparator());
		Archivo.add(salir);
		
		//************************ EDIT **************************
		Editar = new JMenu("Edit");

		
		//************************ Creaci�n de la Barra **************************
		bar = new JMenuBar();
		bar.add(Archivo);
		bar.add(Editar);
		ventanaM.setJMenuBar(bar);
		
		//####################################################################################################################################
		
		//Crear Fondo (ContentPane)
		bg = new JLabel();
		bg.setOpaque(true);
		bg.setBackground(new Color(0,150,0));
		//bg.setPreferredSize(new Dimension(1200,700));
		

		FontA = new Font("Century Gothic", Font.BOLD, 2550);
		FontB = new Font("Century Gothic", Font.BOLD, 22);
		
		
		componentMenu = new JList();
		componentMenu.setCellRenderer(new ImageListCellRenderer());
		
		AND_img = new ImageIcon(Home.class.getResource("/imgs/and.gif"));	//AND
		JLabel AND_label = new JLabel("AND",AND_img,JLabel.LEFT);
		
		NAND_img = new ImageIcon(Home.class.getResource("/imgs/nand.gif"));//NAND
		JLabel NAND_label = new JLabel("NAND",NAND_img,JLabel.LEFT);
		
		NOR_img = new ImageIcon(Home.class.getResource("/imgs/nor.gif"));//NOR
		JLabel NOR_label = new JLabel("NOR",NOR_img,JLabel.LEFT);
		
		NOT_img = new ImageIcon(Home.class.getResource("/imgs/not.gif"));//NOT
		JLabel NOT_label = new JLabel("NOT",NOT_img,JLabel.LEFT);
		
		OR_img = new ImageIcon(Home.class.getResource("/imgs/or.gif"));//OR
		JLabel OR_label = new JLabel("OR",OR_img,JLabel.LEFT);
		
		XOR_img = new ImageIcon(Home.class.getResource("/imgs/xor.gif"));//XOR
		JLabel XOR_label = new JLabel("XOR",XOR_img,JLabel.LEFT);
		
		XNOR_img = new ImageIcon(Home.class.getResource("/imgs/xnor.gif"));//XOR
		JLabel XNOR_label = new JLabel("XNOR",XNOR_img,JLabel.LEFT);
		
		WIRE_img = new ImageIcon(Home.class.getResource("/imgs/wire_hor.gif"));	//WIRE
		JLabel WIRE_label = new JLabel("WIRE HORIZONTAL",WIRE_img,JLabel.LEFT);
		
		WIRE2_img = new ImageIcon(Home.class.getResource("/imgs/wire_hor.gif"));	//WIRE
		JLabel WIRE2_label = new JLabel("VERTICAL Ent - Sal",WIRE2_img,JLabel.LEFT);
		
		WIRE3_img = new ImageIcon(Home.class.getResource("/imgs/wire_hor.gif"));	//WIRE
		JLabel WIRE3_label = new JLabel("VERTICAL Sal - Ent",WIRE3_img,JLabel.LEFT);
		
		Switch_img = new ImageIcon(Home.class.getResource("/imgs/off.gif"));	//ON
		JLabel ON_label = new JLabel("Switch",Switch_img,JLabel.LEFT);
		
		light_img = new ImageIcon(Home.class.getResource("/imgs/light_OFF.gif"));	//LIGHT OFF
		JLabel light_label = new JLabel("Light Bulb",light_img,JLabel.LEFT);

		
		JPanel AND_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel NAND_panel= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel NOR_panel= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel NOT_panel= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel OR_panel= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel XOR_panel= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel XNOR_panel= new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		JPanel WIRE_panel= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel WIRE2_panel= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel WIRE3_panel= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ON_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel light_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		AND_panel.add(AND_label);
		NAND_panel.add(NAND_label);
		OR_panel.add(OR_label);
		NOR_panel.add(NOR_label);
		NOT_panel.add(NOT_label);
		XOR_panel.add(XOR_label);
		XNOR_panel.add(XNOR_label);
		WIRE_panel.add(WIRE_label);
		WIRE2_panel.add(WIRE2_label);
		WIRE3_panel.add(WIRE3_label);
		ON_panel.add(ON_label);
		light_panel.add(light_label);
		
		Object[] panels = {AND_panel,NAND_panel,OR_panel,NOR_panel,NOT_panel,XOR_panel,XNOR_panel,
						   WIRE_panel,WIRE2_panel,WIRE3_panel, ON_panel, light_panel};
		
		componentMenu.setListData(panels);
		
		
		//BUTON DE INICIO DE CIRCUITO
		StartButton = new JButton();
		StartButton.setPreferredSize(new Dimension(100,25));
		StartButton.setFont(FontB);
		StartButton.setBackground(Color.RED);
		StartButton.setText("RUN");
		StartButton.addActionListener(this);
		StartButton.setEnabled(true);
		
		// JPANEL DE FONDO
		background = new JPanel();
		background.setLayout(new BoxLayout(background, BoxLayout.X_AXIS));
		
		SpringLayout LAPanel0 = new SpringLayout();
		SpringLayout LAPanel1 = new SpringLayout();
		SpringLayout LACanvas = new SpringLayout();
		SpringLayout LAPanel7 = new SpringLayout();
		
		//TEXT PANEL
		textPanel = new JPanel();
		textPanel.setBackground(Color.white);
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		// TEXT PANEL - PANEL SUPERIOR 
		panel0 = new JPanel();
		panel0.setBackground(new Color(200,233,165));
		panel0.setMaximumSize(new Dimension(1200,42000));
		panel0.setLayout(LAPanel0);
		panel0.add(componentMenu);
		
		LAPanel0.putConstraint(SpringLayout.NORTH, componentMenu, 15, SpringLayout.NORTH, panel0);
		
		// TEXT PANEL - PANEL CENTRAL
		ConecctingLabel = new JLabel();
		ConecctingLabel.setFont(FontB);
	    ConecctingLabel.setText(StringConecctingFalse);
	    ChangingLabel = new JLabel();
	    ChangingLabel.setFont(FontB);
	    ChangingLabel.setText(ChangingInputFalse);
	    
		panel1 = new JPanel();
		panel1.setBackground(new Color(130,139,239));
		panel1.setMaximumSize(new Dimension(1200,5000));
		panel1.add(ConecctingLabel);
		panel1.add(ChangingLabel);
		panel1.setLayout(LAPanel1);
		
		LAPanel1.putConstraint(SpringLayout.NORTH, ConecctingLabel, 5, SpringLayout.NORTH, panel1);
		LAPanel1.putConstraint(SpringLayout.WEST, ConecctingLabel, 5, SpringLayout.WEST, panel1);
		LAPanel1.putConstraint(SpringLayout.NORTH, ChangingLabel, 30, SpringLayout.NORTH, panel1);
		LAPanel1.putConstraint(SpringLayout.WEST, ChangingLabel, 5, SpringLayout.WEST, panel1);
		
		// TEXT PANEL - PANEL INFERIOR
		panelGO = new JPanel();
		panelGO.setBackground(new Color(200,233,165));
		panelGO.setMaximumSize(new Dimension(1200,10000));
		panelGO.setLayout(LAPanel7);
		panelGO.add(StartButton);
		LAPanel7.putConstraint(SpringLayout.NORTH, StartButton, 50, SpringLayout.NORTH, panelGO);
		LAPanel7.putConstraint(SpringLayout.WEST, StartButton, 85, SpringLayout.WEST, panelGO);
		
		//A�adir Panels a TEXT PANEL
		textPanel.add(panel1);
		textPanel.add(panel0);
		textPanel.add(panelGO);
		
		// PAINT PANEL CANVAS
		canvasBG = new Canvas();
	    canvasBG.addMouseListener(this);

	    paintPanel = new JPanel();
		paintPanel.setBackground(Color.lightGray);
		paintPanel.setMaximumSize(new Dimension(4000,1300));
		paintPanel.setLayout(LACanvas);
		paintPanel.add(canvasBG);
		LACanvas.putConstraint(SpringLayout.NORTH, canvasBG, 5, SpringLayout.NORTH, paintPanel);
		LACanvas.putConstraint(SpringLayout.WEST, canvasBG, 5, SpringLayout.WEST, paintPanel);
		
		background.add(textPanel);
		background.add(paintPanel);
		//************** CONTAINERS FILL *************************
		ventanaM.add(background, 0);
		ventanaM.addKeyListener(this);
		canvasBG.addKeyListener(this);
		paintPanel.addKeyListener(this);
		canvasBG.requestFocus();
	}
	
	//################################################################################
	//**************************CLASE PARA PINTAR CANVAS******************************
	//################################################################################
	class Canvas extends JPanel {
		
		
		public Canvas(){
			setPreferredSize(new Dimension(905,634));
			GroupLayout _CanvasLayout = new GroupLayout(this);
			setLayout(_CanvasLayout);
		}
		@Override
		public void setPreferredSize(Dimension arg0) {
			super.setPreferredSize(arg0);
		}
	    public void drawGrid(Graphics g) { // A�ADE UN GRID COMO FONDO DEL JPANEL
	        super.paintComponent(g);
	    }
	    
	    @Override
	    public void repaint(){
	    	
	    }
	    
	    public void drawWire(Graphics g){
	    	g.setFont(FontA);
	    	g.setColor(Color.RED);
	    	g.drawLine((int)X1, (int)Y1, (int)X2, (int)Y2);
	    	g.drawLine((int)X1+1, (int)Y1, (int)X2+1, (int)Y2);
	    	g.drawLine((int)X1, (int)Y1+1, (int)X2, (int)Y2+1);
	    	g.drawLine((int)X1+1, (int)Y1+1, (int)X2+1, (int)Y2+1);
	    }
	    
	    public void drawCompuerta(Graphics g,Image img){
	    	g.drawImage(img, (int)X1, (int)Y1, null);
	    }
	}
	//################################################################################
	//################################################################################
	
	
	//*******************************************************************************************************
	//******************************************** METODOS **************************************************
	//*******************************************************************************************************
	
	public static guiComponente findComponent(String pID){
		Node<guiComponente> tmp = listaComponentesGUI.getHead();
		while (tmp != listaComponentesGUI.getPointer()){
			if (tmp.getData().getNombre().equals(pID)){
				break;
			}
			else{
				tmp = tmp.getNext();
			}
		}
		return tmp.getData();	
	} 
	
	@Override
	public void mousePressed(MouseEvent e) {
		X1 = e.getPoint().getX();
		Y1 = e.getPoint().getY();
		canvasBG.requestFocus();
		
	}
	
	public void mouseDragged(MouseEvent e){
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		X2 = e.getPoint().getX();
		Y2 = e.getPoint().getY();
		if(changeFlagHome == false){
			switch(componentMenu.getSelectedIndex()){
			case 0:
				//Seccion Gr�fica
				guiComponente tmpAND = new guiComponente("compuerta"+contadorElementosGUI,"And");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpAND);
				tmpAND.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpAND);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("And","compuerta"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
			case 1:
				guiComponente tmpNAND = new guiComponente("compuerta"+contadorElementosGUI,"Nand");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpNAND);
				tmpNAND.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpNAND);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("Nand","compuerta"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
			case 2:
				guiComponente tmpOR = new guiComponente("compuerta"+contadorElementosGUI,"Or");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpOR);
				tmpOR.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpOR);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("Or","compuerta"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
			case 3:
				guiComponente tmpNOR = new guiComponente("compuerta"+contadorElementosGUI,"Nor");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpNOR);
				tmpNOR.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpNOR);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("Nor","compuerta"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
			case 4:
				guiComponente tmpNOT = new guiComponente("compuerta"+contadorElementosGUI,"Not");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpNOT);
				tmpNOT.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpNOT);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("Not","compuerta"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
			case 5:
				guiComponente tmpXOR = new guiComponente("compuerta"+contadorElementosGUI,"Xor");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpXOR);
				tmpXOR.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpXOR);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("Xor","compuerta"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
			case 6:
				guiComponente tmpXNOR = new guiComponente("compuerta"+contadorElementosGUI,"Xnor");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpXNOR);
				tmpXNOR.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpXNOR);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("Xnor","compuerta"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
			case 7:
				guiComponente tmpWIRE = new guiComponente("compuerta"+contadorElementosGUI,"Wire");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpWIRE);
				tmpWIRE.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpWIRE);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("Wire","compuerta"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
			case 8:
				guiComponente tmpWIRE2 = new guiComponente("compuerta"+contadorElementosGUI,"Wire2");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpWIRE2);
				tmpWIRE2.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpWIRE2);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("Wire2","compuerta"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
				
			case 9:
				guiComponente tmpWIRE3 = new guiComponente("compuerta"+contadorElementosGUI,"Wire3");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpWIRE3);
				tmpWIRE3.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpWIRE3);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("Wire3","compuerta"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
				
			case 10:
				guiComponente tmpOff = new guiComponente("compuerta"+contadorElementosGUI,"Off");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpOff);
				tmpOff.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpOff);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("Off","Switch"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
			case 11:
				guiComponente tmpLight = new guiComponente("compuerta"+contadorElementosGUI,"LightOFF");
				strComponenteTemporal = "compuerta"+contadorElementosGUI;
				listaComponentesGUI.insertPointer(tmpLight);
				tmpLight.setCoordenadas((int) X2, (int) Y2);
				canvasBG.add(tmpLight);
				paintPanel.repaint();
				//Secci�n L�gica
				facade.crearCompuerta("LightOFF","LightBulb"+contadorElementosGUI);
				contadorElementosGUI++;
				break;
			}
			paintPanel.repaint();
		}				
	}
	
	@Override
	public void menuSelected(MenuEvent me){
		if(me.getSource().equals(salir)){
			System.exit(0);
		}
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == 27){
			System.exit(0);
		}
		if(e.getKeyCode() == 127){ //BORRADO
			
			if(strComponenteTemporal == "CircuitoCG" ){
				canvasBG.remove(circuitoCargado);
				paintPanel.repaint();
			}
			else{
				@SuppressWarnings("unused")
				boolean deleteApproved = false;
				Node<guiComponente> tmp = listaComponentesGUI.getHead();
				for(int i = 0; i <= listaComponentesGUI.length(); i++){
					if(tmp.getData().getNombre() == strComponenteTemporal){
						deleteApproved = true;
						break;
					}
					else{
						tmp = tmp.getNext();
					}
				}
				
				if(deleteApproved = true){
					deleteApproved = false;
					GUIComponenteTemporal = tmp.getData();
					listaComponentesGUI.delete(tmp.getData());
					canvasBG.remove(GUIComponenteTemporal);
					facade.eliminarCompuerta(strComponenteTemporal);
					paintPanel.repaint();
				}
			}
			
		}
		if(e.getKeyCode() == 67){
			GUIComponenteTemporal = findComponent(strComponenteTemporal);
			if(settingConnection == false){
				settingConnection = true;
				ConecctingLabel.setText(StringConecctingTrue);
			}
			else{
				settingConnection = false;
				ConecctingLabel.setText(StringConecctingFalse);
			}
		}
		if(e.getKeyCode() == 83){ // S, Change Switch
			guiComponente tmp = findComponent(strComponenteTemporal);
			if(tmp.getTipo() == "On" || tmp.getTipo() == "Off"){
				if(tmp.getChangeFlag() == false){
					tmp.setChangeFlag(true);
					ChangingLabel.setText(ChangingInputTrue);
				}
				else{
					tmp.setChangeFlag(false);
					ChangingLabel.setText(ChangingInputFalse);
				}
				// Cambiar Bandera ChangeFlagHome
				
				if(changeFlagHome == false){
					changeFlagHome = true;
				}
				else{
					changeFlagHome = false;
				}
			}
		}
		
	}
	
	
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(salir)){
			System.exit(0);
		}
		if (e.getSource().equals(tabla)){
			SimpleTable1 frame = new SimpleTable1(); 
			frame.pack(); 
			frame.setVisible(true); 
		}
		if (e.getSource().equals(nuevo)){
			
		}
		if(e.getSource().equals(arduino)){
			if(strComponenteTemporal != null){
				guiComponente tmp = findComponent(strComponenteTemporal);
				
				if(tmp.getTipo() == "On" || tmp.getTipo() == "Off" || tmp.getTipo() == "LightOFF" || tmp.getTipo() == "LightON" ||
				   tmp.getTipo() == "Wire" || tmp.getTipo() == "Wire2" || tmp.getTipo() == "Wire3"|| tmp.getTipo() == "Not"){
					
					JOptionPane message = new JOptionPane();
					message.showMessageDialog(null,"Seleccione una compuerta v�lida", "Atenci�n",  JOptionPane.ERROR_MESSAGE);
				}
				else{
					serialComunicationsInput = new SerialAccess();
					serialComunicationsInput.run(strComponenteTemporal,facade);
				}
			}
			else{
				JOptionPane message = new JOptionPane();
				message.showMessageDialog(null,"No ha seleccionado una compuerta", "Atenci�n",  JOptionPane.ERROR_MESSAGE);
			}
			
			
			
			
			
			
		}
		if (e.getSource().equals(guardar)){
			facade.ejecutar(); 				//EJECUTA LA SIMULACION DEL CIRCUITO PARA OBTENER LAS ENTRADAS Y SALIDAS CORRESPONDIENTES
			generarXml archivo = new generarXml(); //Instancia un archivo xml
			
			ArrayList<String> Id = new ArrayList<>();
	        ArrayList<String> descripcion = new ArrayList<>();
	        ArrayList<Integer> entradas = new ArrayList<>();
	        ArrayList<Integer> salidas = new ArrayList<>();
	        ArrayList<Integer> numComponentes = new ArrayList<>();
	        ArrayList<String> compuertas = new ArrayList<>();
			
			String fileName = JOptionPane.showInputDialog("Enter file name:");
			
			descripcion.add(JOptionPane.showInputDialog("Enter file description:"));
			entradas.add(facade.getCantidadEntradas());
			salidas.add(facade.getCantidadSalidas());
			numComponentes.add(facade.getCantidadDeComponentes());
			compuertas = facade.getCompuertas();
			
			try {
				archivo.generarArchivoXML(fileName, descripcion, numComponentes, entradas, salidas,compuertas);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			JOptionPane message = new JOptionPane();
			message.showMessageDialog(null,"Archivo guardado con �xito", "",  JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		if(e.getSource().equals(cargar)){
			String fileName = JOptionPane.showInputDialog("Enter file name:");
			Readxml p = new Readxml(fileName,facade);
			paintPanel.repaint();
		}
		
		if(e.getSource().equals(StartButton)){
			System.out.println("SIMULACI�N CIRCUITO");
			facade.ejecutar();
		}
		
	}
	
	@Override
	public void run(){
		init();
		//
		long elapsed;
		long wait;
		long start;
		
		//loop del Juego
		while(running){
			start = System.nanoTime();
			update();
			paintComponent(g);
			
			elapsed = System.nanoTime()-start;
			
			wait = targetTime - elapsed / 1000000; //Porque esta en ms y ns.
			if (wait < 0) wait = 5;
			try{
				Thread.sleep(wait);
			}
			
			catch(Exception e){
				e.printStackTrace(); // Para detectar Posibles Errores
				
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
	}
	public void update(){
		
	}
	
	protected MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
	
	
	//M�todo heredados pero innecesarios...
	@Override
	public void keyReleased(KeyEvent me) {}
	@Override
	public void keyTyped(KeyEvent me) {}
	@Override
	public void menuCanceled(MenuEvent me) {}
	@Override
	public void menuDeselected(MenuEvent me) {}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}


	public static void rePaintCanvas() {
		paintPanel.repaint();
		
	}


	public void checkCollision(String pID){
		System.out.println("CHECK");
		guiComponente componenteAVerificar = findComponent(pID);
		Node<guiComponente> tmp = listaComponentesGUI.getHead();
		
		System.out.println("Entradax1: "+componenteAVerificar.getEntradaX1());
		System.out.println("Entraday1: "+componenteAVerificar.getEntradaY1());
		/*System.out.println("tmpX1: "+tmp.getData().getEntradaX1());
		System.out.println("tmpY1: "+tmp.getData().getEntradaY1());*/
		
		System.out.println("tmpX1: "+tmp.getData().getEntradaX1());
		
		for(int i = 0; i < listaComponentesGUI.length(); i++){
			if(componenteAVerificar.getEntradaX1()-15 < tmp.getData().getEntradaX1() 
					&& tmp.getData().getEntradaX1() < componenteAVerificar.getEntradaX1()+15){
				System.out.println("TRUE");
				componenteAVerificar.setX((int)tmp.getData().getEntradaX1());
				componenteAVerificar.setY((int)tmp.getData().getEntradaY1());
			}
			
			
			tmp = tmp.getNext();
					
		}
		
		
	}
	
	
	public static boolean getSettingConnection(){
		return settingConnection;
	}
	public static guiComponente getSelectedComponente(){
		return GUIComponenteTemporal;
	}


	public static void settedConnection() {
		settingConnection = false;
		ConecctingLabel.setText(StringConecctingFalse);
		
	}

	public static boolean getChangingConecction(){
		return changeFlagHome;
	}
	public static Facade getFacade() {
		return facade;
	}

	public static void cargarCircuito(List<String> listaDescripcionOUTPUT,
			List<String> listaEntradasOUTPUT, List<String> listaSalidasOUTPUT,
			List<String> listaDeCompuertasOUTPUT) {
		
		circuitoCargado = new guiLoadComponente("CircuitoCG","Compuerta",listaDescripcionOUTPUT,
														listaEntradasOUTPUT,listaSalidasOUTPUT,listaDeCompuertasOUTPUT);
		
		canvasBG.add(circuitoCargado);
		guiComponente temp2 = new guiComponente("CircuitoCG","Compuerta");
		listaComponentesGUI.insertPointer(temp2);
		
	}
	
	public static JPanel getPaintPanel(){
		return paintPanel;
	}


	public static void cargarInformacionDeCircuito(String nombre, List<String> descripcion, List<String> entradas, 
												   List<String> salidas, List<String> compuertas) {
		
		JFrame informationWindow = new JFrame();
		
		informationWindow = new JFrame("Informaci�n de Circuito");
		Dimension d = new Dimension();
		informationWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		informationWindow.setResizable(false);
		informationWindow.setLocation((int)((d.getWidth()+2)+70),15);
		informationWindow.setSize(650,550);
		informationWindow.setVisible(true);
		
		Font font = new Font("Arial",Font.PLAIN, 22);
		
		
		JPanel background = new JPanel();
		background.setLayout(new BoxLayout(background, BoxLayout.X_AXIS));
		
		informationWindow.add(background);
		
		
		JLabel nombreLabel = new JLabel();
		nombreLabel.setText("Nombre: " + nombre);
		nombreLabel.setFont(font);
		
		JLabel descripcionLabel = new JLabel();
		descripcionLabel.setText("Descripci�n: " + descripcion.getHead().getData());
		descripcionLabel.setFont(font);
		
		JLabel entradasLabel = new JLabel();
		entradasLabel.setText("Cantidad de Entradas: " + entradas.getHead().getData());
		entradasLabel.setFont(font);
		
		JLabel salidasLabel = new JLabel();
		salidasLabel.setText("Cantidad de Salidas: " + salidas.getHead().getData());
		salidasLabel.setFont(font);
		
		JLabel compuertasText = new JLabel();
		compuertasText.setText("Compuertas: ");
		compuertasText.setFont(font);
		
		JLabel enter = new JLabel();
		enter.setText(" ");
		enter.setFont(font);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(200,233,165));
		panel.setMaximumSize(new Dimension(1200,12000));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(nombreLabel);
		panel.add(enter);
		panel.add(descripcionLabel);
		panel.add(entradasLabel);
		panel.add(salidasLabel);
		panel.add(compuertasText);
		
		Node<String> compuertaActual = compuertas.getHead();
		for(int j = 0; j < compuertas.length(); j++){
			JLabel temporal = new JLabel();
			temporal.setText(compuertaActual.getData());
			temporal.setFont(font);
			panel.add(temporal);
			compuertaActual = compuertaActual.getNext();
		}
		
		
		
		background.add(panel);
		
		
	}
}
