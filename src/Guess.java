import java.util.Arrays;

public class Guess {
    private final int[] values; // Must be length 4

    public Guess(int a, int b, int c, int d) {
        this.values = new int[] { a, b, c, d };
    }

    public int[] values() {
        return values.clone();
    }

    public int get(int position) {
        return values[position];
    }

    public int length() {
        return values.length;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Guess)) return false;
        Guess g = (Guess) o;
        return Arrays.equals(this.values, g.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }
}
