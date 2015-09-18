package logic.array;


public class Array {
	private int[] arreglo=null;
	private int _tamaño;
	private int _tail=-1;
	private int _count=0;
	@SuppressWarnings("unused")
	private  int _head=-1;
	
	public Array(int ptamaño)
	{	
		_tamaño = ptamaño;
		arreglo= new int [_tamaño];
	}
	
	public void insertData(int pData)
	{
		if (_tail+1==_tamaño)
		{
		}
		
		arreglo[++ _tail]=pData;
		_count++;
		if(_count==1)
			_head=0;	
	}
	
	public void showData(){
		for(int i = 0; i < _tamaño; i ++){
			System.out.println(arreglo[i]);
		}
	}
	
	public void deleteData(int pIndice)
	{
		arreglo[pIndice]=-1;
		_count--;
	}
	
	@SuppressWarnings("unused")
	public void swapDato(int pIndice1,int pIndice2)
	{
		int temp = arreglo[pIndice1];
	}


	public int get_length() {
		return _tamaño;
	}

	public void set_tamaño(int _tamaño) {
		this._tamaño = _tamaño;
	}
	
	public void set_Data(int _dato, int i){
		arreglo[i] = _dato;
	}
	
	public int get_Value(int i){
		return arreglo[i];
	}
	

}
