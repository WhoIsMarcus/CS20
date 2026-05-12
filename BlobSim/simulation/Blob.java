package simulation;

import java.util.UUID;

/**
 * Blob - Represents an individual agent in the simulation
 */
public class Blob {
    private int x;
    private int y;
    private TraitType traitType;
    private float traitStrength; // 0.0 to 1.0
    private int foodCollectedThisDay;
    private String id;

    public Blob(int x, int y, TraitType traitType, float traitStrength) {
        this.x = x;
        this.y = y;
        this.traitType = traitType;
        this.traitStrength = Math.max(0.1f, Math.min(1.0f, traitStrength));
        this.foodCollectedThisDay = 0;
        this.id = UUID.randomUUID().toString();
    }

    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public void addFood() {
        this.foodCollectedThisDay++;
    }

    public void resetFoodCounter() {
        this.foodCollectedThisDay = 0;
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public TraitType getTraitType() { return traitType; }
    public float getTraitStrength() { return traitStrength; }
    public int getFoodCollectedThisDay() { return foodCollectedThisDay; }
    public String getId() { return id; }

    // For movement bonuses
    public int getSpeedBonus() {
        return traitType == TraitType.SPEED ? Math.round(traitStrength * 10) : 0;
    }

    public int getVisionBonus() {
        return traitType == TraitType.VISION ? Math.round(traitStrength * 8) : 0;
    }

    public float getResilienceBonus() {
        return traitType == TraitType.RESILIENCE ? traitStrength : 0;
    }
}