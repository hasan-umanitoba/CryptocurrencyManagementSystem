
//Author Muhammad Hasan Saleem	
import java.util.Objects;

public class Mine extends Block { // Mine is a transaction which is a subclass Block . Entered to the BlockChain
	private String userid; // user id of investor
	private String symbol; // Symbol of currency
	private int quantity; // quantity to be mine

	public Mine(String userID, String Symbol, int Quantity, int previoushash) {
		userid = userID;
		symbol = Symbol;
		quantity = Quantity;
		prevHashcode = previoushash;

		blockHash = hashCodeBlock(prevHashcode, hashCodeTransaction());

	}

	@Override
	public String toString() {
		return "Mine [userid=" + userid + ", symbol=" + symbol + ", quantity=" + quantity + "]";
	}

	public int hashCodeTransaction() { // hashcode of current Transaction
		return Objects.hash(userid, symbol, quantity);
	}

	public int hashCodeBlock(int previoustransactions, int transaction) { // hashcode combined with previous
		return Objects.hash(previoustransactions, transaction);
	}

	// getters and Setters
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
