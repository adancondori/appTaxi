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

	//private String URL = "http://192.168.4.1/proyecto/moviltaxi.php";
	private String URL = "http://192.168.4.1/index/enviar";
	

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
		params.add(new BasicNameValuePair("nro", nro));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}

	public JSONObject EnviarLatitudLongitud(String latitud, String longitud)
			throws ClientProtocolException, IOException, JSONException {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "gps"));
		params.add(new BasicNameValuePair("latitud", latitud));
		params.add(new BasicNameValuePair("longitud", longitud));
		params.add(new BasicNameValuePair("time", Util.getdate(System
				.currentTimeMillis())));

		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}
	// public JSONObject sendMessage(String msg,String nombre)
	// throws ClientProtocolException, IOException, JSONException {
	// // Building Parameters
	// // String url="http://phantro.totalh.net/register.php";
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("tag", "msg"));
	// params.add(new BasicNameValuePair("nombre", nombre));
	// params.add(new BasicNameValuePair("mensaje", msg));
	// System.out.println(params.toString());
	// JSONObject json = jsonParser.getJSONFromUrl(ParserFunction.loginURL,
	// params);
	// return json;
	// }
	//
	// /**
	// * Function to logout user Reset Database
	// */
	// public boolean logoutUser(Context context) {
	// MyHelper helper = new MyHelper();
	// helper.resetTables("usuario", context);
	// return true;
	// }
	//
	// public JSONObject RegisterCliente(String nombre, String direccion,
	// String ci, String nit, String telefono, String descripcion,
	// String latitud, String logitud, String uv)
	// throws ClientProtocolException, IOException, JSONException {
	// // Building Parameters
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("tag", TAG_REGISTRAR));
	// params.add(new BasicNameValuePair(KEY_NOMBRE, nombre));
	// params.add(new BasicNameValuePair(KEY_DIRECCION, direccion));
	// params.add(new BasicNameValuePair(KEY_CI, ci));
	// params.add(new BasicNameValuePair(KEY_NIT, nit));
	// params.add(new BasicNameValuePair(KEY_TELEFONO, telefono));
	// params.add(new BasicNameValuePair(KEY_DESCRIPCION, descripcion));
	// params.add(new BasicNameValuePair(KEY_LATITUD, latitud));
	// params.add(new BasicNameValuePair(KEY_LONGITUD, logitud));
	// params.add(new BasicNameValuePair(KEY_UV, uv));
	//
	// // getting JSON Object
	// JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
	// return json;
	// }
	//
	// // /////////////////////
	// public JSONObject updateProducto() throws ClientProtocolException,
	// IOException, JSONException {
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("tag", TAG_UPDATEPRODUCTO));
	// JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
	// if (json == null) {
	// Log.e("JSON--- ", "---------null--Producto----------");
	// }
	// return json;
	// }
	//
	// public JSONObject updateCliente() throws ClientProtocolException,
	// IOException, JSONException {
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("tag", "listacliente"));
	// JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
	// if (json == null) {
	// Log.e("JSON--- ", "---------null cliente------------");
	// }
	// return json;
	// }
	//
	// public JSONObject updateNegocio() throws ClientProtocolException,
	// IOException, JSONException {
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("tag", "updatenegocio"));
	// JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
	// if (json == null) {
	// Log.e("JSON--- ",
	// "---------null ParserFunction updatene------------");
	// }
	// return json;
	// }
	//
	// // public JSONObject updateImage() throws ClientProtocolException,
	// // IOException, JSONException {
	// // List<NameValuePair> params = new ArrayList<NameValuePair>();
	// // params.add(new BasicNameValuePair("tag", "updateimagen"));
	// // JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
	// // if (json == null) {
	// // Log.e("JSON--- ",
	// // "---------null ParserFunction updatene------------");
	// // }
	// // return json;
	// // }
	// //
	// // public JSONObject loginBack(String cod) throws
	// ClientProtocolException,
	// // IOException, JSONException {
	// // List<NameValuePair> params = new ArrayList<NameValuePair>();
	// // params.add(new BasicNameValuePair("tag", TAG_LOGINBACK));
	// // params.add(new BasicNameValuePair("cod", cod));
	// // JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
	// // if (json == null) {
	// // Log.e("JSON--- ", "ParserFunction----null");
	// // }
	// // return json;
	// // }
	// //
	// // public JSONObject enviarPedido(ArrayList<Pedido> pedidos, String
	// codpromotor,
	// // String codnego) throws ClientProtocolException, IOException,
	// // JSONException {
	// // // Building Parameters
	// // List<NameValuePair> params = new ArrayList<NameValuePair>();
	// // /*
	// // * SimpleDateFormat dateFormat = new
	// // * SimpleDateFormat("dd-MM-yyyy ss:mm:HH"); Calendar cal = new
	// // * GregorianCalendar(); Date date = cal.getTime();
	// // * dateFormat.format(date);
	// // */
	// // Calendar cal = new GregorianCalendar();
	// // Date date = cal.getTime();
	// // SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	// // String formatteDate = df.format(date);
	// //
	// // params.add(new BasicNameValuePair("tag", "pedido"));
	// // params.add(new BasicNameValuePair("fecha", "08" + "/" + "15" + "/"
	// // + "2013"));
	// //
	// // params.add(new BasicNameValuePair("vendedor", codpromotor));//cod
	// promotor
	// // params.add(new BasicNameValuePair("negocio", codnego));//cod negocio
	// // params.add(new BasicNameValuePair("cantidad", String.valueOf(pedidos
	// // .size())));
	// //
	// // int i = 0;
	// // int total = 0;
	// // int l = 0;
	// // while (i < pedidos.size()) {
	// // int cant = Integer.valueOf(pedidos.get(i).getCantidad())
	// // * Integer.valueOf(pedidos.get(i).getPUnitario());
	// //
	// // params.add(new BasicNameValuePair(String.valueOf(l), String
	// // .valueOf(pedidos.get(i).getCod())));
	// // params.add(new BasicNameValuePair(String.valueOf(l + 1), "0"));
	// // params.add(new BasicNameValuePair(String.valueOf(l + 2), pedidos
	// // .get(i).getCantidad()));
	// // params.add(new BasicNameValuePair(String.valueOf(l + 3), String
	// // .valueOf(cant)));
	// // i++;
	// // l = l + 4;
	// // total = total + cant;
	// // }
	// // params.add(new BasicNameValuePair("total", String.valueOf(total)));
	// // System.out.println("RES:  " + params.toString());
	// // System.out.println("RES2:  " + pedidos.toString());
	// // // getting JSON Object
	// // JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
	// // return json;
	// // }
}
