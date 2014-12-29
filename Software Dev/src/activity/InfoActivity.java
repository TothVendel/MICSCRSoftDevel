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
import android.widget.TextView;

public class InfoActivity extends Activity {
	private Button buttonBack; 
	private TextView info;
	private int numberHints;
	
	private void initUIFields() {
		this.buttonBack = (Button) this.findViewById(R.id.button1);
		this.info = (TextView) this.findViewById(R.id.textView1);
	}
	
	
	private void linkBackButton() {
		this.buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				finish();			
			}

			});
		}

	
	private void linkNavigationButtons() {
		this.linkBackButton();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
        this.setTitle("Information about the place");
		this.initUIFields();
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		this.numberHints= bundle.getInt("hints");
		this.linkNavigationButtons();
		this.makeInfoText();
	}
	
	void makeInfoText() {
		if (numberHints==0) {
			setText("The University of Luxembourg is the only university in Luxembourg, founded on 13 August 2003. Prior to that, there were several higher educational institutions such as the cour universitaire or the IST that offered one or two years of academic studies. Luxembourgish students had to go abroad in order to complete their studies at a university (usually to Belgium, France, Germany, Austria, and the United Kingdom). The new university makes it possible for these students to complete their studies in their own country, as well as attract foreign academic interest to Luxembourg. Campus Kirchberg is one of the campuses of the university.");
		}

		else if (numberHints==1) {
			setText("The Utopolis Kirchberg is a multiplex cinema in Luxembourg City, in southern Luxembourg, owned and operated by the Utopia Group. It is located on the Avenue John Fitzgerald Kennedy in the Kirchberg quarter, in the north-east of the city. With a total capacity of 2,693, spread across ten screens, it is the largest cinema in the country, and was opened in December 1996.");
		}

		else if (numberHints==2) {
			setText("Emile Hamilius (16 May 1897 – 7 March 1971) was a Luxembourgish politician for the Democratic Party. He was the Mayor of Luxembourg City from 1946 until 1963, and also sat three stints in the Chamber of Deputies (1937–40, 1945–58, 1959–64). Hamilius was the second President of the Council of European Municipalities and Regions, from 1953 until 1959. In earlier life, Hamilius played football for the Luxembourg national team, including at the 1920 Summer Olympics in Antwerp. He then combined his roles as an ex-footballer with that as a politician by serving as President of the Luxembourg Football Federation between 1950 and 1961. Luxembourg City's Place Émile Hamilius, situated just off Boulevard Royal in Ville Haute, is named after Hamilius. He also gives his name to Centre Émile Hamilius, location of much of Luxembourg City's municipal administration, located on Boulevard Royal. His son, Jean, is a former DP politician himself, serving as a Deputy, minister, and Member of the European Parliament. He also competed at the Summer Olympics (in 1952), like Émile Hamilius.");
		}

		else if (numberHints==3) {
			setText("The European Parliament's presence in Kirchberg, Luxembourg currently consists of the Parliament's secretariat, although the Parliament had held plenary sessions in the city for a brief period.There are a handful of buildings in Luxembourg used by the Parliament. The city hosts the Secretariat of the European Parliament (employing over 4000 people), mostly based in the Kirchberg district.The provisional arrangement was reiterated on 8 April 1965 with the Decision on the provisional location of certain institutions and departments of the Communities. This was following the Merger Treaty, which combined the executives of the three Communities into a single institutional structure. However with the merged executives, the Commission and most departments were grouped together in Brussels, rather than Luxembourg. To compensate Luxembourg for the loss, the agreement granted a city the right to host a number bodies, including the Secretariat of the Assembly (now of the Parliament).");
		}

		else if (numberHints==4) {
			setText("The Grande-Duchesse Joséphine-Charlotte Concert Hall (French: Salle de concerts grande-duchesse Joséphine-Charlotte, German: Konzertsaal Großherzogin Joséphine-Charlotte) is a concert hall located on the Kirchberg plateau in the City of Luxembourg. Opened in 2005, it now plays host to 400 performances each year and is one of the main concert halls in Europe. The inspiration for constructing a concert hall in Luxembourg is closely linked to the old RTL Symphonic Orchestra, now known as the Luxembourg Philharmonic Orchestra (OPL - Orchestre philharmonique du Luxembourg). With the privatisation of RTL in 1992, the channel was no longer obliged to support an orchestra. The Luxembourg State decided to take the orchestra over and place it in the responsibility of an establishment specially created for this purpose, the Henri Pensis Foundation. This step acted as the catalyst for political action in favour of ensuring the construction of a suitable concert hall. In 1995, Luxembourg was nominated European Capital of Culture. The great success of this initiative demonstrated the need for cultural facilities to be further developed. In the same year, the Luxembourg Parliament made the decision to construct, amongst other new structures, a concert hall.");
		}

		else if (numberHints==5) {
			setText("The Grand Théâtre de Luxembourg, inaugurated in 1964 as the Théâtre Municipal de la Ville de Luxembourg, underwent renovation work in 2002–2003 resulting in substantial improvements to the stage technology, acoustics and lighting facilities. It is the city's major venue for drama, opera and ballet.Since 1869, Luxembourg City's main theatre had been the Théâtre des Capucins located near the centre of the old town. In December 1958, after the need for a properly designed theatre building had become a priority, a competition was launched with a view to completing the construction work for the millennium celebrations in 1963. The winner was Alain Bourbonnais, a Parisian architect. Work began in autumn 1959 and the theatre was festively inaugurated on 15 April 1964.");
		}

		else if (numberHints==6) {
			setText("The Municipal Park (French: parc de la Ville) is a public urban park in Luxembourg City, in southern Luxembourg. The eastern edge flanks the boulevard du Prince Henri and, along with the valleys of the Alzette and Pétrusse, forms a boundary that separates the central Ville Haute quarter from the rest of the city.This green arc is segmented into separate sections by the avenue Monterey, the avenue Émile Reuter, and the avenue de la Porte-Neuve. The area bordered by these roads is approximately 20 ha (49 acres). The southern-most section of the park is called Edmund Klein Park (Parc Ed. Klein). The park was created after the demolition of the fortress under the 1867 Treaty of London.The park is the location of the Villa Louvigny, in the southern-most section, and the Villa Vauban, across the avenue Émile Reuter. The Villa Louvigny was the seat of the Compagnie Luxembourgeoise de Télédiffusion, the forerunner of RTL Group, and hosted the Eurovision Song Contest in 1962 and 1966. The Villa Vauban was the original seat of the European Court of Justice, and is now an art museum.During the excavation for the construction of the underground Monterey car park, the remains of the fort was uncovered. Named the Lambert Redoubt, the pentagonal fortress can now be seen just south of avenue Monterey. The Lambert Fortress was originally built in 1685, renovated in 1835-6, and razed between 1868 and 1874.");
		}

	}

	void setText(String s) {
		this.info.setText(s);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
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
