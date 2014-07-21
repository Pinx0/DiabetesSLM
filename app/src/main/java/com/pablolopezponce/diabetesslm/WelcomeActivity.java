package com.pablolopezponce.diabetesslm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.pablolopezponce.diabetesslm.resources.MyRes;

public class WelcomeActivity extends Activity 
{
	public Button btnNext;

	public TextView textViewTitle, textView1, textView2;
	
	public Dialog dialogTOS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Eliminamos el título de la actividad //
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_welcome);

		initializeVariables();
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		
		if(dialogTOS.isShowing())
		{
			dialogTOS.dismiss();
		}
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
	}

	public void initializeVariables() 
	{
		// Text View - Título //
		textViewTitle = (TextView) findViewById(R.id.welcome_text_view_title);
		textViewTitle.setTypeface(MyRes.lightTypeface);

		// Text View - Texto de contenido 1 //
		textView1 = (TextView) findViewById(R.id.welcome_text_view_1);
		textView1.setTypeface(MyRes.lightTypeface);

		// Text View - Texto de contenido 1 //
		textView2 = (TextView) findViewById(R.id.welcome_text_view_2);
		textView2.setTypeface(MyRes.lightTypeface);

		// Button - Botín de siguiente //
		btnNext = (Button) findViewById(R.id.welcome_button_next);
		btnNext.setTypeface(MyRes.regularTypeface);
		btnNext.setOnClickListener(raeBtnNext);

        // Dialog - TOS //
        dialogTOS = new Dialog(WelcomeActivity.this);
	}

	public OnClickListener raeBtnNext = new OnClickListener() {
		@Override
		public void onClick(View v) 
		{

            // Dialog - TOS //
            dialogTOS = new Dialog(WelcomeActivity.this);

			// Eliminamos el título del dialog //
			dialogTOS.requestWindowFeature(Window.FEATURE_NO_TITLE);

			dialogTOS.setContentView(R.layout.activity_welcome_tos);
			
			// Text View - Título TOS //
			TextView dialogTOSTextViewTitle = (TextView)dialogTOS.findViewById(R.id.welcome_tos_text_view_title);
			dialogTOSTextViewTitle.setTypeface(MyRes.lightTypeface);
			
			// Text View - Texto de contenido 1 //
			TextView dialogTOSTextView1 = (TextView)dialogTOS.findViewById(R.id.welcome_tos_text_view_1);
			dialogTOSTextView1.setTypeface(MyRes.lightTypeface);

			// Button - No aceptar los términos de servicio //
			Button dialogTOSButtonDecline = (Button) dialogTOS.findViewById(R.id.welcome_tos_button_decline);
			dialogTOSButtonDecline.setTypeface(MyRes.regularTypeface);
			dialogTOSButtonDecline.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v)
				{
					dialogTOS.hide();
				}
			});

			// Button - Aceptar los tírminos de servicio//
			Button dialogTOSButtonAccept = (Button) dialogTOS.findViewById(R.id.welcome_tos_button_accept);
			dialogTOSButtonAccept.setTypeface(MyRes.regularTypeface);
			dialogTOSButtonAccept.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					Intent startAccountSelectActivity = new Intent(WelcomeActivity.this, AccountSelectActivity.class);
					startActivity(startAccountSelectActivity);
					WelcomeActivity.this.finish();
				}
			});

			dialogTOS.show();
		}
	};
}
