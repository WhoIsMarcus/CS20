

import java.util.*;

/**
 * SimulationEngine - Core simulation logic and state management
 */
public class SimulationEngine {
    private WorldGrid world;
    private List<Blob> blobs;
    private FoodSystem foodSystem;
    private StatsTracker statsTracker;
    private int currentDay;
    private boolean running;
    private int baseMovement;
    private int baseVision;
    private float foodDensity;
    private float mutationRate;

    public SimulationEngine(int gridWidth, int gridHeight, float foodDensity, float mutationRate) {
        this.world = new WorldGrid(gridWidth, gridHeight);
        this.blobs = new ArrayList<>();
        this.foodSystem = new FoodSystem(world, foodDensity);
        this.statsTracker = new StatsTracker();
        this.currentDay = 0;
        this.running = false;
        this.baseMovement = 5;
        this.baseVision = 8;
        this.foodDensity = foodDensity;
        this.mutationRate = mutationRate;
    }

    /**
     * Initialize blobs at the start of simulation
     */
    public void initializeBlobs(int speedCount, int visionCount, int resilienceCount) {
        Random random = new Random();
        
        // Create speed blobs (yellow)
        for (int i = 0; i < speedCount; i++) {
            int x = random.nextInt(world.getWidth());
            int y = random.nextInt(world.getHeight());
            Blob blob = new Blob(x, y, TraitType.SPEED, 0.5f);
            blobs.add(blob);
        }
        
        // Create vision blobs (red)
        for (int i = 0; i < visionCount; i++) {
            int x = random.nextInt(world.getWidth());
            int y = random.nextInt(world.getHeight());
            Blob blob = new Blob(x, y, TraitType.VISION, 0.5f);
            blobs.add(blob);
        }
        
        // Create resilience blobs (blue)
        for (int i = 0; i < resilienceCount; i++) {
            int x = random.nextInt(world.getWidth());
            int y = random.nextInt(world.getHeight());
            Blob blob = new Blob(x, y, TraitType.RESILIENCE, 0.5f);
            blobs.add(blob);
        }
        
        statsTracker.recordGeneration(currentDay, blobs);
    }

    /**
     * Execute one day cycle
     */
    public void simulateDay() {
        if (!running) return;

        // Phase 1: Spawn food
        foodSystem.spawnFood(world.getWidth() * world.getHeight());

        // Phase 2: Movement - each blob tries to find and move toward food
        BlobAI ai = new BlobAI(baseMovement, baseVision);
        for (Blob blob : blobs) {
            ai.moveBlobTowardFood(blob, world, foodSystem.getFoodPositions());
        }

        // Phase 3: Food consumption
        List<int[]> foodPositions = foodSystem.getFoodPositions();
        for (Blob blob : blobs) {
            for (int[] food : foodPositions) {
                if (blob.getX() == food[0] && blob.getY() == food[1]) {
                    blob.addFood();
                    foodSystem.removeFood(food[0], food[1]);
                    break;
                }
            }
        }

        // Phase 4 & 5: Survival check and reproduction
        List<Blob> newBlobs = new ArrayList<>();
        List<Blob> deadBlobs = new ArrayList<>();

        for (Blob blob : blobs) {
            int foodCollected = blob.getFoodCollectedThisDay();
            
            if (foodCollected == 0) {
                // Dies
                deadBlobs.add(blob);
            } else if (foodCollected >= 2) {
                // Reproduces
                Blob offspring = reproduceBlob(blob);
                newBlobs.add(offspring);
            }
            // If foodCollected == 1, survives but doesn't reproduce
        }

        // Phase 6: Cleanup
        blobs.removeAll(deadBlobs);
        blobs.addAll(newBlobs);
        foodSystem.clearFood();
        
        currentDay++;
        statsTracker.recordGeneration(currentDay, blobs);
    }

    /**
     * Create offspring with mutation
     */
    private Blob reproduceBlob(Blob parent) {
        Random random = new Random();
        
        // Offspring inherits parent's trait type
        TraitType traitType = parent.getTraitType();
        
        // Mutate trait strength: ±0-10%
        float parentStrength = parent.getTraitStrength();
        float mutation = (random.nextFloat() - 0.5f) * 0.2f; // ±10%
        float childStrength = Math.max(0.1f, Math.min(1.0f, parentStrength + mutation));
        
        // Small chance (5-15%) to switch traits entirely
        if (random.nextFloat() < mutationRate) {
            traitType = TraitType.values()[random.nextInt(TraitType.values().length)];
        }
        
        int x = parent.getX() + random.nextInt(3) - 1; // Spawn near parent
        int y = parent.getY() + random.nextInt(3) - 1;
        
        // Clamp to world bounds
        x = Math.max(0, Math.min(world.getWidth() - 1, x));
        y = Math.max(0, Math.min(world.getHeight() - 1, y));
        
        return new Blob(x, y, traitType, childStrength);
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void reset(int speedCount, int visionCount, int resilienceCount) {
        blobs.clear();
        foodSystem.clearFood();
        currentDay = 0;
        running = false;
        statsTracker.reset();
        initializeBlobs(speedCount, visionCount, resilienceCount);
    }

    // Getters
    public List<Blob> getBlobs() { return blobs; }
    public FoodSystem getFoodSystem() { return foodSystem; }
    public WorldGrid getWorld() { return world; }
    public int getCurrentDay() { return currentDay; }
    public boolean isRunning() { return running; }
    public StatsTracker getStatsTracker() { return statsTracker; }
}