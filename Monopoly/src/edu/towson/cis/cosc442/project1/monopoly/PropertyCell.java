package edu.towson.cis.cosc442.project1.monopoly;

public class PropertyCell extends Cell {
	private String colorGroup;
	private int housePrice;
	private int numHouses;
	private int rent;
	private int sellPrice;

	/**
	 * Returns the color group to which this property belongs.
	 * @return the color group of the property
	 */
	public String getColorGroup() {
		return colorGroup;
	}

	/**
	 * Returns the price required to build a house on this property.
	 * @return the house building price
	 */
	public int getHousePrice() {
		return housePrice;
	}

	/**
	 * Returns the current number of houses built on this property.
	 * @return the number of houses on the property
	 */
	public int getNumHouses() {
		return numHouses;
	}
    
    @Override
    /**
     * Returns the selling price of this property.
     * @return the sell price of the property
     */
    public int getPrice() {
		return sellPrice;
	}

	/**
	 * Calculates and returns the rent amount to be charged based on ownership and houses.
	 * @return the rent amount to charge
	 */
	public int getRent() {
		int rentToCharge = rent;
		String [] monopolies = theOwner.getMonopolies();
		rentToCharge = calculateMonopoliesRent(rentToCharge, monopolies);
		if(numHouses > 0) {
			rentToCharge = rent * (numHouses + 1);
		}
		return rentToCharge;
	}

	/**
	 * Calculates adjusted rent by doubling it if the property color group is part of the owner's monopolies.
	 * @param rentToCharge the base rent amount before monopoly adjustment
	 * @param monopolies an array of color groups owned as monopolies by the player
	 * @return the adjusted rent after monopoly check
	 */
	private int calculateMonopoliesRent(int rentToCharge, String[] monopolies) {
		for(int i = 0; i < monopolies.length; i++) {
			if(monopolies[i].equals(colorGroup)) {
				rentToCharge = rent * 2;
			}
		}
		return rentToCharge;
	}

	/**
	 * Executes the property's action when a player lands on it, charging rent if owned by another player.
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

	/**
	 * Sets the color group for this property.
	 * @param colorGroup the color group to assign to the property
	 */
	public void setColorGroup(String colorGroup) {
		this.colorGroup = colorGroup;
	}

	/**
	 * Sets the price to build a house on this property.
	 * @param housePrice the house building price to set
	 */
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	/**
	 * Sets the number of houses currently built on this property.
	 * @param numHouses the number of houses to set
	 */
	public void setNumHouses(int numHouses) {
		this.numHouses = numHouses;
	}

	/**
	 * Sets the selling price for this property.
	 * @param sellPrice the sell price to assign
	 */
	public void setPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	/**
	 * Sets the base rent amount for this property without monopoly adjustments.
	 * @param rent the rent amount to set
	 */
	public void setRent(int rent) {
		this.rent = rent;
	}
}
