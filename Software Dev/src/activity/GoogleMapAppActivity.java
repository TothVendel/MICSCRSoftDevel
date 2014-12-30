package activity;

import item.NumberPoints;

import java.util.List;

import com.example.yrdyt.R;
import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import communication.SQLiteNumberPointsDataSource;


public class GoogleMapAppActivity extends Activity {
    private GoogleMap map;
	private TextView hint1;
	private TextView hint3;
	private Button buttonShowHints; 
	private Button buttonPuzzle1; 
	private Button buttonInfo; 
	private SQLiteNumberPointsDataSource dataSource;
	private Integer numberOfHints;
	private Marker myLocation;
	private Marker currentMarker;
	private final String [] positionsName = {"Campus Kirchberg","Utopolis Kirchberg","Hamilius", "Parlement Europeen", "Philharmonie", "Grand Theatre", "Parc ED J Klein"};
	private final LatLng [] positions = {new LatLng(49.626183, 6.158648), new LatLng(49.633579, 6.173556), new LatLng(49.611307, 6.126848), new LatLng(49.627371, 6.145309), new LatLng(49.618798, 6.142430), new LatLng(49.617422, 6.127421), new LatLng(49.611512, 6.122113)};
	private int positionCounter = 0;
	private Polyline line;
	
	/**
    * Initializes UI Fields
    * 
    */
	private void initUIFields() {
		this.buttonPuzzle1 = (Button) this.findViewById(R.id.button2);
		this.buttonShowHints = (Button) this.findViewById(R.id.button1);
		this.buttonInfo = (Button) this.findViewById(R.id.button3);
		this.hint1 = (TextView) this.findViewById(R.id.textView2);
		this.hint3 = (TextView) this.findViewById(R.id.textView3);
	}
    
    /**
    * Initializes first marker location and the textviews' colors
    * 
    */
	private void initFirstMarkerLocation() {
		this.hint1.setTextColor(Color.RED);
		this.hint3.setTextColor(Color.BLUE);
        this.hint3.setText("Location : "+ this.positionsName[this.positionCounter]);
	}
	
	/**
    * Links puzzle button; dependant on how many hints you discovered calls the appropriate game to be played
    * 
    */
	private void linkPuzzleButton() {
		this.buttonPuzzle1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				if (numberOfHints==0) {
					final Intent intent = new Intent(GoogleMapAppActivity.this, PuzzleMillionaireActivity.class);
					GoogleMapAppActivity.this.startActivityForResult(intent, PuzzleMillionaireActivity.KEY_PUZZLE0_INT);
				}
				else if (numberOfHints==1) {
					final Intent intent = new Intent(GoogleMapAppActivity.this, PuzzleMemoryActivity.class);
					GoogleMapAppActivity.this.startActivityForResult(intent, PuzzleMillionaireActivity.KEY_PUZZLE1_INT);					
				}

				else if (numberOfHints==2) {
					final Intent intent = new Intent(GoogleMapAppActivity.this, PuzzleTicTacToeActivity.class);
					GoogleMapAppActivity.this.startActivityForResult(intent, PuzzleMillionaireActivity.KEY_PUZZLE2_INT);					
				}
				
				else if (numberOfHints==3) {
					final Intent intent = new Intent(GoogleMapAppActivity.this, PuzzleMillionaireActivity.class);
					GoogleMapAppActivity.this.startActivityForResult(intent, PuzzleMillionaireActivity.KEY_PUZZLE3_INT);					
				}

				else if (numberOfHints==4) {
					final Intent intent = new Intent(GoogleMapAppActivity.this, PuzzleMemoryActivity.class);
					GoogleMapAppActivity.this.startActivityForResult(intent, PuzzleMillionaireActivity.KEY_PUZZLE4_INT);					
				}

				else if (numberOfHints==5) {
					final Intent intent = new Intent(GoogleMapAppActivity.this, PuzzleTicTacToeActivity.class);
					GoogleMapAppActivity.this.startActivityForResult(intent, PuzzleMillionaireActivity.KEY_PUZZLE5_INT);					
				}

				else if (numberOfHints==6) {
					makeText("You have discovered all the hints! Solve the final puzzle in 'Show Hints'!");
					disablePuzzleButton();
				}

			}
		});
	}
	
	/**
    * Links info button about the place
    * 
    */
	private void linkInfoButton() {
		this.buttonInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				final Bundle bundle = new Bundle();
				bundle.putInt("hints", numberOfHints);
				final Intent intent = new Intent(GoogleMapAppActivity.this, InfoActivity.class);
				intent.putExtras(bundle);
				GoogleMapAppActivity.this.startActivity(intent);
			}
			});
		}

	/**
    * Links navigation buttons
    * 
    */
	private void linkNavigationButtons() {
		this.linkPuzzleButton();
		this.linkShowHintsButton();
		this.linkInfoButton();
	}

    /**
    * Links show hints button
    * 
    */
	private void linkShowHintsButton() {
		this.buttonShowHints.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				final Intent intent = new Intent(GoogleMapAppActivity.this, HintsActivity.class);
				GoogleMapAppActivity.this.startActivityForResult(intent, HintsActivity.KEY_HINTS_INT);
			}
		});
	}

    /**
    * Saves to database
    * 
    * @param text
    * 	the text to be saved 
    *
    * @param position
    * 	the score to be saved
    */
	private void saveToDatabase(String text, int position) {
		dataSource.open();
		dataSource.createNumberPoints(text, position);
		GoogleMapAppActivity.this.makeText("Hint saved in database");
		dataSource.close();

	}
    
    /**
    * Retrieves the number of hints from the database
    * 
    */
	private void calculateNumberOfHints() {
		final AsyncTask<Void, Void, Boolean> worker = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(final Void... arg0) {
				dataSource.open();
				List<NumberPoints> values = dataSource.getAllNumberPoints();
				GoogleMapAppActivity.this.numberOfHints=values.size();				
				return true;
			}
			
			@Override
			protected void onPostExecute(final Boolean result) {
				GoogleMapAppActivity.this.makeText("Database loaded");
				dataSource.close();
				GoogleMapAppActivity.this.hint1.setText(GoogleMapAppActivity.this.numberOfHints.toString());	
			}
			
			@Override
			protected void onPreExecute() {
				GoogleMapAppActivity.this.makeText("Loading database");
			}

		};
		
		worker.execute();

	}
	
	/**
    * Increments number of hints
    * 
    */
	private void numberOfHintsIncrement() {
		this.numberOfHints++;
		this.hint1.setText(this.numberOfHints.toString());
	}
	
	 /**
     * On Activity result (from a puzzle or from the answer to the final question)
     *
     * @param requestCode
     * 	  the requested code 
     *
     * @param resultCode
     * 	  the result code 
     * 
     * @param data
     *
     * 
     */
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (requestCode == PuzzleMillionaireActivity.KEY_PUZZLE0_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE1_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE2_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE3_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE4_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE5_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE6_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE7_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE8_INT || requestCode == PuzzleMillionaireActivity.KEY_PUZZLE9_INT) {
             if (resultCode == RESULT_OK) {
         		this.makeText("Well done, you have discovered a new hint!");
         		this.numberOfHintsIncrement();
         		this.saveToDatabase(HintsActivity.questionwords[(requestCode-100)], (requestCode-100));

         		//change marker
         		this.positionCounter++;
         		if (positionCounter<this.positions.length) {
         			this.currentMarker.setPosition(this.positions[this.positionCounter]);
         			this.moveCameraToMarker(this.positions[this.positionCounter].latitude, this.positions[this.positionCounter].longitude);        
         			this.hint3.setText("Location : " + this.positionsName[this.positionCounter]);
         		}
         		else {
					makeText("You have discovered all the hints! Solve the final puzzle in 'Show Hints'!");
					this.currentMarker.remove();
         		}
				disablePuzzleButton();
             }
             else if (resultCode == RESULT_CANCELED) {
          		this.makeText("You didn't solve the puzzle, try again!");            	 
             }
             
         }

         if (requestCode == HintsActivity.KEY_HINTS_INT) {
             if (resultCode == RESULT_OK) {
         		this.makeText("Congratulations, you won the game");
         		this.finish();
             }
             else if (resultCode == RESULT_CANCELED) {
          		this.makeText("You didn't solve the final puzzle.");
             }
             
         }
         
         
	 }
        /**
        * Makes text
        *
        * @param text
        * 	  the text to be made
        * 
        */
		private void makeText(String text) {
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
		}
	
	/**
    * Initializes Google Maps and your location, sets the markers, calls myLocationChangeListener
    *
    */
    private void initGoogleMap() {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();    	
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.currentMarker= this.addNewMarker(this.positions[this.positionCounter].latitude, this.positions[this.positionCounter].longitude, "Come here");
        this.currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.puzzle));

        this.moveCameraToMarker(this.positions[this.positionCounter].latitude, this.positions[this.positionCounter].longitude);        

        this.enableMyLocation();
        this.myLocation= map.addMarker(new MarkerOptions().position(this.getMyLocation()).title("You are here"));
        this.myLocation.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.iamhere));

        this.map.setOnMyLocationChangeListener(this.myLocationChangeListener);
        }
    
    /**
    * Enables my location
    *
    */
    private void enableMyLocation() {
    	this.map.setMyLocationEnabled(true);
    }
    
    /**
    * Checks if two points (your location and the marker you have to go to) are close enough for you to have arrived 
    *
    * @param a
    * 	  LatLng point a
    *
    * @param b
    * 	  LatLng point b 
    * 
    * @return false
    *
    */
    private Boolean arrivedTwoPoints(LatLng a, LatLng b) {
    	double lat = Math.abs(a.latitude-b.latitude);
    	double longi = Math.abs(a.longitude-b.longitude);

    	//For debugging purposes
	    	//makeText(String.valueOf(lat));
	        //makeText(String.valueOf(longi));

    	
    	if (lat<0.005 && longi<0.005)
    		return true;
    	return false;
    }
    
    /**
    * Gets my location
    *
    * @return new LatLng(me.getLatitude(), me.getLongitude())
    * 		the position you are at, or (0,0) if it failed to get it
    */
    private LatLng getMyLocation() {
    	Location me = this.map.getMyLocation();
    	if (me == null) {
    		Log.e("Mylocation", "null");
    		return new LatLng(0, 0);
    	}
    	//For debugging purposes
	    	//Log.e("my location lat", String.valueOf(me.getLatitude()));
	    	//Log.e("my location long", String.valueOf(me.getLongitude()));

    	return new LatLng(me.getLatitude(), me.getLongitude());
    }
    
    /**
    *	Updates your position if you have moved; and checks if you have arrived on destination 
    *
    */
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng newLoc = new LatLng(location.getLatitude(), location.getLongitude());
            myLocation.setPosition(newLoc);            
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 16.0f));

            //For debugging purposes
	            //makeText(String.valueOf(location.getLatitude()));
	            //makeText(String.valueOf(location.getLongitude()));

            verifyLocation();
            
            removeRoute();
            addRoute(currentMarker.getPosition(), myLocation.getPosition());
            
        }
    };
    
    /**
    * Verifies if you have arrived to the marker you are supposed to go
    *
    */
    private void verifyLocation() {
    	if (this.currentMarker == null) {
    		Log.e("Current marker", "null");
    		return;
    	}
    	if (this.myLocation == null) {
    		Log.e("My location marker", "null");
    		return;
    	}

    	//For debugging purposes
	    	//Log.e("currentMarker", String.valueOf(currentMarker.getPosition().latitude));
	    	//Log.e("my location long", String.valueOf(myLocation.getPosition().longitude));

    	
    	if (this.arrivedTwoPoints(this.currentMarker.getPosition(), this.myLocation.getPosition())) {
    		if (!this.buttonPuzzle1.isEnabled()) {
	    		this.enablePuzzleButton();
	    		this.makeText("You have arrived, the puzzle has been enabled!");
    		}
    	}
    	
    	else if (this.buttonPuzzle1.isEnabled()) {
    		this.disablePuzzleButton();
    		this.makeText("Puzzle is disabled");
    	} 	
    }
    
    /**
    * Removes the line (route) from your location to the marker you have to go
    *
    */
    private void removeRoute() {
    	if (line!=null)
    		line.remove();
    }
    
    /**
    * Adds line (route) from your location to the marker you have to go
    *
    * @param a
    * 	  LatLng point a
    *
    * @param b
    * 	  LatLng point b 
    * 
    * @return line
    *
    */
    private Polyline addRoute(LatLng a, LatLng b) {
    line = map.addPolyline(new PolylineOptions().add(a, b).geodesic(true));
    line.setColor(Color.GRAY);
    line.setWidth(4);
    return line;
    }
    
    /**
    * Adds new marker
    *
    * @param Latitude
    * 	  the new latitude coordinate
    *
    * @param Longitude
    * 	  the new longitude coordinate
    * 
    * @param title
    * 	  the title of the marker
    * 
    * @return map.addMarker(new MarkerOptions().position(latLng).title(title))
    *
    */
    private Marker addNewMarker(double Latitude, double Longitude, String title) {
        LatLng latLng = new LatLng(Latitude, Longitude);
        return map.addMarker(new MarkerOptions().position(latLng).title(title));
    }
    
    /**
    * Moves the camera to the position specified by parameters
    *
    * @param Latitude
    * 	  latitude coordinate
    *
    * @param Longitude
    * 	  Longitude coordinate
    *
    */
    private void moveCameraToMarker(double Latitude, double Longitude)
    {
        LatLng latLng = new LatLng(Latitude, Longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));    	
    }
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_app);
        this.setTitle("City Rally : Luxembourg, Main game");
		this.initDatabase();
        this.initGoogleMap();
		this.initUIFields();
		this.initFirstMarkerLocation();
		this.linkNavigationButtons();	
		this.calculateNumberOfHints();
		this.disablePuzzleButton();
        
		//this.verifyLocation();
		
    }
    
    /**
    * Initializes database
    *
    */
	private void initDatabase () {
		dataSource = new SQLiteNumberPointsDataSource(this);
		this.initPositionCounter();
	}
	
    /**
    * Initializes how many hints you have discovered from the database and saves it to positionCounter
    *
    */
	private void initPositionCounter() {
		dataSource.open();
		List<NumberPoints> elements = dataSource.getAllNumberPoints();
		dataSource.close();
		this.positionCounter = elements.size();
	}
	
	/**
    * Disables puzzle button
    *
    */
	private void disablePuzzleButton() {
		this.buttonPuzzle1.setEnabled(false);
	}
	
	/**
    * Enables puzzle button
    *
    */
	private void enablePuzzleButton() {
		this.buttonPuzzle1.setEnabled(true);
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