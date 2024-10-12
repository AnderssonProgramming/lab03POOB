package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;

public class StickyWallTest {
    private AManufacturing manufacturing;
    private StickyWall StickyWall;
    private ReflectiveCell reflectiveCell;
    private TouristCell touristCell;

    @BeforeEach
    public void setUp() {
        manufacturing = new AManufacturing();

        // Crear un StickyWall en la fila 5
        int wallRow = 5;
        StickyWall = new StickyWall(manufacturing, wallRow);
    }

    @Test
    public void testReflectiveCellStuckWhenCreatedOnStickyWall() {
        // Crear una ReflectiveCell sobre el StickyWall
        reflectiveCell = new ReflectiveCell(manufacturing, 5, 10, true);
        manufacturing.setThing(reflectiveCell.getRow(), reflectiveCell.getColumn(), reflectiveCell);

        // Verificar que la célula está pegada al ser creada sobre el StickyWall
        assertTrue(reflectiveCell.getIsStuck(), "La ReflectiveCell debería estar pegada al ser creada sobre el StickyWall.");

        // Intentar mover la célula
        int initialRow = reflectiveCell.getRow();
        int initialCol = reflectiveCell.getColumn();
        reflectiveCell.decide();
        reflectiveCell.change();

        // Verificar que la posición no ha cambiado
        assertEquals(initialRow, reflectiveCell.getRow(), "La ReflectiveCell no debería moverse una vez pegada.");
        assertEquals(initialCol, reflectiveCell.getColumn(), "La ReflectiveCell no debería moverse una vez pegada.");
    }

    @Test
    public void testTouristCellStuckWhenCreatedAdjacentToStickyWall() {
        // Crear una TouristCell adyacente al StickyWall
        touristCell = new TouristCell(manufacturing, 4, 10, true);
        manufacturing.setThing(touristCell.getRow(), touristCell.getColumn(), touristCell);

        // Verificar que la célula inicialmente no está pegada
        assertFalse(touristCell.getIsStuck(), "La TouristCell no debería estar pegada inicialmente.");

        // Ejecutar decide y change
        touristCell.decide();
        touristCell.change();

        // Verificar que la célula está pegada después de interactuar con el StickyWall
        assertTrue(touristCell.getIsStuck(), "La TouristCell debería estar pegada al estar adyacente al StickyWall.");

        // Intentar mover la célula nuevamente
        int initialRow = touristCell.getRow();
        int initialCol = touristCell.getColumn();
        touristCell.decide();
        touristCell.change();

        // Verificar que la posición no ha cambiado
        assertEquals(initialRow, touristCell.getRow(), "La TouristCell no debería moverse una vez pegada.");
        assertEquals(initialCol, touristCell.getColumn(), "La TouristCell no debería moverse una vez pegada.");
    }
}
