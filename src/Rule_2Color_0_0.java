import java.util.HashSet;
import java.util.Set;

class Rule_2Color_0_0 implements DeductionRule {
    @Override
    public void apply(Guess guess, Feedback feedback, DeductionState state) {
        int[] colors = guess.values();
        Set<Integer> uniqueColors = new HashSet<>();
        for (int color : colors) uniqueColors.add(color);
        for (int color : uniqueColors) {
            state.setColorInCode(color, false);
            state.eliminateAllPositions(color);
        }
    }
}