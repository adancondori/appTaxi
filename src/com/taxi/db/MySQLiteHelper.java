package com.taxi.db;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	/**
	 * Tabla dblbacktrack
	 */
	public final static String id = "id";
	public final static String latitud = "latitud";
	public final static String longitud = "longitud";
	public final static String fecha = "fecha";
	public final static String enviado = "enviado";

	/**
	 * dbluser
	 */
	public final static String nombre = "nombre";
	public final static String nrocelular = "nrocelular";
	public final static String registrado = "registrado";
	public final static String codigoactivacion = "codigoactivacion";
	public final static String codigosms = "codigosms";
	public final static String trabajo = "trabajo";

	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String tbluser = "CREATE TABLE IF NOT EXISTS tbl_user(" + id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + nombre + " TEXT, "
				+ nrocelular + " TEXT, " + registrado + " TEXT, "
				+ codigoactivacion + " TEXT, " + codigosms + " TEXT, "
				+ trabajo + " TEXT );";
		String tblbacktrack = "CREATE TABLE IF NOT EXISTS tbl_backtrack(" + id
				+ " INTEGER PRIMARY KEY, " + latitud + " TEXT, " + longitud
				+ " TEXT, " + fecha + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
				+ enviado + " TEXT );";
		db.execSQL(tbluser);
		db.execSQL(tblbacktrack);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
