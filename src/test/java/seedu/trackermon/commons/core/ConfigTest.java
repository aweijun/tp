package seedu.trackermon.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Preference file Location : preferences.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));
    }

    @Test
    public void hashMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.hashCode() == Objects.hash(defaultConfig.getLogLevel(),
                defaultConfig.getUserPrefsFilePath()));
    }

    @Test
    public void getUserPrefsFilePath_fromEditor_success() {
        Config defaultConfig = new Config();
        assertEquals(Paths.get("preferences.json"), defaultConfig.getUserPrefsFilePath());
    }

}
