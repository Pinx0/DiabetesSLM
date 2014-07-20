package com.pablolopezponce.diabetesslm.setup;

import com.pablolopezponce.diabetesslm.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SetupFragment1 extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.activity_setup_1, null);		
		return v;
	}
}