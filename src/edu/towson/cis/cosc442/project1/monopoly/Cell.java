package edu.towson.cis.cosc442.project1.monopoly;

public abstract class Cell {
	private boolean available = true;
	private String name;
	protected Player theOwner;

	/**
	 * Returns the name of the cell.
	 * @return The name of the cell.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves the player who owns the cell.
	 * @return The owner of the cell as a Player object, or null if unowned.
	 */
	public Player getTheOwner() {
		return theOwner;
	}
	
	/**
	 * Returns the price of the cell, defaulting to zero.
	 * @return The price of the cell as an integer.
	 */
	public int getPrice() {
		return 0;
	}

	/**
	 * Checks if the cell is currently available for purchase or interaction.
	 * @return True if the cell is available; false otherwise.
	 */
	public boolean isAvailable() {
		return available;
	}
	
	/**
	 * Executes the specific action associated with this cell in the game.
	 */
	public abstract void playAction();

	/**
	 * Sets the availability status of the cell.
	 * @param available The new availability status to set for the cell.
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	/**
	 * Assigns a name to the cell.
	 * @param name The name to assign to the cell.
	 */
	void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the player who owns the cell.
	 * @param owner The Player who will be set as the owner of the cell.
	 */
	public void setTheOwner(Player owner) {
		this.theOwner = owner;
	}
    
    /**
     * Returns the string representation of the cell, which is its name.
     * @return The name of the cell.
     */
    public String toString() {
        return name;
    }
}
