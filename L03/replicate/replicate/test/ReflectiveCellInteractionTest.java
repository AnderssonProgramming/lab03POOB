package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;
import java.awt.Color;

public class ReflectiveCellInteractionTest {
    private AManufacturing manufacturing;
    private ReflectiveCell pedrazaCell;
    private ReflectiveCell sanchesCell;

    @BeforeEach
    public void setUp() {
        manufacturing = new AManufacturing();

        // Crear la célula "Pedraza" en la posición (5, 5)
        pedrazaCell = new ReflectiveCell(manufacturing, 5, 5, true);
        manufacturing.setThing(5, 5, pedrazaCell);

        // Crear la célula "Sanches" en la posición (5, 6)
        sanchesCell = new ReflectiveCell(manufacturing, 5, 6, true);
        manufacturing.setThing(5, 6, sanchesCell);
    }

    @Test
    public void testPedrazaAndSanchesInteraction() {
        // Ejecutar varios tic-tacs
        for (int i = 0; i < 5; i++) {
            // Ejecutar decide y change para todas las células en la rejilla
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

        // Verificar que las células están dentro de los límites
        assertTrue(isWithinBounds(pedrazaCell.getRow(), pedrazaCell.getColumn()), "Pedraza cell is out of bounds.");
        assertTrue(isWithinBounds(sanchesCell.getRow(), sanchesCell.getColumn()), "Sanches cell is out of bounds.");

        // Verificar que las células no están en la misma posición
        assertFalse(
            pedrazaCell.getRow() == sanchesCell.getRow() && pedrazaCell.getColumn() == sanchesCell.getColumn(),
            "Pedraza and Sanches cells should not occupy the same position."
        );

        // Verificar que las células siguen activas
        assertTrue(pedrazaCell.isActive(), "Pedraza cell should remain active.");
        assertTrue(sanchesCell.isActive(), "Sanches cell should remain active.");
    }

    private boolean isWithinBounds(int row, int col) {
        int size = manufacturing.getSize();
        return (0 <= row && row < size) && (0 <= col && col < size);
    }
}
