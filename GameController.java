package artemislite;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameController {

	// player turn value
	static int playerTurnValue = 0;

	/*
	 * Rolls two separate dice and returns their total value
	 * 
	 * @return total dice value
	 * 
	 * @Oscar
	 */
	public static int rollDice() {

		// min and max roll vlues for a single die
		int min = 1;
		int max = 6;

		// Thread to add sleeps to the roll
		Thread thread = new Thread();

		// importing random calls to generate random dice number
		Random random = new Random();

		int dice1 = random.nextInt(max - min + 1) + min;
		int dice2 = random.nextInt(max - min + 1) + min;
		int total = dice1 + dice2;

		try {

			System.out.println("\nYou roll the dice.");

			// 2s wait.
			thread.sleep(2000);

			System.out.println("\nThe first dice rolls is " + dice1 +".");

			// 2s wait.
			thread.sleep(2000);

			System.out.println("The second dice roll is " + dice2+".");

			// 1s wait.
			thread.sleep(1000);

			System.out.println("\nYour total Roll is " + total+".");

		} catch (InterruptedException e) {
			// TODO: handle exception
		}

		return total;

	}

	/*
	 * @Oscar Handles player movement. It uses the organistion object var of
	 * position values, puts it into the index of the gameboard to find and change
	 * where the player is located on the board.
	 */

	public void move(ArrayList<Square> gameBoard, Organisation organisation) {

		// get player current postion value
		int currentPlayerPostion = organisation.getPlayerPositionValue();

		// current square player is on
		Square currentSquare = gameBoard.get(currentPlayerPostion);

		// Declare Current Square
		System.out.println("\nYou are currently on " + currentSquare.getName() +".");

		// roll the dice method. Assign the value to dice roll int
		int diceRoll = rollDice();

		// add the dice roll to player position using for loop - if it exceeds 12 reset
		for (int loop = 0; loop < diceRoll; loop++) {

			// Array starts at 0 and ends at 11 (12 squares total)
			if (currentPlayerPostion == 11) {
				currentPlayerPostion = 0;
				passGo(organisation);
			} else {
				currentPlayerPostion++;

			}

			// New Square vfmalue
			
			organisation.setPlayerPositionValue(currentPlayerPostion);
			

		}
		System.out.println("\nYou move and land on " + gameBoard.get(currentPlayerPostion).getName() +"");
		

		// New Square value
		currentSquare = gameBoard.get(currentPlayerPostion);

	}

	/*
	 * @Oscar Simple method to get the current player for use in other methods
	 */
	public static Organisation getCurrentPlayer(ArrayList<Organisation> organsiations) {

		// as we include 0 as the first value - need to minus 1 from size
		int playerNo = organsiations.size();

		if (playerTurnValue == playerNo) {
			playerTurnValue = 0;

		}

		return organsiations.get(playerTurnValue);

	}

	/*
	 * @Oscar Handles player changing. Uses a variable in this class called
	 * playerTurnValue(see top) to keep track of which players turn is next. IT uses
	 * this value and puts it into the organisation arrayList index value to return
	 * the organsiation whos turn is up next.
	 */
	public Organisation changePlayer(ArrayList<Organisation> organsiations) {

		// as we include 0 as the first value - need to minus 1 from size
		int playerNo = organsiations.size() - 1;

		// We need to increment the player Turn value - therefore we cannot return
		// within the id statement - it does not allow us to reach
		// the playerTurnValue++ this way. If we hold them in an object and return them
		// after, we can get around this
		Organisation tempOrg = new Organisation();

		// This if statement handles reseting the player turn to the first player if
		// last player has taken their turn
		if (this.playerTurnValue > playerNo) {
			playerTurnValue = 0;
			tempOrg = organsiations.get(playerTurnValue);
		} else {
			tempOrg = organsiations.get(playerTurnValue);
		}

		// Increment the player turn value by one. When this method is called again it
		// will return the next player
		playerTurnValue++;
		

		// returning organisation who is next in turn
		return tempOrg;

	}

	public static void passGo(Organisation organisation) {
		System.out.println("\n You pass the funding centre. 2000 credits has been awarded to your organistaion.");
		organisation.setCredit(organisation.getCredit() + 2000);
	}

	/*
	 * @Oscar
	 */
	public boolean boardCompleteWinCheck(ArrayList<Square> gameBoard) {

		int upgradeTracker;

		for (int i = 0; i < gameBoard.size(); i++) {

			upgradeTracker = gameBoard.get(i).getUpgrade();

			if (!(upgradeTracker == 4 ) && gameBoard.get(i).isSystem()) {
				return true;
			}

		}

	
		System.out.println("GAME OVER! YOU WIN!");
		return false;

	}

	/*
	 * @William check square method needs tot ake the orgainstaions array instead
	 * odf a single organisation in the parameters. Then use this with the get
	 * player method
	 */
	public static void checkSquare(ArrayList<Square> gameBoard, ArrayList<Organisation> organisations) {
        // scanner set up
        Scanner scanner = new Scanner(System.in);
        String input;
        int playerInput;
        // get square
        int currentPlayerPostion = getCurrentPlayer(organisations).getPlayerPositionValue();
        Square currentSquare = gameBoard.get(currentPlayerPostion);
        // square owner
        int squareOwner = currentSquare.getPlayerValueOwner();
        
        // loop continue conditions
        boolean loopCont = true;
        boolean loopCont2 = true;
        boolean loopCont3 = true;
        boolean loopCont4 = true;
        if (currentSquare.isSystem()) { // check if square is system
            if (currentSquare.getPlayerValueOwner() == 0) { // check square owner - not owned
                do { // do while checks if input is valid
                    System.out.println("This square is unowned. Would you like to purchase it for "
                            + currentSquare.getSquareCost() + " credits? Y/N");
                    input = scanner.next();
                    if (input.equalsIgnoreCase("y")) { // player wants to buy square
                        buySquare(gameBoard, organisations);
                        endTurn(gameBoard, organisations);
                        loopCont = false;
                    } else if (input.equalsIgnoreCase("n")) { // player doesn't want square
                        do { // do while checks if input is valid
                            System.out.println("Would another player like to purchase the sqaure? Y/N");
                            input = scanner.next();
                            if (input.equalsIgnoreCase("y")) { // another player does want square
                                do {
                                    for (Organisation organisation : organisations) {
                                        System.out.println(
                                                organisation.getName() + " do you wish to purchase this square? Y/N");
                                        input = scanner.next();
                                        if (input.equalsIgnoreCase("y")) {
                                            anotherPlayerBuySquare(gameBoard, organisation, currentSquare);
                                            endTurn(gameBoard, organisations);
                                            loopCont3 = false;
                                        } else if (input.equalsIgnoreCase("n")) {
                                            loopCont3 = false;
                                        } else {
                                            System.out.println("Invalid input... Please type Y/N");
                                        }
                                    }
                                    loopCont2 = false;
                                } while (loopCont3);
                            } else if (input.equalsIgnoreCase("n")) { // no other player wants the square
                                endTurn(gameBoard, organisations);
                                loopCont = false;
                                loopCont2 = false;
                            } else { // invalid input
                                System.out.println("Invalid input... Please type Y/N");
                            }
                            loopCont = false;
                        } while (loopCont2);
                    } else { // invalid input
                        System.out.println("Invalid input... Please type Y/N");
                    }
                } while (loopCont);
            } else if (currentSquare.getPlayerValueOwner() == getCurrentPlayer(organisations).getPlayerNumber()) {
                System.out.println("You already own this square...");
                endTurn(gameBoard, organisations);
            } else { // square owned by someone else
                int actualVal = squareOwner - 1;
                Organisation owner;
                owner = organisations.get(actualVal);
                System.out.println("This sqaure is owned by " + owner.getName());
                do { // do while checks if input is valid
                    System.out
                            .println(owner.getName() + " would you like take payment for this? Please enter Y or N...");
                    input = scanner.next();
                    if (input.equalsIgnoreCase("y")) { // player wants to take payment
                        System.out.println("Now required to pay " + currentSquare.getLandingCost() + "credits to "
                                + owner.getName());
                        landingSquare(gameBoard, owner, organisations);
                        endTurn(gameBoard, organisations);
                        loopCont4 = false;
                    } else if (input.equalsIgnoreCase("n")) { // player does not want payment
                        System.out.println(owner.getName() + " does not want payemnt...");
                        endTurn(gameBoard, organisations);
                        loopCont4 = false;
                    } else { // invalid input
                        System.out.println("Invalid input... Please type Y/N");
                    }
                } while (loopCont4);
            }
        } else if (currentSquare == gameBoard.get(6) || currentSquare == gameBoard.get(0)) {
            System.out.println("You have landed on " + currentSquare.getName()
                    + "... There are no options available for the current square...");
            endTurn(gameBoard, organisations);
        }
    }







    
	/*
     * @William end turn method- has win and lose checks
     */
	public static void endTurn(ArrayList<Square> gameBoard, ArrayList<Organisation> organisations) {
		// instantiate game controller
		GameController gameController = new GameController();
		// scanner set up
		Scanner scanner = new Scanner(System.in);
		String input;
		int playerInput;
		boolean loopCon = true;
		do {
			System.out.println("Your turn is about to end... Would you like to upgrade a system? Y/N ");
			input = scanner.next();
			if (input.equalsIgnoreCase("y")) {
				upgradeSquare(gameBoard, organisations);
				loopCon = false;
			} else if (input.equalsIgnoreCase("n")) {
				System.out.println("You have decided not to upgrade a square...");
				loopCon = false;
			} else {
				System.out.println("Invalid input... Please enter Y or N");
			}
		} while (loopCon);

		System.out.println("Your turn is over... Next player");

	}
	
	
	/**
     * 
     * @param gameBoard
     * @param organisation
     */
	public static void anotherPlayerBuySquare(ArrayList<Square> gameBoard, Organisation organisation,
			Square previousPlayerSquare) {
		/**
		 * ReduceCredit get square and cost, display original credit, then minus cost of
		 * square from credit, dipslay new credit.
		 */
		Scanner scanner = new Scanner(System.in);
		String input;

		// ArrayList holding current system
		ArrayList<Square> currentSystem = new ArrayList<>();
		// get player current position value
		Square currentSquare = previousPlayerSquare;
		// current square player is on

		// current system
		int system = currentSquare.getSystemValue();
		for (Square square : gameBoard) {
			if (currentSquare.getSystemValue() == system) {
				currentSystem.add(square);
			}
		}
		

		System.out.println(currentSquare.getName() + " " + currentSquare.getSquareCost());

		// loop continuation condition
		boolean loopCont = true;

		// business rule (not enough credit)
		if (currentSquare.getSquareCost() > organisation.getCredit()) {
			System.out.println("Can't buy not enough credit ");
			// business rule (credit will be 0 if you buy)
		} else if (currentSquare.getSquareCost() == organisation.getCredit()) {
			do {
				System.out.println("You will have 0 credit after buy... Would you still like to buy the square? Y/N");
				input = scanner.next();
				if (input.equalsIgnoreCase("y")) {
					organisation.setCredit(organisation.getCredit() - currentSquare.getSquareCost());
					currentSquare.setPlayerValueOwner(organisation.getPlayerNumber());
					System.out.println(currentSquare.getName() + " bought");
					System.out.println("New Credit " + organisation.getCredit());
					loopCont = false;
				} else if (input.equalsIgnoreCase("n")) {
					System.out.println("You decided not to purchase the square...");
					loopCont = false;
				} else {
					System.out.println("Invalid input... Please enter Y or N");
				}
			} while (loopCont);
		} else {
			organisation.setCredit(organisation.getCredit() - currentSquare.getSquareCost());
			currentSquare.setPlayerValueOwner(organisation.getPlayerNumber());
			System.out.println(currentSquare.getName() + "bought");
			System.out.println("New Credit " + organisation.getCredit());
		}
	}
	/*
	 * @Aine buy Square method
	 * 
	 */
	public static void buySquare(ArrayList<Square> gameBoard, ArrayList<Organisation> organisation) {
		/**
		 * ReduceCredit get square and cost, display original credit, then minus cost of
		 * square from credit, dipslay new credit.
		 */
		Scanner scanner = new Scanner(System.in);
		String input;
		// ArrayList holding current system
		ArrayList<Square> currentSystem = new ArrayList<>();
		// get player current position value
		int currentPlayerPostion = getCurrentPlayer(organisation).getPlayerPositionValue();
		// current square player is on
		Square currentSquare = gameBoard.get(currentPlayerPostion);
		System.out.println(currentSquare.getName() + " " + currentSquare.getSquareCost());
		// current system
		int system = currentSquare.getSystemValue();
		for (Square square : gameBoard) {
			if (currentSquare.getSystemValue() == system) {
				currentSystem.add(square);
			}
		}
		
		
		// loop continuation condition
		boolean loopCont = true;
		if (currentSquare.getSquareCost() > getCurrentPlayer(organisation).getCredit()) { // not enough credit
			System.out.println("Can't buy not enough credit ");
			// business rule (credit will be 0 if you buy)
		} else if (currentSquare.getSquareCost() == getCurrentPlayer(organisation).getCredit()) {
			// lose mechanic
			do {
				System.out.println("You will have 0 credit after buy... Would you still like to buy the square? Y/N");
				input = scanner.next();
				if (input.equalsIgnoreCase("y")) {
					getCurrentPlayer(organisation)
							.setCredit(getCurrentPlayer(organisation).getCredit() - currentSquare.getSquareCost());
					currentSquare.setPlayerValueOwner(getCurrentPlayer(organisation).getPlayerNumber());
					System.out.println(currentSquare.getName() + " bought");
					System.out.println("New Credit " + getCurrentPlayer(organisation).getCredit());
					loopCont = false;
				} else if (input.equalsIgnoreCase("n")) {
					System.out.println("You decided not to purchase the square...");
					loopCont = false;
				} else {
					System.out.println("Invalid input... Please enter Y or N");
				}
			} while (loopCont);
		} else {
			getCurrentPlayer(organisation)
					.setCredit(getCurrentPlayer(organisation).getCredit() - currentSquare.getSquareCost());
			currentSquare.setPlayerValueOwner(getCurrentPlayer(organisation).getPlayerNumber());
			System.out.println(currentSquare.getName() + "bought");
			System.out.println("New Credit " + getCurrentPlayer(organisation).getCredit());
		}
	}

	/*
	 * @Aine get and display organisation credits
	 * 
	 */
	public static void getCredit(ArrayList<Organisation> organisation) {
		
		Organisation org = getCurrentPlayer(organisation);
		/**
		 * player can access their credit during game
		 */
		System.out.println("Your credit is " + org.getCredit());
	}

	/*
	 * @Aine options when landing on a square. need to get the landing player and
	 * player owner and then transfer the funds to them if they say yes.
	 * 
	 */
	public static void landingSquare(ArrayList<Square> gameBoard, Organisation organisation,
			ArrayList<Organisation> organistaions) {
		/**
		 * ReduceCredit get square and cost, display original credit, then minus cost of
		 * square landing cost from credit, dipslay new credit.
		 * 
		 */

		int currentPlayerPostion = getCurrentPlayer(organistaions).getPlayerPositionValue();
		Square currentSquare = gameBoard.get(currentPlayerPostion);
		int currentSqaurePayment = currentSquare.getLandingCost();
		/**
		 * land on square from another player if square is owned (not equal to 0) minus
		 * the landing cost from the current players credit add credit to the player who
		 * owned the square
		 */
		// scanner
		String input;
		Scanner scanner = new Scanner(System.in);
		// current square owner
		int orgVal = gameBoard.get(currentPlayerPostion).getPlayerValueOwner();
		int orgValCorrect = orgVal - 1;
		if (currentSquare.getLandingCost() >= getCurrentPlayer(organistaions).getCredit()) {
			System.out.println("Warning, charging landing cost will cause player to go below 0 credits");
			System.out.println("Do you still want to charge? Press Y for YES, Press N for NO");
			input = scanner.next();
			// loop continue
			boolean loopCont = true;
			do {
				if (input.equalsIgnoreCase("y")) {
					getCurrentPlayer(organistaions)
							.setCredit(getCurrentPlayer(organistaions).getCredit() - currentSquare.getLandingCost());
					System.out.println("Landing cost charged");
					int recievingPlayerCredits = organistaions.get(orgValCorrect).getCredit() + currentSqaurePayment;
					organistaions.get(orgValCorrect).setCredit(recievingPlayerCredits);
					// need to get player who owns square, so that landing cost can be paid to them
					loopCont = false;
				} else if (input.equalsIgnoreCase("n")) {
					System.out.println("No landing cost charged");
					loopCont = false;
				} else {
					System.out.println("Invalid input... Please enter Y/N");
				}
			} while (loopCont);
		} else {
			getCurrentPlayer(organistaions)
					.setCredit(getCurrentPlayer(organistaions).getCredit() - currentSquare.getLandingCost());
			System.out.println("Landing cost charged");
			int recievingPlayerCredits = organistaions.get(orgValCorrect).getCredit() + currentSqaurePayment;
			organistaions.get(orgValCorrect).setCredit(recievingPlayerCredits);
		}
	}

	
	/**
	 * Upgrade system method using the gameboard index's to select the squares within the four systems. Upgrade system checks if the player owns 
	 * all the squares in the system before upgrade can commence. Then checks if player has enough credits to upgrade each time. Cost of upgrade increases 
	 * through each upgrade. 
	 * @param gameboard
	 * @param organisation
	 */
    
	 /**
	  *  @Charlie
	  * Upgrade square method - editied to upgrade one at a time 
	  * 
	  */
	public static void upgradeSquare(ArrayList<Square> gameBoard, ArrayList<Organisation> organisation) {

		Scanner scanner = new Scanner(System.in);
		int key = 0;
		do {

			System.out.println("Which system would you like to upgrade- please select a number");
			System.out.println();
			System.out.println("1. EGS System");
			System.out.println("2. SLS System");
			System.out.println("3. Orion System");
			System.out.println("4. Gateway System");
			System.out.println("5. Cancel Upgrade");

			key = scanner.nextInt();

			switch (key) {
			case 1:
				if (gameBoard.get(1).getPlayerValueOwner() == getCurrentPlayer(organisation).getPlayerNumber()
						&& gameBoard.get(2).getPlayerValueOwner() == getCurrentPlayer(organisation).getPlayerNumber()) {

					System.out.println("You own the EGS system");

					System.out.println("Which square in would you like to upgrade? ");
					System.out.println("a) " + gameBoard.get(1).getName());
					System.out.println("b) " + gameBoard.get(2).getName());
					System.out.println("c) Cancel Upgrade");
					String s = scanner.next();

					switch (s) {
					case "a":

						if (gameBoard.get(1).getUpgrade() == 0) {
							System.out.println("Attempting your First Upgrade.....");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(1).getCostToUpgrade()) {
								getCurrentPlayer(organisation).setCredit(getCurrentPlayer(organisation).getCredit()
										- gameBoard.get(1).getCostToUpgrade());
								gameBoard.get(1).incrementUpgrade();
								System.out.println(gameBoard.get(1).getName() + " has been upgraded for "
										+ gameBoard.get(1).getCostToUpgrade() + " credits");

							} else {
								System.out.println("You do not have enough for your first upgrade credits");
							}

						} else if (gameBoard.get(1).getUpgrade() == 1) {
							System.out.println("Attempting your Second Upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(1).getCostToUpgrade()
									* 1.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(1).getCostToUpgrade() * 1.5));
								gameBoard.get(1).incrementUpgrade();

								System.out.println(gameBoard.get(1).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(1).getCostToUpgrade() * 1.5) + " credits");

							} else {
								System.out.println("You dont have enough credits for your second upgrade");
							}

						} else if (gameBoard.get(1).getUpgrade() == 2) {
							System.out.println("Attempting your third upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(1).getCostToUpgrade() * 2) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(1).getCostToUpgrade() * 2));
								gameBoard.get(1).incrementUpgrade();

								System.out.println(gameBoard.get(1).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(1).getCostToUpgrade() * 2) + " credits");

							} else {
								System.out.println("You do not have enough credits for your third upgrade");
							}
						} else if (gameBoard.get(1).getUpgrade() == 3) {
							System.out.println("Attempting " + gameBoard.get(10).getName() + "'s major development");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(10).getCostToUpgrade()
									* 2.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(10).getCostToUpgrade() * 2.5));
								gameBoard.get(1).incrementUpgrade();

								System.out.println(gameBoard.get(10).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(1).getCostToUpgrade() * 2.5) + " credits");

							} else {
								System.out.println("You do not have enough credits for your major development");
							}
						}

						break;
					case "b":
						if (gameBoard.get(2).getUpgrade() == 0) {
							System.out.println("Attempting your First Upgrade...");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(2).getCostToUpgrade()) {
								getCurrentPlayer(organisation).setCredit(getCurrentPlayer(organisation).getCredit()
										- gameBoard.get(2).getCostToUpgrade());
								gameBoard.get(2).incrementUpgrade();
								System.out.println(gameBoard.get(2).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(2).getCostToUpgrade()) + " credits");

							} else {
								System.out.println("You do not have enough for your first upgrade credits");
							}

						} else if (gameBoard.get(2).getUpgrade() == 1) {
							System.out.println("Attempting your Second Upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(2).getCostToUpgrade()
									* 1.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(2).getCostToUpgrade() * 1.5));
								gameBoard.get(2).incrementUpgrade();

								System.out.println(gameBoard.get(2).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(2).getCostToUpgrade() * 1.5) + " credits");

							} else {
								System.out.println("You dont have enough credits for your second upgrade");
							}

						} else if (gameBoard.get(2).getUpgrade() == 2) {
							System.out.println("Attempting your third upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(2).getCostToUpgrade() * 2) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(2).getCostToUpgrade() * 2));
								gameBoard.get(2).incrementUpgrade();

								System.out.println(gameBoard.get(2).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(2).getCostToUpgrade() * 2) + " credits");

							} else {
								System.out.println("You do not have enough credits for your final upgrade");
							}
						} else if (gameBoard.get(2).getUpgrade() == 3) {
							System.out.println("Attempting " + gameBoard.get(2).getName() + "'s major development");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(2).getCostToUpgrade()
									* 2.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(2).getCostToUpgrade() * 2.5));
								gameBoard.get(2).incrementUpgrade();
								
								System.out.println(gameBoard.get(2).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(2).getCostToUpgrade() * 2.5) + " credits");

							} else {
								System.out.println("You do not have enough credits for your major development");
							}
						}
						break;
					case "c":
						upgradeSquare(gameBoard, organisation);
						break;
					default:
						System.out.println("Invalid input please try again");
						upgradeSquare(gameBoard, organisation);

					}

				} else {
					System.out.println("You do not own the system");
				}
				break;

			case 2:
				if (gameBoard.get(3).getPlayerValueOwner() == getCurrentPlayer(organisation).getPlayerNumber()
						&& gameBoard.get(4).getPlayerValueOwner() == getCurrentPlayer(organisation).getPlayerNumber()
						&& gameBoard.get(5).getPlayerValueOwner() == getCurrentPlayer(organisation).getPlayerNumber()) {

					System.out.println("You own the SLS system");

					System.out.println("Which square in would you like to upgrade? ");
					System.out.println("a) " + gameBoard.get(3).getName());
					System.out.println("b) " + gameBoard.get(4).getName());
					System.out.println("c) " + gameBoard.get(5).getName());
					System.out.println("d) Cancel Upgrade");
					String s = scanner.next();

					switch (s) {
					case "a":
						if (gameBoard.get(3).getUpgrade() == 0) {
							System.out.println("Attempting your First Upgrade...");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(3).getCostToUpgrade()) {
								getCurrentPlayer(organisation).setCredit(getCurrentPlayer(organisation).getCredit()
										- gameBoard.get(3).getCostToUpgrade());
								gameBoard.get(3).incrementUpgrade();
								System.out.println(gameBoard.get(1).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(3).getCostToUpgrade()) + " credits");

							} else {
								System.out.println("You do not have enough for your first upgrade credits");
							}

						} else if (gameBoard.get(3).getUpgrade() == 1) {
							System.out.println("Attempting your Second Upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(3).getCostToUpgrade()
									* 1.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(1).getCostToUpgrade() * 1.5));
								gameBoard.get(3).incrementUpgrade();

								System.out.println(gameBoard.get(3).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(3).getCostToUpgrade() * 1.5) + " credits");

							} else {
								System.out.println("You dont have enough credits for your second upgrade");
							}

						} else if (gameBoard.get(3).getUpgrade() == 2) {
							System.out.println("Attempting your third upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(3).getCostToUpgrade() * 2) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(3).getCostToUpgrade() * 2));
								gameBoard.get(3).incrementUpgrade();

								System.out.println(gameBoard.get(3).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(3).getCostToUpgrade() * 2) + " credits");

							} else {
								System.out.println("You do not have enough credits for your third upgrade");
							}
						} else if (gameBoard.get(3).getUpgrade() == 3) {
							System.out.println("Attempting " + gameBoard.get(3).getName() + "'s major development");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(3).getCostToUpgrade()
									* 2.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(3).getCostToUpgrade() * 2.5));
								gameBoard.get(3).incrementUpgrade();

								System.out.println(gameBoard.get(3).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(3).getCostToUpgrade() * 2.5) + " credits");

							} else {
								System.out.println("You do not have enough credits for your major development");
							}
						}
						break;
					case "b":
						if (gameBoard.get(4).getUpgrade() == 0) {
							System.out.println("Attempting your First Upgrade...");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(4).getCostToUpgrade()) {
								getCurrentPlayer(organisation).setCredit(getCurrentPlayer(organisation).getCredit()
										- gameBoard.get(4).getCostToUpgrade());
								gameBoard.get(4).incrementUpgrade();
								System.out.println(gameBoard.get(4).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(4).getCostToUpgrade()) + " credits");

							} else {
								System.out.println("You do not have enough for your first upgrade credits");
							}

						} else if (gameBoard.get(4).getUpgrade() == 1) {
							System.out.println("Attempting your Second Upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(4).getCostToUpgrade()
									* 1.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(4).getCostToUpgrade() * 1.5));
								gameBoard.get(4).incrementUpgrade();

								System.out.println(gameBoard.get(4).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(4).getCostToUpgrade() * 1.5) + " credits");

							} else {
								System.out.println("You dont have enough credits for your second upgrade");
							}

						} else if (gameBoard.get(4).getUpgrade() == 2) {
							System.out.println("Attempting your third upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(4).getCostToUpgrade() * 2) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(4).getCostToUpgrade() * 2));
								gameBoard.get(4).incrementUpgrade();

								System.out.println(gameBoard.get(4).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(4).getCostToUpgrade() * 3) + " credits");

							} else {
								System.out.println("You do not have enough credits for your final upgrade");
							}
						} else if (gameBoard.get(4).getUpgrade() == 3) {
							System.out.println("Attempting " + gameBoard.get(4).getName() + "'s major development");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(4).getCostToUpgrade()
									* 2.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(4).getCostToUpgrade() * 2.5));
								gameBoard.get(4).incrementUpgrade();

								System.out.println(gameBoard.get(4).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(4).getCostToUpgrade() * 2.5) + " credits");

							} else {
								System.out.println("You do not have enough credits for your major development");
							}
						}
						break;
					case "c":
						if (gameBoard.get(5).getUpgrade() == 0) {
							System.out.println("Attempting your First Upgrade...");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(5).getCostToUpgrade()) {
								getCurrentPlayer(organisation).setCredit(getCurrentPlayer(organisation).getCredit()
										- gameBoard.get(5).getCostToUpgrade());
								gameBoard.get(5).incrementUpgrade();
								System.out.println(gameBoard.get(5).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(5).getCostToUpgrade()) + " credits");

							} else {
								System.out.println("You do not have enough for your first upgrade credits");
							}

						} else if (gameBoard.get(5).getUpgrade() == 1) {
							System.out.println("Attempting your Second Upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(5).getCostToUpgrade()
									* 1.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(5).getCostToUpgrade() * 1.5));
								gameBoard.get(5).incrementUpgrade();

								System.out.println(gameBoard.get(5).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(5).getCostToUpgrade() * 1.5) + " credits");

							} else {
								System.out.println("You dont have enough credits for your second upgrade");
							}

						} else if (gameBoard.get(5).getUpgrade() == 2) {
							System.out.println("Attempting your third upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(5).getCostToUpgrade() * 2) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(5).getCostToUpgrade() * 2));
								gameBoard.get(1).incrementUpgrade();

								System.out.println(gameBoard.get(5).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(5).getCostToUpgrade() * 2) + " credits");

							} else {
								System.out.println("You do not have enough credits for your third upgrade");
							}
						} else if (gameBoard.get(5).getUpgrade() == 3) {
							System.out.println("Attempting " + gameBoard.get(10).getName() + "'s major development");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(5).getCostToUpgrade()
									* 2.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(5).getCostToUpgrade() * 2.5));
								gameBoard.get(5).incrementUpgrade();

								System.out.println(gameBoard.get(5).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(5).getCostToUpgrade() * 2.5) + " credits");

							} else {
								System.out.println("You do not have enough credits for your major development");
							}
						}
						break;
					case "d":
						upgradeSquare(gameBoard, organisation);
						break;
					default:
						System.out.println("Invalid input please try again");
						upgradeSquare(gameBoard, organisation);

					}

				} else {
					System.out.println("You do not own the system");
				}
				break;
			case 3:
				if (gameBoard.get(7).getPlayerValueOwner() == getCurrentPlayer(organisation).getPlayerNumber()
						&& gameBoard.get(8).getPlayerValueOwner() == getCurrentPlayer(organisation).getPlayerNumber()
						&& gameBoard.get(9).getPlayerValueOwner() == getCurrentPlayer(organisation).getPlayerNumber()) {

					System.out.println("You own the Orion System system");

					System.out.println("Which square in would you like to upgrade? ");
					System.out.println("a) " + gameBoard.get(7).getName());
					System.out.println("b) " + gameBoard.get(8).getName());
					System.out.println("c) " + gameBoard.get(9).getName());
					System.out.println("d) Cancel Upgrade");
					String s = scanner.next();

					switch (s) {
					case "a":
						if (gameBoard.get(7).getUpgrade() == 0) {
							System.out.println("Attempting your First Upgrade...");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(7).getCostToUpgrade()) {
								getCurrentPlayer(organisation).setCredit(getCurrentPlayer(organisation).getCredit()
										- gameBoard.get(7).getCostToUpgrade());
								gameBoard.get(7).incrementUpgrade();
								System.out.println(gameBoard.get(7).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(7).getCostToUpgrade()) + " credits");

							} else {
								System.out.println("You do not have enough for your first upgrade credits");
							}

						} else if (gameBoard.get(7).getUpgrade() == 1) {
							System.out.println("Attempting your Second Upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(7).getCostToUpgrade()
									* 1.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(7).getCostToUpgrade() * 1.5));
								gameBoard.get(7).incrementUpgrade();

								System.out.println(gameBoard.get(7).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(7).getCostToUpgrade() * 1.5) + " credits");

							} else {
								System.out.println("You dont have enough credits for your second upgrade");
							}

						} else if (gameBoard.get(7).getUpgrade() == 2) {
							System.out.println("Attempting your third upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(7).getCostToUpgrade() * 2) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(7).getCostToUpgrade() * 2));
								gameBoard.get(7).incrementUpgrade();

								System.out.println(gameBoard.get(7).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(7).getCostToUpgrade() * 2) + " credits");

							} else {
								System.out.println("You do not have enough credits for your final upgrade");
							}
						} else if (gameBoard.get(7).getUpgrade() == 3) {
							System.out.println("Attempting " + gameBoard.get(7).getName() + "'s major development");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(7).getCostToUpgrade()
									* 2.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(7).getCostToUpgrade() * 2.5));
								gameBoard.get(7).incrementUpgrade();

								System.out.println(gameBoard.get(7).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(7).getCostToUpgrade() * 2.5) + " credits");

							} else {
								System.out.println("You do not have enough credits for your major development");
							}
						}
						break;
					case "b":
						if (gameBoard.get(8).getUpgrade() == 0) {
							System.out.println("Attempting your First Upgrade...");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(8).getCostToUpgrade()) {
								getCurrentPlayer(organisation).setCredit(getCurrentPlayer(organisation).getCredit()
										- gameBoard.get(8).getCostToUpgrade());
								gameBoard.get(8).incrementUpgrade();
								System.out.println(gameBoard.get(8).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(8).getCostToUpgrade()) + " credits");

							} else {
								System.out.println("You do not have enough for your first upgrade credits");
							}

						} else if (gameBoard.get(8).getUpgrade() == 1) {
							System.out.println("Attempting your Second Upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(8).getCostToUpgrade()
									* 1.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(8).getCostToUpgrade() * 1.5));
								gameBoard.get(8).incrementUpgrade();

								System.out.println(gameBoard.get(8).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(8).getCostToUpgrade() * 1.5) + " credits");

							} else {
								System.out.println("You dont have enough credits for your second upgrade");
							}

						} else if (gameBoard.get(8).getUpgrade() == 2) {
							System.out.println("Attempting your third upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(8).getCostToUpgrade() * 2) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(8).getCostToUpgrade() * 2));
								gameBoard.get(8).incrementUpgrade();

								System.out.println(gameBoard.get(8).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(8).getCostToUpgrade() * 2) + " credits");

							} else {
								System.out.println("You do not have enough credits for your final upgrade");
							}
						} else if (gameBoard.get(8).getUpgrade() == 3) {
							System.out.println("Attempting " + gameBoard.get(10).getName() + "'s major development");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(8).getCostToUpgrade()
									* 2.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(10).getCostToUpgrade() * 2.5));
								gameBoard.get(8).incrementUpgrade();

								System.out.println(gameBoard.get(8).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(8).getCostToUpgrade() * 2.5) + " credits");

							} else {
								System.out.println("You do not have enough credits for your major development");
							}
						}
						break;
					case "c":
						if (gameBoard.get(9).getUpgrade() == 0) {
							System.out.println("Attempting your First Upgrade...");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(9).getCostToUpgrade()) {
								getCurrentPlayer(organisation).setCredit(getCurrentPlayer(organisation).getCredit()
										- gameBoard.get(9).getCostToUpgrade());
								gameBoard.get(9).incrementUpgrade();
								System.out.println(gameBoard.get(9).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(9).getCostToUpgrade()) + " credits");

							} else {
								System.out.println("You do not have enough for your first upgrade credits");
							}

						} else if (gameBoard.get(9).getUpgrade() == 1) {
							System.out.println("Attempting your Second Upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(9).getCostToUpgrade()
									* 1.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(9).getCostToUpgrade() * 1.5));
								gameBoard.get(9).incrementUpgrade();

								System.out.println(gameBoard.get(9).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(9).getCostToUpgrade() * 1.5) + " credits");

							} else {
								System.out.println("You dont have enough credits for your second upgrade");
							}

						} else if (gameBoard.get(9).getUpgrade() == 2) {
							System.out.println("Attempting your third upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(9).getCostToUpgrade() * 2) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(9).getCostToUpgrade() * 2));
								gameBoard.get(9).incrementUpgrade();

								System.out.println(gameBoard.get(9).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(9).getCostToUpgrade() * 2) + " credits");

							} else {
								System.out.println("You do not have enough credits for your final upgrade");
							}
						} else if (gameBoard.get(9).getUpgrade() == 3) {
							System.out.println("Attempting " + gameBoard.get(9).getName() + "'s major development");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(9).getCostToUpgrade()
									* 2.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(9).getCostToUpgrade() * 2.5));
								gameBoard.get(9).incrementUpgrade();

								System.out.println(gameBoard.get(9).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(9).getCostToUpgrade() * 2.5) + " credits");

							} else {
								System.out.println("You do not have enough credits for your major development");
							}
						}
						break;
					case "d":
						upgradeSquare(gameBoard, organisation);
						break;
					default:
						System.out.println("Invalid input please try again");
						upgradeSquare(gameBoard, organisation);

					}

				} else {
					System.out.println("You do not own the system");
				}
				break;
			case 4:
				if (gameBoard.get(10).getPlayerValueOwner() == getCurrentPlayer(organisation).getPlayerNumber()
						&& gameBoard.get(11).getPlayerValueOwner() == getCurrentPlayer(organisation)
								.getPlayerNumber()) {

					System.out.println("You own the Gateway system");

					System.out.println("Which square in would you like to upgrade? ");
					System.out.println("a) " + gameBoard.get(10).getName());
					System.out.println("b) " + gameBoard.get(11).getName());
					System.out.println("c) Cancel Upgrade");
					String s = scanner.next();

					switch (s) {
					case "a":
						if (gameBoard.get(10).getUpgrade() == 0) {
							System.out.println("Attempting your First Upgrade...");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(10).getCostToUpgrade()) {
								getCurrentPlayer(organisation).setCredit(getCurrentPlayer(organisation).getCredit()
										- gameBoard.get(10).getCostToUpgrade());
								gameBoard.get(10).incrementUpgrade();
								System.out.println(gameBoard.get(10).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(10).getCostToUpgrade()) + " credits");

							} else {
								System.out.println("You do not have enough for your first upgrade credits");
							}

						} else if (gameBoard.get(10).getUpgrade() == 1) {
							System.out.println("Attempting your Second Upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(10).getCostToUpgrade()
									* 1.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(10).getCostToUpgrade() * 1.5));
								gameBoard.get(10).incrementUpgrade();

								System.out.println(gameBoard.get(10).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(10).getCostToUpgrade() * 1.5) + " credits");

							} else {
								System.out.println("You dont have enough credits for your second upgrade");
							}

						} else if (gameBoard.get(10).getUpgrade() == 2) {
							System.out.println("Attempting your third upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(10).getCostToUpgrade()
									* 2) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(10).getCostToUpgrade() * 2));
								gameBoard.get(10).incrementUpgrade();

								System.out.println(gameBoard.get(10).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(10).getCostToUpgrade() * 2) + " credits");

							} else {
								System.out.println("You do not have enough credits for your third upgrade");
							}
						} else if (gameBoard.get(10).getUpgrade() == 3) {
							System.out.println("Attempting " + gameBoard.get(10).getName() + "'s major development");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(10).getCostToUpgrade()
									* 2.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(10).getCostToUpgrade() * 2.5));
								gameBoard.get(10).incrementUpgrade();

								System.out.println(gameBoard.get(10).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(10).getCostToUpgrade() * 2.5) + " credits");

							} else {
								System.out.println("You do not have enough credits for your major development");
							}
						}
						break;
					case "b":
						if (gameBoard.get(11).getUpgrade() == 0) {
							System.out.println("Attempting your First Upgrade...");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(11).getCostToUpgrade()) {
								getCurrentPlayer(organisation).setCredit(getCurrentPlayer(organisation).getCredit()
										- gameBoard.get(11).getCostToUpgrade());
								gameBoard.get(11).incrementUpgrade();
								System.out.println(gameBoard.get(11).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(11).getCostToUpgrade()) + " credits");

							} else {
								System.out.println("You do not have enough for your first upgrade credits");
							}

						} else if (gameBoard.get(11).getUpgrade() == 1) {
							System.out.println("Attempting your Second Upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(11).getCostToUpgrade()
									* 1.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(11).getCostToUpgrade() * 1.5));
								gameBoard.get(11).incrementUpgrade();

								System.out.println(gameBoard.get(11).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(11).getCostToUpgrade() * 1.5) + " credits");

							} else {
								System.out.println("You dont have enough credits for your second upgrade");
							}

						} else if (gameBoard.get(11).getUpgrade() == 2) {
							System.out.println("Attempting your third upgrade");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(11).getCostToUpgrade()
									* 2) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(11).getCostToUpgrade() * 2));
								gameBoard.get(11).incrementUpgrade();

								System.out.println(gameBoard.get(11).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(11).getCostToUpgrade() * 2) + " credits");

							} else {
								System.out.println("You do not have enough credits for your final upgrade");
							}

						} else if (gameBoard.get(11).getUpgrade() == 3) {
							System.out.println("Attempting " + gameBoard.get(11).getName() + "'s major development");

							if (getCurrentPlayer(organisation).getCredit() >= gameBoard.get(11).getCostToUpgrade()
									* 2.5) {
								getCurrentPlayer(organisation)
										.setCredit((int) (getCurrentPlayer(organisation).getCredit()
												- gameBoard.get(11).getCostToUpgrade() * 2.5));
								gameBoard.get(11).incrementUpgrade();

								System.out.println(gameBoard.get(11).getName() + " has been upgraded for "
										+ (int) (gameBoard.get(11).getCostToUpgrade() * 2.5) + " credits");

							} else {
								System.out.println("You do not have enough credits for your major development");
							}
						}
						break;
					case "c":
						upgradeSquare(gameBoard, organisation);
						break;
					default:
						System.out.println("Invalid input please try again");
						upgradeSquare(gameBoard, organisation);

					}

				} else {
					System.out.println("You do not own the system");
				}
				break;
			case 5:
				System.out.println("Cancelling Upgrade");
				// endTurn(gameBoard, organisation);
				return;

			default:
				System.out.println("Invalid input please try again....");
				upgradeSquare(gameBoard, organisation);

			}
		} while (key != 5);

	}

	   

	
	/*
	 * @Oscar
	 * Used to check whether the player wants to quit. If y is selected the game.
	 */
	public boolean gameEnd() {
		
		Scanner scanner = new Scanner(System.in);
		boolean loop = true;
		
		do {
			
			System.out.println("Are you sure you wish to quit? y/n");
			
			String awnser = scanner.nextLine();
			
		if(awnser.equalsIgnoreCase("y")){
			System.out.println("GAME OVER! YOU HAVE QUIT!");
			return false;
		} else if (awnser.equalsIgnoreCase("n")){
			System.out.println("You have chosen not to quit.");
			return true;
		} else {
			System.out.println("Incorrect input. Try again. Please enter either 'y' or 'n'.");
			
		}
		

	} while (loop = true);
		scanner.close();
		return true;
	}
	
	public void checkResource( Organisation organisation) {
		
		int currentPlayerResource = organisation.getCredit();
		
		System.out.println("\nYou have " + currentPlayerResource + " credits available.");
	}
	
	/**
	 * @Oscar
	 * @param gameBoard
	 * @param organisation
	 * outputs all owned properties of the current player
	 */
	public void showOwnedProperties (ArrayList<Square> gameBoard, Organisation organisation) {
		
		boolean ownershipCheck = false;
		
		System.out.println("\nYou own the following Research Stations: \n");
		
		for(Square squareOwned : gameBoard) {
			
			if(squareOwned.getPlayerValueOwner() == organisation.getPlayerNumber()) {
				
				
				if(squareOwned.getUpgrade() ==4) {
					System.out.println(squareOwned.getName() + ". \t Upgrade Level : MAX");
				} else {
					System.out.println(squareOwned.getName() + ". \t Upgrade Level : " + squareOwned.getUpgrade());
				}
				
				ownershipCheck = true;
				
			}
			
		}
		
		if(!ownershipCheck) {
			System.out.println("You currently do not own any property.");
		}
		
	}
	
	public boolean loseCheck(ArrayList<Organisation> organisations) {
		
		for (Organisation org : organisations) {
			
			if(org.getCredit() < 0) {
				
				System.out.println("\nGAME OVER! ORGANISATION HAS RUN OUT OF FUNDING.");
				return true;
			}
			
		}
	
		
		return false;
	}



}
