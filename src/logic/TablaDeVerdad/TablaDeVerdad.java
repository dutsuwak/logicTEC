package logic.TablaDeVerdad;


import jdk.nashorn.internal.runtime.ListAdapter;
import logic.array.Array;
import logic.lista.List;
import logic.lista.Node;

public class TablaDeVerdad {
	List<List<Integer>> filaMatriz;
	List<Integer>columnaMatriz;
	private int columna;
	private int fila;
	int longitud;

	
	
	public TablaDeVerdad(List<String> listaEntradas)
	{
		columnaMatriz = new List<>();
		filaMatriz = new List<>();
		longitud = (int) Math.pow(2, listaEntradas.length());
		
		iniciarMatriz(listaEntradas);
		
		/*insertarTabla(mitadLista(listaEntradas), longitud, 0);
		insertarTabla(mitadLista(listaEntradas), longitud, 1);*/
		
	}
	
	public void iniciarMatriz(List <String> listaEntradas)
	{
	columna=1;
	fila=1;
	int dato=0;
	int particionar= longitud/2;
	boolean flag= false;
	int division;
		
	for(int i=0;i<listaEntradas.length();i++)
	{
		this.filaMatriz.insertPointer(this.columnaMatriz= new List<Integer>());
		this.filaMatriz.getPointer().setColumnaMatriz(columna);
		this.filaMatriz.getPointer().setFilaMatriz(fila);
		
		division= particionar;
		for(int j=0;j<longitud;j++)
		{
			if (j==particionar)
			{
				if (flag==false)
				{
					
					j--;
					dato=1;
					
					particionar+= division;
		
					flag=true;
				}
				else
				{
					j--;
					dato=0;
					
					particionar+= division;
					flag=false;
					
				}
			}
			else
			{
				this.filaMatriz.getPointer().getData().insertPointer(dato);
				this.filaMatriz.getPointer().getData().getPointer().setColumnaMatriz(columna);
				this.filaMatriz.getPointer().getData().getPointer().setFilaMatriz(fila);
				fila++;
			}
		}
		
		flag = false;
		dato = 0;
		particionar = division/2;
		
		columna++;
		fila=1;
	}
	}
	
	public void insertarTablaMatriz(List<String> tabla, int contador,int dato)
	{
		if(contador == tabla.length()-1)
			return;
		else
		{
			for (int j=0;j< filaMatriz.getHead().getData().length();j++)
			{
				if (j== filaMatriz.getHead().getData().length()/2)
				{
					
				}
			}
			for(int j=0;j<longitud/2;j++)
			{
				this.filaMatriz.getPointer().getData().insertPointer(0);
				this.filaMatriz.getPointer().getData().getPointer().setColumnaMatriz(columna);
				this.filaMatriz.getPointer().getData().getPointer().setFilaMatriz(fila);
				fila++;
					
				//System.out.println(columnaMatriz.getUltimoNodo().getDatos());
			}
			
		}
		
	}
	
	
	
	
	public void insertarTabla(List<String> tabla, int contador,int dato)
	{
		if (contador== 1)
		{
			return;
		}
		else
		{
			for (int i=0; i<contador;i++)
			{
			
				
			}
			insertarTabla(mitadLista(tabla), contador/2, 0);
			insertarTabla(mitadLista(tabla), contador/2, 1);
			
		}
		
	}
	
	public List<String> mitadLista(List<String> listaEntradas)
	{
		List<String> listaTemp = new List<>();
		Node<String> temp = listaEntradas.getHead(); 
		for (int i = 0; i<listaEntradas.length()/2;i++)
		{
			listaTemp.insertPointer(temp.getData());
			temp= temp.getNext();		
		}

		return listaTemp;
	}

	public List<List<Integer>> getFilaMatriz() {
		return filaMatriz;
	}

}
