package communication;

import item.NumberPoints;

import java.util.ArrayList;
import java.util.List;

import database.SQLiteTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class SQLiteNumberPointsDataSource {
	private OpenHelper dbHelper;
	private SQLiteDatabase database;
	
	/**
    * SQLLite number points data source constructor, initializes the OpenHelper
    *
    * @param context
    *
    */
	public SQLiteNumberPointsDataSource (Context context) {
		dbHelper=new OpenHelper(context);
	}
	
	/**
    * Opens openhelper
    *
    */
	public void open () throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	/**
    * Closes openhelper
    *
    */
	public void close () {
		dbHelper.close();
	}
	
	/**
    * Deletes table from database
    *
    * @param table
    *      table to be deleted
    *      
    */
	public void deleteTable(SQLiteTable table) {
		this.dbHelper.flushTable(table);
	}
	
	/**
    * Creates number points
    *
    * @param name
    *      name of NumberPoints to be created
    *
    * @param number
    *      score of NumberPoints to be created
    *
    * @return numberPoints
    *      return the created NumberPoints  
    *      
    */
	public NumberPoints createNumberPoints(String name, int number) {
		ContentValues values = new ContentValues();
		values.put(NumberPointsTable.COLUMN_NUMBER, number);
		values.put(NumberPointsTable.COLUMN_NAME, name);
		long insertId = database.insert(NumberPointsTable.NAME, null, values);
		String [] allColumns = {NumberPointsTable.COLUMN_ID, NumberPointsTable.COLUMN_NAME, NumberPointsTable.COLUMN_NUMBER};
		Cursor cursor = database.query(NumberPointsTable.NAME, allColumns, NumberPointsTable.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		NumberPoints numberPoints = cursorToNumberPoints(cursor);
		return numberPoints;
	}
	
	/**
    * Cursor to number points
    *
    * @param cursor
    *      cursor to number points
    *
    * @return numberPoints
    *      return the number points being pointed
    *      
    */
	private NumberPoints cursorToNumberPoints(Cursor cursor) {
		NumberPoints numberPoints = new NumberPoints();
		numberPoints.setId(cursor.getLong(0));
		numberPoints.setName(cursor.getString(1));
		numberPoints.setNumberPoints(cursor.getInt(2));
		return numberPoints;
	}
	
	/**
    * Deletes a numberPoints entry
    *
    * @param numberPoints
    *      number points to be deleted
    *      
    */
	public void deleteNumberPoints(NumberPoints numberPoints) {
		long id = numberPoints.getId();
		database.delete(NumberPointsTable.NAME, NumberPointsTable.COLUMN_ID + " = " + id, null);
	}
	
	/**
    * Gets all the numberPoints entries
    *
    * @return numberPoints
    *      returns all number points
    *      
    */
	public List<NumberPoints> getAllNumberPoints() {
		String [] allColumns = {NumberPointsTable.COLUMN_ID, NumberPointsTable.COLUMN_NAME, NumberPointsTable.COLUMN_NUMBER};
		List <NumberPoints> numberPoints = new ArrayList<NumberPoints>();
		Cursor cursor = database.query(NumberPointsTable.NAME, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			NumberPoints numberPoint = cursorToNumberPoints(cursor);
			numberPoints.add(numberPoint);
			cursor.moveToNext();
		}
		cursor.close();
		return numberPoints;
	}
	
}

