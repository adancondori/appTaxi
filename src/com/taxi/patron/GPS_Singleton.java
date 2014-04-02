package com.taxi.patron;

public class GPS_Singleton {

	private static GPS_Singleton instance = new GPS_Singleton();
	private double latitud = 0.0;
	private double longitud = 0.0;

	private GPS_Singleton() {

	}

	public static GPS_Singleton getInstance() {
		return instance;
	}

	public static void setInstance(GPS_Singleton instance) {
		GPS_Singleton.instance = instance;
	}

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

}
