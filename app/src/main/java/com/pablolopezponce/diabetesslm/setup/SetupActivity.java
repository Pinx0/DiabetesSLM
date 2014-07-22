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
import android.widget.RadioButton;

import com.pablolopezponce.diabetesslm.MainMenuActivity;
import com.pablolopezponce.diabetesslm.R;
import com.pablolopezponce.diabetesslm.resources.MyRes;

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
	
	public ViewPager getpager()
    {
    	return pager;
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

	public void nextPage(View view)
    {
        Log.i(MyRes.TAG, "Ola k ase");
        MyRes.saveSettingsDate(this);
		pager.setCurrentItem(pager.getCurrentItem() + 1);
	}

	public void previousPage(View view)
	{
		pager.setCurrentItem(pager.getCurrentItem() - 1);
	}

    public void finishSetup(View view)
    {
        savedData.edit().putBoolean("finishedSetup", true).commit();
        Intent finishSetupIntent = new Intent(this, MainMenuActivity.class);
        startActivity(finishSetupIntent);
        this.finish();
    }
}
