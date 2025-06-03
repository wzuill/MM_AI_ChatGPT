import java.util.*;

public class CanonicalMatrixRule implements DeductionRule {

    private final List<DeductionEntry> entries;

    public CanonicalMatrixRule(List<DeductionEntry> entries) {
        this.entries = entries;
    }

    public static CanonicalMatrixRule for2Color_0_0() {
        // Matches Rule_2Color_0_0
        List<DeductionEntry> entries = List.of(
                new DeductionEntry('A', false, List.of(0, 1, 2, 3)),
                new DeductionEntry('B', false, List.of(0, 1, 2, 3))
        );
        return new CanonicalMatrixRule(entries);
    }

    @Override
    public void apply(Guess guess, Feedback feedback, DeductionState state) {
        Map<Character, Integer> binding = bindCanonicalLetters(guess);
        for (DeductionEntry entry : entries) {
            Integer actualColor = binding.get(entry.letter);
            if (actualColor == null) continue;
            state.setColorInCode(actualColor, entry.inCode);
            for (int pos : entry.eliminatePositions) {
                state.eliminatePosition(actualColor, pos);
            }
        }
    }

    private Map<Character, Integer> bindCanonicalLetters(Guess guess) {
        int[] values = guess.values();
        Map<Character, Integer> map = new HashMap<>();
        char[] canon = {'A', 'B', 'C', 'D'};
        int canonIndex = 0;
        for (int val : values) {
            if (!map.containsValue(val)) {
                map.put(canon[canonIndex++], val);
                if (canonIndex >= canon.length) break;
            }
        }
        return map;
    }

    public static class DeductionEntry {
        final char letter;
        final boolean inCode;
        final List<Integer> eliminatePositions;

        public DeductionEntry(char letter, boolean inCode, List<Integer> eliminatePositions) {
            this.letter = letter;
            this.inCode = inCode;
            this.eliminatePositions = eliminatePositions;
        }
    }
}
