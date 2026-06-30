package com.framework.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class GenericUtil {
    private GenericUtil() {
    }

    public static String uniqueText(String prefix) {
        return prefix + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static String futureDateTimeLocal(int daysToAdd) {
        return LocalDateTime.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public static String pastDateTimeLocal(int daysToSubtract) {
        return LocalDateTime.now().minusDays(daysToSubtract).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    /**
     * Returns a past deadline as a space-separated "dd-MM-yyyy HH:mm" string
     * (date and time properly separated by a space), suitable to be normalized
     * through {@link #formatForDateTimeLocal(String)} before entering it into a
     * datetime-local input.
     */
    public static String pastDeadlineSpaced(int daysToSubtract) {
        return LocalDateTime.now().minusDays(daysToSubtract).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    /**
     * Normalizes a deadline value coming in different date/time formats (with the
     * date and time separated by a space or a 'T') into the HTML datetime-local
     * format "yyyy-MM-dd'T'HH:mm" so the date and time segments are properly
     * separated for the deadline input.
     */
    public static String formatForDateTimeLocal(String deadline) {
        String normalized = deadline == null ? "" : deadline.trim().replaceAll("\\s+", " ");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        for (String pattern : new String[]{
                "dd-MM-yyyy HH:mm",
                "d-M-yyyy H:mm",
                "yyyy-MM-dd'T'HH:mm",
                "yyyy-MM-dd HH:mm",
                "dd/MM/yyyy HH:mm",
                "MM/dd/yyyy HH:mm"}) {
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDateTime.parse(normalized, inputFormatter).format(outputFormatter);
            } catch (Exception ignored) {
                // Try the next supported pattern.
            }
        }

        // Fall back to replacing the date/time space separator with 'T'.
        return normalized.replace(" ", "T");
    }
}
