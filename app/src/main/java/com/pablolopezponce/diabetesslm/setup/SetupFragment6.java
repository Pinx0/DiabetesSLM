package com.pablolopezponce.diabetesslm.setup;

import com.pablolopezponce.diabetesslm.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SetupFragment6 extends Fragment
{
	View v = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		v = inflater.inflate(R.layout.activity_setup_6, null);
		return v;
	}
}
