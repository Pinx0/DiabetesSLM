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
        Float basal0 = Float.parseFloat(basal_text0.getText().toString());
        savedData.edit().putFloat("basalRate0", basal0).apply();
        EditText basal_text1 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_2);
        Float basal1 = Float.parseFloat(basal_text1.getText().toString());
        savedData.edit().putFloat("basalRate1", basal1).apply();
        EditText basal_text2 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_3);
        Float basal2 = Float.parseFloat(basal_text2.getText().toString());
        savedData.edit().putFloat("basalRate2", basal2).apply();
        EditText basal_text3 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_4);
        Float basal3 = Float.parseFloat(basal_text3.getText().toString());
        savedData.edit().putFloat("basalRate3", basal3).apply();
        EditText basal_text4 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_5);
        Float basal4 = Float.parseFloat(basal_text4.getText().toString());
        savedData.edit().putFloat("basalRate4", basal4).apply();
        EditText basal_text5 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_6);
        Float basal5 = Float.parseFloat(basal_text5.getText().toString());
        savedData.edit().putFloat("basalRate5", basal5).apply();
        EditText basal_text6 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_7);
        Float basal6 = Float.parseFloat(basal_text6.getText().toString());
        savedData.edit().putFloat("basalRate6", basal6).apply();
        EditText basal_text7 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_8);
        Float basal7 = Float.parseFloat(basal_text7.getText().toString());
        savedData.edit().putFloat("basalRate7", basal7).apply();
        EditText basal_text8 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_9);
        Float basal8 = Float.parseFloat(basal_text8.getText().toString());
        savedData.edit().putFloat("basalRate8", basal8).apply();
        EditText basal_text9 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_10);
        Float basal9 = Float.parseFloat(basal_text9.getText().toString());
        savedData.edit().putFloat("basalRate9", basal9).apply();
        EditText basal_text10 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_11);
        Float basal10 = Float.parseFloat(basal_text10.getText().toString());
        savedData.edit().putFloat("basalRate10", basal10).apply();
        EditText basal_text11 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_12);
        Float basal11 = Float.parseFloat(basal_text11.getText().toString());
        savedData.edit().putFloat("basalRate11", basal11).apply();
        EditText basal_text12 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_13);
        Float basal12 = Float.parseFloat(basal_text12.getText().toString());
        savedData.edit().putFloat("basalRate12", basal12).apply();
        EditText basal_text13 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_14);
        Float basal13 = Float.parseFloat(basal_text13.getText().toString());
        savedData.edit().putFloat("basalRate13", basal13).apply();
        EditText basal_text14 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_15);
        Float basal14 = Float.parseFloat(basal_text14.getText().toString());
        savedData.edit().putFloat("basalRate14", basal14).apply();
        EditText basal_text15 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_16);
        Float basal15 = Float.parseFloat(basal_text15.getText().toString());
        savedData.edit().putFloat("basalRate15", basal15).apply();
        EditText basal_text16 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_17);
        Float basal16 = Float.parseFloat(basal_text16.getText().toString());
        savedData.edit().putFloat("basalRate16", basal16).apply();
        EditText basal_text17 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_18);
        Float basal17 = Float.parseFloat(basal_text17.getText().toString());
        savedData.edit().putFloat("basalRate17", basal17).apply();
        EditText basal_text18 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_19);
        Float basal18 = Float.parseFloat(basal_text18.getText().toString());
        savedData.edit().putFloat("basalRate18", basal18).apply();
        EditText basal_text19 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_20);
        Float basal19 = Float.parseFloat(basal_text19.getText().toString());
        savedData.edit().putFloat("basalRate19", basal19).apply();
        EditText basal_text20 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_21);
        Float basal20 = Float.parseFloat(basal_text20.getText().toString());
        savedData.edit().putFloat("basalRate20", basal20).apply();
        EditText basal_text21 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_22);
        Float basal21 = Float.parseFloat(basal_text21.getText().toString());
        savedData.edit().putFloat("basalRate21", basal21).apply();
        EditText basal_text22 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_23);
        Float basal22 = Float.parseFloat(basal_text22.getText().toString());
        savedData.edit().putFloat("basalRate22", basal22).apply();
        EditText basal_text23 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_24);
        Float basal23 = Float.parseFloat(basal_text23.getText().toString());
        savedData.edit().putFloat("basalRate23", basal23).apply();

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
