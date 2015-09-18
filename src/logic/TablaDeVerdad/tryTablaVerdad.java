package logic.TablaDeVerdad;

import logic.lista.List;

public class tryTablaVerdad {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<String> entrada = new List<>();
		
		for(int i =0; i<5;i++)
		  entrada.insertPointer("h");
		
		TablaDeVerdad tabla = new TablaDeVerdad(entrada);
		
		tabla.filaMatriz.getHead().getNext().getData().showData();

	}

}
