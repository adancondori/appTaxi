package com.taxi.db;

import com.taxi.modelo.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyHelper {

	private SQLiteDatabase bd = null;
	private static final String TABLE_USER = "tbl_user";
	private static final String TABLE_BACKTRAC = "tbl_backtrack";

	public void create(Context context) {
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1))
				.getWritableDatabase();
		bd.close();
	}

	public void addObject(String Class, ContentValues values, Context context) {

		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1))
				.getWritableDatabase();

		if ((bd.insert(Class, null, values)) != -1) {
			// System.out.println(Class + " creado(a)");
		} else {
			// System.out.println("Error al intentar crear " + Class);
		}

		bd.close();
	}

	public void deleteObject(String Class, int id, Context context) {

		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1))
				.getWritableDatabase();
		if ((bd.delete(Class, "id=" + id, null)) != -1) {
			// System.out.println(Class + " eliminado(a)");
		} else {
			// System.out.println("Error al intentar eliminar " + Class);
		}

		bd.close();
	}

	public void updateObject(String Class, int id, ContentValues values,
			Context context) {

		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1))
				.getWritableDatabase();

		if ((bd.update(Class, values, "id=" + id, null)) != -1) {
			// System.out.println(Class + " modificado(a)");
		} else {
			// System.out.println("Error al intentar modificar " + Class);
		}

		bd.close();
	}

	public Cursor getObject(String Class, int id, Context context) {

		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1))
				.getWritableDatabase();
		Cursor C = bd.rawQuery("select * from " + Class + " where cod=" + id,
				null);
		bd.close();
		return C;
	}

	public Cursor getObject(String Class, String campo, String valor,
			Context context) {

		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1))
				.getReadableDatabase();
		Cursor C = bd.rawQuery("select * from " + Class + " where " + campo
				+ "='" + valor + "'", null);
		bd.close();
		return C;
	}

	public Cursor getObjects(String sql, Context context) {
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1))
				.getReadableDatabase();
		Cursor C = bd.rawQuery(sql, null);
		if (C != null)
			System.out.println("getObjects   " + C.getColumnCount() + "  "
					+ C.getCount());
		bd.close();
		return C;
	}

	public void execSQL(String sql, Context context) {
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1))
				.getWritableDatabase();
		bd.execSQL(sql);
		bd.close();
	}

	public void close() {
		this.bd.close();
	}

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount(String Class, Context context) {
		String countQuery = "SELECT  * FROM " + Class;
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1))
				.getWritableDatabase();
		Cursor cursor = bd.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		bd.close();
		cursor.close();
		// return row count
		return rowCount;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void resetTables(String Class, Context context) {
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1))
				.getWritableDatabase();
		// Delete All Rows
		bd.delete(Class, null, null);
		bd.close();
	}

	// *---------------- User
	public void user_addUser(Context context, ContentValues values) {
		resetTables(TABLE_USER, context);
		addObject(TABLE_USER, values, context);
	}

	public User user_is_activado(Context context) {
		String sql = "SELECT * FROM " + TABLE_USER + " LIMIT 1;";
		User user = new User();
		Cursor c = getObjects(sql, context);
		if (c != null && c.getCount() != -1) {
			c.moveToFirst();
			user.setId(c.getInt(c.getColumnIndex(User._id)));
			user.setCodigoactivacion(c.getString(c
					.getColumnIndex(User._codigoactivacion)));
			user.setCodigosms(c.getString(c.getColumnIndex(User._codigosms)));
			user.setNombre(c.getString(c.getColumnIndex(User._nombre)));
			user.setNrocelular(c.getString(c.getColumnIndex(User._nrocelular)));
			user.setRegistrado(c.getString(c.getColumnIndex(User._registrado)));
			user.setTrabajo(c.getString(c.getColumnIndex(User._trabajo)));
			c.close();
			return user;
		}
		return null;
	}

	public Boolean user_is_envio_solicitud(Context context) {
		String sql = "SELECT * FROM " + TABLE_USER + " ;";
		Cursor c = getObjects(sql, context);
		if (c != null && c.getCount() > 0 && c.getColumnCount() > 0) {
			return true;
		}
		return false;
	}

	public void user_update(int id, ContentValues values, Context context) {
		updateObject(TABLE_USER, id, values, context);
	}
}
