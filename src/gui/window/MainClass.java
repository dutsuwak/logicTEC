package gui.window;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class MainClass extends JFrame {
	
	JFrame ventanaM;
  public static void main(String args[]) {
	@SuppressWarnings("unused")
	Home ventana = new Home("Editos de Diagramas Lógicos");
    
  }
  public MainClass(String título){
	  ventanaM = new JFrame(título);
	  Dimension d = new Dimension(); //Objeto para obtener tamaño de la pantalla
	  //ImageIcon im = new ImageIcon("src");
	  
	  
	  ventanaM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  //ventanaM.setIconImage(im.getImage());
	  ventanaM.setResizable(false);
	  ventanaM.setLocation((int)((d.getWidth()+2)+70),15);
	  ventanaM.setSize(1200,700);
		
	  init();
	  
	  ventanaM.setVisible(true);


  }
  
  public void init(){
	  
  }
  public void paintComponent(Graphics g) {
	  // TODO Auto-generated method stub
	
  }
}