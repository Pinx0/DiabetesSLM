package com.pablolopezponce.diabetesslm.setup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import es.pablolopezponce.diabetesslm.R;

public class SetupFragment2 extends Fragment 
{

	public Button btnNext, btnPrevious;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.activity_setup_2, null);		
		return v;
	}
}
