import java.util.*;

public class DeductionState {
    private final Map<Integer, Boolean> colorInCode = new HashMap<>();
    private final Map<Integer, Set<Integer>> eliminatedPositions = new HashMap<>();
    private final Map<Integer, Integer> confirmedPositions = new HashMap<>();

    public DeductionState() {
        for (int color = 0; color <= 5; color++) {
            colorInCode.put(color, null); // null = unknown
            eliminatedPositions.put(color, new HashSet<>());
        }
    }

    public void setColorInCode(int color, boolean present) {
        colorInCode.put(color, present);
    }

    public Boolean isColorInCode(int color) {
        return colorInCode.get(color);
    }

    public void eliminatePosition(int color, int position) {
        eliminatedPositions.get(color).add(position);
    }

    public void eliminateAllPositions(int color) {
        eliminatedPositions.get(color).addAll(Set.of(0, 1, 2, 3));
    }

    public boolean isPositionEliminated(int color, int position) {
        return eliminatedPositions.get(color).contains(position);
    }

    public void confirmPosition(int position, int color) {
        confirmedPositions.put(position, color);
    }

    public Integer getConfirmedColor(int position) {
        return confirmedPositions.get(position);
    }

    public Map<Integer, Boolean> getColorInCodeMap() {
        return Collections.unmodifiableMap(colorInCode);
    }

    public Map<Integer, Set<Integer>> getEliminatedPositionsMap() {
        return Collections.unmodifiableMap(eliminatedPositions);
    }

    public Map<Integer, Integer> getConfirmedPositionsMap() {
        return Collections.unmodifiableMap(confirmedPositions);
    }
}
