package simulation;

import java.util.*;

/**
 * FoodSystem - Manages food spawning and tracking
 */
public class FoodSystem {
    private List<int[]> foodPositions; // [x, y] pairs
    private WorldGrid world;
    private float foodDensity;

    public FoodSystem(WorldGrid world, float foodDensity) {
        this.world = world;
        this.foodDensity = foodDensity; // 0.0 to 1.0
        this.foodPositions = new ArrayList<>();
    }

    /**
     * Spawn food across the grid based on density
     */
    public void spawnFood(int totalTiles) {
        clearFood();
        Random random = new Random();
        int foodCount = Math.max(5, (int) (totalTiles * foodDensity * 0.01f));

        for (int i = 0; i < foodCount; i++) {
            int x = random.nextInt(world.getWidth());
            int y = random.nextInt(world.getHeight());
            foodPositions.add(new int[]{x, y});
        }
    }

    public void removeFood(int x, int y) {
        foodPositions.removeIf(food -> food[0] == x && food[1] == y);
    }

    public void clearFood() {
        foodPositions.clear();
    }

    // Getters
    public List<int[]> getFoodPositions() { return new ArrayList<>(foodPositions); }
    public int getFoodCount() { return foodPositions.size(); }
}