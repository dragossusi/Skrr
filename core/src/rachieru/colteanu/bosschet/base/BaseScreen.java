package rachieru.colteanu.bosschet.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import rachieru.colteanu.bosschet.SkrrGame;

/**
 * Created by Dragos on 28.03.2018.
 */

public abstract class BaseScreen implements Screen {

    private SkrrGame game;
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public SkrrGame getGame() {
        return game;
    }

    public BaseScreen(SkrrGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void resize(int width, int height) {
        //disabled
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
