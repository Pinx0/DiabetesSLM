package com.pablolopezponce.diabetesslm.setup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SetupPagerAdapter extends FragmentStatePagerAdapter 
{
	private List<Fragment> fragments;
	
	public SetupPagerAdapter(FragmentManager fm) 
	{
		super(fm);
		this.fragments = new ArrayList<Fragment>();
	}
	
	// Añadir un fragmento en la posicion deseada //
	public void addFragment(int position, Fragment fragment)
	{
        fragments.add(position, fragment);
	}
	
	// Añadir un fragmento //
	public void addFragment(Fragment fragment)
	{
		fragments.add(fragment);
	}
	
	// Cambia los views dependiendo de la opción que escoja el usuario //
	public void replaceFromFragment(int position, Fragment fragment)
	{
		if(getCount()>=position+1)
		{
			removeAllFrom(position);
		}
		fragments.add(fragment);
		fragments.add(new SetupFragment5());
		fragments.add(new SetupFragment6());
		fragments.add(new SetupFragment7());
		notifyDataSetChanged();
	}
	
	@Override
	public Fragment getItem(int position) 
	{
		return fragments.get(position);
	}

	@Override
	public int getCount() 
	{
		return fragments.size();
	}
	
	@Override
	public int getItemPosition(Object object)
	{
		return PagerAdapter.POSITION_NONE;
	}
	
	public void removeAllFrom(int position)
	{
		for(int i = getCount()-1; i >= position; i--)
		{
			fragments.remove(i);
		}
	}

}
