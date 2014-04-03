package com.taxi.modelo;

import android.content.ContentValues;

import com.taxi.db.MySQLiteHelper;

public class User {
	/**
	 * dbluser
	 */
	public final static String _id = "id";
	public final static String _nombre = "nombre";
	public final static String _nrocelular = "nrocelular";
	public final static String _registrado = "registrado";
	public final static String _codigoactivacion = "codigoactivacion";
	public final static String _codigosms = "codigosms";
	public final static String _trabajo = "trabajo";
	/**
	 * dbluser
	 */
	public Integer id = -1;
	public String nombre = "appTaxi";
	public String nrocelular = "";
	public String registrado = "";
	public String codigoactivacion = "";
	public String codigosms = "";
	public String trabajo = "";

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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the nrocelular
	 */
	public String getNrocelular() {
		return nrocelular;
	}

	/**
	 * @param nrocelular
	 *            the nrocelular to set
	 */
	public void setNrocelular(String nrocelular) {
		this.nrocelular = nrocelular;
	}

	/**
	 * @return the registrado
	 */
	public String getRegistrado() {
		return registrado;
	}

	/**
	 * @param registrado
	 *            the registrado to set
	 */
	public void setRegistrado(String registrado) {
		this.registrado = registrado;
	}

	/**
	 * @return the codigoactivacion
	 */
	public String getCodigoactivacion() {
		return codigoactivacion;
	}

	/**
	 * @param codigoactivacion
	 *            the codigoactivacion to set
	 */
	public void setCodigoactivacion(String codigoactivacion) {
		this.codigoactivacion = codigoactivacion;
	}

	/**
	 * @return the codigosms
	 */
	public String getCodigosms() {
		return codigosms;
	}

	/**
	 * @param codigosms
	 *            the codigosms to set
	 */
	public void setCodigosms(String codigosms) {
		this.codigosms = codigosms;
	}

	/**
	 * @return the trabajo
	 */
	public String getTrabajo() {
		return trabajo;
	}

	/**
	 * @param trabajo
	 *            the trabajo to set
	 */
	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}

	public ContentValues getContentvalues() {
		ContentValues values = new ContentValues();
		if (id != -1) {
			values.put(MySQLiteHelper.id, this.id);
		}
		values.put(MySQLiteHelper.nombre, this.nombre);
		values.put(MySQLiteHelper.nrocelular, this.nrocelular);
		values.put(MySQLiteHelper.registrado, this.registrado);
		values.put(MySQLiteHelper.codigoactivacion, this.codigoactivacion);
		values.put(MySQLiteHelper.codigosms, this.codigosms);
		values.put(MySQLiteHelper.trabajo, this.trabajo);
		return values;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
