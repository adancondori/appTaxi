package com.taxi.apptaxi;

import java.util.Calendar;

import com.taxi.gps.GPSManager;
import com.taxi.util.Util;
import com.taxi.util.alarmChecker;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	LocationManager handle; // Gestor del servicio de localización BotonActivar
	private boolean servicioActivo;

	private Button botonActivar;
	private static PendingIntent pendingIntent;
	private int _tiempo_rango = 1000 * 10;
	private GPSManager gps = new GPSManager(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getContentResolver();
		if (pendingIntent == null) {
			ActivarAlarma();
		}
		IU_compoentes();
		gps.start();
	}

	/**
	 * El botón activar permitirá activar y desactivar el servicio.
	 */
	public void IU_compoentes() {
		botonActivar = (Button) findViewById(R.id.BotonActivar);

		servicioActivo = false;

		botonActivar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (servicioActivo) {
					servicioActivo = !servicioActivo;
					botonActivar.setBackgroundResource(R.color._verde);
					botonActivar.setText("Libre");
				} else {
					servicioActivo = !servicioActivo;
					botonActivar.setBackgroundResource(R.color._red);
					botonActivar.setText("Ocupado");
				}
			}
		});
	}

	/**
	 * Desactiva o activa la alarma, estableciendola al estado contrario del
	 * actual
	 */
	private void CambiarEstadoAlarma() {
		if (pendingIntent == null) {
			// La alarma est‡ desactivada, la activamos
			ActivarAlarma();
			// tvalarma.setText("Alarma Activada");
			// buttoniniciar.setText("Detener");
		} else {
			// La alarma est‡ activada, la desactivamos
			DesactivarAlarma();
			// buttoniniciar.setText("Iniciar");
			// tvalarma.setText("Alarma Desactivada");
		}
	}

	/**
	 * Desactiva la alarma
	 */
	private void DesactivarAlarma() {
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);

		pendingIntent = null;

		Toast.makeText(MainActivity.this, "Usted Salio del Sistema. AppTaxi",
				Toast.LENGTH_LONG).show();

	}

	/**
	 * Activa la alarma
	 */
	private void ActivarAlarma() {

		Intent myIntent = new Intent(MainActivity.this, alarmChecker.class);
		pendingIntent = PendingIntent.getService(MainActivity.this, 0,
				myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 10);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), _tiempo_rango, pendingIntent);

		Toast.makeText(MainActivity.this,
				"Bienvenido a AppTaxi. \nSistema Iniciado", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (pendingIntent != null) {

		}
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		DesactivarAlarma();
		System.out.println("App Detenida :MainActivity");
		super.onStop();
	}

	static final String STATE_SCORE = "Pendingintent";

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
