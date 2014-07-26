package com.pablolopezponce.diabetesslm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pablolopezponce.diabetesslm.ConnectionService.LocalBinder;
import com.pablolopezponce.diabetesslm.resources.DrawerMenuItem;
import com.pablolopezponce.diabetesslm.resources.MyRes;
import com.pablolopezponce.diabetesslm.resources.NavigationAdapter;

import java.util.ArrayList;

public class MainMenuActivity extends Activity {
	
    ConnectionService mService;
    boolean mBound = false;
    private DrawerLayout NavDrawerLayout;
    private ListView NavDrawerList;
    private ArrayList<DrawerMenuItem> NavDrawerItems;
    private TypedArray NavIcons;
    private String[] titles;
    NavigationAdapter NavAdapter;
    private ActionBarDrawerToggle mDrawerToggle;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                NavDrawerLayout,
                R.drawable.health_icon,
                R.string.app_name,
                R.string.title_activity_food


        ) {
            public void onDrawerClosed(View view) {
                //por si queremos que haga algo al cerrar el drawer
            }
            public void onDrawerOpened(View view) {
                //por si queremos que haga algo al abrir el drawer
            }
        };

        NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavDrawerLayout.setDrawerListener(mDrawerToggle);
        NavDrawerList = (ListView) findViewById(R.id.left_drawer);
        View drawerHeader = getLayoutInflater().inflate(R.layout.drawer_menu_header, null);

        NavDrawerList.addHeaderView(drawerHeader);
        NavIcons = getResources().obtainTypedArray(R.array.nav_icons);
        titles = getResources().getStringArray(R.array.nav_options);
        NavDrawerItems = new ArrayList<DrawerMenuItem>();
        NavDrawerItems.add(new DrawerMenuItem(titles[0],NavIcons.getResourceId(0, -1)));
        NavDrawerItems.add(new DrawerMenuItem(titles[1],NavIcons.getResourceId(1, -1)));
        NavDrawerItems.add(new DrawerMenuItem(titles[2],NavIcons.getResourceId(2, -1)));
        NavDrawerItems.add(new DrawerMenuItem(titles[3],NavIcons.getResourceId(3, -1)));
        NavDrawerItems.add(new DrawerMenuItem(titles[4],NavIcons.getResourceId(4, -1)));
        NavDrawerItems.add(new DrawerMenuItem(titles[5],NavIcons.getResourceId(5, -1)));
        NavAdapter = new NavigationAdapter(this,NavDrawerItems);
        NavDrawerList.setAdapter(NavAdapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        NavDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                OpenActivityFromDrawer(position);
            }
        });

	}
	
	@Override
    protected void onResume() {
        super.onResume();
        // Bind to LocalService
        Intent intent = new Intent(this, ConnectionService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unbind from the service
        if (mBound) {
        	unbindService(mConnection);
            mBound = false;
        }
    }

    public void openGlucoseActivity(View view) {
        Intent startGlucoseActivity = new Intent(MainMenuActivity.this, GlucoseActivity.class);
        startActivity(startGlucoseActivity);
    }
    public void openFoodActivity(View view) {
        Intent startFoodActivity = new Intent(MainMenuActivity.this, FoodActivity.class);
        startActivity(startFoodActivity);
    }
    public void openBolusActivity(View view) {
        Intent startBolusActivity = new Intent(MainMenuActivity.this, BolusActivity.class);
        startActivity(startBolusActivity);
    }
    public void openExerciseActivity(View view) {
        Intent startExerciseActivity = new Intent(MainMenuActivity.this, ExerciseActivity.class);
        startActivity(startExerciseActivity);
    }
    public void openDiseaseActivity(View view) {
        Intent startDiseaseActivity = new Intent(MainMenuActivity.this, DiseaseActivity.class);
        startActivity(startDiseaseActivity);
    }
    public void openSettingsActivity() {
        Intent startSettingsActivity = new Intent(MainMenuActivity.this, SettingsActivity.class);
        startActivity(startSettingsActivity);
    }
    public void openStatisticsActivity() {
        Intent startStatisticsActivity = new Intent(MainMenuActivity.this, StatisticsActivity.class);
        startActivity(startStatisticsActivity);
    }
    public void openDataActivity() {
        Intent startDataActivity = new Intent(MainMenuActivity.this, DataActivity.class);
        startActivity(startDataActivity);
    }
    public void openGraphsActivity() {
        Intent startGraphsActivity = new Intent(MainMenuActivity.this, GraphsActivity.class);
        startActivity(startGraphsActivity);
    }
    public void openAboutActivity() {
        Intent startAboutActivity = new Intent(MainMenuActivity.this, AboutActivity.class);
        startActivity(startAboutActivity);
    }

    public void OpenActivityFromDrawer(int position) {
        Log.i(MyRes.TAG,"position" + position);
        switch (position) {
            case 1:

                break;
            case 2:
                openDataActivity();
                break;
            case 3:
                openStatisticsActivity();
                break;
            case 4:
                openGraphsActivity();
                break;
            case 5:
                openSettingsActivity();
                break;
            case 6:
                openAboutActivity();
                break;
            default:

                break;
        }
    }



	/** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}
