package rachieru.colteanu.bosschet.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class ShapeCreator {
    public static Body createRectangle(Vector2 position, Vector2 size, BodyDef.BodyType type, World world, float density, boolean sensor) {
        final BodyDef bdef = new BodyDef();
        bdef.position.set(position.x / 50.0f, position.y / 50.0f);
        bdef.type = type;
        final Body body = world.createBody(bdef);

        // define fixture
        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 50.0f, size.y / 50.0f);
        final FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = density;
        fdef.isSensor = sensor;
        body.createFixture(fdef);
        shape.dispose();

        return body;
    }
}
