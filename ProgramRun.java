
//CRYPTOCURRENCY MANAGEMENT PROGRAM
import java.io.*;

import javax.swing.JFileChooser;
import java.util.Scanner;

//CLASS programrun has the mainmethod
//executes the whole program
public class ProgramRun {

	public static void main(String args[]) { // Main method

		try {

			System.out.println("Type in the File Name to Read ");
			Scanner scanner = new Scanner(System.in); // Scanner to input File name
			String Filename = scanner.next();

			BufferedReader inFile = null; // Bufferreader to readfile
			inFile = new BufferedReader(new FileReader(Filename)); // Buffered reader myreader reads the chosen
																	// file

			String line = "";
			List investorList = new List(); // empty investor list //making TWO LISTS
			List cryptolist = new List(); // empty crypto list
			String lastline = "";

			do { // Do while loop which processes line by line and calls the method
					// commandprocessor
				lastline = line;

				line = inFile.readLine(); // each line gets read

				if (line == null) {
					break; // if it is null move out of the loop

				}

				commandProcessor(line, investorList, cryptolist); // calls the method to perform function

			} while (line != null);
			if (!lastline.startsWith("END")) {
				System.out.println("Error");
				System.out.println("File not ended with END");

			}
			inFile.close(); // closes file

		}

		catch (IOException e) {
			System.out.println("Nope. That didn't work: " + e.getMessage());

		}
	}

	public static void commandProcessor(String line, List investorList, List cryptolist) {

		line = line.trim(); // removes any gap
		if (line.length() > 0) { // check if line is not empty
			if (line.equals("END")) {
				System.out.println("DONE");
//ends with end command 
				System.exit(0);
			} else if (line.startsWith("#")) { // rewriring comments
				System.out.println(line);
			}

			else {
// Checking other commands 
				String firstword = line.substring(0, line.indexOf(' ')); // gets first word
				String words[] = line.split(" "); // Array of words //gets array of words

				switch (firstword) { // using switch case method to switch to respective command

				case "NEW": // NEW INVESTOR COMMAND
					Investor newInvestor = new Investor(words[1], words[2], words[3], words[4]); // makes new investor

					if (investorList.searchItem(words[3]) == null) { // if new then add
						// new
						ListItem item = new InvestorItem(newInvestor);
						System.out.println("Success");
						System.out.println(newInvestor.toString());
						investorList.add(item);
					} else { // else duplicate
						System.out.println("\nDuplicate : Multiple Entry of : " + " " + newInvestor.getFirstname() + " "
								+ newInvestor.getLastname() + " " + newInvestor.getUserID());
						System.out.println("");

					}

					break; // break new

				case "CRYPTO": // COMMAND CREATE NEW CRYPTOCURRENCY
// new crypto currency 
					String name = words[1]; // 80 char long max
					String Symbol = words[2]; // four letter //if same symbol duplicate
					int quantity = Integer.parseInt(words[3]);

					if (cryptolist.searchItem(Symbol) == null) { // searches if its already in list
						// not duplicate

						Cryptocurrency newcrypto = new Cryptocurrency(name, Symbol, quantity);
						ListItem item = new CryptoItem(newcrypto);
						System.out.println("\nSuccess");
						System.out.println("new crypto currency was created " + newcrypto.getName() + " "
								+ newcrypto.getSymbol() + " " + newcrypto.getQuantity());

						cryptolist.add(item); // adds to list
					}

					else { // duplicate
						System.out.println("\nDuplicate : ");
						System.out.println("There is another cryptocurrency with symbol :" + Symbol);

					}
					break;

				case "MINE": // COMMAND MINE : MINING CRYPTOCURRENCY
//mining is free
					String userid = words[1];
					String symbol = words[2];
					int minequantity = Integer.parseInt(words[3]);

					if (investorList.searchItem(userid) != null && cryptolist.searchItem(symbol) != null) { // if the
																											// investor
																											// and both
																											// cryptoexist
						CryptoItem crypto = (CryptoItem) cryptolist.searchItem(symbol);

						if (crypto.getQuantity() < minequantity) { // checking quantity to mine is consistent
							System.out.println("\nInsufficient Currency ");
							System.out.println(" Currency to mine :" + minequantity + " and available currency : "
									+ crypto.getQuantity());

						} else { // creating new investor and portfolioitem . if crypto is already owned then add
									// units else add a new portfolioitem to investor
//we have sufficient currency 
							InvestorItem newinv = (InvestorItem) investorList.searchItem(userid);
							PortfolioItem CRYPTO = (PortfolioItem) newinv.getInvestor().searchPortfolio(symbol);

							ListItem item = new PortfolioItem(crypto.getCryptocurrency(), minequantity);
							if (newinv.getInvestor().searchPortfolio(symbol) != null) {
								int original = CRYPTO.getUnits();

								CRYPTO.setUnits(minequantity + original);
							} else {
								newinv.getInvestor().addtoPortfolio(item);

							}
							Mine mineitem;
//NOW WE ADD TRANSACTION TO BLOCKCHAIN ------
							if (crypto.getCryptocurrency().getBlockchain().getHead() == null) // first item
							{
								ListItem newblock = new Mine(userid, symbol, minequantity, 0);
								crypto.getCryptocurrency().getBlockchain().add(newblock);
								mineitem = (Mine) newblock;

							} else { // not first item
								Block mineItem = (Block) crypto.getCryptocurrency().getBlockChain().getPointer()
										.getData();

								ListItem newblock = new Mine(userid, symbol, minequantity, mineItem.blockHash);
								crypto.getCryptocurrency().getBlockchain().add(newblock);
								mineitem = (Mine) newblock;

							}
							crypto.getCryptocurrency().setQuantity(crypto.getQuantity() - minequantity);
							System.out.println("Success");

							System.out.println(mineitem.toString() + "\n"); // PRINTS BACK
						}

					} else { // IF EITHER THE INVESTOR OR CRYPTOCURRENCY NOT FOUND
						System.out.println("\nNot Found : ");
						if (investorList.searchItem(userid) == null) {
							System.out.println("Investor with id " + userid + " was not found\n");

						}
						if (cryptolist.searchItem(symbol) == null) {
							System.out.println("Cryptocurrency  with symbol " + symbol + " was not found\n");

						}
					}

					break;

				case "TRADE": // COMMAND TRADE
					//
					String trader1 = words[1]; // user id investors
					String trader2 = words[2];
					// currency 1 and 2 could be crypto or canadian dollars
					String currency1 = words[3]; // crypto or canadian
					int quantity1 = Integer.parseInt(words[4]);
					String currency2 = words[5]; // crypto or canadian
					int quantity2 = Integer.parseInt(words[6]);
					Boolean curr1Dollars = false;
					Boolean curr2Dollars = false;
					Boolean notfound = false;
					Boolean sametrader = false;

					// MAKING INVESTORS AND CRYPTO
					InvestorItem TRADER1 = (InvestorItem) investorList.searchItem(trader1);
					InvestorItem TRADER2 = (InvestorItem) investorList.searchItem(trader2);

					CryptoItem CRYPTO1 = (CryptoItem) cryptolist.searchItem(currency1);
					CryptoItem CRYPTO2 = (CryptoItem) cryptolist.searchItem(currency2);

					// NOW CHECKING IF BOTH TRADERS ARE SAME ,
					if (trader1.equals(trader2)) { // same trader case
						System.out.println("\nSame Trader : " + trader1);
						sametrader = true;

					}
					// CHECKING IF ITS CRYPTO OR CANADIAN DOLLARS
					if (currency1.equals("CAD")) { // identifying whether they are Canadian dollars or Crypto
						curr1Dollars = true;

					}
					if (currency2.equals("CAD")) {
						curr2Dollars = true;

					}

					// CHECKING WHETHER INVESTOR EXISTS AND CRYPTO EXIST AND WHETHER CRYPTOCURRENCY
					// IS
					// ......
					if (investorList.searchItem(trader1) == null) {
						notfound = true;
						System.out.println("Not Found : trader :" + trader1);

					}
					if (investorList.searchItem(trader2) == null) {
						notfound = true;
						System.out.println("Not Found : trader :" + trader2);

					}

					// search cryptocurrency (
					if (!curr1Dollars && !notfound) {
						if (cryptolist.searchItem(currency1) == null) {
							notfound = true;
							System.out.println("\nNot Found : Cryptocurrency does not exist  :" + currency1);
							//
						}

					}
					if (!curr2Dollars && !notfound) {

						if (cryptolist.searchItem(currency2) == null) {
							notfound = true;
							System.out.println("\nNot Found : Cryptocurrency does not exist  :" + currency2);
							//
						}
					}

					if (!curr1Dollars && !notfound) {
						if (TRADER1.getInvestor().searchPortfolio(currency1) == null) {
							notfound = true;
							System.out.println("\nInsufficient : Investor does not have  :" + currency1);
							//
						}

					}
					if (!curr2Dollars && !notfound) {
						if (TRADER2.getInvestor().searchPortfolio(currency2) == null) {

							notfound = true;
							System.out.println("\nInsufficient : Investor does not have  :" + currency2);
						}
					}

					// not found case done
					// HERE NO SAME TRADER AND ALL CASES PASS .... WE CAN PROCEED TO CHECKING FUNDS
					if (!notfound && !sametrader) { // if there is no error called not found
						// now check funds
						//

						Boolean insuffFunds = false;

						// FROM HERE IT CHECKS WHETHER IT IS CANADIAN DOLLARS OR NOT . IF DOLLARS THEN
						// CHECK DOLLAR AMOUNT ELSE CHECK CRYPTO AMOUNT

						if (!curr1Dollars) { // if cryptocurrency
							PortfolioItem PROT1 = (PortfolioItem) TRADER1.getInvestor().searchPortfolio(currency1);

							int originalquantity1 = PROT1.getUnits();
							if ((originalquantity1) < quantity1) {
								System.out.println("\nInsufficient funds : of  " + currency1 + "of Trader with userd "
										+ trader1 + " ");
								insuffFunds = true;

							}
						} else {
							// it is canadian dollar
							int originaldollars = TRADER1.getInvestor().getCash();
							if ((originaldollars) < quantity1) {
								System.out.println(
										"\nInsufficient funds : " + currency1 + "of Trader with userid " + trader1);
								insuffFunds = true;

							}
						}

						if (!curr2Dollars) {
							PortfolioItem PROT1 = (PortfolioItem) TRADER2.getInvestor().searchPortfolio(currency2);

							int originalquantity2 = PROT1.getUnits();
							if ((originalquantity2 - quantity2) < 0) {
								System.out.println(
										"\nInsufficient funds : " + currency2 + "of Trader with userd " + trader2);
								insuffFunds = true;

							}
						} else {
							// it is canadian dollar
							int originaldollars = TRADER2.getInvestor().getCash();
							if ((originaldollars - quantity2) < 0) {
								System.out.println(
										"\nInsufficient funds : " + currency2 + " of Trader with user id :" + trader2);
								insuffFunds = true;

							}
						}

// IF WE HAVE SUFFICIENT CURRENT FOR TRADING BW TWO INVESTORS 

						if (!insuffFunds) { // if we have sufficient funds
							System.out.println("\n\nSuccess Trade");

							// MAKING TRADERS
							Investor Trader1 = TRADER1.getInvestor();
							Investor Trader2 = TRADER2.getInvestor();
							Boolean currencyalready1 = false;
							Boolean currencyalready2 = false;
//three type of trade with sequence
							// crypt crypto
							// dollar crypto
							// crypto dollar
// give crypto to 1st
							if (!curr1Dollars && !curr2Dollars) { // crypto crypto trade
								if (Trader1.searchPortfolio(currency2) != null) // already has that currency
								{

									PortfolioItem PROT1 = (PortfolioItem) TRADER1.getInvestor()
											.searchPortfolio(currency2);
									PortfolioItem PROT2 = (PortfolioItem) TRADER2.getInvestor()
											.searchPortfolio(currency2);

									int originalunits2 = PROT2.getUnits();
									int originalunits = PROT1.getUnits();

									PROT1.setUnits(originalunits + quantity2); // gets quant
																				// from other
																				// trader
									PROT2.setUnits(originalunits2 - quantity2); // - the units
																				// given
								} else {
									// doesnt have this currency is portfolio
									PortfolioItem PROT2 = (PortfolioItem) TRADER2.getInvestor()
											.searchPortfolio(currency2);

									int originalunits2 = PROT2.getUnits();

									ListItem newitem = new PortfolioItem(CRYPTO2.getCryptocurrency(), quantity2);
									Trader1.addtoPortfolio(newitem);
									PROT2.setUnits(originalunits2 - quantity2); // - the units
																				// given
									// remove quantity from other trader

								}
								// now trader 2 will get currency and 1 will subtract

								if (Trader2.searchPortfolio(currency1) != null) // already has that currency
								{
									PortfolioItem PROT2 = (PortfolioItem) TRADER2.getInvestor()
											.searchPortfolio(currency1);
									PortfolioItem PROT1 = (PortfolioItem) TRADER1.getInvestor()
											.searchPortfolio(currency1);

									int originalunits2 = PROT2.getUnits();
									int originalunits = PROT1.getUnits();

									PROT2.setUnits(originalunits2 + quantity1); // gets quant
																				// from
																				// other
																				// trader
									PROT1.setUnits(originalunits - quantity1); // - the units
																				// given
								} else {// ----------------
									// doesnt have this currency is portfolio
									PortfolioItem PROT2 = (PortfolioItem) TRADER1.getInvestor()
											.searchPortfolio(currency1);

									int originalunits = PROT2.getUnits();

									ListItem newitem = new PortfolioItem(CRYPTO1.getCryptocurrency(), quantity1);
									Trader2.addtoPortfolio(newitem);
									PROT2.setUnits(originalunits - quantity1); // - the units
																				// given
									// remove quantity from other trader

								}

								// MAKING CRYPTOCURRENCY
								Cryptocurrency crypto1 = CRYPTO1.getCryptocurrency();
								Cryptocurrency crypto2 = CRYPTO2.getCryptocurrency();

								// here we add TRANSACTION TO BLOCKCHAIN
								// we check whether the transaction is consistent with previous
								// whether its first transaction or not

								if (crypto1.getBlockChain().getHead() == null) {
									ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
											quantity2, 0);
									crypto1.getBlockchain().add(newblock);
								} else {
									// not first item
									ListItem Item = crypto1.getBlockChain().getPointer().getData();

									if (Item instanceof Trade) {
										Trade tradeitem = (Trade) Item;
										ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
												quantity2, tradeitem.blockHash);
										crypto1.getBlockchain().add(newblock);
									} else {
										Mine mineitem = (Mine) Item;
										ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
												quantity2, mineitem.blockHash);
										crypto1.getBlockchain().add(newblock);
									}

								}

								// here we add TRANSACTION TO BLOCKCHAIN
								// we check whether the transaction is consistent with previous
								// whether its first transaction or not

								if (crypto2.getBlockChain().getHead() == null) {
									ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
											quantity2, 0);
									crypto2.getBlockchain().add(newblock);
									System.out.println(newblock.toString() + "\n");

								} else {
									ListItem Item = crypto2.getBlockChain().getPointer().getData();

									// not first item

									if (Item instanceof Trade) {
										Trade tradeitem = (Trade) Item;
										ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
												quantity2, tradeitem.blockHash);
										crypto2.getBlockchain().add(newblock);
										System.out.println(newblock.toString() + "\n");

									} else {
										Mine mineitem = (Mine) Item;
										ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
												quantity2, mineitem.blockHash);
										crypto2.getBlockchain().add(newblock);
										System.out.println(newblock.toString() + "\n");

									}

								}

								// two transaction for two crypto currencies

							}

							else if (curr1Dollars && !curr2Dollars) { // dollar crypto trade
								// trader1 dollars
								// trader2crypto

								if (Trader1.searchPortfolio(currency2) != null) // already has that currency
								{

									PortfolioItem PROT1 = (PortfolioItem) TRADER1.getInvestor()
											.searchPortfolio(currency2);
									PortfolioItem PROT2 = (PortfolioItem) TRADER2.getInvestor()
											.searchPortfolio(currency2);

									int originalunits2 = PROT2.getUnits();
									int originalunits = PROT1.getUnits();

									PROT1.setUnits(originalunits + quantity2); // gets quant
																				// from other
																				// trader
									PROT2.setUnits(originalunits2 - quantity2); // - the units
																				// given
								} else {
									// doesnt have this currency is portfolio
									PortfolioItem PROT2 = (PortfolioItem) TRADER2.getInvestor()
											.searchPortfolio(currency2);

									int originalunits2 = PROT2.getUnits();

									ListItem newitem = new PortfolioItem(CRYPTO2.getCryptocurrency(), quantity2);
									Trader1.addtoPortfolio(newitem);
									PROT2.setUnits(originalunits2 - quantity2); // - the units
																				// given
									// remove quantity from other trader

								}

								//

								// Trader 1 got crypto and subtracted from trader 2
								// now trader 1 would give cash to trader1

								int originalcash2 = Trader2.getCash();
								Trader2.setCash(originalcash2 + quantity1);
								int originalcash1 = Trader1.getCash();
								Trader1.setCash(originalcash1 - quantity1);

								Cryptocurrency crypto2 = CRYPTO2.getCryptocurrency();

								if (crypto2.getBlockChain().getHead() == null) {
									ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
											quantity2, 0);
									crypto2.getBlockchain().add(newblock);
									System.out.println(newblock.toString() + "\n");

								} else {
									ListItem Item = crypto2.getBlockChain().getPointer().getData();

									// not first item
									if (Item instanceof Trade) {
										Trade tradeitem = (Trade) Item;
										ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
												quantity2, tradeitem.blockHash);
										crypto2.getBlockchain().add(newblock);
										System.out.println(newblock.toString() + "\n");

									} else {
										Mine mineitem = (Mine) Item;
										ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
												quantity2, mineitem.blockHash);
										crypto2.getBlockchain().add(newblock);
										System.out.println(newblock.toString() + "\n");

									}

								}

							}

							else if (!curr1Dollars && curr2Dollars) { // crypto dollar trade
								// trader1 has crypto
								// trader 2 has dollars

								if (Trader2.searchPortfolio(currency1) != null) // already has that currency
								{
									PortfolioItem PROT2 = (PortfolioItem) TRADER2.getInvestor()
											.searchPortfolio(currency1);
									PortfolioItem PROT1 = (PortfolioItem) TRADER1.getInvestor()
											.searchPortfolio(currency1);

									int originalunits2 = PROT2.getUnits();
									int originalunits = PROT1.getUnits();

									PROT2.setUnits(originalunits2 + quantity1); // gets quant
																				// from
																				// other
																				// trader
									PROT1.setUnits(originalunits - quantity1); // - the units
																				// given
								} else {// ----------------
									// doesnt have this currency is portfolio
									PortfolioItem PROT2 = (PortfolioItem) TRADER1.getInvestor()
											.searchPortfolio(currency1);

									int originalunits = PROT2.getUnits();

									ListItem newitem = new PortfolioItem(CRYPTO1.getCryptocurrency(), quantity1);
									Trader2.addtoPortfolio(newitem);
									PROT2.setUnits(originalunits - quantity1); // - the units
																				// given
									// remove quantity from other trader

								}

								int originalcash2 = Trader2.getCash();
								int originalcash1 = Trader1.getCash();
								Trader1.setCash(originalcash1 + quantity2);
								Trader2.setCash(originalcash2 - quantity2);

								// here we add TRANSACTION TO BLOCKCHAIN
								// we check whether the transaction is consistent with previous
								// whether its first transaction or not

								Cryptocurrency crypto1 = CRYPTO1.getCryptocurrency();
								if (crypto1.getBlockChain().getHead() == null) {
									ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
											quantity2, 0);
									crypto1.getBlockchain().add(newblock);
									System.out.println(newblock.toString() + "\n");

								} else {
									// not first item
									ListItem Item = crypto1.getBlockChain().getPointer().getData();

									if (Item instanceof Trade) {
										Trade tradeitem = (Trade) Item;
										ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
												quantity2, tradeitem.blockHash);
										crypto1.getBlockchain().add(newblock);
										System.out.println(newblock.toString() + "\n");

									} else {
										Mine mineitem = (Mine) Item;
										ListItem newblock = new Trade(trader1, trader2, currency1, quantity1, currency2,
												quantity2, mineitem.blockHash);
										crypto1.getBlockchain().add(newblock);
										System.out.println(newblock.toString() + "\n");

									}

								}
							}

						}

					}
					break;
				case "REPORT": // command REPORT OF INVESTOR
					String reportUserid = words[1];
//SEARCHES OF INVESTOR IF IT EXISTS GIVES BACK REPORT
					if (investorList.searchItem(reportUserid) == null) {
						System.out.println("\nNot Found : \n There is no Investor with userID :  " + reportUserid);

					} else {
						System.out.println("\n\nReport Produced");

						InvestorItem inv = (InvestorItem) investorList.searchItem(reportUserid);

						inv.getInvestor().printPortfolio();
					}

					break;

				case "CRYPORT": // command CRYPORT : PRODUCES REPORT OF CRYPTOCURRENCY
//should produce a report of the status of a cryptocurrency.
					String currencySymbol = words[1];
					if (cryptolist.searchItem(currencySymbol) == null) {
						// not found
						System.out.println("Not Found \nThere is no cryptocurrency with symbol " + currencySymbol);
					} else {
						// found in the list
						// produces a report

						CryptoItem cryp = (CryptoItem) cryptolist.searchItem(currencySymbol);
						cryp.getCryptocurrency().printBlockChain();

						// verify block chain BY HASHCODE!
						if (cryp.getCryptocurrency().getBlockChain().verifyChain() != null) { // item returned means
																								// that someblock is
																								// altered otherwise
																								// null
							System.out.println(
									"\n Verification Failed :Hashcode has been altered of this block with info :  ");

							Block block = (Block) cryp.getCryptocurrency().getBlockchain().verifyChain();
							if (block instanceof Trade) {
								Trade trade = (Trade) cryp.getCryptocurrency().getBlockchain().verifyChain();
								trade.toString();
							} else {

								Mine mine = (Mine) cryp.getCryptocurrency().getBlockchain().verifyChain();
								mine.toString();

							}
						} else {
							System.out.println("\nSuccessfully verified : No Altered hashcode"); // SUCCESSFUL
																									// VERIFICATION
						}
					}
					break;

				}
			}
		}
	}

}
