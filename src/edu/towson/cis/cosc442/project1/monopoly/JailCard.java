package edu.towson.cis.cosc442.project1.monopoly;


public class JailCard extends Card {
    int type;
    
    /**
     * Constructs a JailCard with the specified card type.
     * @param cardType the integer representing the type of the JailCard
     */
    public JailCard(int cardType) {
        type = cardType;
    }

    /**
     * Applies the action of sending the current player directly to jail without collecting $200.
     */
    public void applyAction() {
        Player currentPlayer = GameMaster.instance().getCurrentPlayer();
		GameMaster.instance().getGameBoard().queryCell("Jail");
		GameMaster.instance().sendToJail(currentPlayer);
    }

    /**
     * Returns the type identifier of this JailCard.
     * @return the integer representing the JailCard type
     */
    public int getCardType() {
        return type;
    }

    /**
     * Returns a descriptive label explaining the JailCard's effect.
     * @return a String describing the card's action
     */
    public String getLabel() {
        return "Go to Jail immediately without collecting" +
        		" $200 when passing the GO cell";
    }
}
