package logic.lista;


public class Node<k> extends Nodo<k> {
	
    public Node(k pData) {
		super(pData);
		// TODO Auto-generated constructor stub
	}




	private int columnaMatriz;
    private int filaMatriz;





	/**
	 * @return the columnaMatriz
	 */
	public int getColumnaMatriz() {
		return columnaMatriz;
	}




	/**
	 * @param columnaMatriz the columnaMatriz to set
	 */
	public void setColumnaMatriz(int columnaMatriz) {
		this.columnaMatriz = columnaMatriz;
	}




	/**
	 * @return the filaMatriz
	 */
	public int getFilaMatriz() {
		return filaMatriz;
	}




	/**
	 * @param filaMatriz the filaMatriz to set
	 */
	public void setFilaMatriz(int filaMatriz) {
		this.filaMatriz = filaMatriz;
	}

}
