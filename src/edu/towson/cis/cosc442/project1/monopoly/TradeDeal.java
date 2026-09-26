package edu.towson.cis.cosc442.project1.monopoly;

public class TradeDeal {
    private int amount;
    private int playerIndex;
    private String propertyName;

    /**
     * Returns the amount of money involved in the trade deal.
     * @return the trade deal amount
     */
    public int getAmount() {
        return amount;
    }
    
    /**
     * Retrieves the index of the player who is selling the property.
     * @return the seller player's index
     */
    public int getPlayerIndex() {
        return playerIndex;
    }
    
    /**
     * Gets the name of the property involved in the trade deal.
     * @return the property name
     */
    public String getPropertyName() {
        return propertyName;
    }
    
    /**
     * Creates a message describing the trade proposal including buyer, property, seller, and offer amount.
     * @return a string message representing the trade proposal
     */
    public String makeMessage() {
        String message = GameMaster.instance().getCurrentPlayer() + 
        	" wishes to purchase " +
        	propertyName + " from " + 
        	GameMaster.instance().getPlayer(playerIndex) +
        	" for " + amount + ".  " + 
        	GameMaster.instance().getPlayer(playerIndex) +
        	", do you wish to trade your property?";
        return message;
    }
    
    /**
     * Sets the amount of money offered in the trade deal.
     * @param amount the amount of money to set for the trade
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    /**
     * Assigns the property name involved in the trade deal.
     * @param propertyName the name of the property to set
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    /**
     * Defines the index of the player who is selling the property in the trade.
     * @param playerIndex the index of the seller player
     */
    public void setSellerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
