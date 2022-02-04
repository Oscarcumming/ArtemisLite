/**
 * 
 */
package artemislite;

import java.util.Scanner;


/**
 * @author oscar
 *
 */
public class GameApp {
	
	//has to instatiated outside of the loop
	static Scanner scanner = new Scanner(System.in);
	
	// instatiate GameState object - contains the gamebaord Array and player organisation array
	static GameState gs = new GameState();

	// Instantiate GameController object - contains move, roll dice, change player
	static GameController gc = new GameController();
	
	//controls the menu loop - changing through players
	static boolean gameContinue = true;
	
	//changes the ending of the menu looop if it is a loss
	static boolean lose = false;
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//GAME START MESSAGE
		System.out.println("\nWELCOME TO ARTEMIS LITE - THE INTERSTELLAR SPACETRAVEL TEAMWORK BOARDGAME.");
		System.out.println("GET READY ORGANISATIONS OF THE FUTURE! IT IS UP TO YOU TO BRING HUMANITY TO THE STARS.");
		
		
		// call gamestate method to fill the array with a fresh gameboard - see method in Gamestate Class
		gs.createGameBoard();

		// call gamestate method to ask the user how many players are playing, what their org names are and then adds them to an arrayList
		gs.createOrganistaions();
		
		do{
			
			
			
			menu();
			
			//method :check win / lose conditions to break the game turn loop
			if (gameContinue) {
				gameContinue= gc.boardCompleteWinCheck(gs.gameBoard);
			}
			
			//checks to see if lose condition is met and then breaks the loop
			if(gameContinue) {
				lose = gc.loseCheck(gs.organistaions);
			}
			
			
		
			
		
			
			
			
			
			
		} while(gameContinue && !lose);
	
		//winning narrative
		if(!lose) {
			winMessage();
		} else if (lose) {
			loseMessage();
		}
		
		
		scanner.close();
		

	}
	
	
	/*
	 * Menu presented at the start of the turn to the current player. Use switch statement.
	 * @Oscar
	 */
	public static void menu() {
		
		
		
		//get the current organisation turn. 
		Organisation player = gc.getCurrentPlayer(gs.organistaions);
		
		//boolean to controll first menu
		boolean firstMenuEnd = false;
		
		//boolean to control second menu
		boolean secondMenuEnd = true;
		
		
		
		//Menu to be printed to screen and inform input
		System.out.println("\nGet ready " + player.getName() + ". It is your turn! Please type in the number of your chosen option below :\n");
		
		
		do {
			
		int key = 0;
			
		System.out.println("1. \tRoll dice");
		System.out.println("2. \tShow current resources");
		System.out.println("3. \tShow owned properties");
		System.out.println("4. \tQuit Game");
			
			
		//Ask for player input.
		key = scanner.nextInt();
		
		switch (key) {
		case 1: {
			//rolls dice and moves player
			gc.move(gs.gameBoard, player);
			firstMenuEnd = true;
			secondMenuEnd = false;
			break;
		}
		case 2: {
			
			//get current credits of player
			gc.checkResource(player);
			
			//message stating they are being brought back to the main menu again
			System.out.println("\nPlease choose a new option from below :\n");
			
			//method to show current resources
			break;
		}
		case 3 : {
			
			//get current credits of player
			gc.showOwnedProperties(gs.gameBoard, player);
			
			//message stating they are being brought back to the main menu again
			System.out.println("\nPlease choose a new option from below :\n");
			
			//method to show current resources
			break;
		}
		
		case 4 : {
			//call method to end the game
			GameApp.gameContinue = gc.gameEnd();
			
			boolean quitter = GameApp.gameContinue;
			
			if(!quitter) {
				firstMenuEnd = true;
				lose=true;
				gameContinue = false;
				break;
			}
			
			//if game has not ended the menu loops back to the beginning
			System.out.println("\nPlease choose a new option from below :\n");
			
			break;
			
		}
		
		default: {
			
			System.out.println("Input is invalid. Please try again using numbers 1, 2, 3, 4 followed by enter.");
	
			
		}
		}
		
		}while(!firstMenuEnd);
		
		
		
		
		//this is the second menu that occurs after the player selects from the first.
		//This will ask the player if they want to upgrade
		if(!secondMenuEnd){
			
			//checks the square you've landed on :
			// can buy, pass or pay player
			gc.checkSquare(gs.gameBoard, gs.organistaions);
			// method asking if they wish to upgrade - if yes then upgrade.
			
			//method to check if player owns all a system
			
			//then change player
			gc.changePlayer(gs.organistaions);
		}
		
		
		

	}
	
	/*
	 * @Oscar
	 * Output message when the game is won
	 */
		public static void winMessage() {
			// Thread to add sleeps to the roll
			Thread thread = new Thread();
			
			try {
				
				//Winning message
				thread.sleep(2000);
				System.out.println("\nCONGRATULATIONS! THE FINAL TECHNOLOGY HAS BEEN DEVELOPED!\n");
				thread.sleep(2000);
				System.out.println("NASA MAKES PREPARATIONS... ");
				thread.sleep(2000);
				System.out.println("THE THE ROCKET HAS BEEN ASSEMBLED.");
				thread.sleep(2000);
				System.out.println("PREPARE FOR LAUNCH... ");
				thread.sleep(2000);
				System.out.println("COUNTDOWN BEGINS!\n");
				thread.sleep(2000);
				System.out.println("10");
				thread.sleep(1000);
				System.out.println("9");
				thread.sleep(1000);
				System.out.println("8");
				thread.sleep(1000);
				System.out.println("7");
				thread.sleep(1000);
				System.out.println("6");
				thread.sleep(1000);
				System.out.println("5");
				thread.sleep(1000);
				System.out.println("4");
				thread.sleep(1000);
				System.out.println("3");
				thread.sleep(1000);
				System.out.println("2");
				thread.sleep(1000);
				System.out.println("1");
				thread.sleep(1000);
				System.out.println("LAUNCH!");
				thread.sleep(2000);
				System.out.println("\nA SHAKEY, UNCERIAN IGNITION...");
				thread.sleep(2000);
				System.out.println("SLOWLY IT LIFTS OFF THE GROUND....");
				thread.sleep(2000);
				System.out.println("ACCELERATION BEGINS...");
				thread.sleep(2000);
				System.out.println("THE ROCKET GETS FASTER...");
				thread.sleep(2000);
				System.out.println("AND FASTER...");
				thread.sleep(2000);
				System.out.println("THE ROCKET KEEPS STEADY CLIMBING ALITUDE...");
				thread.sleep(2000);
				System.out.println("READINGS ARE GOOD...");
				thread.sleep(2000);
				System.out.println("CLIMBING HIGHER...");
				thread.sleep(2000);
				System.out.println("FURTHER AND FURTHER...");
				thread.sleep(2000);
				System.out.println("HIGHER AND HIGHER...");
				thread.sleep(2000);
				System.out.println("THE ROCKET BREAKS THE ATMOSPHERE!\n");
				thread.sleep(2000);
				System.out.println("SUCCESS!\n");
				thread.sleep(4000);
				
				System.out.println("HUMANITY TAKES ITS FIRST STEPS IN THE EXPLORATION OF GREATER SPACE.");
				thread.sleep(3000);
				System.out.println("THIS WOULD NOT HAVE BEEN POSSIBLE WITHOUT YOU... ");
				thread.sleep(3000);
				System.out.println("\nTHE ORGANISATIONS OF THE FUTURE.");
				thread.sleep(3000);
				System.out.println("\nCONGRATULATIONS, YOU HAVE COMPLETED ARTEMIS LITE!");
				
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		public static void loseMessage() {
			
			// Thread to add sleeps to the roll
			Thread thread = new Thread();
		
			
			try {
				
				thread.sleep(2000);
				System.out.println("\nMISSION FAILED.");
				thread.sleep(2000);
				System.out.println("AN ORGANISATION HAS WITHRDAWN FROM THE GAME.");
				thread.sleep(2000);
				System.out.println("HUMANITY WILL HAVE TO WAIT... ");
				thread.sleep(2000);
				System.out.println("IN TAKING ITS FIRST STEPS TO TRAVEL ACROSS THE STARS");
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	
	
}
