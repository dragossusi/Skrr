package rachieru.colteanu.bosschet.base;

public enum VerticalDirection {
    FORWARD(1.0f),
    BACKWARD(-1.0f);
    VerticalDirection(float value) {
        this.value=value;
    }
    public float value;
}
