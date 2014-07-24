package com.pablolopezponce.diabetesslm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.pablolopezponce.diabetesslm.ConnectionService.LocalBinder;

public class MainMenuActivity extends Activity {
	
    ConnectionService mService;
    boolean mBound = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
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
