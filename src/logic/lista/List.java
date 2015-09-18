package logic.lista;


public class List <k> {

	protected Node<k> _head;
	protected Node<k> _pointer;
	protected int counter=0;
	
	//constructor
	public List() {
		this._head = null;
		this._pointer = null;
		
	}

	
	public void insertPointer(k pData) {
		counter++;
		if (_head == null){
			_head = _pointer = new Node<k>(pData);
		}
		else{
			Node<k> tmp = new Node<k>(pData);
			_pointer.setNext(tmp);
			tmp.setPrevious(_pointer);
			_pointer = tmp;
			_pointer.setNext(_head);
			_head.setPrevious(_pointer);
		}
	}
	
	
	
	public boolean findData(k pData){
		Node<k> tmp = _head;
		while (tmp != _pointer){
			if (tmp.getData().equals(pData)){
				//System.out.println(true);
				return true;
			}
			else{
				tmp = tmp.getNext();
			}
		}
		return false;
	}
	
	public void showData(){
		
		Node<k> tmp = _head;
		while (tmp != _pointer){
			System.out.println(tmp.getData());
			tmp = tmp.getNext();
		}
		System.out.println(tmp.getData());
	}
	
	public void deleteAll(){ //BORRAR TOD
		if(_head == null){
			return;
		}
		else{
			Node<k> tmp = _pointer;
			while(tmp != _head){
				if(tmp==_pointer){
					_pointer = _pointer.getPrevious();
					_pointer.setNext(_head);
					_head.setPrevious(_pointer);
					tmp = _pointer;
				}
			}
			_head = _pointer = null;
		}
	}
	
	public void delete(k pData){
		if (_head == null) {
			System.out.println("la lista es vacía");
			return;
		}
		
		if (_head.getData().equals(pData) ){
			_head = _head.getNext();
			_pointer.setNext(_head);
			counter--;
			return;
		}
		Node<k> tmp = _head;
		while (tmp.getNext() != _head & tmp.getNext().getData() != pData){
			tmp = tmp.getNext();
		}
		if (tmp.getNext() == _head) {
			//System.out.println("Delete  " + tmp.getData()); // CAUSA ERROR DESCONOCIDO 404 
			return;
		}
		else{
			if (tmp.getNext() == _pointer){
				_pointer = tmp;
				_pointer.setNext(_head);
				counter--;
				return;
			}
			else {
				tmp.setNext(tmp.getNext().getNext());
				counter--;
			}
		}
	}
	
	public Node<k> getHead(){
		return _head;
	}
	
	public Node<k> getPointer(){
		return _pointer;
	}
	public int length(){
		return counter;
	}
	
	public void Swap(Node<k> firstElement, Node<k> secondElement){
		Node<k> tmp = firstElement;
		k tmp3 = firstElement.getData();
		while(tmp.getNext() != _head){
			if (tmp == firstElement){
				firstElement.setData(secondElement.getData());
				secondElement.setData(tmp3);
			}
			tmp = tmp.getNext();
		}
	}	
}
