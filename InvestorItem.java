class InvestorItem extends ListItem { // class InvestorItem
	private Investor investor; // has an Investor Object

	public InvestorItem(Investor in) {

		investor = in; // sets investor

	}

	public Investor getInvestor() {
		return investor;
	}

	public void setInvestor(Investor investor) {
		this.investor = investor;
	}

} // End InvestorItem
