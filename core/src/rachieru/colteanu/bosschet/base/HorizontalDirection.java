package rachieru.colteanu.bosschet.base;

public enum HorizontalDirection {
    LEFT(1.0f),
    RIGHT(-1.0f);
    HorizontalDirection(float value) {
        this.value=value;
    }
    public float value;
}
