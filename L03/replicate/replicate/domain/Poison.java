package domain;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Poison extends Artefact implements Thing {
    
    private static final List<Color> RAINBOW_COLORS = Arrays.asList(
        Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, 
        Color.CYAN, Color.BLUE, Color.MAGENTA
    );
    private int colorIndex = 0;
    private Color color;

    public Poison() {
        // Start with a random color from the rainbow
        colorIndex = new Random().nextInt(RAINBOW_COLORS.size());
        color = RAINBOW_COLORS.get(colorIndex);
    }

    @Override
    public void decide() {
        // Poison doesn't have a next state in the traditional sense, so this can be left empty
    }

    @Override
    public void change() {
        // Update the color to the next one in the rainbow sequence
        colorIndex = (colorIndex + 1) % RAINBOW_COLORS.size();
        color = RAINBOW_COLORS.get(colorIndex);
    }

    @Override
    public int shape() {
        return SQUARE; // Poison is square-shaped
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isActive() {
        return true; // Poison is always "active" in the sense that it exists
    }
}
