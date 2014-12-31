package activity;

import item.NumberPoints;

import java.util.List;

import com.example.yrdyt.R;
import communication.SQLiteNumberPointsDataSource;


import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HintsActivity extends Activity {
	public static final int KEY_HINTS_INT = 200;
	
	private TextView question;
	private EditText edit_text;
	private Button buttonExit; 
	private Button buttonSubmit; 
	private String answer;
	private SQLiteNumberPointsDataSource dataSource;
	private String questionProgress;
	private int numberOfHints;
	public static final String [] questionwords = {"What kind", "of sport", "did", "Luxembourg", "win", "recently the World Cup of?", "End"};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hints);
        this.setTitle("Solve the final question");
		this.initUIFields();
		this.setAnswer("Babyfoot");
		this.loadDatabase();
		this.linkNavigationButtons();
	}
	
	/**
    * Sets answer for the question
    *
    */
	private void setAnswer (String answer) {
		this.answer=answer;
	}
    
    /**
    * Sets final question 
    *
    * @param answer
    * 	 the string question to be set
    *
    * @param hints
    * 	 number of hints
    *
    */
	private void setQuestion (String answer, int hints) {
		String questionToShow="";
		if (hints>0 && hints<7) {
				questionToShow=answer;
		}
		else if (hints==0) {
			questionToShow="'No hints discovered yet.'";
		}
		
		else if (hints==7) {
			questionToShow="What kind of sport did Luxembourg win recently the World cup of?";
		}
		
		this.question.setText(questionToShow);
		this.question.setTextColor(Color.rgb(hints*20, hints*30, hints*10));
	}
	
	/**
    * Links navigation buttons
    *
    */
	private void linkNavigationButtons() {
		this.linkButtonExit();
		this.linkButtonSubmit();
	}
	
	/**
    * Links exit button
    *
    */
	private void linkButtonExit() {
		this.buttonExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				HintsActivity.this.endGame(false);
			}
		});
	}
    
    /**
    * Links submit button
    *
    */
	private void linkButtonSubmit() {
		this.buttonSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				String answer = (String) edit_text.getText().toString();
				if (answer.equalsIgnoreCase(HintsActivity.this.answer)) {
					HintsActivity.this.endGame(true);
				}
				
				else {
					HintsActivity.this.endGame(false);					
				}
				
			}
		});
	}
    
    /**
    * Ends the activity after clicking on submit
    *
    * @param answer
    * 	 if answer is correct or not 
    *
    */
	private void endGame(Boolean answer){
		if (answer==true) {		
			this.setResult(RESULT_OK);
		}
		else {
			this.setResult(RESULT_CANCELED);		
		}
		this.finish();
	}
	
	/**
    * Initializes UI Fields
    *
    */	
	private void initUIFields() {
		this.buttonExit = (Button) this.findViewById(R.id.button2);
		this.buttonSubmit = (Button) this.findViewById(R.id.button1);
		this.question = (TextView) this.findViewById(R.id.textView1);
		this.edit_text = (EditText) this.findViewById(R.id.editText1);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hints, menu);
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
	/*
	private String getQuestionProgress() {
		return this.questionProgress;
	}
	*/
	
	/**
    * Sets the progress of a question
    *
    * @param progress
    * 	 current progress of the question
    *
    * @param numberOfHints
    * 	 current number of hints discovered
    */
	private void setQuestionProgress(String progress, int numberOfHints) {
		this.questionProgress=progress;
		this.numberOfHints = numberOfHints;
	}
	
	/**
    * Loads database
    *
    */
	private void loadDatabase() {
		dataSource = new SQLiteNumberPointsDataSource(this);
		final AsyncTask<Void, Void, Boolean> worker = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(final Void... arg0) {
				dataSource.open();
				List<NumberPoints> values = dataSource.getAllNumberPoints();

				//Copying the words (hints) from the database to a string to show it in the textview.
				//Number of hints is the number of entries in the database because each entry corresponds to a hint for a puzzle solved
				String content = "";
				for (int i = 0; i<values.size(); i++) {					
					NumberPoints temp = values.get(i);					
					content+=temp.getName();
					if(i!=(values.size()-1)) {
						content+=" ";
					}
				}
				HintsActivity.this.setQuestionProgress(content, values.size());
				
				Log.e("Content of the database :", content);
				
				return true;
			}
			
			@Override
			protected void onPostExecute(final Boolean result) {
				HintsActivity.this.makeText("Database loaded");
				dataSource.close();
				HintsActivity.this.setQuestion(HintsActivity.this.questionProgress, HintsActivity.this.numberOfHints);	

			}
			
			@Override
			protected void onPreExecute() {
				HintsActivity.this.makeText("Loading database");
			}

		};
		
		worker.execute();
	}
    
    /**
    * Makes toaster text
    *
    */
	private void makeText(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
	}

	
}
