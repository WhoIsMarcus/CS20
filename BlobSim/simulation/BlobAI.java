package simulation;

import java.util.*;

/**
 * BlobAI - Handles blob movement logic and decision-making
 */
public class BlobAI {
    private int baseMovement;
    private int baseVision;
    private Random random;

    public BlobAI(int baseMovement, int baseVision) {
        this.baseMovement = baseMovement;
        this.baseVision = baseVision;
        this.random = new Random();
    }

    /**
     * Move a blob toward the nearest visible food, or randomly if none visible
     */
    public void moveBlobTowardFood(Blob blob, WorldGrid world, List<int[]> foodPositions) {
        int movement = calculateMovement(blob);
        int visionRange = calculateVisionRange(blob);

        // Find nearest food within vision range
        int[] nearestFood = findNearestFood(blob, foodPositions, visionRange);

        if (nearestFood != null) {
            moveTowardTarget(blob, nearestFood[0], nearestFood[1], movement, world);
        } else {
            randomWalk(blob, movement, world);
        }
    }

    /**
     * Calculate total movement steps for a blob based on trait
     */
    private int calculateMovement(Blob blob) {
        return baseMovement + blob.getSpeedBonus();
    }

    /**
     * Calculate vision range for a blob based on trait
     */
    private int calculateVisionRange(Blob blob) {
        return baseVision + blob.getVisionBonus();
    }

    /**
     * Find the nearest food within vision range
     */
    private int[] findNearestFood(Blob blob, List<int[]> foodPositions, int visionRange) {
        int[] nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (int[] food : foodPositions) {
            double distance = Math.sqrt(
                Math.pow(blob.getX() - food[0], 2) + 
                Math.pow(blob.getY() - food[1], 2)
            );

            if (distance <= visionRange && distance < minDistance) {
                minDistance = distance;
                nearest = food;
            }
        }

        return nearest;
    }

    /**
     * Move blob toward target position using Manhattan-style pathfinding
     */
    private void moveTowardTarget(Blob blob, int targetX, int targetY, int steps, WorldGrid world) {
        for (int i = 0; i < steps; i++) {
            int nextX = blob.getX();
            int nextY = blob.getY();

            // Move in X direction first
            if (blob.getX() < targetX) {
                nextX = blob.getX() + 1;
            } else if (blob.getX() > targetX) {
                nextX = blob.getX() - 1;
            }
            // Then move in Y direction
            else if (blob.getY() < targetY) {
                nextY = blob.getY() + 1;
            } else if (blob.getY() > targetY) {
                nextY = blob.getY() - 1;
            }

            // Ensure within bounds
            if (world.isInBounds(nextX, nextY)) {
                blob.moveTo(nextX, nextY);
            }
        }
    }

    /**
     * Random walk when no food is visible
     */
    private void randomWalk(Blob blob, int steps, WorldGrid world) {
        for (int i = 0; i < steps; i++) {
            int direction = random.nextInt(4);
            int nextX = blob.getX();
            int nextY = blob.getY();

            switch (direction) {
                case 0: nextX++; break; // Right
                case 1: nextX--; break; // Left
                case 2: nextY++; break; // Down
                case 3: nextY--; break; // Up
            }

            if (world.isInBounds(nextX, nextY)) {
                blob.moveTo(nextX, nextY);
            }
        }
    }
}