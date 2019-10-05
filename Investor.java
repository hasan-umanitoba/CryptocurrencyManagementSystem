//Investor Class defines an Investor
//With its info and 
public class Investor {

	private String firstname; // firstname of Investor
	private String lastname; // lastName
								// if same user id duplicate
	private String userID; // userId of Investor
	private int cash; // cashBalance
	private List portfolio; // list that is portfolio contains Crypto and their amount

	// Constructor sets the fields respectively

	public Investor(String firstname, String lastname, String userid, String cash) {
		this.cash = Integer.parseInt(cash);
		this.firstname = firstname;
		this.lastname = lastname;
		this.userID = userid;
		portfolio = new List();

	}

	// Getter and setter methods
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	// adding to portfolio
	public void addtoPortfolio(ListItem data) {
		portfolio.add(data);
	}

//searching an item in investor portfolio and returning it
	public ListItem searchPortfolio(String symbol) { // searching crypto

		return portfolio.searchItem(symbol);

	}

	@Override
	public String toString() { // toString prints info
		return "Investor [firstname=" + firstname + ", lastname=" + lastname + ", userID=" + userID + ", cash=" + cash
				+ "]";
	}

	public void printPortfolio() { // prints the whole list
		System.out.print("Name  : " + firstname + " " + lastname + " userID : " + userID + " Cash : " + cash
				+ "\nList of all cryptocurrencies :\n");
		portfolio.printall();

	}

	public List getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(List portfolio) {
		this.portfolio = portfolio;
	}
}
