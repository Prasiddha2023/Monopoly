package edu.towson.cis.cosc442.project1.monopoly;

public class RailRoadCell extends Cell {
	static private int baseRent;
	static public String COLOR_GROUP = "RAILROAD";
	static private int price;

	/**
	 * Sets the static base rent value for all Railroad cells.
	 * @param baseRent the base rent amount to set
	 */
	public static void setBaseRent(int baseRent) {
		RailRoadCell.baseRent = baseRent;
	}

	/**
	 * Sets the static purchase price for all Railroad cells.
	 * @param price the purchase price to set
	 */
	public static void setPrice(int price) {
		RailRoadCell.price = price;
	}
	
	/**
	 * Returns the purchase price of this Railroad cell.
	 * @return the purchase price of the Railroad cell
	 */
	public int getPrice() {
		return RailRoadCell.price;
	}

	/**
	 * Calculates and returns the rent for this Railroad cell based on the owner's number of Railroad properties.
	 * @return the rent amount to be charged
	 */
	public int getRent() {
		return RailRoadCell.baseRent * (int)Math.pow(2, theOwner.numberOfRR() - 1);
	}
	
	/**
	 * Executes the action when a player lands on this Railroad cell, charging rent if owned by another player.
	 */
	public void playAction() {
		Player currentPlayer = null;
		if(!isAvailable()) {
			currentPlayer = GameMaster.instance().getCurrentPlayer();
			if(theOwner != currentPlayer) {
				currentPlayer.payRentTo(theOwner, getRent());
			}
		}
	}
}
