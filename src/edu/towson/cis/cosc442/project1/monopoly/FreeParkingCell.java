package edu.towson.cis.cosc442.project1.monopoly;

public class FreeParkingCell extends Cell {

	/**
	 * Constructs a FreeParkingCell and sets its name to "Free Parking".
	 */
	public FreeParkingCell() {
		setName("Free Parking");
	}

	/**
	 * Defines the action performed when a player lands on the Free Parking cell, which is to do nothing.
	 */
	public void playAction() {
		return;
	}
}
