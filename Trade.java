import java.util.Objects;

public class Trade extends Block { // Trade is a Transaction that is entered into The BlockChain
	private String userTrader1; // Investor1
	private String userTrader2; // Investor2
	private String currency1; // Currency1
	private int quantity1; // Quantity1
	private String currency2; // currency2
	private int quantity2; // quantity2

//Sets the field
	public Trade(String userTrader1, String userTrader2, String currency1, int quantity1, String currency2,
			int quantity2, int previoushashcode) {
		this.userTrader1 = userTrader1;
		this.userTrader2 = userTrader2;
		this.currency1 = currency1;
		this.quantity1 = quantity1;
		this.currency2 = currency2;
		this.quantity2 = quantity2;
		prevHashcode = previoushashcode;
		blockHash = hashCodeBlock(prevHashcode, hashCodeTransaction());

	}

	@Override
	public String toString() {
		return "Trade [userTrader1=" + userTrader1 + ", userTrader2=" + userTrader2 + ", currency1=" + currency1
				+ ", quantity1=" + quantity1 + ", currency2=" + currency2 + ", quantity2=" + quantity2 + "]";
	}

//produces hashcode of the transaction
	public int hashCodeTransaction() {
		return Objects.hash(userTrader1, userTrader2, currency1, quantity1, currency2, quantity2);

	}

//produces hashcode of the Current Block by combining
	public int hashCodeBlock(int previoustransactions, int transaction) {
		return Objects.hash(previoustransactions, transaction);
	}

	// Getter AND SETTER methods
	public String getUserTrader1() {
		return userTrader1;
	}

	public void setUserTrader1(String userTrader1) {
		this.userTrader1 = userTrader1;
	}

	public String getUserTrader2() {
		return userTrader2;
	}

	public void setUserTrader2(String userTrader2) {
		this.userTrader2 = userTrader2;
	}

	public String getCurrency1() {
		return currency1;
	}

	public void setCurrency1(String currency1) {
		this.currency1 = currency1;
	}

	public int getQuantity1() {
		return quantity1;
	}

	public void setQuantity1(int quantity1) {
		this.quantity1 = quantity1;
	}

	public String getCurrency2() {
		return currency2;
	}

	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
	}

	public int getQuantity2() {
		return quantity2;
	}

	public void setQuantity2(int quantity2) {
		this.quantity2 = quantity2;
	}

} // End Trade
