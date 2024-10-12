/**
 * The TouristCell class represents a specialized type of cell within a manufacturing simulation environment.
 * It extends the Cell class and simulates a "tourist" behavior where the cell moves toward the center of the environment
 * when active, and towards the origin when inactive.
 */
package domain;

import java.awt.Color;

public class TouristCell extends Cell {
    /**
     * The central position of the lattice that the cell is attracted to.
     */
    private int centerRow, centerCol;

    /**
     * Reference to the manufacturing environment where the cell resides.
     */
    private AManufacturing aManufacturing;

    /**
     * The direction of movement (row and column) for the cell.
     */
    private int directionRow = 0, directionCol = 0;

    /**
     * The next row and column position for the cell.
     */
    private int nextRow, nextCol;

    /**
     * Constructs a TouristCell with the given parameters.
     *
     * @param am      The manufacturing environment where the cell resides.
     * @param row     The initial row position of the cell.
     * @param column  The initial column position of the cell.
     * @param active  Whether the cell is initially active.
     */
    public TouristCell(AManufacturing am, int row, int column, boolean active) {
        super(am, row, column, active);
        this.aManufacturing = am;
        this.centerRow = am.getSize() / 2;
        this.centerCol = am.getSize() / 2;
        updateColor();
    }

    /**
     * Determines the next action for the cell.
     * If the cell is active, it moves towards the center of the lattice.
     * If inactive, it moves towards the origin (0, 0).
     */
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
                // If there is Poison in the intended direction, become inactive
                nextState = INACTIVE;
                nextRow = row;
                nextCol = column;
            } else {
                // Unable to move, stay in place and become inactive
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
                // If there is Poison in the intended direction, become active
                nextState = ACTIVE;
                nextRow = row;
                nextCol = column;
            } else {
                // Unable to move, stay in place and become active
                nextState = ACTIVE;
                nextRow = row;
                nextCol = column;
            }
        }
    }

    /**
     * Updates the state and position of the cell.
     * If the cell is stuck, it does not move. Otherwise, it moves to the calculated next position.
     */
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

    /**
     * Updates the color of the cell based on its current state.
     * Active cells are colored orange, while inactive cells are colored yellow.
     */
    private void updateColor() {
        color = isActive() ? Color.ORANGE : Color.YELLOW;
    }
}