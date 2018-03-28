package rachieru.colteanu.bosschet.play;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import rachieru.colteanu.bosschet.SkrrGame;
import rachieru.colteanu.bosschet.base.BaseScreen;

/**
 * Created by Dragos on 28.03.2018.
 */

public class PlayScreen extends BaseScreen {

    Button button;

    public PlayScreen(SkrrGame game) {
        super(game);
        button = new Button();
        button.setX(22);
        button.setY(33);
    }

    @Override
    public void show() {
        super.show();
        getStage().addActor(button);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
