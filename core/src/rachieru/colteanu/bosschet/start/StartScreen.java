package rachieru.colteanu.bosschet.start;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import rachieru.colteanu.bosschet.SkrrGame;
import rachieru.colteanu.bosschet.base.BaseScreen;
import rachieru.colteanu.bosschet.play.PlayScreen;

/**
 * Created by Dragos on 27.03.2018.
 */

public class StartScreen extends BaseScreen {

    private TextButton button;
    private Skin skin;

    public StartScreen(final SkrrGame game) {
        super(game);
    }

    @Override
    public void show() {
        skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        skin.add("default-font", new BitmapFont());
        skin.load(Gdx.files.internal("uiskin.json"));

        initButtons();
    }

    private void initButtons() {
        button = new TextButton("Play", skin, "default");
        button.setPosition(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setScreen(new PlayScreen(getGame()));
            }
        });
        getStage().addActor(button);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getStage().draw();
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
