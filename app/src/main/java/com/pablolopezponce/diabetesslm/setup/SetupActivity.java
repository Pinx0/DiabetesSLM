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

    public int parseIntFromEditText(EditText field) {
        String field_string = field.getText().toString();
        if (field_string != null && !field_string.isEmpty()) {
            return Integer.parseInt(field_string);
        } else {
            return 0;
        }
    }
    public float parseFloatFromEditText(EditText field) {
        String field_string = field.getText().toString();
        if (field_string != null && !field_string.isEmpty()) {
            return Float.parseFloat(field_string);
        } else {
            return 0;
        }
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
            int age = parseIntFromEditText(age_text);
            EditText weight_text = (EditText) findViewById(R.id.setup_fragment_2_edit_text_weight);
            int weight = parseIntFromEditText(weight_text);
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
        Float basal0 = parseFloatFromEditText(basal_text0);
        savedData.edit().putFloat("basalRate0", basal0).apply();
        EditText basal_text1 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_2);
        Float basal1 = parseFloatFromEditText(basal_text1);
        savedData.edit().putFloat("basalRate1", basal1).apply();
        EditText basal_text2 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_3);
        Float basal2 = parseFloatFromEditText(basal_text2);
        savedData.edit().putFloat("basalRate2", basal2).apply();
        EditText basal_text3 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_4);
        Float basal3 = parseFloatFromEditText(basal_text3);
        savedData.edit().putFloat("basalRate3", basal3).apply();
        EditText basal_text4 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_5);
        Float basal4 = parseFloatFromEditText(basal_text4);
        savedData.edit().putFloat("basalRate4", basal4).apply();
        EditText basal_text5 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_6);
        Float basal5 = parseFloatFromEditText(basal_text5);
        savedData.edit().putFloat("basalRate5", basal5).apply();
        EditText basal_text6 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_7);
        Float basal6 = parseFloatFromEditText(basal_text6);
        savedData.edit().putFloat("basalRate6", basal6).apply();
        EditText basal_text7 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_8);
        Float basal7 = parseFloatFromEditText(basal_text7);
        savedData.edit().putFloat("basalRate7", basal7).apply();
        EditText basal_text8 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_9);
        Float basal8 = parseFloatFromEditText(basal_text8);
        savedData.edit().putFloat("basalRate8", basal8).apply();
        EditText basal_text9 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_10);
        Float basal9 = parseFloatFromEditText(basal_text9);
        savedData.edit().putFloat("basalRate9", basal9).apply();
        EditText basal_text10 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_11);
        Float basal10 = parseFloatFromEditText(basal_text10);
        savedData.edit().putFloat("basalRate10", basal10).apply();
        EditText basal_text11 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_12);
        Float basal11 = parseFloatFromEditText(basal_text11);
        savedData.edit().putFloat("basalRate11", basal11).apply();
        EditText basal_text12 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_13);
        Float basal12 = parseFloatFromEditText(basal_text12);
        savedData.edit().putFloat("basalRate12", basal12).apply();
        EditText basal_text13 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_14);
        Float basal13 = parseFloatFromEditText(basal_text13);
        savedData.edit().putFloat("basalRate13", basal13).apply();
        EditText basal_text14 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_15);
        Float basal14 = parseFloatFromEditText(basal_text14);
        savedData.edit().putFloat("basalRate14", basal14).apply();
        EditText basal_text15 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_16);
        Float basal15 = parseFloatFromEditText(basal_text15);
        savedData.edit().putFloat("basalRate15", basal15).apply();
        EditText basal_text16 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_17);
        Float basal16 = parseFloatFromEditText(basal_text16);
        savedData.edit().putFloat("basalRate16", basal16).apply();
        EditText basal_text17 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_18);
        Float basal17 = parseFloatFromEditText(basal_text17);
        savedData.edit().putFloat("basalRate17", basal17).apply();
        EditText basal_text18 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_19);
        Float basal18 = parseFloatFromEditText(basal_text18);
        savedData.edit().putFloat("basalRate18", basal18).apply();
        EditText basal_text19 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_20);
        Float basal19 = parseFloatFromEditText(basal_text19);
        savedData.edit().putFloat("basalRate19", basal19).apply();
        EditText basal_text20 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_21);
        Float basal20 = parseFloatFromEditText(basal_text20);
        savedData.edit().putFloat("basalRate20", basal20).apply();
        EditText basal_text21 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_22);
        Float basal21 = parseFloatFromEditText(basal_text21);
        savedData.edit().putFloat("basalRate21", basal21).apply();
        EditText basal_text22 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_23);
        Float basal22 = parseFloatFromEditText(basal_text22);
        savedData.edit().putFloat("basalRate22", basal22).apply();
        EditText basal_text23 = (EditText) findViewById(R.id.setup_fragment_3_edit_text_24);
        Float basal23 = parseFloatFromEditText(basal_text23);
        savedData.edit().putFloat("basalRate23", basal23).apply();

        nextPage(view);
    }
    public void nextPage4 (View view)
    {
        EditText basal_text = (EditText) findViewById(R.id.setup_fragment_4_edit_text_1);
        int basal = parseIntFromEditText(basal_text);
        savedData.edit().putInt("basalRate", basal).apply();
        nextPage(view);
    }
    public void nextPage5 (View view)
    {
        EditText breakfast_text = (EditText) findViewById(R.id.setup_fragment_5_edit_text_1);
        float breakfast = parseFloatFromEditText(breakfast_text);
        savedData.edit().putFloat("breakfast", breakfast).apply();
        EditText lunch_text = (EditText) findViewById(R.id.setup_fragment_5_edit_text_2);
        float lunch = parseFloatFromEditText(lunch_text);
        savedData.edit().putFloat("lunch", lunch).apply();
        EditText dinner_text = (EditText) findViewById(R.id.setup_fragment_5_edit_text_3);
        float dinner = parseFloatFromEditText(dinner_text);
        savedData.edit().putFloat("dinner", dinner).apply();
        nextPage(view);
    }
    public void nextPage6 (View view)
    {
        EditText correction_text = (EditText) findViewById(R.id.setup_fragment_6_edit_text_1);
        int correction = parseIntFromEditText(correction_text);
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
