package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;
import java.awt.Color;

public class TouristCellTest {
    private TouristCell touristCell;
    private AManufacturing manufacturing;

    @BeforeEach
    public void setUp() {
        manufacturing = new AManufacturing();
        touristCell = new TouristCell(manufacturing, 0, 0, true);
        manufacturing.setThing(0, 0, touristCell);
    }

    @Test
    public void testInitialConfiguration() {
        // Verify that the cell is active and has the color orange
        assertTrue(touristCell.isActive());
        assertEquals(Color.ORANGE, touristCell.getColor());
    }

    @Test
    public void testMoveToCenter() {
        // Verify that the cell moves towards the center when active and space is available
        touristCell.decide();
        touristCell.change();
        
        // The cell should have moved one step closer to the center
        assertEquals(1, touristCell.getRow());
        assertEquals(1, touristCell.getColumn());
    }

    @Test
    public void testBecomeInactiveWhenBlocked() {
        // Place another cell in the center to block movement
        TouristCell blocker = new TouristCell(manufacturing, 1, 1, true);
        manufacturing.setThing(1, 1, blocker);

        // The cell should become inactive if it cannot move towards the center
        touristCell.decide();
        touristCell.change();

        assertFalse(touristCell.isActive());
        assertEquals(Color.YELLOW, touristCell.getColor());
    }

    @Test
    public void testMoveToEdgeWhenInactive() {
        // Place the cell in the center of the grid and make it inactive
        touristCell = new TouristCell(manufacturing, manufacturing.getSize() / 2, manufacturing.getSize() / 2, false);
        manufacturing.setThing(manufacturing.getSize() / 2, manufacturing.getSize() / 2, touristCell);

        touristCell.decide();
        touristCell.change();

        // The cell should have moved towards the edge of the grid
        assertTrue(touristCell.getRow() < manufacturing.getSize() / 2);
        assertTrue(touristCell.getColumn() < manufacturing.getSize() / 2);
    }

    @Test
    public void testBecomeActiveWhenBlockedToEdge() {
        // Place another cell at the edge to block movement towards the edge
        TouristCell blocker = new TouristCell(manufacturing, 0, 0, true);
        manufacturing.setThing(0, 0, blocker);

        touristCell = new TouristCell(manufacturing, 1, 1, false);
        manufacturing.setThing(1, 1, touristCell);

        // The cell should become active if it cannot move towards the edge
        touristCell.decide();
        touristCell.change();

        assertTrue(touristCell.isActive());
        assertEquals(Color.ORANGE, touristCell.getColor());
    }
}
