package simulation;

import java.awt.Color;

/**
 * TraitType 
 */
public enum TraitType {
    SPEED("Speed", new Color(255, 223, 0)),        // Yellow
    VISION("Vision", new Color(220, 53, 69)),      // Red
    RESILIENCE("Resilience", new Color(0, 123, 255)); // Blue

    private final String displayName;
    private final Color color;

    TraitType(String displayName, Color color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() { return displayName; }
    public Color getColor() { return color; }
}