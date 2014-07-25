package com.pablolopezponce.diabetesslm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.pablolopezponce.diabetesslm.resources.HttpsClient;
import com.pablolopezponce.diabetesslm.resources.MyRes;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConnectionService extends Service {

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
                        String userEmail = savedData.getString("userEmail", null);
                        String mScope = getString(R.string.oauth_scope);
                        String token = null;
                        String response;

		/*	VOLVER A CONSEGUIR EL TOKEN */

		try
			{
				// if this returns, the OAuth framework thinks the token should
				// be usable
				token = GoogleAuthUtil.getToken(ConnectionService.this, userEmail, mScope);

			}

			catch (UserRecoverableAuthException userAuthEx)
			{
				// This means that the app hasn't been authorized by the user
				// for access
				// to the scope, so we're going to have to fire off the
				// (provided) Intent
				// to arrange for that. But we only want to do this once.
				// Multiple
				// attempts probably mean the user said no.

				response = "You have denied permission";
                Log.i(MyRes.TAG, response);

			}
			catch (IOException ioEx)
			{
				// Something is stressed out; the auth servers are by definition
				// high-traffic and you can't count on 100% success. But it
				// would be
				// bad to retry instantly, so back off
				response = "No response from authorization server.";
                Log.i(MyRes.TAG, response);
			}
			catch (GoogleAuthException fatalAuthEx)
			{
				response = "Fatal authorization exception: " + fatalAuthEx.getLocalizedMessage();
                Log.i(MyRes.TAG, response);
			}
			catch (Exception e) {
                Log.i(MyRes.TAG, "Exception");
				e.printStackTrace();
			}

                        if (token != null)
                        {
                            Log.i(MyRes.TAG, "Token: " + token);
                            savedData.edit().putString("token", token).apply();

                            //DATOS DE CONFIGURACION A ENVIAR
                            Boolean unitSystem = savedData.getBoolean("unitSystem", false);
                            Boolean sugarBloodUnit = savedData.getBoolean("sugarBloodUnit", false);
                            Boolean injectionSystem = savedData.getBoolean("injectionSystem", false);
                            Boolean genre = savedData.getBoolean("genre", false);
                            int age = savedData.getInt("age", 0);
                            int weight = savedData.getInt("weight", 0);
                            Float basalRate0 = savedData.getFloat("basalRate0", 0);
                            Float basalRate1 = savedData.getFloat("basalRate1", 0);
                            Float basalRate2 = savedData.getFloat("basalRate2", 0);
                            Float basalRate3 = savedData.getFloat("basalRate3", 0);
                            Float basalRate4 = savedData.getFloat("basalRate4", 0);
                            Float basalRate5 = savedData.getFloat("basalRate5", 0);
                            Float basalRate6 = savedData.getFloat("basalRate6", 0);
                            Float basalRate7 = savedData.getFloat("basalRate7", 0);
                            Float basalRate8 = savedData.getFloat("basalRate8", 0);
                            Float basalRate9 = savedData.getFloat("basalRate9", 0);
                            Float basalRate10 = savedData.getFloat("basalRate10", 0);
                            Float basalRate11 = savedData.getFloat("basalRate11", 0);
                            Float basalRate12 = savedData.getFloat("basalRate12", 0);
                            Float basalRate13 = savedData.getFloat("basalRate13", 0);
                            Float basalRate14 = savedData.getFloat("basalRate14", 0);
                            Float basalRate15 = savedData.getFloat("basalRate15", 0);
                            Float basalRate16 = savedData.getFloat("basalRate16", 0);
                            Float basalRate17 = savedData.getFloat("basalRate17", 0);
                            Float basalRate18 = savedData.getFloat("basalRate18", 0);
                            Float basalRate19 = savedData.getFloat("basalRate19", 0);
                            Float basalRate20 = savedData.getFloat("basalRate20", 0);
                            Float basalRate21 = savedData.getFloat("basalRate21", 0);
                            Float basalRate22 = savedData.getFloat("basalRate22", 0);
                            Float basalRate23 = savedData.getFloat("basalRate23", 0);
                            int basalRate = savedData.getInt("basalRate", 0);
                            Float breakfast = savedData.getFloat("breakfast", 0);
                            Float lunch = savedData.getFloat("lunch", 0);
                            Float dinner = savedData.getFloat("dinner", 0);
                            int correction = savedData.getInt("correction", 0);
                            Boolean updateSettings = savedData.getBoolean("updateSettings", false);
                            Long settingsTimeServer = savedData.getLong("settingsTimeServer", 0);

                             HttpPost httppost = new HttpPost(getString(R.string.url));
                            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("mToken", token));
                            nameValuePairs.add(new BasicNameValuePair("mEmail", userEmail));
                            nameValuePairs.add(new BasicNameValuePair("settingsTime", settingsTimeServer.toString()));

                            //Si hemos actualizado algo en el dispositivo, forzamos el update en el server
                            if(updateSettings) {
                                nameValuePairs.add(new BasicNameValuePair("updateData", "1"));
                                nameValuePairs.add(new BasicNameValuePair("unitSystem", unitSystem.toString()));
                                nameValuePairs.add(new BasicNameValuePair("sugarBloodUnit", sugarBloodUnit.toString()));
                                nameValuePairs.add(new BasicNameValuePair("injectionSystem", injectionSystem.toString()));
                                nameValuePairs.add(new BasicNameValuePair("genre", genre.toString()));
                                nameValuePairs.add(new BasicNameValuePair("age", Integer.toString(age)));
                                nameValuePairs.add(new BasicNameValuePair("weight", Integer.toString(weight)));
                                nameValuePairs.add(new BasicNameValuePair("basalRate0", basalRate0.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate1", basalRate1.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate2", basalRate2.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate3", basalRate3.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate4", basalRate4.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate5", basalRate5.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate6", basalRate6.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate7", basalRate7.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate8", basalRate8.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate9", basalRate9.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate10", basalRate10.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate11", basalRate11.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate12", basalRate12.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate13", basalRate13.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate14", basalRate14.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate15", basalRate15.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate16", basalRate16.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate17", basalRate17.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate18", basalRate18.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate19", basalRate19.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate20", basalRate20.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate21", basalRate21.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate22", basalRate22.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate23", basalRate23.toString()));
                                nameValuePairs.add(new BasicNameValuePair("basalRate", Integer.toString(basalRate)));
                                nameValuePairs.add(new BasicNameValuePair("breakfast", breakfast.toString()));
                                nameValuePairs.add(new BasicNameValuePair("lunch", lunch.toString()));
                                nameValuePairs.add(new BasicNameValuePair("dinner", dinner.toString()));
                                nameValuePairs.add(new BasicNameValuePair("correction", Integer.toString(correction)));
                            }

                            try
                            {
                                DefaultHttpClient httpclient = new HttpsClient(getApplicationContext());
                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                // Execute HTTP Post Request
                                //ResponseHandler<String> responseHandler = new BasicResponseHandler();
                                HttpResponse httpResponse = httpclient.execute(httppost);

                                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                                StringBuilder builder = new StringBuilder();
                                for (String line; (line = reader.readLine()) != null;) {
                                    builder.append(line).append("\n");
                                }
                                JSONTokener tokener = new JSONTokener(builder.toString());

                                JSONObject object = (JSONObject) tokener.nextValue();
                                response = object.getString("status");
                                Log.i(MyRes.TAG, "Status: " + response);

                                //Si hemos mandado una actualización, nos devuelve la fecha en la que se ha realizado
                                if(response.equalsIgnoreCase("UPDATED")) {
                                    String newSettingsTimeServerString = object.getString("settingsTimeServer");
                                    Long newSettingsTimeServer = Long.parseLong(newSettingsTimeServerString);
                                    savedData.edit().putLong("settingsTimeServer", newSettingsTimeServer).apply();
                                    savedData.edit().putBoolean("updateSettings", false).apply();
                                }
                                //Si hay una actualización más nueva que la ultima desde este dispositivo, hay que actualizarlo.
                                if(response.equalsIgnoreCase("NEED_UPDATE")) {
                                    Log.i(MyRes.TAG,"Data: " + object.getString("data"));
                                    //falta parsear los datos y actualizar las preferencias
                                }
                            } catch (Exception e)
                            {
                                response = "Error in http connection " + e.toString();
                                Log.i(MyRes.TAG, response);
                            }

                        }
    				}

    			} catch(InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    		
    	};
    	t.start();
    }
}
