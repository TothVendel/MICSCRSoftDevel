package activity;

import com.example.yrdyt.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

class AnimatedButton {
	
	private ImageButton button;	
	private PuzzleTicTacToeActivity the_game;			
	private int cell_number;		
	private boolean taken;		
	private static final long DELAY_MS = 125;
	private int stage;
	private static int global_turn = 0;
	private int turn;
	
	private static Object animation_mutex = new Object( );

	private final Handler animation_handler = new Handler( );

	private final Runnable update_gui = new Runnable() {
	    public void run() {
	    	setImage( );
	    }
	};
	
	/**
    * Gets image button 
    *
    * @return this.button
    *      returns the image button 
    *
    */
	public ImageButton getImageButton() {
		return this.button;
	}
	
	/**
    * Sets images for the buttons
    *
    */
	private void setImage( ) {
		if((turn % 2) == 0) {
			switch(stage) {
				case 0: button.setImageResource(R.drawable.x); break;
				case 1:	button.setImageResource(R.drawable.x);	break;
				case 2:	button.setImageResource(R.drawable.x);	break;
				case 3: button.setImageResource(R.drawable.x); break;
				case 4:	button.setImageResource(R.drawable.x);	break;
				case 5:	button.setImageResource(R.drawable.x);	break;
				case 6: button.setImageResource(R.drawable.x); break;
				case 7:	button.setImageResource(R.drawable.x);	break;
			}
		} else {
			switch(stage) {
				case 0:	button.setImageResource(R.drawable.o);	break;
				case 1:	button.setImageResource(R.drawable.o);	break;
				case 2:	button.setImageResource(R.drawable.o);	break;
				case 3:	button.setImageResource(R.drawable.o);	break;
				case 4:	button.setImageResource(R.drawable.o);	break;
				case 5:	button.setImageResource(R.drawable.o);	break;
				case 6:	button.setImageResource(R.drawable.o);	break;
				case 7:	button.setImageResource(R.drawable.o);	break;
		}
		}
	}
	
	AnimatedButton(ImageButton b, PuzzleTicTacToeActivity g, int cell) {

		button = b;
		the_game = g;
		cell_number = cell;
		global_turn = 0;
		taken = false;

		button.setOnClickListener(new Button.OnClickListener( ) {
            public void onClick(View v) {

            	if(taken) {
            		the_game.getStatus().setText("This Cell is Already Taken");
            		return;
            	}
            	taken = true;

            	synchronized(animation_mutex) {

    				turn = global_turn;
    				global_turn++;
    				
            		Thread animation_thread = new Thread() {
            			public void run() {	           				

            				for(stage = 0; stage<8; stage++) {
            					animation_handler.post(update_gui);
            					SystemClock.sleep(DELAY_MS);
            				}
            			}
            		};            		
            		animation_thread.start();

            		the_game.update(cell_number, turn);
            	}
            }
        });
	}
}


public class PuzzleTicTacToeActivity extends Activity {

	private AnimatedButton buttons [] = new AnimatedButton [9];
	private TextView status;
	private int num_players;
	private Cell [] cells;

	enum Cell {
		OPEN,
		X,
		O
	}
	
	enum Outcome {
		NONE,
		P1_WON,
		P2_WON,
		COMPUTER_WON,
		CAT
	}
	
	/**
    * Setup the game
    *
    */	
	private void setupGame( ) {
		cells = new Cell [9];
		
		for(int i=0; i<9; i++) {
			cells[i] = Cell.OPEN;
		}
	}
	
	/**
    * Checks if you have three same x or o's in a row
    *
    * @param c1
    *            cell 
    *
    * @param c2
    *            cell
    *
    * @param c3
    *            cell
    *
    * @param value
    *            checked value
    *
    */
	private boolean checkTriple(int c1, int c2, int c3, Cell value) {
		if((cells[c1] == cells[c2]) && (cells[c2] == cells[c3]) && (cells[c3] == value))
			return true;
		else
			return false;
	}
	
	/**
    * Checks game for possible three same x or o's in a row
    *
    */
	private Outcome checkGame( ) {

		if(checkTriple(0, 1, 2, Cell.X)) return Outcome.P1_WON;
		else if(checkTriple(0, 1, 2, Cell.O)) {
			return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
		}

		if(checkTriple(3, 4, 5, Cell.X)) return Outcome.P1_WON;
		else if(checkTriple(3, 4, 5, Cell.O)) {
			return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
		}

		if(checkTriple(6, 7, 8, Cell.X)) return Outcome.P1_WON;
		else if(checkTriple(6, 7, 8, Cell.O)) {
			return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
		}

		if(checkTriple(0, 3, 6, Cell.X)) return Outcome.P1_WON;
		else if(checkTriple(0, 3, 6, Cell.O)) {
			return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
		}

		if(checkTriple(1, 4, 7, Cell.X)) return Outcome.P1_WON;
		else if(checkTriple(1, 4, 7, Cell.O)) {
			return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
		}

		if(checkTriple(2, 5, 8, Cell.X)) return Outcome.P1_WON;
		else if(checkTriple(2, 5, 8, Cell.O)) {
			return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
		}

		if(checkTriple(2, 4, 6, Cell.X)) return Outcome.P1_WON;
		else if(checkTriple(2, 4, 6, Cell.O)) {
			return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
		}
		
		if(checkTriple(0, 4, 8, Cell.X)) return Outcome.P1_WON;
		else if(checkTriple(0, 4, 8, Cell.O)) {
			return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
		}

		boolean cats = true;
		for(int i = 0; i < 9; i++) {
			if(cells[i] == Cell.OPEN)
				cats = false;
		}
		
		if(cats)
			return Outcome.CAT;
		else
			return Outcome.NONE;
	}
	
    /**
    * Checks whether can win
    *
    * @param index
    *            index
    *
    * @param player
    *            player
    *
    * @return can
    		return whether can win(true or false)
    *
    */
	private boolean canWin(int index, Cell player) {
		
		Cell old = cells[index];

		cells[index] = player;

		boolean can;
		Outcome out = checkGame( );
		if((out == Outcome.NONE) || (out == Outcome.CAT))
			can = false;
		else
			can = true;

		cells[index] = old;
		
		return can;
	}
	
	/**
    * Ranks move for AI
    *
    * @param index
    *            the rank number 
    *
    * @return 5
    		returns the rank 5
    *
    */
	private int rankMove(int index) {

		if(cells[index] != Cell.OPEN)
			return 0;

		if(canWin(index, Cell.O))
			return 100;

		if(canWin(index, Cell.X))
			return 50;

		if(index == 4)
			return 25;

		if((index == 0) || (index == 2) || (index == 6) || (index == 8))
			return 10;

		return 5;
		
	}
	
	/**
    * Ai implementation
    *
    */
	private void doAi( ) {
		int rankings [] = new int[9];

		for(int i = 0; i < 9; i++) {
			if(cells[i] == Cell.OPEN) {
				rankings[i] = rankMove(i);
			}
		}

		int best_ranking = 0;
		for(int i = 0; i < 9; i++) {
			if(rankings[i] > rankings[best_ranking])
				best_ranking = i;
		}

		buttons[best_ranking].getImageButton().performClick( );
	}

    /**
    * Updates the game and handles end-game possibilities
    *
    * @param cell
    *            cell 
    *
    * @param turn
    *            turn
    */
	public void update(int cell, int turn) {

		cells[cell] = ((turn % 2) == 0) ? Cell.X : Cell.O;

		Outcome o;
		switch(o = checkGame( )) {
			case P1_WON:
				status.setText("Player 1 Won!!");
				this.setResult(RESULT_OK);
				this.finish();
			case P2_WON:
				status.setText("You Have Lost!!");
				this.setResult(RESULT_CANCELED);
				this.finish();

			case COMPUTER_WON:
				status.setText("You Have Lost!!");
				this.setResult(RESULT_CANCELED);
				this.finish();
			case CAT:
				status.setText("It's a Tie!");
				this.setResult(RESULT_CANCELED);
				this.finish();
			case NONE:
				status.setText(((turn % 2) == 0) ? "O's Turn" : "X's Turn"); 
				break;
		}

		if((num_players == 1) && ((turn % 2) == 0) && (o == Outcome.NONE))
			doAi( );
	}
	
	/**
    * Gets status
    *
    * @return this.status
    *      returns the current status
    *
    */
	public TextView getStatus() {
		return this.status;
	}
    

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        
        setupGame( );
        
        status = (TextView) findViewById(R.id.TextView01);

        num_players = 1;
      
        status.setText("X's Turn");

        int button_ids [] = {R.id.ImageButton01, R.id.ImageButton02, R.id.ImageButton03,
        					 R.id.ImageButton04, R.id.ImageButton05, R.id.ImageButton06,
        					 R.id.ImageButton07, R.id.ImageButton08, R.id.ImageButton09}; 

        for(int i=0; i<9; i++) {
        	buttons[i] = new AnimatedButton((ImageButton) findViewById(button_ids[i]), this, i);
        }   
	}
}