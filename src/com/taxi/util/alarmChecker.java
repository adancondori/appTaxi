package com.taxi.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.taxi.apptaxi.MainActivity;
import com.taxi.apptaxi.PrincipalActivity;
import com.taxi.apptaxi.R;
import com.taxi.db.MyHelper;
import com.taxi.modelo.Backtrack;
import com.taxi.modelo.User;
import com.taxi.patron.GPS_Singleton;
import com.taxi.util.Rpc_send;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class alarmChecker extends Service implements Runnable {

	public static final int APP_ID_NOTIFICATION = 0;
	private NotificationManager mManager;
	private ParserFunction function = new ParserFunction();
	private boolean sw = true;

	/**
	 *
	 *
	 */
	public void run() {
		handler.sendEmptyMessage(0);
	}

	@Override
	public void onCreate() {
		mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG)
				.show();
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		// Toast.makeText(this, "MyAlarmService.onStart()",
		// Toast.LENGTH_LONG).show();

		// Creamos un hilo que obtendra la informaci—n de forma as’ncrona
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG)
				.show();
		return super.onUnbind(intent);
	}

	// *-----------------------HILO 2 PARA EL ENVIO----
	/**
	 * Procesa eventos desde el hilo run
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String message = "";
			if (GPS_Singleton.getInstance().getLatitud() != 0
					&& GPS_Singleton.getInstance().getLongitud() != 0) {
				message = "Ubicacion actual es:  Latitude = "
						+ GPS_Singleton.getInstance().getLatitud()
						+ ", Longitude = "
						+ GPS_Singleton.getInstance().getLongitud();

				// Toast.makeText(getApplicationContext(),
				// "Enviando Dato al servidor: " + message, Toast.LENGTH_SHORT)
				// .show();
				String latitud = String.valueOf(GPS_Singleton.getInstance()
						.getLatitud());
				String longitud = String.valueOf(GPS_Singleton.getInstance()
						.getLongitud());
				String estado = String.valueOf(GPS_Singleton.getInstance()
						.getEstado());
				Backtrack backtrack = new Backtrack();
				backtrack.setLatitud(latitud);
				backtrack.setLongitud(longitud);
				backtrack.setEnviado(estado);
				GPS_Singleton.getInstance().addbacktracks(backtrack);
				// *-------------
				if (GPS_Singleton.getInstance().getBacktracks() != null
						&& GPS_Singleton.getInstance().getBacktracks().size() > 0
						&& sw == true) {
					// enviar
					new TheTask().execute();
					sw = false;
				}

			} else {
				// Log("Verifique su gps: AlarmaCheker");
			}

		}
	};

	public String enviarJson() {
		if (function != null) {
			MyHelper liteHelper = new MyHelper();
			Backtrack backtrack;
			User user = liteHelper.user_is_activado(getApplicationContext());
			backtrack = GPS_Singleton.getInstance().getfirstbacktrack();
			if (backtrack != null) {
				String estado = String.valueOf(GPS_Singleton.getInstance()
						.getEstado());
				try {
					JSONObject object = function.EnviarLatitudLongitud(
							user.getNrocelular(), user.getCodigoactivacion(),
							backtrack.getLatitud(), backtrack.getLongitud(),
							estado);
					String success = object.getString("peticion");
					if (success.trim().equals("OK")) {
						System.out.println("Enviado latitud y longitud");
						GPS_Singleton.getInstance().getActivity()
								.cambiarEstado_ocupado();
						GPS_Singleton.getInstance().removefirstbacktrack();
						return "OK";
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return "NO";
		}
		// *-- ENVIO CON RPC-----------------
		// Rpc_send rSend = new Rpc_send();
		// String latitud = String.valueOf(GPS_Singleton.getInstance()
		// .getLatitud());
		// String longitud = String.valueOf(GPS_Singleton.getInstance()
		// .getLongitud());
		// String time =
		// String.valueOf(Util.getdate(System.currentTimeMillis()));
		// time = rSend.envio_server(latitud, longitud, time);
		// Toast.makeText(getApplicationContext(), "rpc " + time,
		// Toast.LENGTH_LONG).show();
		return "NO";
	}

	private class TheTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			screenOnOff(true);
		}

		@Override
		protected String doInBackground(String... arg0) {
			return enviarJson();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equalsIgnoreCase("OK")) {
				sw = true;
				// *----------------------------------------------
			} else {
			}
		}
	}

	private void screenOnOff(boolean blScreenOn) {
		if (blScreenOn)
			GPS_Singleton.getInstance().getActivity().getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		else
			GPS_Singleton.getInstance().getActivity().getWindow()
					.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
}