s
public class List { // Class List //General Type of List. One List Used Throughout the whole program

	private Node head; // head Node
	private Node pointer; // current Node pointer
//constructor

	public List() {
		head = null;
		pointer = null;
	}

//Getter and Setter methods
	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}

	public Node getPointer() {
		return pointer;
	}

	public void setPointer(Node pointer) {
		this.pointer = pointer;
	}

	// Add to list method
	// Adds to the end of list
	public void add(ListItem data) {

		Node newNode = new Node(data, null);
		if (head == null) { // empty list
			this.head = newNode;
			pointer = head;

		} else {
			pointer.setNext(newNode);
			pointer = pointer.getNext(); // pointer moves forward

		}

	}

// search Item Method
//This method searches and returns a listItem if found in the List else it returns null	

	public ListItem searchItem(String searchitem) {
		ListItem returnitem = null;

		if (head != null) {
			Node pointer = head;
			while (pointer != null) {
				ListItem item = pointer.getData();

				if (item instanceof InvestorItem) {
					InvestorItem Iitem = (InvestorItem) item;
					if (searchitem.equals(Iitem.getInvestor().getUserID())) {
						returnitem = item;
						return returnitem;
					}

				} else if (item instanceof CryptoItem) {
					CryptoItem Iitem = (CryptoItem) item;

					if (searchitem.equals(Iitem.getCryptocurrency().getSymbol())) {
						returnitem = item;
						return returnitem;
					}
				} else if (item instanceof PortfolioItem) {
					PortfolioItem Iitem = (PortfolioItem) item;
					if (searchitem.equals(Iitem.getCryptocurrency().getSymbol())) {
						returnitem = item;
						return returnitem;
					}
				}

				pointer = pointer.getNext(); // Moves forward

			}
		}

		return returnitem;

	}

//Verifies The list to check whether hashcodes are consistent with previous transactions 
	// returns some object if the list is altered
	public ListItem verifyChain() {
		Block returnvalue = null; // returns
		Node forward = head; // Forward and prev pointers of node to traverse
		Node prev = null;

		if (head != null) {

			while (forward != null) { // traversing and checking instanceof to check whether it is a trade or mine
										// Transaction
				Block item = (Block) forward.getData();
				Block previtem;

				if (forward == head) { // first item
					if (item instanceof Mine) {
						Mine mineitem = (Mine) item;
						int originalhashcode = mineitem.blockHash;

						if (mineitem.hashCodeBlock(0, mineitem.hashCodeTransaction()) != originalhashcode) {
							returnvalue = item;

						}

					} else if (item instanceof Trade) {
						Trade tradeitem = (Trade) item;
						int originalhashcode = tradeitem.blockHash;

						if (tradeitem.hashCodeBlock(0, tradeitem.hashCodeTransaction()) != originalhashcode) {
							returnvalue = item;

						}

					}
				} else {
					// not first
					previtem = (Block) prev.getData();
					if (item instanceof Mine) {
						Mine mineitem = (Mine) item;
						int originalhashcode = mineitem.blockHash;

						if (mineitem.hashCodeBlock(previtem.blockHash,
								mineitem.hashCodeTransaction()) != originalhashcode) {
							returnvalue = item;

						}

					} else if (item instanceof Trade) {
						Trade tradeitem = (Trade) item;

						int originalhashcode = tradeitem.blockHash;

						if (tradeitem.hashCodeBlock(previtem.blockHash,
								tradeitem.hashCodeTransaction()) != originalhashcode) {
							returnvalue = item;

						}

					}

				}
				prev = forward;
				forward = forward.getNext(); // moves forward

			}

		}

		return returnvalue; // returns either null or ListItem

	}

	// method to print
	// it identifies which item it is first
	// then it prints out the info of each item
	public void printall() {
		if (head != null) {
			Node check = head;
			while (check != null) {
				ListItem item = check.getData();

				// IDENTIFYING AND CASTING TO THE CORRECT TYPE TO RETRIEVE INFO
				if (item instanceof Mine) {
					Mine mineitem = (Mine) item;
					System.out.println(mineitem.toString());

				} else if (item instanceof Trade) {
					Trade tradeitem = (Trade) item;
					System.out.println(tradeitem.toString());

				} else if (item instanceof InvestorItem) {
					InvestorItem Iitem = (InvestorItem) item;

					System.out.println(Iitem.toString());

				} else if (item instanceof CryptoItem) {
					CryptoItem Iitem = (CryptoItem) item;
					System.out.println(Iitem.toString());

				} else if (item instanceof PortfolioItem) {
					PortfolioItem Iitem = (PortfolioItem) item;
					System.out.println(Iitem.toString());
				}

				check = check.getNext(); // MOVES NEXT
			}

		}
	}

}