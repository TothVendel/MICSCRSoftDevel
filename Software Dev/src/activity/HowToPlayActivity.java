package activity;

import com.example.yrdyt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HowToPlayActivity extends Activity {
	private Button buttonPlay; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_how_to_play);
        this.setTitle("Starting new game, Tutorial");
		this.initUIFields();
		this.linkNavigationButtons();
	}

	private void linkNavigationButtons() {
		this.linkButtonPlay();
	}
	
	private void linkButtonPlay() {
		this.buttonPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				final Intent intent = new Intent(HowToPlayActivity.this, GoogleMapAppActivity.class);
				HowToPlayActivity.this.startActivity(intent);
			}
		});
	}

	private void initUIFields() {
		this.buttonPlay = (Button) this.findViewById(R.id.button1);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.how_to_play, menu);
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
