package com.taxi.apptaxi;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.taxi.controlador.Controller_User;
import com.taxi.db.MyHelper;
import com.taxi.db.MySQLiteHelper;
import com.taxi.modelo.User;
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
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class PrincipalActivity extends Activity {
	EditText textnro;
	TextView mensaje;
	Button buttonenviar;
	MyHelper liteHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		liteHelper = new MyHelper();
		// VERIFICA SI EXISTE EN LA BD
		IU_iniciar();
		if (liteHelper.user_is_envio_solicitud(getApplicationContext())) {
			// VERIFICA SI ESA ACTIVADO
			User user = liteHelper.user_is_activado(getApplicationContext());
			if (user.registrado.equals("OK")) {
				// SI ESTA ACTIVADO IR A LA INTERFAZ PRINCIPAL
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				// SI NO ESTA VOLVER A ENVIAR ACTIVACION
				ParserFunction function = new ParserFunction();
				JSONObject jsonObject;
				try {
					jsonObject = function.estaactivado(user.getNrocelular(),
							user.getCodigoactivacion());
					if (jsonObject != null) {
						String success = jsonObject.getString("success");
						if (success.toUpperCase().trim().equals("OK")) {
							user.setRegistrado("OK");
							liteHelper.user_update(user.getId(),
									user.getContentvalues(),
									getApplicationContext());
						} else {
							desabilitar();
						}
					} else {
						desabilitar();
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
		}

	}

	public void desabilitar() {
		textnro.setVisibility(View.INVISIBLE);
		mensaje.setText("Usted aun no esta activado por la central, por favor espere, esto puede demorar hasta 24 horas");
		buttonenviar.setVisibility(View.INVISIBLE);
	}

	private void IU_iniciar() {
		textnro = (EditText) findViewById(R.id.textnro);
		mensaje = (TextView) findViewById(R.id.txt_mensaje);
		buttonenviar = (Button) findViewById(R.id.buttonenviar);
		buttonenviar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Enviar_Web();
			}
		});
	}

	/**
	 * 
	 */
	public void Enviar_Web() {
		String tel = textnro.getText().toString();
		ParserFunction function = new ParserFunction();
		try {
			JSONObject object = function.EnviarNro(tel);
			if (object != null) {
				// JSONObject json_user = object.getJSONObject("user");
				String success = object.getString("success");
				String codigoactivacion = object.getString("codigoactivacion");
				String codigouser = object.getString("codigouser");

				if (success.toUpperCase().trim().equals("OK")) {
					User user = new User();
					user.setCodigoactivacion(codigoactivacion);
					user.setCodigosms(codigouser);
					user.setNrocelular(tel);
					user.setRegistrado("NO");
					user.setNombre("appTaxi:acc");
					user.setTrabajo("0");
					liteHelper.user_addUser(getApplicationContext(),
							user.getContentvalues());
					send_Sms(user.getCodigosms().trim());
					desabilitar();
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
		Toast.makeText(getApplicationContext(), "entro", Toast.LENGTH_SHORT)
				.show();
		// SmsManager sms = SmsManager.getDefault();
		// sms.sendTextMessage("73975405", null,
		// "appTaxi: codigo de activacion "
		// + cod, null, null);
	}
}
