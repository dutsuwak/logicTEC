package logic.Circuit.Components;

import gui.facade.Facade;
import logic.lista.List;
import logic.lista.Node;
import logic.array.Array;


public class Componente {

	private List<Array> listaEntradas;
	private List<Array> listaSalidas;
	private String _ID;
	private String _tipo;

	public Componente(String pCompuerta, String pID){
		listaEntradas = new List<>();
		listaSalidas = new List<>();
		_ID = pID;
		_tipo=pCompuerta;
		
		constructor();
		
	}
	
	private void constructor(){
		listaSalidas.insertPointer(new Array(2));
		listaSalidas.getHead().getData().insertData(0);
		listaSalidas.getHead().getData().insertData(7);
		
		if(_tipo.equals("Wire") || _tipo.equals("Wire2") || _tipo.equals("Wire3")|| _tipo.equals("Not") || 
		   _tipo.equals("On") || _tipo.equals("Off")){
			listaEntradas.insertPointer(new Array(2));
			Node<Array> tmp = listaEntradas.getHead();
			tmp.getData().insertData(0);
			tmp.getData().insertData(7);
		}
		else if(_tipo.equals("LightOFF") || _tipo.equals("LightON")){
			listaEntradas.insertPointer(new Array(2));
			Node<Array> tmp = listaEntradas.getHead();
			tmp.getData().insertData(0);
			tmp.getData().insertData(7);
		}
		else{
			for(int i = 0; i < 2; i++)
				listaEntradas.insertPointer(new Array(2));
			Node<Array> tmp = listaEntradas.getHead();
			while(tmp != listaEntradas.getPointer()){
				tmp.getData().insertData(0);
				tmp.getData().insertData(7);
				tmp = tmp.getNext();
			}
			tmp.getData().insertData(0);
			tmp.getData().insertData(7);
		}
		
	}
	public void logicOperation(Facade f)
	{	
		//System.out.print("ID: " + _ID);
		//System.out.println(" TIPO: " + _tipo);
		switch (_tipo) {
		case "And":  
			if(listaEntradas.getHead().getData().get_Value(0) == 1 && 
				listaEntradas.getHead().getNext().getData().get_Value(0) == 1) listaSalidas.getHead().getData().set_Data(1, 0);
			else listaSalidas.getHead().getData().set_Data(0, 0);
        
            break;
            
        case "Or":
        	
        	if(listaEntradas.getHead().getData().get_Value(0) == 0 && 
    				listaEntradas.getHead().getNext().getData().get_Value(0) == 0) 
        		listaSalidas.getHead().getData().set_Data(0, 0);
        	
        	else listaSalidas.getHead().getData().set_Data(1, 0);
        	
        	break;
        	
        case "Nor":
        	if(listaEntradas.getHead().getData().get_Value(0) == 0 && 
        			listaEntradas.getHead().getNext().getData().get_Value(0) == 0) 
        		listaSalidas.getHead().getData().set_Data(1, 0);
        	
        	else listaSalidas.getHead().getData().set_Data(0, 0);
        	
        	break;
            
        case "Not":
        	if(listaEntradas.getHead().getData().get_Value(0) == 0) listaSalidas.getHead().getData().set_Data(1, 0);
        	
        	else listaSalidas.getHead().getData().set_Data(0, 0);
        	
        	break;
        	
        case "Xor":
        	if(listaEntradas.getHead().getData().get_Value(0) != listaEntradas.getHead().getNext().getData().get_Value(0)) 
        		listaSalidas.getHead().getData().set_Data(1, 0);
        	
        	else listaSalidas.getHead().getData().set_Data(0, 0);
        	
        	break;
        
        case "Xnor":
        	if(listaEntradas.getHead().getData().get_Value(0) == listaEntradas.getHead().getNext().getData().get_Value(0)) 
        		listaSalidas.getHead().getData().set_Data(1, 0);
        	
        	else listaSalidas.getHead().getData().set_Data(0, 0);
        	
        	break;
        
        case "Nand":
        	if(listaEntradas.getHead().getData().get_Value(0) == 1 && 
        			listaEntradas.getHead().getNext().getData().get_Value(0) == 1) 
        		listaSalidas.getHead().getData().set_Data(0, 0);
     	
        	else listaSalidas.getHead().getData().set_Data(1, 0);
     	
        	break;
        	
        case "Wire":
        	listaSalidas.getHead().getData().set_Data(listaEntradas.getHead().getData().get_Value(0), 0);
        	break;
        	
        case "Wire2":
        	listaSalidas.getHead().getData().set_Data(listaEntradas.getHead().getData().get_Value(0), 0);
        	break;
        
        case "Wire3":
        	listaSalidas.getHead().getData().set_Data(listaEntradas.getHead().getData().get_Value(0), 0);
        	break;
        
        case "On":
        	listaSalidas.getHead().getData().set_Data(1, 0);
        	break;
        case "Off":
        	listaSalidas.getHead().getData().set_Data(0, 0);
        	break;
        case "LightOFF":
        	if(listaEntradas.getHead().getData().get_Value(0) == 0){
        	}
        	else{
        		f.changeLightImg(_ID);
        	}
        	
    }
		
	}
	
	//metodo para declarar el ID
	public void set_ID(String _ID) {
		this._ID = _ID;
	}
	
	//metodo para definir el valor de una entrada del componente
	public void setIn(int pPatilla, int pValor)
	{
		Node<Array> in = listaEntradas.getHead();
		if(pPatilla == 1)
			in = in.getNext();
		in.getData().set_Data(pValor, 0);
	}
	
	//metodo para definir si se entrada del circuito o no
	public void setInV(int pPatilla, int pvalor){
		Node<Array> in = listaEntradas.getHead();
		if(pPatilla == 1)
			in = in.getNext();
		in.getData().set_Data(pvalor, 1);
	}
	
	//metodo para establecer si se es salida del circuito o no
	public void setOutC(int pvalor)
	{
		listaSalidas.getPointer().getData().set_Data(pvalor, 1);
	}
	
	//metodo que devuelve el valor de ID
	public String get_ID(){
		return _ID;
	}
	
	//metodo que retorna la lista de entradas
	public List<Array> getListaEntradas() {
		return listaEntradas;
	}

	//metodo que retorna la lista de salidas
	public List<Array> getListaSalidas() {
		return listaSalidas;
	}

	
	public int getValorE(int pentrada){
		Node<Array> in = listaEntradas.getHead(); 
		if(pentrada == 1)
			in = in.getNext();
		return in.getData().get_Value(1);
	}
	
	public int getValorS(){
		Node<Array> in = listaSalidas.getHead();
		return in.getData().get_Value(1);
	}

	public int getIn(int pEntrada)
	{
		Node<Array> in = listaEntradas.getHead(); 
		if(pEntrada == 1)
			in = in.getNext();
		return in.getData().get_Value(0);
	}
	
	public int getOut()
	{
		return listaSalidas.getPointer().getData().get_Value(0);
	}
	
	public String getTipo(){
		return _tipo;
	}

	public void setTipo(String tipo) {
		_tipo = tipo;
		
	}

}
