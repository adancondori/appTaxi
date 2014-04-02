package com.taxi.apptaxi;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.taxi.controlador.Controller_User;
import com.taxi.util.ParserFunction;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class PrincipalActivity extends Activity {
	EditText textnro;
	Button buttonenviar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		IU_iniciar();

	}

	private void IU_iniciar() {
		textnro = (EditText) findViewById(R.id.textnro);
		buttonenviar = (Button) findViewById(R.id.buttonenviar);
		buttonenviar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Enviar_Web();
				// Intent intent = new Intent(getApplicationContext(),
				// MainActivity.class);
				// startActivity(intent);
				// finish();
			}
		});
	}

	/**
	 * 
	 * @return
	 */
	public boolean isActivado() {
		Controller_User user = new Controller_User();

		return true;
	}

	/**
	 * 
	 */
	public void Enviar_Web() {
		ParserFunction function = new ParserFunction();
		try {
			JSONObject object = function
					.EnviarNro(textnro.getText().toString());
			if (object != null) {
				// JSONObject json_user = object.getJSONObject("user");
				String success = object.getString("success");
				String codigoactivacion = object.getString("codigoactivacion");
				String codigouser = object.getString("codigouser");

				if (success.toUpperCase().trim().equals("OK")) {
					send_Sms(codigouser);
					if (isActivado()) {
						Intent intent = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(intent);
						finish();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"No se recivio mensaje ", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"No se recivio mensaje ", Toast.LENGTH_LONG).show();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	public void send_Sms(String cod) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage("73975405", null, "appTaxi: codigo de activacion "
				+ cod, null, null);
	}
}
