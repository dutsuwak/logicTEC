package logic.Circuit.Circuit;

import gui.window.Home;
import logic.Circuit.Components.Componente;
import logic.TablaDeVerdad.TablaDeVerdad;
import logic.array.Array;
import logic.lista.List;
import logic.lista.Node;

public class Circuit {
	
	
	private List<Componente> listaComponentes;
	//private Componente compuerta;
	private List<String> listaEntradas;
	private List<String> listaSalidas;
	
	public Circuit()
	{
		listaComponentes = new List<Componente>();
		listaEntradas = new List<>();
		listaSalidas = new List<>();
	}
	
	
	public void AddComponentes(String pTipo, String pID) 
	{
		Componente compuerta = new Componente(pTipo,pID);
		listaComponentes.insertPointer(compuerta);
		
	}
		
	
	public void eliminarComponentes(String pComponente){
		Componente tmp = findComponent(pComponente);
		//System.out.println(tmp.get_ID());
		listaComponentes.delete(tmp);
		listaEntradas.deleteAll();
		listaSalidas.deleteAll();
	}
	
	
	public void comprobarCircuito()
	{
		Node<Componente> tmp = listaComponentes.getHead();
		while (tmp != listaComponentes.getPointer()){
			tmp.getData().logicOperation(Home.getFacade());
			Node<Array> nodoE = tmp.getData().getListaEntradas().getHead();
			while(nodoE != tmp.getData().getListaEntradas().getPointer()){
				//System.out.println(tmp.getData().get_ID() + " " + nodoE.getData().get_Value(1) + " entrada");
				if(nodoE.getData().get_Value(1) == 7)
					listaEntradas.insertPointer(nodoE.getData().get_Value(0) +" " +tmp.getData().get_ID());
				nodoE = nodoE.getNext();
			}
			//System.out.println(tmp.getData().get_ID() + " " + nodoE.getData().get_Value(1) + " entrada");
			if(nodoE.getData().get_Value(1) == 7)
				listaEntradas.insertPointer(nodoE.getData().get_Value(0)+" " +tmp.getData().get_ID());
			//System.out.println(tmp.getData().get_ID()+ "" + tmp.getData().getListaSalidas().getHead().getData().get_Value(0) + " salida");
			if(tmp.getData().getListaSalidas().getHead().getData().get_Value(1) == 7){
				listaSalidas.insertPointer(tmp.getData().getListaSalidas().getHead().getData().get_Value(0)+" " +tmp.getData().get_ID());
			}
			tmp = tmp.getNext();
		}
		tmp.getData().logicOperation(Home.getFacade());
		Node<Array> nodoE = tmp.getData().getListaEntradas().getHead();
		while(nodoE != tmp.getData().getListaEntradas().getPointer()){
			//System.out.println(tmp.getData().get_ID() + " " + nodoE.getData().get_Value(1) + " entrada");
			if(nodoE.getData().get_Value(1) == 7)
				listaEntradas.insertPointer(nodoE.getData().get_Value(0)+" " +tmp.getData().get_ID());
			nodoE = nodoE.getNext();
		}
		//System.out.println(tmp.getData().get_ID() + " " + nodoE.getData().get_Value(1) + " entrada");
		if(nodoE.getData().get_Value(1) == 7)
			listaEntradas.insertPointer(nodoE.getData().get_Value(0)+" " +tmp.getData().get_ID());
		//System.out.println(tmp.getData().get_ID()+ "" + tmp.getData().getListaSalidas().getHead().getData().get_Value(0)+ " salida");
		if(tmp.getData().getListaSalidas().getHead().getData().get_Value(1) == 7){
			listaSalidas.insertPointer(tmp.getData().getListaSalidas().getHead().getData().get_Value(0)+" "+tmp.getData().get_ID());
		}
		
		
		//System.out.println("TAMAÑO: " + listaEntradas.length());
		System.out.println("*****ENTRADAS DEL CIRCUITO*****");
		listaEntradas.showData();
		System.out.println("*****SALIDAS DEL CIRCUITO*****");
		listaSalidas.showData();
	}
	
	
	public void setConection (String pIDsalida,int pNumeroSalida,String pIDentrada, int pPatilla ){
		Componente source = findComponent(pIDsalida);
		Componente target = findComponent(pIDentrada);
		
		source.logicOperation(Home.getFacade());

		if(target.getTipo().equals("Wire")||target.getTipo().equals("Wire2") ||target.getTipo().equals("Wire3") ){
			source.setOutC(8);
			target.setIn(pPatilla, source.getOut());
			target.setInV(pPatilla , 8);
			target.setOutC(8);
			
		}
		else if(source.getTipo().equals("On") || source.getTipo().equals("Off")){
			if(source.getTipo().equals("On")){
				source.setIn(0, 1);
			}
			target.setIn(pPatilla, source.getOut());
			source.setInV(0 , 8);
			source.setOutC(8);
		}
		else if(target.getTipo().equals("LightOFF") || target.getTipo().equals("LightON")){
			target.setIn(pPatilla, source.getOut());
			source.setInV(0 , 8);
			source.setOutC(8);
			target.logicOperation(Home.getFacade());
		}
		else{
			source.setOutC(8);
			target.setIn(pPatilla, source.getOut());
			target.setInV(pPatilla , 8);
		}
		
	}
	
	public void deleteConection(String pID, String pID2)
	{
	}
	
	public void cambiarValor(String pID,int pValor, int pPatilla){
		Componente target = findComponent(pID);
		target.setIn(pPatilla, pValor);
	
	}
	
	public Componente findComponent(String pID)
	{
		Node<Componente> tmp = listaComponentes.getHead();
		while (tmp != listaComponentes.getPointer()){
			if (tmp.getData().get_ID().equals(pID)){
				//System.out.println(true);
				break;
			}
			else{
				tmp = tmp.getNext();
			}
		}
		return tmp.getData();	
	}
	
	
	
	public void generarTablaDeVerdad()
	{
		
		comprobarCircuito();
		
		Node<Componente> tmp = listaComponentes.getHead(); //temporal para moverse en la lista de componentes
		List<Componente> listaEntradasTablaDeVerdad = new List<>(); //lista para meter los componente que poseen entradas del circuit
		TablaDeVerdad tabla = new TablaDeVerdad(listaEntradas); //genera la tabla
		
		for(int i =0; i < listaComponentes.length(); i++){ //Se mueve a traves de la lista de componentes
			if(tmp.getData().getListaEntradas().getHead().getData().get_Value(1) == 7){ //verifica si el valor de ent1 es entrada del 
				listaEntradasTablaDeVerdad.insertPointer(tmp.getData());				//circuito
			}
			tmp = tmp.getNext();
		}
		
		List<List<Integer>> listaPosiblesEntradasCircuito =  tabla.getFilaMatriz(); //Agarra los posibles valores de las entradas
																					//del circuito, generadas por TablaDeVerdad
		Node<Integer> temporalFila = listaPosiblesEntradasCircuito.getHead().getData().getHead();//fila temporal para moverse
		Node<List<Integer>> temporalColumna = listaPosiblesEntradasCircuito.getHead();//columna temporal para moverse
		Node<Componente> temporalComponente = listaEntradasTablaDeVerdad.getHead();//componente temporal para moverse
		
		for(int i = 0; i < listaEntradasTablaDeVerdad.length(); i++ ){
			for(int j = 0; j < listaPosiblesEntradasCircuito.length(); j++){
				int valorA = temporalFila.getData();					//valor de la lista de filas
				//temporalComponente.getData().setIn(pPatilla, pValor);  //Insertar el nuevo valor en el componente
				
				//TODO
				//Debemos verificar que la segunda patilla del componente sea o no sea entrada del circuito
				
			}
		}
		
	}
	
	public List<String> getListaEntradas(){
		return listaEntradas;
	}
	
	public List<String> getListaSalidas(){
		return listaSalidas;
	}
	
	public int getCantidadDeComponentes(){
		return listaComponentes.length();
	}
	
	public List<Componente> getListaComponentes(){
		return listaComponentes;
	}
	

}