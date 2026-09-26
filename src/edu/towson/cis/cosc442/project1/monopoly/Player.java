package edu.towson.cis.cosc442.project1.monopoly;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


public class Player {
	//the key of colorGroups is the name of the color group.
	private Hashtable<String, Integer> colorGroups = new Hashtable<String, Integer>();
	private boolean inJail;
	private int money;
	private String name;

	private Cell position;
	private ArrayList<PropertyCell> properties = new ArrayList<PropertyCell>();
	private ArrayList<Cell> railroads = new ArrayList<Cell>();
	private ArrayList<Cell> utilities = new ArrayList<Cell>();
	
	/**
	 * Constructs a Player, initializing in jail status to false and setting the starting position to 'Go' on the game board if available.
	 */
	public Player() {
		GameBoard gb = GameMaster.instance().getGameBoard();
		inJail = false;
		if(gb != null) {
			position = gb.queryCell("Go");
		}
	}

    /**
     * Assigns ownership of a property cell to the player, adds it to the appropriate property list, updates color groups, and deducts the purchase amount from the player's money.
     * @param property The property cell to purchase and assign to the player
     * @param amount The amount of money paid for the property
     */
    public void buyProperty(Cell property, int amount) {
        property.setTheOwner(this);
        if(property instanceof PropertyCell) {
            PropertyCell cell = (PropertyCell)property;
            properties.add(cell);
            colorGroups.put(
                    cell.getColorGroup(), 
                    new Integer(getPropertyNumberForColor(cell.getColorGroup())+1));
        }
        if(property instanceof RailRoadCell) {
            railroads.add(property);
            colorGroups.put(
                    RailRoadCell.COLOR_GROUP, 
                    new Integer(getPropertyNumberForColor(RailRoadCell.COLOR_GROUP)+1));
        }
        if(property instanceof UtilityCell) {
            utilities.add(property);
            colorGroups.put(
                    UtilityCell.COLOR_GROUP, 
                    new Integer(getPropertyNumberForColor(UtilityCell.COLOR_GROUP)+1));
        }
        setMoney(getMoney() - amount);
    }
	
	/**
	 * Checks if the player owns any monopolies allowing house purchases.
	 * @return true if the player owns at least one monopoly; false otherwise
	 */
	public boolean canBuyHouse() {
		return (getMonopolies().length != 0);
	}

	/**
	 * Checks whether the player owns a property with the specified name.
	 * @param property The name of the property to check ownership for
	 * @return true if the player owns the property; false otherwise
	 */
	public boolean checkProperty(String property) {
		for(int i=0;i<properties.size();i++) {
			Cell cell = (Cell)properties.get(i);
			if(cell.getName().equals(property)) {
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Transfers all owned properties to another player, resetting ownership and availability if the target player is null.
	 * @param player The player to receive the properties, or null to reset ownership
	 */
	public void exchangeProperty(Player player) {
		for(int i = 0; i < getPropertyNumber(); i++ ) {
			PropertyCell cell = getProperty(i);
			cell.setTheOwner(player);
			if(player == null) {
				cell.setAvailable(true);
				cell.setNumHouses(0);
			}
			else {
				player.properties.add(cell);
				colorGroups.put(
						cell.getColorGroup(), 
						new Integer(getPropertyNumberForColor(cell.getColorGroup())+1));
			}
		}
		properties.clear();
	}
    
    /**
     * Returns an array of all properties owned by the player, including properties, railroads, and utilities.
     * @return An array of all owned property cells
     */
    public Cell[] getAllProperties() {
        ArrayList<Cell> list = new ArrayList<Cell>();
        list.addAll(properties);
        list.addAll(utilities);
        list.addAll(railroads);
        return (Cell[])list.toArray(new Cell[list.size()]);
    }

	/**
	 * Retrieves the current amount of money the player has.
	 * @return The player's current money balance
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Returns an array of color group names for which the player owns all properties, i.e., monopolies.
	 * @return Array of monopoly color group names owned by the player
	 */
	public String[] getMonopolies() {
		ArrayList<String> monopolies = new ArrayList<String>();
		Enumeration<String> colors = colorGroups.keys();
		while(colors.hasMoreElements()) {
			String color = (String)colors.nextElement();
            if(!(color.equals(RailRoadCell.COLOR_GROUP)) && !(color.equals(UtilityCell.COLOR_GROUP))) {
    			Integer num = (Integer)colorGroups.get(color);
    			GameBoard gameBoard = GameMaster.instance().getGameBoard();
    			if(num.intValue() == gameBoard.getPropertyNumberForColor(color)) {
    				monopolies.add(color);
    			}
            }
		}
		return (String[])monopolies.toArray(new String[monopolies.size()]);
	}

	/**
	 * Gets the name of the player.
	 * @return The player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Allows the player to pay bail money to leave jail; if bankrupt after payment, properties are forfeited and ownership reset.
	 */
	public void getOutOfJail() {
		money -= JailCell.BAIL;
		if(isBankrupt()) {
			money = 0;
			exchangeProperty(null);
		}
		inJail = false;
		GameMaster.instance().updateGUI();
	}

	/**
	 * Gets the current position of the player on the game board.
	 * @return The cell representing the player's current position
	 */
	public Cell getPosition() {
		return this.position;
	}
	
	/**
	 * Retrieves the property at the specified index from the player's owned properties.
	 * @param index The index of the property to retrieve
	 * @return The PropertyCell at the specified index
	 */
	public PropertyCell getProperty(int index) {
		return (PropertyCell)properties.get(index);
	}
	
	/**
	 * Returns the total number of properties owned by the player.
	 * @return The count of properties owned
	 */
	public int getPropertyNumber() {
		return properties.size();
	}

	/**
	 * Returns the number of properties the player owns for a given color group.
	 * @param name The name of the color group
	 * @return The count of properties owned in the color group
	 */
	private int getPropertyNumberForColor(String name) {
		Integer number = (Integer)colorGroups.get(name);
		if(number != null) {
			return number.intValue();
		}
		return 0;
	}

	/**
	 * Determines whether the player is bankrupt (money less than or equal to zero).
	 * @return true if the player is bankrupt; false otherwise
	 */
	public boolean isBankrupt() {
		return money <= 0;
	}

	/**
	 * Checks if the player is currently in jail.
	 * @return true if the player is in jail; false otherwise
	 */
	public boolean isInJail() {
		return inJail;
	}

	/**
	 * Returns the number of railroad properties owned by the player.
	 * @return Count of railroads owned
	 */
	public int numberOfRR() {
		return getPropertyNumberForColor(RailRoadCell.COLOR_GROUP);
	}

	/**
	 * Returns the number of utility properties owned by the player.
	 * @return Count of utilities owned
	 */
	public int numberOfUtil() {
		return getPropertyNumberForColor(UtilityCell.COLOR_GROUP);
	}
	
	/**
	 * Pays rent to another player, deducting the rent value from this player's money and transferring it to the owner; handles bankruptcy by transferring ownership of properties.
	 * @param owner The player receiving the rent payment
	 * @param rentValue The amount of rent to pay
	 */
	public void payRentTo(Player owner, int rentValue) {
		if(money < rentValue) {
			owner.money += money;
			money -= rentValue;
		}
		else {
			money -= rentValue;
			owner.money +=rentValue;
		}
		if(isBankrupt()) {
			money = 0;
			exchangeProperty(owner);
		}
	}
	
	/**
	 * Attempts to purchase the property at the player's current position if it is available.
	 */
	public void purchase() {
		if(getPosition().isAvailable()) {
			Cell c = getPosition();
			c.setAvailable(false);
			if(c instanceof PropertyCell) {
				PropertyCell cell = (PropertyCell)c;
				purchaseProperty(cell);
			}
			if(c instanceof RailRoadCell) {
				RailRoadCell cell = (RailRoadCell)c;
				purchaseRailRoad(cell);
			}
			if(c instanceof UtilityCell) {
				UtilityCell cell = (UtilityCell)c;
				purchaseUtility(cell);
			}
		}
	}
	
	/**
	 * Purchases a specified number of houses for all properties in a given monopoly owned by the player, if sufficient funds are available.
	 * @param selectedMonopoly The name of the monopoly color group in which to buy houses
	 * @param houses The number of houses to purchase per property
	 */
	public void purchaseHouse(String selectedMonopoly, int houses) {
		GameBoard gb = GameMaster.instance().getGameBoard();
		PropertyCell[] cells = gb.getPropertiesInMonopoly(selectedMonopoly);
		if((money >= (cells.length * (cells[0].getHousePrice() * houses)))) {
			for(int i = 0; i < cells.length; i++) {
				int newNumber = cells[i].getNumHouses() + houses;
				if (newNumber <= 5) {
					cells[i].setNumHouses(newNumber);
					this.setMoney(money - (cells[i].getHousePrice() * houses));
					GameMaster.instance().updateGUI();
				}
			}
		}
	}
	
	/**
	 * Purchases a specific property cell, assigning ownership and deducting its price from the player's money.
	 * @param cell The property cell to purchase
	 */
	private void purchaseProperty(PropertyCell cell) {
        buyProperty(cell, cell.getPrice());
	}

	/**
	 * Purchases a specific railroad cell, assigning ownership and deducting its price from the player's money.
	 * @param cell The railroad cell to purchase
	 */
	private void purchaseRailRoad(RailRoadCell cell) {
	    buyProperty(cell, cell.getPrice());
	}

	/**
	 * Purchases a specific utility cell, assigning ownership and deducting its price from the player's money.
	 * @param cell The utility cell to purchase
	 */
	private void purchaseUtility(UtilityCell cell) {
	    buyProperty(cell, cell.getPrice());
	}

    /**
     * Sells a property cell by removing ownership from the player, updating property lists, and adding the sale amount to the player's money.
     * @param property The property cell to sell
     * @param amount The sale price received
     */
    public void sellProperty(Cell property, int amount) {
        property.setTheOwner(null);
        if(property instanceof PropertyCell) {
            properties.remove(property);
        }
        if(property instanceof RailRoadCell) {
            railroads.remove(property);
        }
        if(property instanceof UtilityCell) {
            utilities.remove(property);
        }
        setMoney(getMoney() + amount);
    }

	/**
	 * Sets the player's jail status to the specified value.
	 * @param inJail The new jail status to assign
	 */
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	/**
	 * Sets the player's current amount of money to the specified value.
	 * @param money The new money amount to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * Sets the player's name to the specified string.
	 * @param name The new name to assign to the player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Updates the player's position on the game board to the specified cell.
	 * @param newPosition The new position cell to assign to the player
	 */
	public void setPosition(Cell newPosition) {
		this.position = newPosition;
	}

    /**
     * Returns the player's name as its string representation.
     * @return The player's name
     */
    public String toString() {
        return name;
    }
    
    /**
     * Resets the player's property lists, clearing all owned properties, railroads, and utilities.
     */
    public void resetProperty() {
    	properties = new ArrayList<PropertyCell>();
    	railroads = new ArrayList<Cell>();
    	utilities = new ArrayList<Cell>();
	}
}
