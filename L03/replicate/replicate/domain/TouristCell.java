package domain;

import java.awt.Color;

public class TouristCell extends Cell {
    private int centerRow, centerCol;
    private AManufacturing aManufacturing;

    public TouristCell(AManufacturing am, int row, int column, boolean active) {
        super(am, row, column, active);
        this.aManufacturing = am; 
        this.centerRow = am.getSize() / 2;
        this.centerCol = am.getSize() / 2;
        updateColor();
    }

    @Override
    public void decide() {
        if (isActive()) {
            int dr = Integer.compare(centerRow, getRow());
            int dc = Integer.compare(centerCol, getColumn());

            if (neighborIsEmpty(dr, dc) && !(aManufacturing.getThing(row + dr, column + dc) instanceof Poison)) {
                aManufacturing.setThing(row, column, null);
                row += dr;
                column += dc;
                aManufacturing.setThing(row, column, this);
            } else if (aManufacturing.getThing(row + dr, column + dc) instanceof Poison) {
                nextState = INACTIVE;
            } else {
                nextState = INACTIVE;
            }
        } else {
            int dr = Integer.compare(0, getRow());
            int dc = Integer.compare(0, getColumn());

            if (neighborIsEmpty(dr, dc) && !(aManufacturing.getThing(row + dr, column + dc) instanceof Poison)) {
                aManufacturing.setThing(row, column, null);
                row += dr;
                column += dc;
                aManufacturing.setThing(row, column, this);
            } else if (aManufacturing.getThing(row + dr, column + dc) instanceof Poison) {
                nextState = ACTIVE;
            } else {
                nextState = ACTIVE;
            }
        }
    }

    @Override
    public void change() {
        super.change();
        updateColor();
    }

    private void updateColor() {
        color = (isActive() ? Color.ORANGE : Color.YELLOW);
    }
}
