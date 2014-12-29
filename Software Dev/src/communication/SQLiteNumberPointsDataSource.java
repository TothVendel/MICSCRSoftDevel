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
	
	public SQLiteNumberPointsDataSource (Context context) {
		dbHelper=new OpenHelper(context);
	}
	 
	public void open () throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close () {
		dbHelper.close();
	}
	
	public void deleteTable(SQLiteTable table) {
		this.dbHelper.flushTable(table);
	}
	
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
	
	private NumberPoints cursorToNumberPoints(Cursor cursor) {
		NumberPoints numberPoints = new NumberPoints();
		numberPoints.setId(cursor.getLong(0));
		numberPoints.setName(cursor.getString(1));
		numberPoints.setNumberPoints(cursor.getInt(2));
		return numberPoints;
	}
	
	public void deleteNumberPoints(NumberPoints numberPoints) {
		long id = numberPoints.getId();
		database.delete(NumberPointsTable.NAME, NumberPointsTable.COLUMN_ID + " = " + id, null);
	}
	
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

