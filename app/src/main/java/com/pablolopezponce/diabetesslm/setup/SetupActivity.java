package com.pablolopezponce.diabetesslm.setup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;

import com.pablolopezponce.diabetesslm.MainMenuActivity;
import com.pablolopezponce.diabetesslm.R;
import com.pablolopezponce.diabetesslm.resources.MyRes;

import java.util.Map;

public class SetupActivity extends FragmentActivity 
{
	public ViewPager pager = null;
	public SetupPagerAdapter pagerAdapter;
    public SharedPreferences savedData;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Eliminamos el título de la actividad //
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup);

		initializeVariables();
	}

	public void initializeVariables() 
	{
		// Inicialización del View Pager //
		this.pager = (ViewPager) findViewById(R.id.pager);
		this.pager.setPageTransformer(true, new ZoomOutPageTransformer());

		// Adaptador del View Pager //
		pagerAdapter = new SetupPagerAdapter(getSupportFragmentManager());
		pagerAdapter.addFragment(0, new SetupFragment1());
		pagerAdapter.addFragment(1, new SetupFragment2());

		// Asociamos el View Pager con el adaptador //
		this.pager.setAdapter(pagerAdapter);

        savedData = this.getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
		
	}
	

	@Override
	public void onBackPressed() 
	{
		// Return to previous page when we press back button
		if (pager.getCurrentItem() == 0)
			super.onBackPressed();
		else
			this.pager.setCurrentItem(this.pager.getCurrentItem() - 1);
	}

	// Test //
	public void onRadioButtonClicked(View view) 
	{
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) 
		{
		case R.id.setup_fragment_2_radio_button_pump:
			if (checked) 
			{
				pagerAdapter.replaceFromFragment(2, new SetupFragment3());
			}
			break;
		case R.id.setup_fragment_2_radio_button_pen:
			if (checked) 
			{
				pagerAdapter.replaceFromFragment(2, new SetupFragment4());
			}
			break;
		}
	}

    public boolean isRadioButtonChecked (View view)
    {
        return ((RadioButton) view).isChecked();
    }

	public void nextPage(View view)
    {
        MyRes.saveSettingsDate(this);
		pager.setCurrentItem(pager.getCurrentItem() + 1);
	}

    public void nextPage1 (View view)
    {
        boolean unitSystem = isRadioButtonChecked(findViewById(R.id.setup_fragment_radio_button_imperial));
        boolean sugarBloodUnit = isRadioButtonChecked(findViewById(R.id.setup_fragment_radio_button_mmoll));
        savedData.edit().putBoolean("unitSystem", unitSystem).apply();
        savedData.edit().putBoolean("sugarBloodUnit", sugarBloodUnit).apply();
        nextPage(view);
    }

    public void nextPage2 (View view)
    {
        boolean injectionSystem = isRadioButtonChecked(findViewById(R.id.setup_fragment_2_radio_button_pen));
        boolean injectionSystem2 = isRadioButtonChecked(findViewById(R.id.setup_fragment_2_radio_button_pump));
        if(injectionSystem||injectionSystem2) {
            boolean genre = isRadioButtonChecked(findViewById(R.id.setup_fragment_2_radio_button_woman));
            EditText age_text = (EditText) findViewById(R.id.setup_fragment_2_edit_text_age);
            int age = Integer.parseInt(age_text.getText().toString());
            EditText weight_text = (EditText) findViewById(R.id.setup_fragment_2_edit_text_weight);
            int weight = Integer.parseInt(weight_text.getText().toString());
            savedData.edit().putBoolean("injectionSystem", injectionSystem).apply();
            savedData.edit().putBoolean("genre", genre).apply();
            savedData.edit().putInt("age", age).apply();
            savedData.edit().putInt("weight", weight).apply();
            nextPage(view);
        }
    }

    public void nextPage3 (View view)
    {
        EditText basal_text0 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_1);
        int basal0 = Integer.parseInt(basal_text0.getText().toString());
        savedData.edit().putInt("basalRate0", basal0).apply();
        EditText basal_text1 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_2);
        int basal1 = Integer.parseInt(basal_text1.getText().toString());
        savedData.edit().putInt("basalRate1", basal1).apply();
        /* falta repetir con el resto de horas */
        nextPage(view);
    }
    public void nextPage4 (View view)
    {
        EditText basal_text = (EditText) findViewById(R.id.setup_fragment_4_edit_text_1);
        int basal = Integer.parseInt(basal_text.getText().toString());
        savedData.edit().putInt("basalRate", basal).apply();
        nextPage(view);
    }
    public void nextPage5 (View view)
    {
        EditText breakfast_text = (EditText) findViewById(R.id.setup_fragment_5_edit_text_1);
        float breakfast = Float.parseFloat(breakfast_text.getText().toString());
        savedData.edit().putFloat("breakfast", breakfast).apply();
        EditText lunch_text = (EditText) findViewById(R.id.setup_fragment_5_edit_text_2);
        float lunch = Float.parseFloat(lunch_text.getText().toString());
        savedData.edit().putFloat("lunch", lunch).apply();
        EditText dinner_text = (EditText) findViewById(R.id.setup_fragment_5_edit_text_3);
        float dinner = Float.parseFloat(dinner_text.getText().toString());
        savedData.edit().putFloat("dinner", dinner).apply();
        nextPage(view);
    }
    public void nextPage6 (View view)
    {
        EditText correction_text = (EditText) findViewById(R.id.setup_fragment_6_edit_text_1);
        int correction = Integer.parseInt(correction_text.getText().toString());
        savedData.edit().putInt("correction", correction).apply();
        nextPage(view);
    }

	public void previousPage(View view)
	{
		pager.setCurrentItem(pager.getCurrentItem() - 1);
	}

    public void finishSetup(View view)
    {
        savedData.edit().putBoolean("finishedSetup", true).apply();

        /* muestra todas las preferencias guardadas, debugging simplemente */
        Map<String,?> keys = savedData.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.i("map values", entry.getKey() + ": " +
                    entry.getValue().toString());
        }
        /* fin muestra */
        Intent finishSetupIntent = new Intent(this, MainMenuActivity.class);
        startActivity(finishSetupIntent);
        this.finish();
    }
}
