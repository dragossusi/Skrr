package rachieru.colteanu.bosschet.ui.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Dragos on 29.03.2018.
 */

public class Player extends Sprite {

    String nume;
    String id;

    private static Texture texture = new Texture(Gdx.files.internal("player.png"));
    //todo texture

    public Player(double x, double y, String nume, String id) {
        setX((float) x);
        setY((float) y);
        this.nume = nume;
        this.id = id;
    }


    public Player(String id) {
        super(texture);
        this.id = id;
        setScale(0.10f);
        setX(0.0f);
        setY(0.0f);
    }

    public Player(double x,double y, String id) {
        this(id);
        setX((float) x);
        setY((float) y);
    }

}
