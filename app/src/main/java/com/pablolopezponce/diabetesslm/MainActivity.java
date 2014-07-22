package com.pablolopezponce.diabetesslm;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import com.pablolopezponce.diabetesslm.resources.MyRes;
import com.pablolopezponce.diabetesslm.setup.SetupActivity;


public class MainActivity extends Activity 
{
	public SharedPreferences savedData;
	
	private String userEmail;
	private boolean finishedSetup;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		savedData = this.getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
		userEmail = savedData.getString("userEmail", null);
		finishedSetup = savedData.getBoolean("finishedSetup", false);
		
		if(userEmail!=null && finishedSetup && false) {
			// Inicializamos la actividad de menú principal porque ya tenemos lo necesario //
			Intent startMainMenuActivity = new Intent(MainActivity.this, MainMenuActivity.class);
			startActivity(startMainMenuActivity);
			this.finish();
			
		} else if(userEmail!=null || true) {

			// Inicializamos la actividad de setup porque el user no lo acabó de rellenar //
			Intent startSetupActivity = new Intent(MainActivity.this, SetupActivity.class);
			startActivity(startSetupActivity);
			this.finish();
			
		} else {
		
			// Inicializamos la actividad de bienvenida //
			Intent startWelcomeActivity = new Intent(MainActivity.this, WelcomeActivity.class);
			startActivity(startWelcomeActivity);
			this.finish();
		}
		
		// Typeface - Light //
		MyRes.lightTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
		
		// Typeface - Regular //
		MyRes.regularTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");		
	}
}
