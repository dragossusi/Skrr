package rachieru.colteanu.bosschet.ui.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Dragos on 29.03.2018.
 */

public class Player extends Sprite {

    private static Texture texture = new Texture(Gdx.files.internal("player.png"));

    public Player() {
        super(texture);
        setScale(0.10f);
    }

    public Player(double x,double y) {
        this();
        setX((float) x);
        setY((float) y);
    }
}
