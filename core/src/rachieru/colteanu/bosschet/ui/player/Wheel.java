package rachieru.colteanu.bosschet.ui.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import rachieru.colteanu.bosschet.ui.base.BodyHolder;

public class Wheel extends BodyHolder {
    public static final int UPPER_LEFT = 0;
    public static final int UPPER_RIGHT = 1;
    public static final int DOWN_LEFT = 2;
    public static final int DOWN_RIGHT = 3;

    private static final float DEGTORAD = 0.0174532925199432957f;

    private final boolean powered;
    private final Player player;

    public Wheel(final Vector2 position, final Vector2 size, final BodyDef.BodyType type, final World world, final float density, final int id, final Player player, final boolean powered) {
        super(position, size, type, world, density, true);
        this.player = player;
        this.powered = powered;
    }

    public void setAngle(final float angle){
        getBody().setTransform(getBody().getPosition(), player.getBody().getAngle() + angle * DEGTORAD);
    }

    public boolean isPowered() {
        return powered;
    }
}
