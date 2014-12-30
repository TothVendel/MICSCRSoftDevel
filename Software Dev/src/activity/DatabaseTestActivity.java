package activity;

import item.NumberPoints;

import java.util.List;

import com.example.yrdyt.R;
import communication.SQLiteNumberPointsDataSource;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DatabaseTestActivity extends Activity {
	private SQLiteNumberPointsDataSource dataSource;
	private EditText edit_text;
	private Button button_save;
	private Button button_map;

	/**
    * Initializes UI Fields
    * 
    */
	private void initUIFields() {
		this.edit_text = (EditText) this.findViewById(R.id.editText1);
		this.button_save = (Button) this.findViewById(R.id.button1);
		this.button_map = (Button) this.findViewById(R.id.button2);

	}
	
	/**
    * Links Navigation Buttons
    * 
    */
	private void linkNavigationButtons() {
		this.linkSaveButton();
		this.linkMapButton();
	}
	
    /**
    * Links Save Button
    * 
    */
	private void linkSaveButton() {
		this.button_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				dataSource.open();
				String test = edit_text.getText().toString();
				dataSource.createNumberPoints(test, 0);
				
				DatabaseTestActivity.this.makeText("String saved in database");
				dataSource.close();
			}
		});
	}
    
    /**
    * Links Map Button
    * 
    */
	private void linkMapButton() {
		this.button_map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				DatabaseTestActivity.this.makeText("Opening map");
				final Intent intent = new Intent(DatabaseTestActivity.this, GoogleMapAppActivity.class);
				DatabaseTestActivity.this.startActivity(intent);

			}
		});
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
        this.setTitle("Testing database");
		this.initUIFields();
		this.linkNavigationButtons();
		this.loadDatabase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
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
	
    /**
    * Makes toaster text
    * 
    */
	private void makeText(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
	}
	
	/**
    * Closes database
    * 
    */
	private void closeDatabase() {
		dataSource.close();
	}
	
	/**
    * Opens and loads database
    * 
    */
	private void loadDatabase() {
		dataSource = new SQLiteNumberPointsDataSource(this);

		final AsyncTask<Void, Void, Boolean> worker = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(final Void... arg0) {
				dataSource.open();
				List<NumberPoints> values = dataSource.getAllNumberPoints();
				String content = "";
				for (int i = 0; i<values.size(); i++) {					
					NumberPoints test = values.get(i);					
					content+=test.getName()+" ";
				}
				Log.e("Content of the database :", content);
				//text_database.setText(content);
				return true;
			}
			
			@Override
			protected void onPostExecute(final Boolean result) {
				DatabaseTestActivity.this.makeText("Database loaded");
				closeDatabase();
			}
			
			@Override
			protected void onPreExecute() {
				DatabaseTestActivity.this.makeText("Loading database");
			}

		};
		
		worker.execute();
		

		
	}
	
}
