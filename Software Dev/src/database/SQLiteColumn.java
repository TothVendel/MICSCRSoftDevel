package database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SQLiteColumn implements SQLiteColumnInterface {

	public static String	DATATYPE_NULL		= "NULL";
	public static String	DATATYPE_INTEGER	= "INTEGER";
	public static String	DATATYPE_REAL		= "REAL";
	public static String	DATATYPE_TEXT		= "TEXT";

	/**
	 * Validates the name
	 * 
	 * @param name
	 *            from the string
	 */
	private static void validateName(final String name) {
		if (SQLiteVerificator.isNull(name)) { throw new NullPointerException(); }
		if (SQLiteVerificator.isEmpty(name)) { throw new IllegalArgumentException(); }
		SQLiteColumn.validateNameSyntax(name);
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

	/**
	 * Validates the type
	 * 
	 * @param type
	 *            from the string
	 */
	private static void validateType(final String type) {
		if (SQLiteVerificator.isNull(type)) { throw new NullPointerException(); }
		if (SQLiteVerificator.isEmpty(type)) { throw new IllegalArgumentException(); }
		SQLiteColumn.validateTypeSyntax(type);
	}

	/**
	 * Validates type syntax
	 * 
	 * @param type
	 *            from the string
	 */
	private static void validateTypeSyntax(final String type) {
		if (type.equals(SQLiteColumn.DATATYPE_NULL)) {
			return;
		} else if (type.equals(SQLiteColumn.DATATYPE_INTEGER)) {
			return;
		} else if (type.equals(SQLiteColumn.DATATYPE_REAL)) {
			return;
		} else if (type.equals(SQLiteColumn.DATATYPE_TEXT)) {
			return;
		}
		else {
			throw new IllegalArgumentException();
		}

	}

	private String	mName	= null;
	private String	mSpec	= "";
	private String	mType	= null;

	/**
	 * @param name
	 *            sets Name
	 * @param type
	 *            sets Type
	 */
	public SQLiteColumn(final String name, final String type) throws IllegalArgumentException {
		this.setName(name);
		this.setType(type);

	}

	/**
	 * @param name
	 *            sets Name
	 * @param type
	 *            sets Type
	 * @param spec
	 *            sets Spec
	 */
	public SQLiteColumn(final String name, final String type, final String spec) throws IllegalArgumentException {
		this.setName(name);
		this.setType(type);
		this.setSpec(spec);
	}

	@Override
	public String getName() {
		return this.mName;
	}

	@Override
	public String getQueryPart() {
		return this.getName() + " " + this.getType() + " " + this.getSpec();
	}

	@Override
	public String getSpec() {
		return this.mSpec;
	}

	@Override
	public String getType() {
		return this.mType;
	}

	/**
	 * @param name
	 *            sets Name
	 */
	private void setName(final String name) {
		SQLiteColumn.validateName(name);
		this.mName = name.toLowerCase();
	}

	/**
	 * @param spec
	 *            sets spec
	 */
	private void setSpec(final String spec) {
		this.mSpec = spec.toUpperCase();
	}

	/**
	 * @param type
	 *            sets Type
	 */
	private void setType(final String type) {
		SQLiteColumn.validateType(type);
		this.mType = type.toUpperCase();
	}

}
