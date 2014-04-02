package com.taxi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class Util {

	public static void turnGPSOn(Context context) {
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		context.sendBroadcast(intent);

		String provider = Settings.Secure.getString(
				context.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!provider.contains("gps")) {
			// if gps is disabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			context.sendBroadcast(poke);
		}
		// String provider = Settings.Secure.getString(
		// context.getContentResolver(),
		// Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		//
		// if (!provider.contains("gps")) { // if gps is disabled
		// final Intent poke = new Intent();
		// poke.setClassName("com.android.settings",
		// "com.android.settings.widget.SettingsAppWidgetProvider");
		// poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
		// poke.setData(Uri.parse("3"));
		// context.sendBroadcast(poke);
		// }
	}

	public static void turnGPSOff(final Context context) {
		String provider = Settings.Secure.getString(
				context.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (provider.contains("gps")) { // if gps is enabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			context.sendBroadcast(poke);
		}
		// String provider = Settings.Secure.getString(
		// context.getContentResolver(),
		// Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		//
		// if (provider.contains("gps")) { // if gps is enabled
		// final Intent poke = new Intent();
		// poke.setClassName("com.android.settings",
		// "com.android.settings.widget.SettingsAppWidgetProvider");
		// poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
		// poke.setData(Uri.parse("3"));
		// context.sendBroadcast(poke);
		//
		// }
	}

	public static String getdate(long now) {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm",
				new Locale("es_ES"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(now);
		Date date = calendar.getTime();
		return formatter.format(calendar.getTime());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
