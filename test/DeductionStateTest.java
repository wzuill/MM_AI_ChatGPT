import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeductionStateTest {

    @Test
    void testColorInCodeTracking() {
        DeductionState state = new DeductionState();
        state.setColorInCode(2, true);
        state.setColorInCode(4, false);
        assertEquals(true, state.isColorInCode(2));
        assertEquals(false, state.isColorInCode(4));
        assertNull(state.isColorInCode(1)); // Not set
    }

    @Test
    void testEliminateSinglePosition() {
        DeductionState state = new DeductionState();
        state.eliminatePosition(3, 2);
        assertTrue(state.isPositionEliminated(3, 2));
        assertFalse(state.isPositionEliminated(3, 1));
    }

    @Test
    void testEliminateAllPositions() {
        DeductionState state = new DeductionState();
        state.eliminateAllPositions(1);
        for (int pos = 0; pos < 4; pos++) {
            assertTrue(state.isPositionEliminated(1, pos));
        }
    }

    @Test
    void testConfirmAndRetrievePosition() {
        DeductionState state = new DeductionState();
        state.confirmPosition(2, 5);
        assertEquals(5, state.getConfirmedColor(2));
        assertNull(state.getConfirmedColor(1));
    }

    @Test
    void testMapsAreImmutable() {
        DeductionState state = new DeductionState();
        assertThrows(UnsupportedOperationException.class, () -> {
            state.getColorInCodeMap().put(0, true);
        });
    }
}

