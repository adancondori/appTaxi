package com.taxi.util;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCParams.Versions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Rpc_send {
	String URL = "http://192.168.43.21/server/recibirdato";

	public String envio_server(String latitud, String longitud, String time) {
		JSONRPCClient client = JSONRPCClient.create(URL, Versions.VERSION_2);
		// client.setConnectionTimeout(2000);
		// client.setSoTimeout(2000);
		String string = "";

		try {
			// item = client.callJSONObject("cf.getData", latitud, longitud,
			// time);
			JSONArray items = client.callJSONArray("cf.getData", latitud,
					longitud, time);
			JSONObject item = items.getJSONObject(0);
			string = string + item.getString("msg") + "\n\n";

		} catch (JSONRPCException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}

	private String getDataMethod(int numRows) {
		JSONRPCClient client = JSONRPCClient.create(URL, Versions.VERSION_2);
		// client.setConnectionTimeout(2000);
		// client.setSoTimeout(2000);
		String string = "";
		try {
			JSONArray items = client.callJSONArray("cf.getData", numRows);
			double beforeLoop = System.currentTimeMillis();
			for (int i = 0; i < items.length(); i++) {
				try {
					JSONObject item = items.getJSONObject(i);
					string = string + item.getString("number") + ". "
							+ item.getString("title") + " - "
							+ item.getString("datetime") + "\n\n";
				} catch (JSONException e) {
					Log.i("MainActivity", e.toString());
					// e.printStackTrace();
				}
			}
			double afterLoop = System.currentTimeMillis();
			Log.i("JSON", "Loop: " + Double.toString(afterLoop - beforeLoop));
		} catch (JSONRPCException e) {
			Log.i("MainActivity", e.toString());
			// e.printStackTrace();
		}
		return string;
	}

}
