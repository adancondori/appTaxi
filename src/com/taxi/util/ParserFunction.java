package com.taxi.util;

import android.content.Context;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author ADAM
 */
public class ParserFunction {

	private JSONParser jsonParser;

	// private String URL = "http://192.168.4.1/proyecto/moviltaxi.php";
	// private String URL = "http://192.168.4.1/index/enviar";
	private String URL = "http://www.taxidirector.com/movil.php";

	/**
     *
     */

	public ParserFunction() {
		// TODO Auto-generated constructor stub
		jsonParser = new JSONParser();
	}

	/**
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @param uRL
	 *            the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 * 
	 * @param nro
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONObject EnviarNro(String nro) throws ClientProtocolException,
			IOException, JSONException {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "registrar"));
		params.add(new BasicNameValuePair("tel", nro));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}

	public JSONObject EnviarLatitudLongitud(String tel,
			String codigoactivacion, String latitud, String longitud,
			String estado) throws ClientProtocolException, IOException,
			JSONException {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "envioposicion"));
		params.add(new BasicNameValuePair("tel", tel));
		params.add(new BasicNameValuePair("codigoactivacion", codigoactivacion));
		params.add(new BasicNameValuePair("lat", latitud));
		params.add(new BasicNameValuePair("long", longitud));
		params.add(new BasicNameValuePair("tmp", Util.getdate(System
				.currentTimeMillis())));
		params.add(new BasicNameValuePair("estado", estado));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}

	public JSONObject estaactivado(String tel, String codigoactivacion)
			throws ClientProtocolException, IOException, JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "estaactivado"));
		params.add(new BasicNameValuePair("tel", tel));
		params.add(new BasicNameValuePair("codigoactivacion", codigoactivacion));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}

	public JSONObject cambioestado(String tel, String codigoactivacion,
			String estado) throws ClientProtocolException, IOException,
			JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "cambioestado"));
		params.add(new BasicNameValuePair("tel", tel));
		params.add(new BasicNameValuePair("codigoactivacion", codigoactivacion));
		params.add(new BasicNameValuePair("estado", estado));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}

}
