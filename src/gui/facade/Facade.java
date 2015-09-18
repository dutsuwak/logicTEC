package gui.facade;


import java.util.ArrayList;

import gui.Circuit.Components.guiComponente;
import gui.window.Home;
import logic.Circuit.Circuit.Circuit;
import logic.Circuit.Components.*;
import logic.lista.List;
import logic.lista.Node;

public class Facade {
	public static Circuit circuito;
	//IComponents nuevaCompuertaa;
	
	public Facade(){
		circuito = new Circuit();
	}
	
	public void crearCompuerta(String pTipo, String  pID){
		circuito.AddComponentes(pTipo, pID);
		//System.out.println(circuito.getCantidadDeComponentes());
	}
	
	public void switchValor(String pID,int pValor, int pPatilla){
		circuito.cambiarValor(pID, pValor, pPatilla);
	}
	
	
	public void eliminarCompuerta(String pCompuerta)
	{
		circuito.eliminarComponentes(pCompuerta);
		//System.out.println(circuito.getCantidadDeComponentes());
	}
	
	public void setConection(String pIDsalida,int pNumeroSalida,String pIDentrada, int pPatilla)
	{
		circuito.setConection(pIDsalida, pNumeroSalida, pIDentrada, pPatilla);
	}

	public static void setComponenteActual(String nombre) {
		if(getComponenteActual() == nombre){
			Home.strComponenteTemporal = nombre;
		}
		else{
			Home.strComponenteTemporalAnterior = Home.strComponenteTemporal;
			Home.strComponenteTemporal = nombre;
			guiComponente a = Home.findComponent(Home.strComponenteTemporalAnterior);
			a.setBorderNull();
		}
		
	}
	
	public static String getComponenteActual(){
		String tmp = Home.strComponenteTemporal;
		return tmp;
	}
	public static void rePaintCanvas(){
		Home.rePaintCanvas();
	}

	public void ejecutar() {
		circuito.comprobarCircuito();
		
	}

	public void changeSwitchType(String bool, String nombre){
		Componente tmp = circuito.findComponent(nombre);
		
		tmp.setTipo(bool);
		
	}
	
	public boolean getChangingInput(){
		return Home.getChangingConecction();
	}

	public void changeLightImg(String pID) {
		guiComponente tmp = Home.findComponent(pID);
		tmp.changeLightImg();
	}

	public void print(String pID) {
		Componente tmp = circuito.findComponent(pID);
		System.out.println("ENTRADA 0: " + tmp.getIn(0));
		System.out.println("ENTRADA 1: " + tmp.getIn(1));
	}

	public int getOutput(String pID) {
		Componente tmp = circuito.findComponent(pID);
		tmp.logicOperation(this);
		return tmp.getOut();
	}

	public Integer getCantidadEntradas() {
		return circuito.getListaEntradas().length();
	}
	
	public Integer getCantidadSalidas() {
		return circuito.getListaSalidas().length();
	}
	
	public Integer getCantidadDeComponentes(){
		return circuito.getCantidadDeComponentes();
	}

	public ArrayList<String> getCompuertas() {
		List<Componente> listaComponentesTemporal = circuito.getListaComponentes();
		ArrayList<String> listaComponentesFinal = new ArrayList<>();
		
		Node<Componente> tmp = listaComponentesTemporal.getHead();
		for(int i = 0; i < listaComponentesTemporal.length(); i++){
			listaComponentesFinal.add("ID: " + tmp.getData().get_ID() + " " + "Tipo: " + tmp.getData().getTipo());
			tmp = tmp.getNext();
		}
		
		
		return listaComponentesFinal;
	}

	public void cargarCircuito(List<String> listaDescripcionOUTPUT,List<String> listaEntradasOUTPUT, 
			List<String> listaSalidasOUTPUT,List<String> listaDeCompuertasOUTPUT) {
		
		Home.cargarCircuito(listaDescripcionOUTPUT,listaEntradasOUTPUT,listaSalidasOUTPUT,listaDeCompuertasOUTPUT);
	}

	public void cargarInformacionDeCircuito(String nombre, List<String> descripcion, List<String> entradas, 
											List<String> salidas, List<String> compuertas) {
		
		Home.cargarInformacionDeCircuito(nombre,descripcion,entradas,salidas,compuertas);
		
	}

	
}
