package logic.TablaDeVerdad;
import javax.swing.JTable; 
import javax.swing.JScrollPane; 
import javax.swing.JFrame; 
import java.awt.*; 
import java.awt.event.*;

@SuppressWarnings("serial")
public class SimpleTable1 extends JFrame {
	
	private String[] nombreColumnas= { "ENTRADA2","ENTRADA3", "SALIDA"};
	@SuppressWarnings("unused")
	private int columnaSalida=3;
	private JTable table; 
	
	public SimpleTable1() {
		super("Tabla De Verdad");
		
		//Array bidimensional de objetos con los datos de la tabla 
	Object[][] data = { 
	{0, 0, 0}, 
	{0, 1, 1}, 
	{1, 0, 0}, 
	{1, 1, 0},  
	};
	
	
	//Array de ‘String’ con los titulos de las columnas 
	
	
	
	//Creacion de la tabla 
	table = new JTable(data, nombreColumnas); 
	table.setPreferredScrollableViewportSize(new Dimension(500, 80));
	
	//Creamos un scrollpanel y se lo agregamos a la tabla 
	JScrollPane scrollpane = new JScrollPane(table);
	
	//Agregamos el scrollpanel al contenedor 
	getContentPane().add(scrollpane, BorderLayout.CENTER);
	
	//manejamos la salida 
	addWindowListener(new WindowAdapter() {
	
	public void windowClosing(WindowEvent e) { 
	System.exit(0); 
	} 
	}); 
	}
	
	public static void main(String ar[]) { 
	SimpleTable1 frame = new SimpleTable1(); 
	frame.pack(); 
	frame.setVisible(true); 
	} 
}
