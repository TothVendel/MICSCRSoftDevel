package database;

public class SQLiteVerificator {

	/**
	 * Checks if function argument string is empty
	 * 
	 * @param argument
	 */
	public static boolean isEmpty(final String argument) {
		return argument.equals("");
	}

	/**
	 * Checks if function argument string is null
	 * 
	 * @param argument
	 */
	public static boolean isNull(final String argument) {
		return argument == null;
	}

	/**
	 * Checks if function argument string is null or empty
	 * 
	 * @param argument
	 */
	public static boolean nullOrEmpty(final String argument) {
		return SQLiteVerificator.isNull(argument) || SQLiteVerificator.isEmpty(argument);
	}

}
