package domain;

import java.awt.Color;

public class StickyWall implements Thing {
    private AManufacturing aManufacturing;
    private int wallRow;
    private int startColumn, endColumn;
    private Color color;

    public StickyWall(AManufacturing am, int row) {
        this.aManufacturing = am;
        this.wallRow = row;
        this.color = Color.BLACK;

        int size = am.getSize();
        this.startColumn = 0;      // Start at the first column
        this.endColumn = size - 1; // End at the last column

        // Place the StickyWall on the selected row
        for (int c = startColumn; c <= endColumn; c++) {
            aManufacturing.setThing(wallRow, c, this);
        }
    }

    public int getWallRow() {
        return wallRow;
    }

    @Override
    public void decide() {
        // The StickyWall does not perform any action in decide
    }

    @Override
    public void change() {
        // The StickyWall does not change state
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isActive() {
        return true; // Always active
    }

    @Override
    public int shape() {
        return Thing.SQUARE; // Represented as a square
    }
}
