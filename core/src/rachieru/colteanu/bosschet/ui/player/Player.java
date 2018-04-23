package rachieru.colteanu.bosschet.ui.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import rachieru.colteanu.bosschet.base.HorizontalDirection;
import rachieru.colteanu.bosschet.base.VerticalDirection;

/**
 * Created by Dragos on 29.03.2018.
 */

public class Player extends Sprite {

    float speed = 5.0f;

    private String nume;
    private String id;
    private Vector2 direction = Vector2.X;

    public Player(Texture texture, String id, float x, float y) {
        super(texture);
        this.id = id;
        setScale(0.10f);
        setX(x);
        setY(y);
    }

    public Player(Texture texture, String id) {
        this(texture, id, 0.0f, 0.0f);
    }

    public Player(Texture texture, String id, double x, double y, String nume) {
        this(texture, id, (float) x, (float) y);
        this.nume = nume;
    }

    public String getId() {
        return id;
    }

    public void rotate(HorizontalDirection dir) {
        direction.rotate(2f * dir.value);
        setRotation(direction.angle());
    }

    public void move(VerticalDirection dir) {
        setX(getX() + direction.x * speed * dir.value);
        setY(getY() + direction.y * speed * dir.value);
    }
}
