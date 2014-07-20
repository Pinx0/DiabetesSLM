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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import com.pablolopezponce.diabetesslm.resources.HttpsClient;
import com.pablolopezponce.diabetesslm.resources.MyRes;
import com.pablolopezponce.diabetesslm.setup.SetupActivity;


public class AccountSelectActivity extends Activity 
{
	
	private static final int PICK_ACCOUNT_REQUEST = 0;
	
	public TextView textViewTitle, textView1;
	
	public Button btnChooseAccount;
	
	public ProgressDialog connectionProgress;
	
	public SharedPreferences savedData;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Eliminamos el t�tulo de la actividad //
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_select);

		initializeVariables();
	}

	// Inicializaci�n de variables //
	public void initializeVariables() 
	{
		// TextView - T�tulo //
		textViewTitle = (TextView)findViewById(R.id.account_select_text_view_title);
		textViewTitle.setTypeface(MyRes.lightTypeface);
		
		// TextView - Texto de contenido 1 //
		textView1 = (TextView)findViewById(R.id.account_select_text_view_1);
		textView1.setTypeface(MyRes.lightTypeface);
		
		// Button - Escoger tu cuenta //
		btnChooseAccount = (Button) findViewById(R.id.account_select_button_choose_account);
		btnChooseAccount.setTypeface(MyRes.regularTypeface);
		btnChooseAccount.setOnClickListener(raeBtnChooseAccount);
		
		// Progress Dialog - Conexi�n con el servidor //
		connectionProgress = new ProgressDialog(this);
		connectionProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
		connectionProgress.setCancelable(false);
		
		savedData = this.getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
	}
	
	// Listener del bot�n de selecci�n de cuenta //
	public OnClickListener raeBtnChooseAccount = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			showGoogleAccountPicker();
		}
	};

	// Inicializaci�n del selector de cuenta //
	private void showGoogleAccountPicker() 
	{
		String[] accounts = new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE };
		Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null, accounts, true, null, null, null, null);
		startActivityForResult(googlePicker, PICK_ACCOUNT_REQUEST);
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) 
	{
		if (requestCode == PICK_ACCOUNT_REQUEST && resultCode == RESULT_OK) 
		{
			String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
			new getTokenAsync().execute(accountName);
			Log.d(MyRes.TAG, "Account Name=" + accountName);
		}
	}

	// Hilo que se encarga de realizar la conexi�n con el servidor //
	private class getTokenAsync extends AsyncTask<String, Void, String> 
	{
		private static final int REQUEST_AUTHORIZATION = 0;

		protected void onPreExecute() 
		{
			connectionProgress.show();
			connectionProgress.setContentView(R.layout.activity_account_select_custom_progress_dialog);
		}

		@Override
		protected String doInBackground(String... userEmail) 
		{
			MyRes.userEmail = (String) userEmail[0];
			String mScope = getString(R.string.oauth_scope);
			String token = "";
			String response = "";

			try 
			{
				// if this returns, the OAuth framework thinks the token should
				// be usable
				token = GoogleAuthUtil.getToken(AccountSelectActivity.this, MyRes.userEmail, mScope);

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
				startActivityForResult(userAuthEx.getIntent(), REQUEST_AUTHORIZATION);
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
				Log.d(MyRes.TAG, "Fatal Authorization Exception");
				response = "Fatal authorization exception: " + fatalAuthEx.getLocalizedMessage();
			}
			
			if (token != null) 
			{
				Log.i(MyRes.TAG, "Token: " + token);
				
				savedData.edit().putString("userEmail", userEmail[0]).commit();
				savedData.edit().putString("token", token).commit();
				
				HttpPost httppost = new HttpPost(getString(R.string.url));
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("mToken", token));
				
				// No se si deber�amos cambiar el nombre de la variable mEmail //
				nameValuePairs.add(new BasicNameValuePair("mEmail", MyRes.userEmail));
				
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
			return response;
		}

		protected void onPostExecute(String response) 
		{
			if (connectionProgress.isShowing())
				connectionProgress.dismiss();

			Log.i(MyRes.TAG, "Response: " + response);
			
			if (response.equals("OK")) 
			{
				Intent launchSetup = new Intent(AccountSelectActivity.this, SetupActivity.class);
				startActivity(launchSetup);
				AccountSelectActivity.this.finish();
			}
		}
	}
}

//
