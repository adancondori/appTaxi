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
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class alarmChecker extends Service implements Runnable {

	public static final int APP_ID_NOTIFICATION = 0;
	private NotificationManager mManager;
	private ParserFunction function = new ParserFunction();

	/**
	 *
	 *
	 */
	public void run() {
		handler.sendEmptyMessage(0);
	}

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

				enviarJson();
			} else {
				message = "Verifique su gps: AlarmaCheker";
			}

		}
	};

	public boolean enviarJson() {
		if (function != null) {
			MyHelper liteHelper = new MyHelper();
			User user = liteHelper.user_is_activado(getApplicationContext());
			String latitud = String.valueOf(GPS_Singleton.getInstance()
					.getLatitud());
			String longitud = String.valueOf(GPS_Singleton.getInstance()
					.getLongitud());
			String estado = String.valueOf(GPS_Singleton.getInstance()
					.getEstado());
			try {
				JSONObject object = function.EnviarLatitudLongitud(
						user.getNrocelular(), user.getCodigoactivacion(),
						latitud, longitud, estado);
				String success = object.getString("peticion");
				if (success.trim().equals("OK")) {
					Toast.makeText(getApplicationContext(),
							"Enviado latitud y longitud", Toast.LENGTH_SHORT)
							.show();
					// recibo OK
					GPS_Singleton.getInstance().getActivity()
							.cambiarEstado_ocupado();
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
		return false;
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

}