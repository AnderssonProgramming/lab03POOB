package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;
import java.awt.Color;

public class ReflectiveCellTest {
    private AManufacturing manufacturing;
    private ReflectiveCell reflectiveCell;

    @BeforeEach
    public void setUp() {
        manufacturing = new AManufacturing();
        reflectiveCell = new ReflectiveCell(manufacturing, 1, 1, true);
        manufacturing.setThing(1, 1, reflectiveCell);
    }

    @Test
    public void testRandomMovementAndBounce() {
        // Save the initial position
        int initialRow = reflectiveCell.getRow();
        int initialCol = reflectiveCell.getColumn();

        // Execute decide and change
        reflectiveCell.decide();
        reflectiveCell.change();

        // Ensure that the cell moved to a valid position or bounced
        int newRow = reflectiveCell.getRow();
        int newCol = reflectiveCell.getColumn();

        assertTrue(
            // abs for the absolute value of diference
            (Math.abs(newRow - initialRow) <= 1 && Math.abs(newCol - initialCol) <= 1),
            "ReflectiveCell should move to a neighboring cell."
        );
    }

    @Test
    public void testInteractionWithPoison() {
        // Positions adjacent to (1,1)
        int[][] adjacentPositions = {
            {0, 1}, // up
            {2, 1}, // down
            {1, 0}, // left
            {1, 2}, // right
            {0, 0}, // up left
            {0, 2}, // up right
            {2, 0}, // down left
            {2, 2}  // down right
        };          

        // Place Poison in all adjacent positions
        for (int[] pos : adjacentPositions) {
            Poison poison = new Poison();
            manufacturing.setThing(pos[0], pos[1], poison);
        }

        // Execute decide and change
        reflectiveCell.decide();
        reflectiveCell.change();

        // Verify that the cell is now inactive
        assertFalse(reflectiveCell.isActive(), "ReflectiveCell should become inactive after encountering poison.");
        assertEquals(Color.GRAY, reflectiveCell.getColor(), "ReflectiveCell should be gray when inactive.");
    }
}
