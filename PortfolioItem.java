class PortfolioItem extends ListItem { // Class Portfolio Item Each Investor has A list called portfolio consisting
										// PortFolio Items
	private Cryptocurrency cryptocurrency; // Item has a cryptocurrency
	private int units; // and units of that crypto owned by investor

	public PortfolioItem(Cryptocurrency crypto, int units) { // constructor sets fields
		this.units = units;
		cryptocurrency = crypto;

	}

	public Cryptocurrency getCryptocurrency() {
		return cryptocurrency;
	}

	public void setCryptocurrency(Cryptocurrency cryptocurrency) {
		this.cryptocurrency = cryptocurrency;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	@Override
	public String toString() {
		return "PortfolioItem [" + cryptocurrency.getSymbol() + ", units=" + units + "]";
	}

} // End Mine