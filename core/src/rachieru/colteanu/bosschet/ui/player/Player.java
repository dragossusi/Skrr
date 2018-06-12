package rachieru.colteanu.bosschet.ui.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import rachieru.colteanu.bosschet.base.HorizontalDirection;
import rachieru.colteanu.bosschet.base.VerticalDirection;
import rachieru.colteanu.bosschet.ui.base.BodyHolder;
import rachieru.colteanu.bosschet.utils.MapLoader;

/**
 * Created by Dragos on 29.03.2018.
 */

public class Player extends BodyHolder {//Sprite {

    public static final int DRIVE_DIRECTION_NONE = 0;
    public static final int DRIVE_DIRECTION_FORWARD = 1;
    public static final int DRIVE_DIRECTION_BACKWARD = 2;

    public static final int TURN_DIRECTION_NONE = 0;
    public static final int TURN_DIRECTION_LEFT = 1;
    public static final int TURN_DIRECTION_RIGHT = 2;

    private static final float LINEAR_DAMPING = 0.5f;
    private static final float RESTITUTION = 0.2f;

    float speed = 5.0f;

    private String nume;
    private String id;
    private Vector2 direction = Vector2.X;
    private float velocity = 0.0f;
    private int driveDirection;
    private int turnDirection;
    private float acceleration;
    private float drift;
    private float maxSpeed;

    public Player(final float maxSpeed, final float drift, final float acceleration, final MapLoader mapLoader, int wheelDrive, World world) {
        super(mapLoader.getPlayer());
        this.id = id;
        getBody().setLinearDamping(LINEAR_DAMPING);
        getBody().getFixtureList().get(0).setRestitution(RESTITUTION);
    }

//    public Player(Texture texture, String id) {
//        this(texture, id, 0.0f, 0.0f, 0.f, 0.f);
//    }
//
//    public Player(Texture texture, String id, double x, double y, double dirX, double dirY, String nume) {
//        this(texture, id, (float) x, (float) y, (float) dirX, (float) dirY);
//        this.nume = nume;
//    }

    public String getId() {
        return id;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
//        setRotation(direction.angle());
    }

    @Deprecated
    public void rotate(HorizontalDirection dir) {
        direction.rotate(2f * dir.value);
//        setRotation(direction.angle());
    }

    @Deprecated
    public void move(VerticalDirection dir) {
//        setX(getX() + direction.x * speed * dir.value);
//        setY(getY() + direction.y * speed * dir.value);
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDriveDirection(int driveDirection) {
        this.driveDirection = driveDirection;
    }

    public void setTurnDirection(int turnDirection) {
        this.turnDirection = turnDirection;
    }
}
