package artemislite;

import java.util.ArrayList;

/*
 * THIS CLASS WILL HOLD THE PLAYER DETAILS AND GAME STATE OF THE BOARD
 * 
 */
import java.util.Scanner;

public class GameState {
	
	
	
	//Array List that will hold the gameBoard Square values
	ArrayList <Square> gameBoard = new ArrayList<Square>();
	
	//Array List that will hold the organistion values
	public ArrayList<Organisation> organistaions = new ArrayList<Organisation>();
		
	
	/**
	 * @Oscar
	 * Creates the game board
	 * There is no player input. We specify all the values manually.
	 * We can run this method to recreate a fresh board at any time.
	 */
	
	public void createGameBoard() {
		
		//Constructing the squares for the board
		//						(name, 										squareCost, upgrade, playerOwnerValue, costToUpgrade, landingCost, systemValue, 		isSystem)
		//Staring square 
		Square sq0 = new Square("FUNDING CENTRE" ,     							0,        	0,      	0,                 	0,         	 0,             0,      false);
		
		//FIRST SYSTEM !! WILL CHANGE
		Square sq1 = new Square("EGS 1 - CRAWLER TRANSPORTER",   				1000,       0,         	0, 					1000, 		1000, 			1,	 	true);
		Square sq2 = new Square("EGS 2 - VEICHLE ASSEMBLY", 					1000, 		0, 			0, 					1000, 		1000,			1, 		true);
		
		//SECOND SYSTEM !! WILL CHANGE 
		Square sq3 = new Square("SLS 1 - SOLID ROCKET BOOSTERS", 				2000, 		0 , 		0, 					2000, 		2000,			2, 		true);
		Square sq4 = new Square("SLS 2 - RS-25 ENGINE", 						2000, 		0 , 		0, 					2000, 		2000, 			2, 		true);
		Square sq5 = new Square("SLS 3 - SPACECRAFT AND PAYLOAD ADAPTER", 		2000, 		0 , 		0, 					2000, 		2000, 			2, 		true);
		
		//THE NOTHING SQAURE !! WIL CHANGE
		Square sq6 = new Square("DEAD SPACE" , 									0, 			0, 			0, 					0, 			0, 				0, 		false);
		
		//THIRD SYSTEM !! WILL CHANGE
		Square sq7 = new Square("ORION 1 - SERVICE MODULE", 					3000, 		0, 			0, 					3000, 		3000, 			3, 		true);
		Square sq8 = new Square("ORION 2 - CREW MODULE", 						3000, 		0, 			0, 					3000, 		3000, 			3, 		true);
		Square sq9 = new Square("ORION 3 - LAUNCH ABORT SYSTEM", 				3000, 		0, 			0, 					3000, 		3000, 			3, 		true);
		
		//FIRST SYSTEM !! WILL CHANGE
		Square sq10 = new Square("GATEWAY1 - POWER AND PROPULSION", 			4000, 		0, 			0, 					4000, 		4000, 			4, 		true);
		Square sq11 = new Square("GATEWAY2 - HABITATION AND LOGISTICS OUTPOST", 4000, 		0, 			0, 					4000,		4000, 			4, 		true);
		
		//add above squares to the arrayList
		this.gameBoard.add(sq0);
		this.gameBoard.add(sq1);
		this.gameBoard.add(sq2);
		this.gameBoard.add(sq3);
		this.gameBoard.add(sq4);
		this.gameBoard.add(sq5);
		this.gameBoard.add(sq6);
		this.gameBoard.add(sq7);
		this.gameBoard.add(sq8);
		this.gameBoard.add(sq9);
		this.gameBoard.add(sq10);
		this.gameBoard.add(sq11);
		
	}
	
	/**
	 * @Oscar
	 * Asks the user how many players are playing.
	 * It uses the number of player values to run a creation loop.
	 * In this loop it asks for names and creates the orgainsation objects
	 * It then adds them to an arraylist.
	 * 
	 */
	
	public void createOrganistaions() {

		// Scanner for name input
		Scanner scanner = new Scanner(System.in);

		// Organisation to be run through the for loop and added to the ArraList. This
		// arrayList is then returned.
		Organisation organisation;

		// Name String to hold chosen name.
		String name;

		// Current Player value in the creation loop
		int playerValue = 1;

		// number of organistions to be created
		int playerNumber;
		
		//control for input
		boolean inputControl= false;

		// System Prompt

		System.out.println("\nTo play Artemis Lite it requires between 2 and 4 Players. ");

	
		
		do {
			
			System.out.println("\nPlease input the number of players and press enter :");
			
			playerNumber = scanner.nextInt();
			
			if(playerNumber >= 2 && playerNumber <= 4) {
				inputControl=true;
			} else {
				System.out.println("\nInvlaid input. Please input 2,3 or 4 and press enter.");
			}
			
		}while (!inputControl);
		
		

		// success prompt
		System.out.println("\nYou have specified " + playerNumber + " players. Get ready to name your Organisation!");

		for (int i = 0; i < playerNumber; i++) {

			System.out.println("\nPlayer " + playerValue
					+ " : Please Input your Organistaion Name. Type in your value and press enter :");

			// Taking the input and assigning it to name String
			name = scanner.next();

			// Name entered successfully
			System.out
					.println("\nName Entered Successfully. Player " + playerValue + " Your organsiation has been created!");

			// create object to be returned. !!__!!! THESE ARE DEFAULT STARTING VALUES. NEED
			// TO DISCUSS AND CONFRIM!!!!_____!!!___!!!____!1
			organisation = new Organisation(name, 4000, 0, playerValue);

			// add to the player value. This ensures that once player one has been completed
			// the player value for player 2 will be 2.
			playerValue++;

			this.organistaions.add(organisation);

		}

	}
	
	
	
	
	
		
	

}
