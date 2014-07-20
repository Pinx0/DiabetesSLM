package com.pablolopezponce.diabetesslm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import com.pablolopezponce.diabetesslm.resources.HttpsClient;
import com.pablolopezponce.diabetesslm.resources.MyRes;

public class ConnectionService extends Service {

	protected static final String REQUEST_AUTHORIZATION = null;
	final Handler handler = new Handler();
	// Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    
    public boolean keepActive = false;
    
    public SharedPreferences savedData;

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
    	ConnectionService getService() {
            // Return this instance of ConnectionService so clients can call public methods
            return ConnectionService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
    	savedData = this.getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
    	keepActive = true;
    	secondThread();
        return mBinder;
    }
    
    @Override public boolean onUnbind(Intent intent) {
    	keepActive = false;
		return false;
    }
    
    protected void secondThread() {
    	Thread t = new Thread() {
    		public void run() {
    			try {
    				while(keepActive) {
    					Thread.sleep(15000);
    					handler.post(syncData);
    				}
    				
    			} catch(InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    		
    	};
    	t.start();
    }
	private Runnable syncData = new Runnable(){
	    public void run(){
	    	
	    	String userEmail = savedData.getString("userEmail", null);
			String mScope = getString(R.string.oauth_scope);
			String token = "";
			String response = "";

			try 
			{
				// if this returns, the OAuth framework thinks the token should
				// be usable
				token = GoogleAuthUtil.getToken(ConnectionService.this, userEmail, mScope);

			}
			
			catch (UserRecoverableAuthException userAuthEx) 
			{
				// TRADUCIR //
				// This means that the app hasn't been authorized by the user
				// for access
				// to the scope, so we're going to have to fire off the
				// (provided) Intent
				// to arrange for that. But we only want to do this once.
				// Multiple
				// attempts probably mean the user said no.
				response = "You have denied permission";

			} 
			catch (IOException ioEx) 
			{
				// Something is stressed out; the auth servers are by definition
				// high-traffic and you can't count on 100% success. But it
				// would be
				// bad to retry instantly, so back off
				response = "No response from authorization server.";
			} 
			catch (GoogleAuthException fatalAuthEx) 
			{
				response = "Fatal authorization exception: " + fatalAuthEx.getLocalizedMessage();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if (token != null) 
			{
				Log.i(MyRes.TAG, "Token: " + token);
				
				savedData.edit().putString("userEmail", userEmail).commit();
				savedData.edit().putString("token", token).commit();
				
				HttpPost httppost = new HttpPost(getString(R.string.url));
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				
				try 
				{
					DefaultHttpClient httpclient = new HttpsClient(getApplicationContext());
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					// Execute HTTP Post Request
					//ResponseHandler<String> responseHandler = new BasicResponseHandler();
					HttpResponse httpResponse = httpclient.execute(httppost);
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
					StringBuilder builder = new StringBuilder();
					for (String line = null; (line = reader.readLine()) != null;) {
					    builder.append(line).append("\n");
					}
					JSONTokener tokener = new JSONTokener(builder.toString());
					
					JSONObject object = (JSONObject) tokener.nextValue();
					response = object.getString("status");
					Log.i("--->", "" + response);

				} catch (Exception e) 
				{
					Log.e(MyRes.TAG, "Error in http connection " + e.toString());
					response = "Error in http connection " + e.toString();
				}

			}

	    	
	    }
	};
	

}
