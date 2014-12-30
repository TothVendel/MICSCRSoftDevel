package activity;

import com.example.yrdyt.R;
import communication.SQLiteNumberPointsDataSource;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity {
	private Button buttonPuzzle1; 
	private Button buttonShowHints; 
	private Button buttonShowMap; 
	private Button buttonMap;
	private Button buttonPuzzle2; 
	private Button buttonPuzzle3; 
	
	
	
	private TextView hint1;
	private SQLiteNumberPointsDataSource dataSource;

    /**
    * Initializes UI Fields
    *
    */
	private void initUIFields() {
		this.buttonPuzzle1 = (Button) this.findViewById(R.id.button1);
		this.buttonPuzzle2 = (Button) this.findViewById(R.id.button5);
		this.buttonPuzzle3 = (Button) this.findViewById(R.id.button6);
		this.buttonShowHints = (Button) this.findViewById(R.id.button2);
		this.buttonShowMap = (Button) this.findViewById(R.id.button3);
		this.buttonMap = (Button) this.findViewById(R.id.button4);
		this.hint1 = (TextView) this.findViewById(R.id.textView2);
	}
	
	/**
    * Links navigation buttons
    *
    */
	private void linkNavigationButtons() {
		this.linkPuzzleButton();
		this.linkPuzzle2Button();
		this.linkPuzzle3Button();
		this.linkShowHintsButton();
		this.linkShowMapButton();
		this.linkMapButton();
	}
	
	/**
    * Links puzzle button
    *
    */
	private void linkPuzzleButton() {
		this.buttonPuzzle1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				final Intent intent = new Intent(TestActivity.this, PuzzleMillionaireActivity.class);
				TestActivity.this.startActivityForResult(intent, PuzzleMillionaireActivity.KEY_PUZZLE0_INT);
			}
		});
	}
    
    /**
    * Links puzzle2 button
    *
    */
	private void linkPuzzle2Button() {
		this.buttonPuzzle2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				final Intent intent = new Intent(TestActivity.this, PuzzleMemoryActivity.class);
				TestActivity.this.startActivityForResult(intent, PuzzleMillionaireActivity.KEY_PUZZLE0_INT);
			}
		});
	}
    
    /**
    * Links puzzle3 button
    *
    */
	private void linkPuzzle3Button() {
		this.buttonPuzzle3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				final Intent intent = new Intent(TestActivity.this, PuzzleTicTacToeActivity.class);
				TestActivity.this.startActivityForResult(intent, PuzzleMillionaireActivity.KEY_PUZZLE3_INT);
			}
		});
	}

	/**
    * Saves to database
    *
    * @param text
    *      saves text
    *
    * @param position
    *      saves score
    *
    */
	private void saveToDatabase(String text, int position) {
		dataSource.open();
		dataSource.createNumberPoints(text, position);
		TestActivity.this.makeText("Hint saved in database");
		dataSource.close();

	}
	 
	 /**
     * On activity result
     *
     * @param requestCode
     *      
     *
     * @param resultCode
     *      
     *
     * @param data
     *      
     */
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (requestCode == PuzzleMillionaireActivity.KEY_PUZZLE0_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE1_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE2_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE3_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE4_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE5_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE6_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE7_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE8_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE9_INT) {
             if (resultCode == RESULT_OK) {
         		this.makeText("Well done, you have discovered a new hint! Congratulations!");
         		this.saveToDatabase(HintsActivity.questionwords[(requestCode-100)], (requestCode-100));
         		//erase marker from map for the puzzle solved
             }
             else if (resultCode == RESULT_CANCELED) {
          		this.makeText("TRY AGAIN");            	 
             }
             
         }

         if (requestCode == HintsActivity.KEY_HINTS_INT) {
             if (resultCode == RESULT_OK) {
         		this.makeText("Congratulations, you won the game");
         		this.finish();
             }
             else if (resultCode == RESULT_CANCELED) {
          		//this.hint1.setText("You didn't solve the puzzle");
          		this.makeText("You didn't solve the puzzle");
             }
             
         }
         
         
	 }
	
	/**
    * Makes toaster text
    *
    * @param
    *     text to be made
    *
    */
	private void makeText(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
	}

	/**
    * Links show hints button
    *
    */
	private void linkShowHintsButton() {
		this.buttonShowHints.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				final Intent intent = new Intent(TestActivity.this, HintsActivity.class);
				TestActivity.this.startActivityForResult(intent, HintsActivity.KEY_HINTS_INT);
			}
		});
	}
    
    /**
    * Links map button
    *
    */
	private void linkMapButton() {
		this.buttonMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				final Intent intent = new Intent(TestActivity.this, DatabaseTestActivity.class);
				TestActivity.this.startActivity(intent);
			}
		});
	}

	/**
    * Links Show map button
    *
    */
	private void linkShowMapButton() {
		this.buttonShowMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				TestActivity.this.makeText("Opening map");
				final Intent intent = new Intent(TestActivity.this, GoogleMapAppActivity.class);
				TestActivity.this.startActivity(intent);
			}
		});
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
        this.setTitle("Testing components");
		this.initUIFields();
		this.linkNavigationButtons();	
		this.initDatabase();
	}
    
    /**
    * Initializes the database
    *
    */
	private void initDatabase () {
		dataSource = new SQLiteNumberPointsDataSource(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
