package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;
import java.awt.Color;
import java.util.List;
import java.util.Arrays;

public class PoisonTest {
    private Poison poison;
    private static final List<Color> RAINBOW_COLORS = Arrays.asList(
        Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, 
        Color.CYAN, Color.BLUE, Color.MAGENTA
    );

    @BeforeEach
    public void setUp() {
        poison = new Poison();
    }

    @Test
    public void testInitialColor() {
        // Verify that the initial color is one of the rainbow colors
        assertTrue(RAINBOW_COLORS.contains(poison.getColor()),"Poison's initial color should be from the rainbow colors.");
    }

    @Test
    public void testColorChangesInSequence() {
        // Go through the rainbow sequence and verify the color changes
        Color initialColor = poison.getColor();
        int initialIndex = RAINBOW_COLORS.indexOf(initialColor);

        for (int i = 0; i < RAINBOW_COLORS.size(); i++) {
            poison.change();
            Color expectedColor = RAINBOW_COLORS.get((initialIndex + i + 1) % RAINBOW_COLORS.size());
            assertEquals(expectedColor, poison.getColor(),"Poison's color should change in the rainbow sequence.");
        }
    }

    @Test
    public void testShapeIsSquare() {
        // Confirm that the poison object is always square-shaped
        assertEquals(Thing.SQUARE, poison.shape(),"Poison should be square-shaped.");
    }

    @Test
    public void testIsAlwaysActive() {
        // Poison should always be active
        assertTrue(poison.isActive(),"Poison should always be active.");
    }
}
