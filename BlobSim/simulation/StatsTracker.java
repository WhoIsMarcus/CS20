package simulation;

import java.util.*;

/**
 * StatsTracker - Records and manages simulation statistics
 */
public class StatsTracker {
    private List<Integer> speedPopulation;
    private List<Integer> visionPopulation;
    private List<Integer> resiliencePopulation;
    private List<Integer> totalPopulation;
    private List<Integer> foodConsumedPerGen;

    public StatsTracker() {
        this.speedPopulation = new ArrayList<>();
        this.visionPopulation = new ArrayList<>();
        this.resiliencePopulation = new ArrayList<>();
        this.totalPopulation = new ArrayList<>();
        this.foodConsumedPerGen = new ArrayList<>();
    }

    /**
     * Record stats for the current generation
     */
    public void recordGeneration(int day, List<Blob> blobs) {
        int speedCount = 0;
        int visionCount = 0;
        int resilienceCount = 0;
        int totalFood = 0;

        for (Blob blob : blobs) {
            switch (blob.getTraitType()) {
                case SPEED:
                    speedCount++;
                    break;
                case VISION:
                    visionCount++;
                    break;
                case RESILIENCE:
                    resilienceCount++;
                    break;
            }
            totalFood += blob.getFoodCollectedThisDay();
        }

        speedPopulation.add(speedCount);
        visionPopulation.add(visionCount);
        resiliencePopulation.add(resilienceCount);
        totalPopulation.add(blobs.size());
        foodConsumedPerGen.add(totalFood);
    }

    public void reset() {
        speedPopulation.clear();
        visionPopulation.clear();
        resiliencePopulation.clear();
        totalPopulation.clear();
        foodConsumedPerGen.clear();
    }

    // Getters
    public List<Integer> getSpeedPopulation() { return new ArrayList<>(speedPopulation); }
    public List<Integer> getVisionPopulation() { return new ArrayList<>(visionPopulation); }
    public List<Integer> getResiliencePopulation() { return new ArrayList<>(resiliencePopulation); }
    public List<Integer> getTotalPopulation() { return new ArrayList<>(totalPopulation); }
    public List<Integer> getFoodConsumedPerGen() { return new ArrayList<>(foodConsumedPerGen); }

    public int getCurrentSpeedCount() {
        return speedPopulation.isEmpty() ? 0 : speedPopulation.get(speedPopulation.size() - 1);
    }

    public int getCurrentVisionCount() {
        return visionPopulation.isEmpty() ? 0 : visionPopulation.get(visionPopulation.size() - 1);
    }

    public int getCurrentResilienceCount() {
        return resiliencePopulation.isEmpty() ? 0 : resiliencePopulation.get(resiliencePopulation.size() - 1);
    }

    public int getTotalPopulationCount() {
        return totalPopulation.isEmpty() ? 0 : totalPopulation.get(totalPopulation.size() - 1);
    }
}