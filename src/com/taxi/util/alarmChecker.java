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

//				Toast.makeText(getApplicationContext(),
//						"Enviando Dato al servidor: " + message, Toast.LENGTH_SHORT)
//						.show();
				enviarJson();
			} else {
				message = "Verifique su gps: AlarmaCheker";
			}

		}
	};

	public boolean enviarJson() {
		Rpc_send rSend = new Rpc_send();
		String latitud = String.valueOf(GPS_Singleton.getInstance()
				.getLatitud());
		String longitud = String.valueOf(GPS_Singleton.getInstance()
				.getLongitud());
		String time = String.valueOf(Util.getdate(System.currentTimeMillis()));
		time = rSend.envio_server(latitud, longitud, time);
		Toast.makeText(getApplicationContext(), "rpc " + time,
				Toast.LENGTH_LONG).show();
		// MyHelper helper = new MyHelper();
		// Cursor cursor2 = helper.getObjects("select * from user ",
		// getApplicationContext());

		// String state1 = "";
		// String state2 = "";
		// if (cursor2 != null) {
		// cursor2.moveToFirst();
		// while (!cursor2.isAfterLast()) {
		// state1 = cursor2.getString(cursor2.getColumnIndex("login"));
		// state2 = cursor2.getString(cursor2.getColumnIndex("passw"));
		// cursor2.moveToNext();
		// }
		// }
		// cursor2.close();

		// try {
		// ParserFunction function;
		// function = new ParserFunction();
		// JSONObject object = function.EnviarLatitudLongitud(
		// String.valueOf(GPS_Singleton.getInstance().getLatitud()),
		// String.valueOf(GPS_Singleton.getInstance().getLongitud()));
		// if (object != null) {
		// JSONObject object2 = object.getJSONObject("gps");
		// // System.out.println(cad);
		// // String cadena[] = cad.split(":");
		// // System.out.println(cadena[0] + "--" + cadena[1]);
		// // if (Integer.valueOf(cadena[0]) > 0) {
		// // return true;
		// // }
		// String cad = object2.getString("msg");
		// Toast.makeText(getApplicationContext(), "Taxi: " + cad,
		// Toast.LENGTH_LONG).show();
		// }
		//
		// } catch (ClientProtocolException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
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
		// TODO Auto-generated method stub
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