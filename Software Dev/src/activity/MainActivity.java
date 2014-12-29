package activity;

import item.NumberPoints;

import java.util.List;

import com.example.yrdyt.R;

import communication.NumberPointsTable;
import communication.SQLiteNumberPointsDataSource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
	private Button buttonPlay; 
	private Button buttonHowToPlay;
	private Button buttonDeleteDatabase;	
	private Button buttonExit; 
	private Button buttonTest; 
	
	
	private void initUIFields() {
		this.buttonPlay = (Button) this.findViewById(R.id.button2);
		this.buttonHowToPlay = (Button) this.findViewById(R.id.button1);
		this.buttonExit = (Button) this.findViewById(R.id.button3);
		this.buttonDeleteDatabase = (Button) this.findViewById(R.id.button4);
		this.buttonTest = (Button) this.findViewById(R.id.button5);
	}
	
	private void linkNavigationButtons() {
		this.linkPlayButton();
		this.linkHowToPlayButton();
		this.linkDeleteDatabaseButton();
		this.linkExitButton();
		this.linkTestButton();
	}
	
	private void linkPlayButton() {
		this.buttonPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				if (emptyDatabase()) {
					makeText("Starting new game");
					final Intent intent = new Intent(MainActivity.this, HowToPlayActivity.class);
					MainActivity.this.startActivity(intent);						
				}
				else {
					makeText("Continuing saved game");					
					final Intent intent = new Intent(MainActivity.this, GoogleMapAppActivity.class);
					MainActivity.this.startActivity(intent);
				}
			}
		});
	}

	private void linkTestButton() {
		this.buttonTest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
					final Intent intent = new Intent(MainActivity.this, TestActivity.class);
					MainActivity.this.startActivity(intent);
			}
		});
	}

	
	private void linkHowToPlayButton() {
		this.buttonHowToPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
					final Intent intent = new Intent(MainActivity.this, HowToPlayActivity.class);
					MainActivity.this.startActivity(intent);
			}
		});
	}
	
	private void linkDeleteDatabaseButton() {
		this.buttonDeleteDatabase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				MainActivity.this.flushDatabase(); //this line deletes table from the database
				MainActivity.this.makeText("Local database erased.");
			}
		});
	}

	private void linkExitButton() {
		this.buttonExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
					MainActivity.this.finish();
			}
		});
	}
	
	private void flushDatabase() {
		SQLiteNumberPointsDataSource dataSource = new SQLiteNumberPointsDataSource(this);
		dataSource.open();
		NumberPointsTable table = new NumberPointsTable(); 
		dataSource.deleteTable(table);
		dataSource.close();
	}

	private Boolean emptyDatabase() {
		SQLiteNumberPointsDataSource dataSource = new SQLiteNumberPointsDataSource(this);
		dataSource.open();
		List<NumberPoints> elements = dataSource.getAllNumberPoints();
		dataSource.close();
		return elements.isEmpty();
	}

	private void makeText(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
	}

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("City Rally : Luxembourg");
		this.initUIFields();
		this.linkNavigationButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
