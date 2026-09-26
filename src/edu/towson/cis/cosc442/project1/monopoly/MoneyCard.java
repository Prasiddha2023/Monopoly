package edu.towson.cis.cosc442.project1.monopoly;

public class MoneyCard extends Card {
    private int amount;
    private int cardType;
    
    private String label;
    
    /**
     * Constructs a MoneyCard with a label, amount of money, and card type.
     * @param label the label or description of the card
     * @param amount the monetary amount associated with the card
     * @param cardType the type identifier of the card
     */
    public MoneyCard(String label, int amount, int cardType){
        this.label = label;
        this.amount = amount;
        this.cardType = cardType;
    }

    /**
     * Applies the card's action by adding the amount to the current player's money.
     */
    public void applyAction() {
        Player currentPlayer = GameMaster.instance().getCurrentPlayer();
		currentPlayer.setMoney(currentPlayer.getMoney() + amount);
    }

    /**
     * Returns the type identifier of this MoneyCard.
     * @return the integer representing the card's type
     */
    public int getCardType() {
        return cardType;
    }

    /**
     * Returns the label or description of this MoneyCard.
     * @return the card's label as a String
     */
    public String getLabel() {
        return label;
    }
}
