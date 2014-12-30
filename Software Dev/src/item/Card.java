package item;

import android.widget.Button;

public class Card{

	private int x;
	private int y;
	private Button button;
	
	/**
    * Create card (Constructor)
    *
    * @param button
    *		button that is linked to the card
    * @param x
    *		coordinate
    * @param y
    *		coordinate 
    */
	public Card(Button button, int x,int y) {
		this.x = x;
		this.y=y;
		this.button=button;
	}
    
    /**
    * Gets x position
    *
    * @return x
    * 
    */
	public int getX() {
		return x;
	}
    
    /**
    * Sets x position
    *
    * @param x
    * 
    */
	public void setX(int x) {
		this.x = x;
	}
    
    /**
    * Gets y position
    *
    * @return y
    * 
    */
	public int getY() {
		return y;
	}
    
    /**
    * Sets y position
    *
    * @param y
    * 
    */
	public void setY(int y) {
		this.y = y;
	}
    
    /**
    * Gets button
    *
    * @return button
    * 
    */
	public Button getButton() {
		return button;
	}
    
    /**
    * Sets button 
    *
    * @param button
    * 
    */
	public void setButton(Button button) {
		this.button = button;
	}
	

}
