package simulation;

/**
 * WorldGrid - Represents the 2D grid world
 */
public class WorldGrid {
    private int width;
    private int height;
    private boolean[][] occupied; // Track which tiles have blobs for collision detection if needed

    public WorldGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.occupied = new boolean[width][height];
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void markOccupied(int x, int y) {
        if (isInBounds(x, y)) {
            occupied[x][y] = true;
        }
    }

    public void markEmpty(int x, int y) {
        if (isInBounds(x, y)) {
            occupied[x][y] = false;
        }
    }

    public boolean isOccupied(int x, int y) {
        return isInBounds(x, y) && occupied[x][y];
    }

    public void reset() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                occupied[x][y] = false;
            }
        }
    }

    // Getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}