package activity;

import java.util.Random;
import com.example.yrdyt.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PuzzleMillionaireActivity extends Activity {
	//Keys for the markers
	public static final int KEY_PUZZLE0_INT = 100;
	public static final int KEY_PUZZLE1_INT = 101;
	public static final int KEY_PUZZLE2_INT = 102;
	public static final int KEY_PUZZLE3_INT = 103;
	public static final int KEY_PUZZLE4_INT = 104;
	public static final int KEY_PUZZLE5_INT = 105;
	public static final int KEY_PUZZLE6_INT = 106;
	public static final int KEY_PUZZLE7_INT = 107;
	public static final int KEY_PUZZLE8_INT = 108;
	public static final int KEY_PUZZLE9_INT = 109;

	public static final String KEY_PUZZLE1 = "PUZZLE1";

	
	private Button buttonA; 
	private Button buttonB; 
	private Button buttonC; 
	private Button buttonD; 

	private Button buttonHelpPC; 
	private Button buttonHelpSkip; 
	private Button buttonHelpPublic; 
	private TextView score;
	private Integer scoreCounter;

	private TextView question;
	private Integer questionCounter;
	private Integer correctAnswer;

	private TextView askPublic;

	
	private String[] questions;
	private String[][] answers;
	private Integer[] correctAnswers;
	
	/**
    * Initializes UI Fields 
    * 
    */
	private void initUIFields() {
		this.buttonA = (Button) this.findViewById(R.id.button1);
		this.buttonB = (Button) this.findViewById(R.id.button3);
		this.buttonC = (Button) this.findViewById(R.id.button2);
		this.buttonD = (Button) this.findViewById(R.id.button4);

		this.buttonHelpPC = (Button) this.findViewById(R.id.button5);
		this.buttonHelpSkip = (Button) this.findViewById(R.id.button6);
		this.buttonHelpPublic = (Button) this.findViewById(R.id.button7);
		this.score = (TextView) this.findViewById(R.id.textView8);

		this.question = (TextView) this.findViewById(R.id.textView2);
		this.askPublic = (TextView) this.findViewById(R.id.textView9);

	}
	
	/**
    * Links navigation buttons 
    * 
    */
	private void linkNavigationButtons() {
		this.linkButtonA();
		this.linkButtonB();
		this.linkButtonC();
		this.linkButtonD();
		this.linkButtonHelpPC();
		this.linkButtonHelpSkip();
		this.linkButtonHelpPublic();

		
	}
	
    /**
    * Links button A
    * 
    */
	private void linkButtonA() {
		this.buttonA.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				PuzzleMillionaireActivity.this.isCorrectAnswer(1);
			}
		});
	}
    
    /**
    * Links button B 
    * 
    */
	private void linkButtonB() {
		this.buttonB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				PuzzleMillionaireActivity.this.isCorrectAnswer(2);

			}
		});
	}

    /**
    * Links button C 
    * 
    */
	private void linkButtonC() {
		this.buttonC.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				PuzzleMillionaireActivity.this.isCorrectAnswer(3);
			}
		});
	}
    
    /**
    * Links button D 
    * 
    */
	private void linkButtonD() {
		this.buttonD.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				PuzzleMillionaireActivity.this.isCorrectAnswer(4);
			}
		});
	}

    /**
    * Links button Help PC 
    * 
    */
	private void linkButtonHelpPC() {
		this.buttonHelpPC.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				
					if (PuzzleMillionaireActivity.this.getCorrectAnswer()==1 || PuzzleMillionaireActivity.this.getCorrectAnswer()==3) {
						PuzzleMillionaireActivity.this.helpPC(2);
						PuzzleMillionaireActivity.this.helpPC(4);						
					}
					else {
						PuzzleMillionaireActivity.this.helpPC(1);
						PuzzleMillionaireActivity.this.helpPC(3);												
					}
					
					PuzzleMillionaireActivity.this.buttonHelpPC.setEnabled(false);

			}
		});
	}
    
    /**
    * Links button Help skip
    * 
    */
	private void linkButtonHelpSkip() {
		this.buttonHelpSkip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
					scoreCounter+=1;
					score.setText(scoreCounter.toString());

					if (questionCounter==5) {
						endGame();
					}
					else {
						setupNewQuestion();
					}
					PuzzleMillionaireActivity.this.buttonHelpSkip.setEnabled(false);				
			}
		});
	}
    
    /**
    * Links button help public 
    * 
    */
	private void linkButtonHelpPublic() {
		this.buttonHelpPublic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
					if (PuzzleMillionaireActivity.this.getRandomNumber(10)>7) {
						//Lie about the answer
						PuzzleMillionaireActivity.this.askPublic.setText("The public says the correct answer is :"+(Integer) (PuzzleMillionaireActivity.this.getRandomNumber(4)+1));
					}
					else {
						PuzzleMillionaireActivity.this.askPublic.setText("The public says the correct answer is :"+ PuzzleMillionaireActivity.this.getCorrectAnswer().toString());
					}					
				PuzzleMillionaireActivity.this.buttonHelpPublic.setEnabled(false);

			}
		});
	}
  
    /**
    * Checks if answer is correct 
    *
    * @param choice
    *        the answer to be checked
    *
    */
	private void isCorrectAnswer(int choice) {
		if (choice==getCorrectAnswer()) {
			this.makeText("Correct answer");
			scoreCounter+=1;
			this.score.setText(scoreCounter.toString());
		}
		else if (incorrectAnswer(choice)) {
			this.makeText("Incorrect answer");		
		}
		else
			this.makeText("False answer");
		
		if (questionCounter==5) {
			this.endGame();
		}
		else {
			this.setupNewQuestion();
		}
	}
	
	/**
    * Sets correct answer 
    *
    * @param choice
    *        set to the correct answer
    *
    */
	private void setCorrectAnswer(Integer choice) {
		correctAnswer=choice;
	}
    
    /**
    * Gets correct answer
    *
    * @return correctAnswer
    *     the returned correct answer 
    *
    */
	private Integer getCorrectAnswer() {
		return correctAnswer;
	}

	/**
    * Checks for incorrect answer if you used the 50/50 helpline
    *
    * @param choice
    *        the answer to be checked
    *
    * @return false
    *      
    *
    */
	private Boolean incorrectAnswer(int choice) {
		if (choice==1) 
			return checkStringEmpty(this.buttonA.getText().toString());
		else if (choice==2)
			return checkStringEmpty(this.buttonB.getText().toString());
		else if (choice==3)
			return checkStringEmpty(this.buttonC.getText().toString());
		else if (choice==4)
			return checkStringEmpty(this.buttonD.getText().toString());
		return false;
	}
	
	/**
    * Checks if string is empty 
    *
    * @param string
    *     the checked string parameter
    *
    * @return false
    *
    */
	private Boolean checkStringEmpty(String string) {
		if (string=="")
			return true;
		return false;
	}
	
	/**
    * Makes toaster text 
    *
    * @param text
    *     the text to be checked
    *
    */
	private void makeText(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
	}
	
    /**
    * Used by the 50/50 helpline when he disables 2 buttons
    *
    * @param choice
    *     the answer to be disabled
    *
    */
	private void helpPC(int choice) {
			if (choice==1)
				this.buttonA.setText("");
			else if (choice==2)
				this.buttonB.setText("");			
			else if (choice==3)
				this.buttonC.setText("");		
			else if (choice==4)
				this.buttonD.setText("");						
		}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puzzle1);
        this.setTitle("Solve this puzzle");
		this.initUIFields();
		this.linkNavigationButtons();
		this.initCounters();	
		this.initQuestions();
		this.setupNewQuestion();
	}

	/**
    * Initializes the questions for the game and the answers, as well as the correct answers 
    *
    */
	private void initQuestions() {
		String[] temp=	{"Who was the first president in the USA?", "What are Belgians good at?", "What country excels at making French Fries?", "Who was the world cup winner in 2014 in Soccer?", "Who did Mario rescue from the castle in all of his games?"};
		this.questions=temp;
		String[][] temp2 = {{"Washington", "Ribery", "Lincoln", "Holland"}, {"Nothing", "Doing bunjee-jumps", "Sprinting fast", "Making beer"}, {"France", "Belgium", "Luxembourg", "Australia"}, {"Germany", "France", "Argentina", "Brazil"}, {"Luigi", "Bowser", "Peach", "Daisy"}};
		this.answers=temp2;
		Integer[] temp3 = {1, 4, 2, 1, 3};
		this.correctAnswers= temp3;
	}
	
	/**
    * Initializes the question counter and your current score counter 
    *
    */
	private void initCounters() {
		this.scoreCounter=0;
		this.questionCounter=0;		
	}
	
	/**
    * Sets up new question
    *
    */
	private void setupNewQuestion() {
		this.score.setText(scoreCounter.toString()+" / "+ questionCounter.toString());
		Integer choice = this.questionCounter;

		questionCounter+=1;
	
		this.askPublic.setText("");	
		this.setCorrectAnswer(this.correctAnswers[choice]);		

		
		this.question.setText("Question " + questionCounter.toString()+" : "+ this.questions[choice]);

		this.buttonA.setText(this.answers[choice][0]);
		this.buttonB.setText(this.answers[choice][1]);
		this.buttonC.setText(this.answers[choice][2]);
		this.buttonD.setText(this.answers[choice][3]);	
	}
	
	/**
    * Ends game after 5 questions
    *
    */
	private void endGame(){
		if (this.scoreCounter>3) {		
			this.setResult(RESULT_OK);
		}
		else {
			this.setResult(RESULT_CANCELED);		
		}
		this.finish();
	}
	
	/**
    * Gets random number  
    *
    * @param range
    *     from 0 to range the number to be get
    *
    * @return rand.nextInt(range);
    *     returned random number
    *
    */
	private Integer getRandomNumber(int range)
	{
		Random rand = new Random();
		return rand.nextInt(range);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.puzzle1, menu);
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
