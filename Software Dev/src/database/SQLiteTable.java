package database;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * Table implementation for the local database
 */
public class SQLiteTable implements SQLiteTableInterface {

	private static final String	QUERY_CREATE		= "CREATE TABLE IF NOT EXISTS";
	private static final String	QUERY_DROP			= "DROP TABLE IF EXISTS";
	private static final String	QUERY_END			= ";";
	private static final String	SPACE				= " ";
	private static final String	COLUMNS_BEGIN		= "(";
	private static final String	COLUMNS_END			= ")";
	private static final String	COLUMNS_SEPARATOR	= ",";

	/**
	 * Validates the name
	 * 
	 * @param name
	 *            from the string
	 */
	private static void validateName(final String name) {
		if (SQLiteVerificator.isNull(name)) { throw new NullPointerException(); }
		if (SQLiteVerificator.isEmpty(name)) { throw new IllegalArgumentException(); }
		SQLiteTable.validateNameSyntax(name);
	}

	/**
	 * Validates name syntax
	 * 
	 * @param name
	 *            from the string
	 */
	private static void validateNameSyntax(final String name) {
		final Pattern p = Pattern.compile("[a-z_]*");
		final Matcher m = p.matcher(name);
		if (!m.matches()) { throw new IllegalArgumentException(); }
	}

	private final List<SQLiteColumnInterface>	mColumns	= new ArrayList<SQLiteColumnInterface>();

	private String								mName		= null;

	private String								mConstraint	= "";

	/**
	 * @param name
	 *            sets Name
	 */
	public SQLiteTable(final String name) {
		this.setName(name);
	}

	/**
	 * Adds column
	 * 
	 * @param column
	 *            from column
	 */
	public void addColumn(final SQLiteColumnInterface column) {
		this.getColumns().add(column);
	}

	@Override
	public List<SQLiteColumnInterface> getColumns() {
		return this.mColumns;
	}

	@Override
	public String getConstraint() {
		return this.mConstraint;
	}

	@Override
	public String getCreateQuery() throws SQLiteEmptyTableException {

		if (this.getColumns().isEmpty()) { throw new SQLiteEmptyTableException(); }

		String query = "";

		query += SQLiteTable.QUERY_CREATE;
		query += SQLiteTable.SPACE;
		query += this.getName();

		query += SQLiteTable.COLUMNS_BEGIN;
		int i = 0;
		for (; i < (this.getColumns().size() - 1); ++i) {
			query += this.getColumns().get(i).getQueryPart();
			query += SQLiteTable.COLUMNS_SEPARATOR;
		}

		query += this.getColumns().get(i).getQueryPart();

		if (!SQLiteVerificator.nullOrEmpty(this.getConstraint())) {
			query += ",";
			query += this.getConstraint();
		}

		query += SQLiteTable.COLUMNS_END;
		query += SQLiteTable.QUERY_END;

		Log.d("query", query);

		return query;

	}

	@Override
	public String getDropQuery() {
		String query = "";
		query += SQLiteTable.QUERY_DROP;
		query += SQLiteTable.SPACE;
		query += this.getName();
		query += SQLiteTable.QUERY_END;
		return query;
	}

	@Override
	public String getName() {
		return this.mName;
	}

	/**
	 * Sets constraint
	 * 
	 * @param constraint
	 *            sets mConstraint
	 */
	public void setConstraint(final String constraint) {
		this.mConstraint = constraint;
	}

	/**
	 * @param name
	 *            sets mName
	 */
	private void setName(final String name) {
		SQLiteTable.validateName(name);
		this.mName = name;
	}
}
