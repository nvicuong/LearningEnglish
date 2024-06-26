package games;

import java.util.Objects;

public class Pair {
    private Point start, target;

    public Pair(Point start, Point target) {
        this.start = start;
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(start, pair.start) && Objects.equals(target, pair.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, target);
    }

    @Override
    public String toString() {
        return start.toString() + target.toString();
    }

    public Point getStart() {
        return start;
    }

    public Point getTarget() {
        return target;
    }
}
