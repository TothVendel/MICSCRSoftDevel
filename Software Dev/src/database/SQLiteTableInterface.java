package database;

import java.util.List;

/**
 * Abstract interface of SQLite Table
 */
public interface SQLiteTableInterface {

	public abstract List<SQLiteColumnInterface> getColumns();

	public abstract String getConstraint();

	public abstract String getCreateQuery() throws SQLiteEmptyTableException;

	public abstract String getDropQuery();

	public abstract String getName();

}
