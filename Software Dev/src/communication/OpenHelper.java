package communication;

import database.SQLiteEmptyTableException;
import database.SQLiteTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OpenHelper extends SQLiteOpenHelper {

	private static final String		DATABASE_NAME				= "Database";
	private static final int		DATABASE_VERSION			= 1;
	
	public static final SQLiteTable	TABLE_NUMBER_POINTS				= new NumberPointsTable();

	public OpenHelper(final Context context) {
		super(context, OpenHelper.DATABASE_NAME, null, OpenHelper.DATABASE_VERSION);
	}

	/**
	 * Creates the local db
	 * 
	 * @param db
	 * @throws SQLiteEmptyTableException
	 */
	public void create(final SQLiteDatabase db) throws SQLiteEmptyTableException {
		db.execSQL(OpenHelper.TABLE_NUMBER_POINTS.getCreateQuery());
	}

	/**
	 * Empties user table
	 */
	public void flushTable(final SQLiteTable table) {
		final SQLiteDatabase db = this.getWritableDatabase();
		db.delete(table.getName(), null, null);
		db.close();
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		try {
			this.create(db);
		} catch (final SQLiteEmptyTableException e) {
			Log.e("SQLiteEmptyTableException", e.getStackTrace().toString());
		}

	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		if (oldVersion < newVersion) {
			db.execSQL(OpenHelper.TABLE_NUMBER_POINTS.getDropQuery());
		}
		try {
			this.create(db);
		} catch (final SQLiteEmptyTableException e) {
			Log.e("SQLiteEmptyTableException", e.getStackTrace().toString());
		}

	}

}
