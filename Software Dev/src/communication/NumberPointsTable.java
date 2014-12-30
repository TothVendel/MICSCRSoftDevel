package communication;

import database.SQLiteColumn;
import database.SQLiteTable;

/**
 * SQLite table for number of points
 * 
 */
public class NumberPointsTable extends SQLiteTable {
	public static final String	NAME	= "points";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_NUMBER = "numberpoints";
	
	/**
    * NumberPoints table
    *
    */
	public NumberPointsTable() {
		super(NumberPointsTable.NAME);
		this.addColumn(new SQLiteColumn(COLUMN_ID, "INTEGER", "PRIMARY KEY"));
		this.addColumn(new SQLiteColumn(COLUMN_NAME, "TEXT", "COLLATE BINARY NOT NULL"));
		this.addColumn(new SQLiteColumn(COLUMN_NUMBER, "INTEGER", "NOT NULL"));
	}

}
