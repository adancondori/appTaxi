package com.taxi.patron;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.taxi.apptaxi.MainActivity;
import com.taxi.apptaxi.PrincipalActivity;
import com.taxi.modelo.Backtrack;

public class GPS_Singleton {
	// *--------------VARIABLES
	private static GPS_Singleton instance = new GPS_Singleton();
	private double latitud = 0.0;
	private double longitud = 0.0;
	private int estado = ESTADO_LIBRE;

	public static int ESTADO_LIBRE = 0;
	public static int ESTADO_OCUPADO = 1;
	public static int ESTADO_FUERA_DE_SERVICIO = 2;
	private MainActivity activity;
	public ArrayList<Backtrack> backtracks;

	private GPS_Singleton() {

	}

	public static GPS_Singleton getInstance() {
		return instance;
	}

	public static void setInstance(GPS_Singleton instance) {
		GPS_Singleton.instance = instance;
	}

	// *--------METODOS-----------------
	/**
	 * @return the latitud
	 */
	public double getLatitud() {
		return latitud;
	}

	/**
	 * @param latitud
	 *            the latitud to set
	 */
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	/**
	 * @return the longitud
	 */
	public double getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud
	 *            the longitud to set
	 */
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return the activity
	 */
	public MainActivity getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(MainActivity activity) {
		this.activity = activity;
	}

	/**
	 * @return the backtracks
	 */
	public ArrayList<Backtrack> getBacktracks() {
		return backtracks;
	}

	/**
	 * @param backtracks
	 *            the backtracks to set
	 */
	public void setBacktracks(ArrayList<Backtrack> backtracks) {
		this.backtracks = backtracks;
	}

	public void addbacktracks(Backtrack backtrack) {
		synchronized (backtracks) {
			if (backtracks != null) {
				backtracks.add(backtrack);
			} else {
				backtracks = new ArrayList<Backtrack>();
				backtracks.add(backtrack);
			}
		}
	}

	public void removefirstbacktrack() {
		synchronized (backtracks) {
			if (backtracks != null && backtracks.size() > 0) {
				backtracks.remove(0);
			}
		}
	}

	public Backtrack getfirstbacktrack() {
		synchronized (backtracks) {
			if (backtracks != null && backtracks.size() > 0) {
				return backtracks.get(0);
			}
		}
		return null;
	}
}
