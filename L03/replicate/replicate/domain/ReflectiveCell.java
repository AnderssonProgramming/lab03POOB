package domain;
import java.awt.Color;
import java.util.Random;

public class ReflectiveCell extends Cell {
    private int directionRow, directionCol;
    private AManufacturing aManufacturing;
    private static final Random random = new Random();

    public ReflectiveCell(AManufacturing am, int row, int column, boolean active) {
        super(am, row, column, active);
        this.aManufacturing = am;
        // Initialize with a random direction
        this.directionRow = random.nextInt(3) - 1; // -1, 0, or 1
        this.directionCol = random.nextInt(3) - 1;
        updateColor();
    }

    @Override
    public void decide() {
        // Attempt to move in the current direction
        int newRow = row + directionRow;
        int newCol = column + directionCol;

        // Check if the cell can move in the current direction
        if (aManufacturing.inLatice(newRow, newCol)) {
            Thing target = aManufacturing.getThing(newRow, newCol);
            if (target == null) {
                // Move to the empty position
                aManufacturing.setThing(row, column, null);
                row = newRow;
                column = newCol;
                aManufacturing.setThing(row, column, this);
            } else if (target instanceof Poison) {
                // If target is Poison, change state to inactive
                nextState = INACTIVE;                
            } else if (target instanceof StickyWall){
                
            } else {
                // Bounce back by reversing the direction
                directionRow = -directionRow;
                directionCol = -directionCol;
            }
        } else {
            // Out of bounds: bounce back
            directionRow = -directionRow;
            directionCol = -directionCol;
        }
    }

    @Override
    public void change() {
        super.change();
        updateColor();
    }

    private void updateColor() {
        color = (isActive() ? Color.GREEN : Color.GRAY);
    }
}
