/**
 * The Cell class represents a basic unit within a manufacturing simulation, extending the Artefact class.
 * It also implements the Thing interface, allowing interaction within the manufacturing lattice environment.
 * Cells have states, positions, and can interact with other components in the simulation.
 */
package domain;

import java.awt.Color;

public class Cell extends Artefact implements Thing {
    /**
     * The next state of the cell, either ACTIVE or INACTIVE.
     */
    protected char nextState;

    /**
     * The color representing the cell.
     */
    protected Color color;

    /**
     * Reference to the manufacturing environment that the cell resides in.
     */
    private AManufacturing aManufacturing;

    /**
     * The position of the cell within the lattice (row and column).
     */
    protected int row;
    protected int column;

    /**
     * Indicates whether the cell is stuck, which occurs when it is adjacent to a StickyWall.
     */
    protected boolean isStuck = false;

    /**
     * Constructs a Cell with the given parameters.
     *
     * @param am      The manufacturing environment the cell resides in.
     * @param row     The initial row position of the cell.
     * @param column  The initial column position of the cell.
     * @param active  Whether the cell is initially active.
     */
    public Cell(AManufacturing am, int row, int column, boolean active) {
        this.aManufacturing = am;
        this.row = row;
        this.column = column;
        this.state = active ? Artefact.ACTIVE : Artefact.INACTIVE;
        this.nextState = state;
        this.aManufacturing.setThing(row, column, this);
        this.color = Color.BLACK;
    }

    /**
     * Gets the current row position of the cell.
     *
     * @return The row position of the cell.
     */
    public final int getRow() {
        return row;
    }

    /**
     * Gets the current column position of the cell.
     *
     * @return The column position of the cell.
     */
    public final int getColumn() {
        return column;
    }

    /**
     * Gets the color of the cell.
     *
     * @return The color of the cell.
     */
    public final Color getColor() {
        return color;
    }

    /**
     * Determines the next state of the cell based on the current simulation step.
     * Alternates between ACTIVE and INACTIVE states.
     */
    public void decide() {
        nextState = (getSteps() % 2 == 0) ? Artefact.ACTIVE : Artefact.INACTIVE;
    }

    /**
     * Updates the state of the cell to its next state and checks if it should be stuck.
     * If the cell is adjacent to a StickyWall, it becomes stuck and stops moving.
     */
    public void change() {
        state = nextState;

        // Check if the cell is adjacent to a StickyWall or is on top of one
        if (!isStuck && isAdjacentToStickyWall()) {
            isStuck = true;
        }

        // If the cell is stuck, it stays in place
        if (isStuck) {
            step();
        } else {
            // Additional behavior for unstuck cells can be added here
        }
    }

    /**
     * Checks if the cell is adjacent to or on top of a StickyWall.
     *
     * @return True if the cell is adjacent to a StickyWall, false otherwise.
     */
    private boolean isAdjacentToStickyWall() {
        // Check the current position
        Thing currentThing = aManufacturing.getThing(row, column);
        if (currentThing instanceof StickyWall) {
            return true;
        }

        // Check adjacent positions
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int newRow = row + dr;
                int newCol = column + dc;
                if (aManufacturing.inLatice(newRow, newCol)) {
                    Thing adjacentThing = aManufacturing.getThing(newRow, newCol);
                    if (adjacentThing instanceof StickyWall) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the number of active neighboring cells.
     *
     * @return The number of active neighbors.
     */
    public int neighborsActive() {
        return aManufacturing.neighborsActive(row, column);
    }

    /**
     * Checks if a neighboring cell in a specific direction is empty.
     *
     * @param dr The row offset for the neighbor.
     * @param dc The column offset for the neighbor.
     * @return True if the neighboring cell is empty, false otherwise.
     */
    public boolean neighborIsEmpty(int dr, int dc) {
        return aManufacturing.isEmpty(row + dr, column + dc);
    }

    /**
     * Gets whether the cell is currently stuck.
     *
     * @return True if the cell is stuck, false otherwise.
     */
    public boolean getIsStuck() {
        return isStuck;
    }
}