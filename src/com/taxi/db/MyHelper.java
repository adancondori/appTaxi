package com.taxi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyHelper {

	private SQLiteDatabase bd = null;
	
	public void create(Context context){
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1)).getWritableDatabase();		
		bd.close();
	}
	
	public  void addObject(String Class, ContentValues values, Context context) {
		
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1)).getWritableDatabase();

		if((bd.insert(Class, null, values)) != -1)
		{
			//System.out.println(Class + " creado(a)");
		}
		else
		{
			//System.out.println("Error al intentar crear " + Class);
		}
		
		bd.close();
	}
	
	public  void deleteObject(String Class, int id, Context context) {
					
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1)).getWritableDatabase();
		if((bd.delete(Class, "id="+id, null)) != -1)
		{
			//System.out.println(Class + " eliminado(a)");
		}
		else
		{
			//System.out.println("Error al intentar eliminar " + Class);
		}
		
		bd.close();
	}
	
	public  void updateObject(String Class, int id, ContentValues values, Context context) {
		
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1)).getWritableDatabase();
		
		if((bd.update(Class, values, "cod="+id, null)) != -1)
		{
			//System.out.println(Class + " modificado(a)");
		}
		else
		{
			//System.out.println("Error al intentar modificar " + Class);
		}
		
		bd.close();
	}
	
	public Cursor getObject(String Class, int id, Context context) {
		
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1)).getWritableDatabase();
		Cursor C = bd.rawQuery("select * from "+ Class +" where cod=" + id, null);
		bd.close();
		return C;
	}
	
	public Cursor getObject(String Class, String campo, String valor, Context context) {
		
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1)).getReadableDatabase();
		Cursor C = bd.rawQuery("select * from "+ Class +" where "+ campo +"='"+ valor +"'", null);
		bd.close();
		return C;
	}
	
	public Cursor getObjects(String sql, Context context) {
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1)).getReadableDatabase();
		Cursor C = bd.rawQuery(sql, null);
		if(C!=null)
			System.out.println("getObjects   "+C.getColumnCount()+"  "+C.getCount());
		bd.close();
		return C;
	}
	
	public void execSQL(String sql, Context context) {
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1)).getWritableDatabase();
		bd.execSQL(sql);
		bd.close();
	}
	
	public void close() {
		this.bd.close();
	}
	

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount(String Class,Context context) {
		String countQuery = "SELECT  * FROM " + Class;
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1)).getWritableDatabase();
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
	public void resetTables(String Class,Context context) {
		this.bd = (new MySQLiteHelper(context, "dbtaxi", null, 1)).getWritableDatabase();
		// Delete All Rows
		bd.delete(Class, null, null);
		bd.close();
	}
	
}
