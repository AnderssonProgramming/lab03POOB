package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.*;

class AManufacturingTest {

    private AManufacturing aManufacturing;

    @BeforeEach
    public void setUp() {
        aManufacturing = new AManufacturing();
    }

    @Test
    @DisplayName("Verify the board size")
    public void testGetSize() {
        assertEquals(50, aManufacturing.getSize(), "The initial size must be 50");
    }

    @Test
    @DisplayName("Verify that isEmpty method works perfectly")
    public void testIsEmpty() {
        assertTrue(aManufacturing.isEmpty(0, 0), "The cell (0,0) must be empty at the beginning");
    }

    @Test
    @DisplayName("Verify neighborsActive functionality")
    public void testNeighborsActive() {
        // Create an active cell in the position(1, 1)
        Thing cell = new Cell(aManufacturing, 1, 1, true);
        aManufacturing.setThing(1, 1, cell);

        // Create active cells around cell (1, 1)
        aManufacturing.setThing(0, 1, new Cell(aManufacturing, 0, 1, true));
        aManufacturing.setThing(1, 0, new Cell(aManufacturing, 1, 0, true));
        aManufacturing.setThing(2, 1, new Cell(aManufacturing, 2, 1, true));
        
        int activeNeighbors = aManufacturing.neighborsActive(1, 1);
        assertEquals(3, activeNeighbors, "The cell (1,1) must have 3 active neighbors");
    }

    @Test
    @DisplayName("Verify the state change of cells with ticTac")
    public void testTicTac() {
        // Configure an active initial cell in (2, 2)
        Thing cell = new Cell(aManufacturing, 2, 2, true);
        aManufacturing.setThing(2, 2, cell);
        
        aManufacturing.ticTac();
        assertTrue(aManufacturing.getThing(2, 2).isActive(), "The cell(2,2) must be active while ticTac maintains without active neighbors");
    }
}
