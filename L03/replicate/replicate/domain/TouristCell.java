package domain;

import java.awt.Color;

public class TouristCell extends Cell {
    private int centerRow, centerCol;
    private AManufacturing aManufacturing;
    private int directionRow = 0, directionCol = 0;
    private int nextRow, nextCol;

    public TouristCell(AManufacturing am, int row, int column, boolean active) {
        super(am, row, column, active);
        this.aManufacturing = am;
        this.centerRow = am.getSize() / 2;
        this.centerCol = am.getSize() / 2;
        updateColor();
    }

    @Override
    public void decide() {
        if (isStuck) {
            // Do nothing, the cell is stuck
            nextRow = row;
            nextCol = column;
            return;
        }

        if (isActive()) {
            int dr = Integer.compare(centerRow, getRow());
            int dc = Integer.compare(centerCol, getColumn());

            if (neighborIsEmpty(dr, dc) && !(aManufacturing.getThing(row + dr, column + dc) instanceof Poison)) {
                nextRow = row + dr;
                nextCol = column + dc;
            } else if (aManufacturing.getThing(row + dr, column + dc) instanceof Poison) {
                nextState = INACTIVE;
                nextRow = row;
                nextCol = column;
            } else {
                nextState = INACTIVE;
                nextRow = row;
                nextCol = column;
            }
        } else {
            int dr = Integer.compare(0, getRow());
            int dc = Integer.compare(0, getColumn());

            if (neighborIsEmpty(dr, dc) && !(aManufacturing.getThing(row + dr, column + dc) instanceof Poison)) {
                nextRow = row + dr;
                nextCol = column + dc;
            } else if (aManufacturing.getThing(row + dr, column + dc) instanceof Poison) {
                nextState = ACTIVE;
                nextRow = row;
                nextCol = column;
            } else {
                nextState = ACTIVE;
                nextRow = row;
                nextCol = column;
            }
        }
    }

    @Override
    public void change() {
        super.change();
        updateColor();

        if (isStuck) {
            // Do nothing, the cell remains in its position
            return;
        }

        // Move to next position
        aManufacturing.setThing(row, column, null);
        row = nextRow;
        column = nextCol;
        aManufacturing.setThing(row, column, this);
    }

    private void updateColor() {
        color = (isActive() ? Color.ORANGE : Color.YELLOW);
    }
}
