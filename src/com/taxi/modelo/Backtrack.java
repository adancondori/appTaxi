package com.taxi.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;

import com.taxi.db.MySQLiteHelper;

public class Backtrack {
	/**
	 * Tabla dblbacktrack
	 */
	public Integer id = -1;
	public String latitud = "";
	public String longitud = "";
	public Date fecha = null;
	public String enviado = "";

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the latitud
	 */
	public String getLatitud() {
		return latitud;
	}

	/**
	 * @param latitud
	 *            the latitud to set
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	/**
	 * @return the longitud
	 */
	public String getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud
	 *            the longitud to set
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the enviado
	 */
	public String getEnviado() {
		return enviado;
	}

	/**
	 * @param enviado
	 *            the enviado to set
	 */
	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}

	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	public ContentValues getContentvalues() {
		ContentValues values = new ContentValues();
		if (id != -1) {
			values.put(MySQLiteHelper.id, this.id);
		}
		values.put(MySQLiteHelper.latitud, this.latitud);
		values.put(MySQLiteHelper.longitud, this.longitud);
		values.put(MySQLiteHelper.fecha, this.fecha.toString());
		values.put(MySQLiteHelper.enviado, this.enviado);
		return values;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
