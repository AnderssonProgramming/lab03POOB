package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;

public class StickyWallTest {
    private AManufacturing manufacturing;
    private StickyWall stickyWall;
    private ReflectiveCell reflectiveCell;
    private TouristCell touristCell;

    @BeforeEach
    public void setUp() {
        manufacturing = new AManufacturing();

        // Create a StickyWall on row 5
        int wallRow = 5;
        stickyWall = new StickyWall(manufacturing, wallRow);
    }

    @Test
    public void testReflectiveCellNotCreatedWhenOnStickyWall() {
        // Create a ReflectiveCell on the StickyWall
        reflectiveCell = new ReflectiveCell(manufacturing, 5, 10, true);
        manufacturing.setThing(reflectiveCell.getRow(), reflectiveCell.getColumn(), reflectiveCell);        
        
        // Verify that when something is created at that position, it actually isn’t created, and after this, the instance of the Thing at that position is still a wall
        boolean isAWall = false; 
        Thing targetThing = manufacturing.getThing(5, 10);
        
        if (targetThing instanceof StickyWall) {
            isAWall = true;
        }
        
        assertTrue(isAWall, "The thing at this position should still be an instance of StickyWall.");
    }

    @Test
    public void testTouristCellStuckWhenCreatedAdjacentToStickyWall() {
        // Create a TouristCell adjacent to the StickyWall
        touristCell = new TouristCell(manufacturing, 4, 10, true);
        manufacturing.setThing(touristCell.getRow(), touristCell.getColumn(), touristCell);

        // Verify that the cell is initially not stuck
        assertFalse(touristCell.getIsStuck(), "The TouristCell should not initially be stuck.");

        // Execute decide and change
        touristCell.decide();
        touristCell.change();

        // Verify that the cell is stuck after interacting with the StickyWall
        assertTrue(touristCell.getIsStuck(), "The TouristCell should be stuck when adjacent to the StickyWall.");

        // Attempt to move the cell again
        int initialRow = touristCell.getRow();
        int initialCol = touristCell.getColumn();
        touristCell.decide();
        touristCell.change();

        // Verify that the position has not changed
        assertEquals(initialRow, touristCell.getRow(), "The TouristCell should not move once stuck.");
        assertEquals(initialCol, touristCell.getColumn(), "The TouristCell should not move once stuck.");
    }
}
