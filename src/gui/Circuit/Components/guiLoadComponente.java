package gui.Circuit.Components;

import gui.facade.Facade;
import gui.window.Home;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dataAccess.ReadProperties;
import logic.lista.List;

@SuppressWarnings("serial")
public class guiLoadComponente extends JLabel implements MouseListener, MouseMotionListener {
	
	private String nombre = "";
	private String tipo = "";
	private Point posicion = new Point(250,250);
	private Dimension d = new Dimension(90,60);
	private int x = 1;
	private int y = 1;
	private Point startPoint, startDrag, offset;
	private boolean changeFlag = false;
	public List<String> descripcion,entradas,salidas,compuertas;
	
	private int clicks = 0;
	
	public guiLoadComponente(String pNombre, String pTipo,
			List<String> listaDescripcion,List<String> listaEntradas, 
			List<String> listaSalidas,List<String> listaDeCompuertas){
		
		super();
		nombre = pNombre;
		this.setIcon(new ImageIcon(getClass().getResource(ReadProperties.file.getSetting("img_circuito"))));
		tipo = pTipo;
		
		descripcion = listaDescripcion;
		entradas = listaEntradas;
		salidas = listaSalidas;
		compuertas = listaDeCompuertas;
		
		
		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.setSize(d);
		this.setPreferredSize(d);
		this.setLocation(posicion);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
	}
	
	
	public String getNombre(){
		return nombre;
	}
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point current = this.getScreenLocation(arg0);
		offset = new Point((int)current.getX() - (int) startDrag.getX(), (int) current.getY() - (int) startDrag.getY());
		Point newLocation = new Point((int) (this.startPoint.getX() + offset.getX()), (int) (this.startPoint.getY() + offset.getY()));
		this.setLocation(newLocation);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Facade facade = Home.getFacade();
		Facade.setComponenteActual(nombre);
		if(clicks >= 1){
			clicks = 0;
			facade.cargarInformacionDeCircuito(nombre,descripcion,entradas,salidas,compuertas);
		}
		else{
			clicks++;
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setBorder(BorderFactory.createLineBorder(new Color(255,0,0)));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if(Facade.getComponenteActual() == nombre){
			this.setBorder(BorderFactory.createLineBorder(new Color(0,255,0)));
		}
		else{
			this.setBorder(null);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.startDrag = getScreenLocation(arg0);
		this.startPoint = this.getLocation();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		x = this.getLocation().x;
		y = this.getLocation().y;
		this.setLocation(x, y);
		
	}
	
	
	private Point getScreenLocation(MouseEvent evt){
		Point cursor = evt.getPoint();
		Point targetLocation = this.getLocationOnScreen();
		
		return new Point((int) (targetLocation.getX() + cursor.getX()), (int)(targetLocation.getY() + cursor.getY()));
	}

	
	public void setBorderNull(){
		this.setBorder(null);
	}
}
 