package rachieru.colteanu.bosschet.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import rachieru.colteanu.bosschet.SkrrGame;

/**
 * Created by Dragos on 28.03.2018.
 */

public abstract class BaseScreen implements Screen {

    private SkrrGame game;
    private Stage stage;

    public SkrrGame getGame() {
        return game;
    }

    public BaseScreen(SkrrGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        System.out.println(stage.toString());
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
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return game.getSkin();
    }
}
