package artemislite;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * 
 * @author 40316282 Oscar Cumming 
 * Class to hold Organsition values.
 */
public class Organisation {
	
	
	
	/*
	 * Organisation vars to be set
	 */
	
	//Player chosen name String
	private String name;
	
	//Current Player credit amount (Need to rename)
	private int credit;
	
	//The current position of the player on the board. Starts at 0.
	private int playerPositionValue;
	
	//The unique player number to be used to define ownership of squares. Also used to specifiy payment between players.
	private int playerNumber;
	
	
	
	
	
	//Default Constructor (doesn't do anything so ignore).
	public Organisation() {
		
	}

	
	/*
	 * @Oscar
	 * Constructor we will be using in organisation creation
	 */
	public Organisation(String name, int credit, int playerPositionValue, int playerNumber) {
		super();
		this.name = name;
		this.credit = credit;
		this.playerPositionValue = playerPositionValue;
		this.playerNumber = playerNumber;
	}
	
	
	
	
	
	
	/*
	 * @Oscar
	 * Displays Player organsitaion values
	 */
	
	public void displayValues() {
		
		System.out.println("Displaying Organisation " + this.name + " infomation : \n");
		System.out.println("Organisation Name           \t: " + this.name);
		System.out.println("Current Credits             \t: " + this.credit);
		System.out.println("Current Board postion Value \t: " + this.playerPositionValue);
		System.out.println("Player Number Value         \t:" + this.playerNumber);
		System.out.println("");
		
	}
	
	


	
	
	/*
	 * Just all the getters and setters.
	 * Special business rules apply to : 
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getPlayerPositionValue() {
		return playerPositionValue;
	}

	public void setPlayerPositionValue(int playerPositionValue) {
		this.playerPositionValue = playerPositionValue;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	
	
	

}
