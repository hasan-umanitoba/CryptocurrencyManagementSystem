public class Cryptocurrency { // Each investor can have cryptocurrency. they can trade or mine it
//NAME SYMBOL QUANT AND A LIST WHICH IS A BLOCKCHAIN : TRANSACTIONS OF TRADE OR MINE
	private String name;
	private String Symbol;
	private int quantity;
	private List blockchain; // list of transactions

	@Override
	public String toString() {
		return "Cryptocurrency [name=" + name + ", Symbol=" + Symbol + ", Available units " + quantity + "]";

	}

//Constructor to set fields
	public Cryptocurrency(String name, String symbol, int quantity) {
		this.quantity = quantity;
		this.name = name;
		this.Symbol = symbol;

		this.blockchain = new List();

	}

	// prints BlockChain of transactions
	public void printBlockChain() {
		System.out.println("\n\nReport Produced");
		System.out.println(this.toString());
		blockchain.printall();
	}

	public List getBlockchain() {
		return blockchain;
	}

	// Verifies Chain by checkiking hashcode is not altered
	public void verifyChain() {

		if (blockchain.verifyChain() != null) { // if true
			// error
			System.out.println("\n Block has been altered ");
			if (blockchain.verifyChain() instanceof Trade) {
				Trade item = (Trade) blockchain.verifyChain();
				System.out.println("Information of the block :" + item.toString());

			} else {
				// its a mine
				Mine item = (Mine) blockchain.verifyChain();
				System.out.println("Information of the block :" + item.toString());

			}
		} else {
			// if there is no change :
			System.out.println("\nBlockChain verified successful ");
		}

	}

	public List getBlockChain() {
		return this.blockchain;

	}

	// Getter and setter methods
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return Symbol;
	}

	public void setSymbol(String symbol) {
		Symbol = symbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
