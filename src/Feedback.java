import java.util.Objects;

public class Feedback {
    private final int black;
    private final int white;

    public Feedback(int black, int white) {
        this.black = black;
        this.white = white;
    }

    public int getBlack() {
        return black;
    }

    public int getWhite() {
        return white;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;
        Feedback feedback = (Feedback) o;
        return black == feedback.black && white == feedback.white;
    }

    @Override
    public int hashCode() {
        return Objects.hash(black, white);
    }

    @Override
    public String toString() {
        return "(" + black + ", " + white + ")";
    }
}
