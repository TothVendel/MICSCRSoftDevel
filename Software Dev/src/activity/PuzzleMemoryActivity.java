package activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import com.example.yrdyt.R;
import item.Card;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PuzzleMemoryActivity extends Activity {
    private static int row = -1;
	private static int col = -1;
	private Context context;
	private Drawable backImage;
	private int [] [] cards;
	private int cardsCounter;
	private int currentCardsCounter;
	private List<Drawable> images;
	private Card first;
	private Card second;
	private ButtonListener buttonListener;
	private TextView question;

	
	private static Object lock = new Object();
	
	private int turns;
	private TableLayout mtable;
	private UpdateCardsHandler handler;
    
   	/**
    * Loads images
    * 
    */
	private void loadImages() {
    	images = new ArrayList<Drawable>();
    	
    	int [] cardsUI = {R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6, R.drawable.card7, R.drawable.card8, R.drawable.card9, R.drawable.card10, R.drawable.card11, R.drawable.card12, R.drawable.card13, R.drawable.card14, R.drawable.card15, R.drawable.card16, R.drawable.card17, R.drawable.card18, R.drawable.card19, R.drawable.card20, R.drawable.card21}; 
    	
    	for (int i=0; i<21; i++) {
    		images.add(getResources().getDrawable(cardsUI[i]));
    	}	
	}
	
	/**
    * Initializes UI Fields 
    * 
    */
	private void initUIFields() {
	       backImage =  getResources().getDrawable(R.drawable.icon);	       
	       buttonListener = new ButtonListener();
	       mtable = (TableLayout)findViewById(R.id.TableLayout03);
	       this.question = (TextView) this.findViewById(R.id.textView1);
	       context  = mtable.getContext();
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new UpdateCardsHandler();
        setContentView(R.layout.activity_puzzle_memory);
        this.setTitle("Memory Puzzle");
        loadImages();
        initUIFields();
        setupNewGame();
    }
    
    /**
    * Sets up new game
    * 
    */
    private void setupNewGame() {
			int x,y;
			Random r = new Random();
			x= r.nextInt(3) + 4;
			if (x==6 || x==5)
				y=6;
			else
				y=r.nextInt(3) + 4;			
			newGame(x,y);    	
			
    }
    
    /**
    * Creates new game
    *
    * @param c
    * 	initializes column parameter
    *
    * @param r
    * 	initializes parameter
    *
    */
    private void newGame(int c, int r) {
    	row = r;
    	col = c;
    	
    	cards = new int [col] [row];
    	
    	mtable.removeView(findViewById(R.id.TableRow01));
    	mtable.removeView(findViewById(R.id.TableRow02));
    	
    	TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
    	tr.removeAllViews();
    	
    	mtable = new TableLayout(context);
    	tr.addView(mtable);
    	
    	 for (int y = 0; y < row; y++) {
    		 mtable.addView(createRow(y));
          }
    	 
    	 first=null;
    	 loadCards();
    	 
    	 turns=0;
    	 question.setText("Try to solve this memory game in less than "+ (row*col*2) + " moves!");
    	 ((TextView)findViewById(R.id.tv1)).setText("Tries: "+turns);
	}
    
    /**
    * Loads cards 
    * 
    */
	private void loadCards(){
		try{
	    	int size = row*col;
	    	cardsCounter=size;
	    	currentCardsCounter=0;
	    	
	    	Log.i("loadCards()","size=" + size);
	    	
	    	ArrayList<Integer> list = new ArrayList<Integer>();
	    	
	    	for(int i=0;i<size;i++){
	    		list.add(new Integer(i));
	    	}
   	
	    	Random r = new Random();
	    
	    	for(int i=size-1;i>=0;i--){
	    		int t=0;
	    		
	    		if(i>0){
	    			t = r.nextInt(i);
	    		}
	    		
	    		t=list.remove(t).intValue();
	    		cards[i%col][i/col]=t%(size/2);
	    		
	    		Log.i("loadCards()", "card["+(i%col)+
	    				"]["+(i/col)+"]=" + cards[i%col][i/col]);
	    	}
	    }
		catch (Exception e) {
			Log.e("loadCards()", e+"");
		}
		
    }
    
    /**
    * Creates row
    *
    * @param y
    * 	 the y position
    * @return row
    *     the row to be returned
    *
    */
    private TableRow createRow(int y){
    	 TableRow row = new TableRow(context);
    	 row.setHorizontalGravity(Gravity.CENTER);
         
         for (int x = 0; x < col; x++) {
		         row.addView(createImageButton(x,y));
         }
         return row;
    }
    
    /**
    * Creates image button
    *
    * @param x
    *	 the x position
    *
    * @param y
    * 	 the y position
    * 	 
    * @return button
    *     the button to be returned
    *
    */
    private View createImageButton(int x, int y){
    	Button button = new Button(context);
    	button.setBackgroundDrawable(backImage);
    	button.setId(100*x+y);
    	button.setOnClickListener(buttonListener);
    	return button;
    }
    
    class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			synchronized (lock) {
				if(first!=null && second != null){
					return;
				}
				int id = v.getId();
				int x = id/100;
				int y = id%100;
				turnCard((Button)v,x,y);
			}
			
		}
        
        /**
        * Turns card
        *
        * @param button
        *	 the button type
        *
        * @param x
        *	 the x position
        *
        * @param y
        * 	 the y position
   	    *
    	*/
		private void turnCard(Button button,int x, int y) {
			button.setBackgroundDrawable(images.get(cards[x][y]));
			
			if(first==null){
				first = new Card(button,x,y);
			}
			else{ 
				
				if(first.getX() == x && first.getY() == y){
					return; //the user pressed the same card
				}
					
				second = new Card(button,x,y);
				
				turns++;
				((TextView)findViewById(R.id.tv1)).setText("Tries: "+turns);
				
			
				TimerTask tt = new TimerTask() {
					
					@Override
					public void run() {
						try{
							synchronized (lock) {
							  handler.sendEmptyMessage(0);
							}
						}
						catch (Exception e) {
							Log.e("E1", e.getMessage());
						}
					}
				};
				
				  Timer t = new Timer(false);
			        t.schedule(tt, 1300);
			}
			
				
		   }
			
		}
    
    /**
    * Ends game 
    * 
    */
	private void endGame(){
		if (this.turns<(cardsCounter*2)) {		
			this.setResult(RESULT_OK);
		}
		else {
			this.setResult(RESULT_CANCELED);		
		}
		this.finish();
	}

    /**
    * Checks whether game is over 
    * 
    */
    private void checkEndgame() {
    	if (cardsCounter==currentCardsCounter) {
    		endGame();			
    	}
	}

	
    class UpdateCardsHandler extends Handler{
    	
    	@Override
    	public void handleMessage(Message msg) {
    		synchronized (lock) {
    			checkCards();
    		}
    	}
  	    
  	    /**
    	* Checks cards 
    	* 
    	*/
    	public void checkCards(){
    	    	if(cards[second.getX()][second.getY()] == cards[first.getX()][first.getY()]){
    				first.getButton().setVisibility(View.INVISIBLE);
    				second.getButton().setVisibility(View.INVISIBLE);
    				currentCardsCounter+=2;
    				checkEndgame();
    			}
    			else {
    				second.getButton().setBackgroundDrawable(backImage);
    				first.getButton().setBackgroundDrawable(backImage);
    			}
    	    	
    	    	first=null;
    			second=null;
    	    }
    }

}