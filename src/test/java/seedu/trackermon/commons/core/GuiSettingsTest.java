package seedu.trackermon.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import java.util.Objects;

import org.junit.jupiter.api.Test;

class GuiSettingsTest {

    @Test
    void equalsMethod() {
        GuiSettings defaultGuiSettings = new GuiSettings();
        assertNotNull(defaultGuiSettings);
        assertTrue(defaultGuiSettings.equals(defaultGuiSettings));
    }

    @Test
    public void hashMethod() {
        GuiSettings defaultGuiSettings = new GuiSettings();
        assertNotNull(defaultGuiSettings);
        assertTrue(defaultGuiSettings.hashCode() == Objects.hash(defaultGuiSettings.getWindowWidth(),
                defaultGuiSettings.getWindowHeight(), defaultGuiSettings.getWindowCoordinates()));

        double windowWidth = 1080;
        double windowHeight = 720;
        int xPos = 0;
        int yPos = 0;
        Point windowCoordinates = new Point(xPos, yPos);
        GuiSettings customGuiSettings = new GuiSettings(windowWidth, windowHeight, xPos, yPos);
        assertTrue(customGuiSettings.hashCode() == Objects.hash(windowWidth, windowHeight, windowCoordinates));
    }
    @Test
    void testToString() {
        String defaultGuiSettingsAsString = "Width : 740.0\n" + "Height : 600.0\n"
                + "Position : null";
        assertEquals(defaultGuiSettingsAsString, new GuiSettings().toString());
    }
}
