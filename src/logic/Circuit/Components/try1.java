package logic.Circuit.Components;

import logic.Circuit.Circuit.Circuit;

public class try1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Circuit cir = new Circuit();
		
		cir.AddComponentes("and","compuerta1");
		cir.AddComponentes("or","compuerta2");
		cir.AddComponentes("Nand","compuerta3");
		cir.AddComponentes("Nor","compuerta4");
		
		cir.cambiarValor("compuerta1", 1, 0);
		cir.cambiarValor("compuerta1", 0, 1);
		cir.cambiarValor("compuerta2", 0, 0);
		cir.setConection("compuerta1", 0, "compuerta2", 1);
		cir.cambiarValor("compuerta3", 1, 0);
		cir.setConection("compuerta2", 0, "compuerta3", 1);
		cir.setConection("compuerta2", 0, "compuerta4", 0);
		cir.setConection("compuerta1", 0, "compuerta4", 1);
		cir.comprobarCircuito();
	}

}