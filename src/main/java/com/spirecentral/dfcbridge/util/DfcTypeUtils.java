package com.spirecentral.dfcbridge.util;

/**
 * Utility class for DFC type conversions.
 */
public final class DfcTypeUtils {

    private DfcTypeUtils() {
        // Utility class
    }

    /**
     * Converts a DFC data type constant to its string representation.
     *
     * @param dataType the DFC data type constant
     * @return the string representation
     */
    public static String dataTypeToString(int dataType) {
        switch (dataType) {
            case 0: return "BOOLEAN";
            case 1: return "INTEGER";
            case 2: return "STRING";
            case 3: return "ID";
            case 4: return "TIME";
            case 5: return "DOUBLE";
            default: return "UNDEFINED";
        }
    }

    /**
     * Converts a DFC permission level to its label.
     *
     * @param permit the DFC permission level
     * @return the permission label
     */
    public static String permitToLabel(int permit) {
        switch (permit) {
            case 1: return "NONE";
            case 2: return "BROWSE";
            case 3: return "READ";
            case 4: return "RELATE";
            case 5: return "VERSION";
            case 6: return "WRITE";
            case 7: return "DELETE";
            default: return "UNKNOWN";
        }
    }

    /**
     * Sanitizes a string for use in DQL queries by escaping single quotes.
     *
     * @param input the input string to sanitize
     * @return the sanitized string safe for DQL
     */
    public static String sanitizeDqlString(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("'", "''");
    }
}
