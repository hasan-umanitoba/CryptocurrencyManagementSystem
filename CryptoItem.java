
// subclass of Listitem used for making cryptocurrency List
public class CryptoItem extends ListItem { // It is a CryptoCurrency item
	private Cryptocurrency cryptocurrency; // Contains CryptoCurrency Variable

	public CryptoItem(Cryptocurrency insertcrypto) { // Sets the fields

		cryptocurrency = insertcrypto;

	}

	public Cryptocurrency getCryptocurrency() {
		return cryptocurrency;
	}

	public void setCryptocurrency(Cryptocurrency cryptocurrency) {
		this.cryptocurrency = cryptocurrency;
	}

	public int getQuantity() {
		return cryptocurrency.getQuantity();
	}

} // End CryptoItem
