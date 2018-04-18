package rachieru.colteanu.bosschet.ui.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Dragos on 29.03.2018.
 */

public class Player extends Sprite {

    String nume;
    String id;

    //todo texture

    public Player(int x, int y, String nume, String id) {
        setX(x);
        setY(y);
        this.nume = nume;
        this.id = id;
    }

}
