package item;

import android.widget.Button;

public class Card{

	private int x;
	private int y;
	private Button button;
	
	public Card(Button button, int x,int y) {
		this.x = x;
		this.y=y;
		this.button=button;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}
	

}
