/**
 * 
 */
package artemislite;

import java.util.ArrayList;

/**
 * @author oscar
 *
 */
public class Square {
	
	/*
	 * Square vars to be set. We will create and set these all manually within a method. This is good for game control and rules control.
	 * Also allows us to reset the board with a method call- good for a replay feature.
	 */
	
	//Name of sqaure to be set
	private String name;
	
	//Cost of Sqaure to buy
	private int squareCost;
	
	//Current Level of upgrade - will be automatically set to 0 when instantiated. Max value of 4?
	private int upgrade;
	
	//Default is 0 meaning unowned. If a player buys a square this value changes to match their playerValue; 
	private int playerValueOwner;
	
	//Cost to upgrade.
	private int costToUpgrade;
	
	//Payment amount if another player lands on the square
	private int landingCost;
	
	//What system they belong to
	private int systemValue;
	
	//Checks to see if its a system Sqaure
	private boolean isSystem;

	
	//Default Constructor (doesn't do anything so ignore).
		public Square() {
			
		}
	
	/*
	 * @Oscar
	 * Constructor with fields. Will be cused to create the game board. Will be added to an arraylist
	 */
	public Square(String name, int squareCost, int upgrade, int playerValueOwner, int costToUpgrade, int landingCost, int systemValue, boolean isSystem) {
		super();
		this.name = name;
		this.squareCost = squareCost;
		this.upgrade = upgrade;
		this.playerValueOwner = playerValueOwner;
		this.costToUpgrade = costToUpgrade;
		this.landingCost = landingCost;
		this.systemValue = systemValue;
		this.isSystem = isSystem;
	}
	
	
	
	

	/*
	 * @Oscar
	 * Displays the squares value
	 */
	public void displayValues() {
		
		System.out.println("Values for Sqaure : " + this.name);
		System.out.println();
		System.out.println("Name: \t Square Cost : \t No. of Upgrades : \t Landing Cost : ");
		System.out.println(this.name + "\t" + this.squareCost + "\t\t" + this.upgrade + "\t\t\t" + this.landingCost);
		System.out.println("-------------------------------------------------------------------------------------");
		
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

	public int getSquareCost() {
		return squareCost;
	}

	public void setSquareCost(int squareCost) {
		this.squareCost = squareCost;
	}

	public int getUpgrade() {
		return upgrade;
	}
	
	/**
	 * Increment the upgrade variable as a setter. Business rule - can only be incremented if it is above 0  and less than 4 
	 */
	public void incrementUpgrade() {
		if(upgrade >=0 && upgrade <5) {
		this.upgrade++;
		} 
			
	}

	public int getPlayerValueOwner() {
		return playerValueOwner;
	}

	public void setPlayerValueOwner(int playerValueOwner) {
		this.playerValueOwner = playerValueOwner;
	}

	public int getCostToUpgrade() {
		return costToUpgrade;
	}

	public void setCostToUpgrade(int costToUpgrade) {
		this.costToUpgrade = costToUpgrade;
	}

	public int getLandingCost() {
		return landingCost;
	}

	public void setLandingCost(int landingCost) {
		this.landingCost = landingCost;
	}

	public int getSystemValue() {
		return systemValue;
	}

	public void setSystemValue(int systemValue) {
		this.systemValue = systemValue;
	}

	public boolean isSystem() {
		return isSystem;
	}

	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}
	
	
	
	
	
	
	

}
