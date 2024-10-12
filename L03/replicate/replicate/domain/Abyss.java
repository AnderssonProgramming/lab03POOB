package domain;

import java.awt.Color;

public class Abyss implements Thing {
    private AManufacturing aManufacturing;
    private int abyssRow;
    private int startColumn, endColumn;
    private Color color;

    public Abyss(AManufacturing am, int row) {
        this.aManufacturing = am;
        this.abyssRow = row;
        this.color = Color.BLACK;

        int size = am.getSize();
        this.startColumn = 0;      // Start at the first column
        this.endColumn = size - 1; // End at the last column

        // Place the Abyss on the selected row
        for (int c = startColumn; c <= endColumn; c++) {
            aManufacturing.setThing(abyssRow, c, this);
        }
    }

    public int getAbyssRow() {
        return abyssRow;
    }

    @Override
    public void decide() {
        // The Abyss does not perform any action in decide
    }

    @Override
    public void change() {
        // The Abyss does not change state
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
