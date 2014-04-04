package com.taxi.apptaxi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.taxi.controlador.Controller_User;
import com.taxi.db.MyHelper;
import com.taxi.db.MySQLiteHelper;
import com.taxi.modelo.User;
import com.taxi.util.ParserFunction;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
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
	public final static int _ENVIAR_ACTIVACION = 0;
	public final static int _VERIFICAR_ACTIVACION = 1;
	public static int _TIPO_ACTIVACION = _ENVIAR_ACTIVACION;
	public User user = new User();

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
			user = liteHelper.user_is_activado(getApplicationContext());
			if (user.registrado.equals("OK")) {
				// SI ESTA ACTIVADO IR A LA INTERFAZ PRINCIPAL
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				_TIPO_ACTIVACION = _VERIFICAR_ACTIVACION;
				new TheTask().execute();
			}
		}

	}

	public String Verificar_Avtivacion_Usuario() {
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
							user.getContentvalues(), getApplicationContext());
					Intent intent = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					// desabilitar();
					return "NO";
				}
				return "OK";
			} else {
				// desabilitar();
				return "NO";
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
		return "NO";
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
				_TIPO_ACTIVACION = _ENVIAR_ACTIVACION;
				new TheTask().execute();
			}
		});
	}

	/**
	 * 
	 */
	public String Enviar_Web() {
		String tel = textnro.getText().toString();
		ParserFunction function = new ParserFunction();
		try {
			JSONObject object = function.EnviarNro(tel);
			if (object != null) {
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
					// desabilitar();
					return "OK";
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
		return "NO";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	public void send_Sms(String cod) {
		// Toast.makeText(getApplicationContext(), "entro", Toast.LENGTH_SHORT)
		// .show();
		// SmsManager sms = SmsManager.getDefault();
		// sms.sendTextMessage("73975405", null,
		// "appTaxi: codigo de activacion "
		// + cod, null, null);
	}

	// *******************************************************************
	private void screenOnOff(boolean blScreenOn) {
		if (blScreenOn)
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		else
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private class TheTask extends AsyncTask<String, Void, String> {

		public ProgressDialog progressDialog = new ProgressDialog(
				PrincipalActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Enviando Datos al servidor...");
			progressDialog.show();
			progressDialog.setCancelable(false);
			screenOnOff(true);
		}

		@Override
		protected String doInBackground(String... arg0) {
			// Enviar_Web();
			if (_TIPO_ACTIVACION == _ENVIAR_ACTIVACION) {
				return Enviar_Web();
			} else {
				return Verificar_Avtivacion_Usuario();
			}

			// return "true";
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equalsIgnoreCase("OK")) {

				progressDialog.dismiss();
				// *----------------------------------------------
				if (_TIPO_ACTIVACION == _VERIFICAR_ACTIVACION) {
					desabilitar();
					finish();
				} else {
					desabilitar();
				}

			} else {
				desabilitar();
				progressDialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						PrincipalActivity.this);
				builder.setMessage(
						"Su conexión a internet demora demasiado, revise su conexión.")
						.setTitle("Advertencia")
						.setCancelable(false)
						.setNeutralButton("Aceptar",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
	}

}
