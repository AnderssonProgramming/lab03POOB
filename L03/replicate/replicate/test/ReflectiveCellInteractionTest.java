/**
 * Unit tests for interactions between ReflectiveCells in the manufacturing environment.
 * These tests ensure that ReflectiveCells are correctly created, interact properly over multiple tic-tac cycles,
 * and do not overlap or move out of bounds.
 */
package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;
import java.awt.Color;

public class ReflectiveCellInteractionTest {
    /**
     * The manufacturing environment used for the tests.
     */
    private AManufacturing manufacturing;

    /**
     * Instances of ReflectiveCell used in the tests.
     */
    private ReflectiveCell pedrazaCell;
    private ReflectiveCell sanchesCell;

    /**
     * Sets up the manufacturing environment and creates ReflectiveCells before each test.
     */
    @BeforeEach
    public void setUp() {
        manufacturing = new AManufacturing();

        // Create the "Pedraza" cell at position (5, 5)
        pedrazaCell = new ReflectiveCell(manufacturing, 5, 5, true);
        manufacturing.setThing(5, 5, pedrazaCell);

        // Create the "Sanches" cell at position (5, 6)
        sanchesCell = new ReflectiveCell(manufacturing, 5, 6, true);
        manufacturing.setThing(5, 6, sanchesCell);
    }

    /**
     * Tests the interaction between the "Pedraza" and "Sanches" ReflectiveCells over multiple tic-tac cycles.
     * Ensures that the cells stay within bounds, do not overlap, and remain active.
     */
    @Test
    public void testPedrazaAndSanchesInteraction() {
        // Execute several tic-tac cycles
        for (int i = 0; i < 5; i++) {
            // Execute decide and change for all cells in the grid
            for (int r = 0; r < manufacturing.getSize(); r++) {
                for (int c = 0; c < manufacturing.getSize(); c++) {
                    Thing thing = manufacturing.getThing(r, c);
                    if (thing != null) {
                        thing.decide();
                    }
                }
            }
            for (int r = 0; r < manufacturing.getSize(); r++) {
                for (int c = 0; c < manufacturing.getSize(); c++) {
                    Thing thing = manufacturing.getThing(r, c);
                    if (thing != null) {
                        thing.change();
                    }
                }
            }
        }

        // Verify that the cells are within bounds
        assertTrue(isWithinBounds(pedrazaCell.getRow(), pedrazaCell.getColumn()), "Pedraza cell is out of bounds.");
        assertTrue(isWithinBounds(sanchesCell.getRow(), sanchesCell.getColumn()), "Sanches cell is out of bounds.");

        // Verify that the cells do not occupy the same position
        assertFalse(
            pedrazaCell.getRow() == sanchesCell.getRow() && pedrazaCell.getColumn() == sanchesCell.getColumn(),
            "Pedraza and Sanches cells should not occupy the same position."
        );

        // Verify that the cells remain active
        assertTrue(pedrazaCell.isActive(), "Pedraza cell should remain active.");
        assertTrue(sanchesCell.isActive(), "Sanches cell should remain active.");
    }

    /**
     * Checks if the given row and column are within the bounds of the manufacturing environment.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return True if the row and column are within bounds, false otherwise.
     */
    private boolean isWithinBounds(int row, int col) {
        int size = manufacturing.getSize();
        return (0 <= row && row < size) && (0 <= col && col < size);
    }
}