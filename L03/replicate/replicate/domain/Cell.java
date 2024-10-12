package domain;

import java.awt.Color;

public class Cell extends Artefact implements Thing {
    protected char nextState;
    protected Color color;
    private AManufacturing aManufacturing;
    protected int row, column;
    protected boolean isStuck = false;

    public Cell(AManufacturing am, int row, int column, boolean active) {
        this.aManufacturing = am;
        this.row = row;
        this.column = column;
        state = (active ? Artefact.ACTIVE : Artefact.INACTIVE);
        nextState = state;
        aManufacturing.setThing(row, column, this);
        color = Color.BLACK;
    }

    public final int getRow() {
        return row;
    }

    public final int getColumn() {
        return column;
    }

    public final Color getColor() {
        return color;
    }

    public void decide() {
        nextState = (getSteps() % 2 == 0 ? Artefact.ACTIVE : Artefact.INACTIVE);
    }

    public void change() {        
        state = nextState;

        // Check if the cell is adjacent to a StickyWall or on top of one
        if (!isStuck && isAdjacentToStickyWall()) {
            isStuck = true;
        }

        // If the cell is stuck, it does not move
        if (isStuck) {
            step();
        } else {
            // Normal behavior (if any movement logic exists)
        }
    }

    private boolean isAdjacentToStickyWall() {
        // Check current position
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

    public int neighborsActive() {
        return aManufacturing.neighborsActive(row, column);
    }

    public boolean neighborIsEmpty(int dr, int dc) {
        return aManufacturing.isEmpty(row + dr, column + dc);
    }
    
    public boolean getIsStuck(){
        return isStuck;
    }
}
