//Node Class Contains a ListItem and a Next Pointer to it 
//used in the List Class

public class Node {

	public void setData(ListItem data) {
		this.data = data;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	private ListItem data; // List Data
	private Node next; // next Node

	public Node(ListItem data, Node next) { // Constructor
		this.data = data; // sets the Data
		this.next = next;
	}

	public ListItem getData() {
		return data;
	}

	public Node getNext() {
		return next;
	}

}
