package rachieru.colteanu.bosschet.start;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import rachieru.colteanu.bosschet.SkrrGame;
import rachieru.colteanu.bosschet.base.BaseScreen;
import rachieru.colteanu.bosschet.play.view.PlayScreen;

/**
 * Created by Dragos on 27.03.2018.
 */

public class StartScreen extends BaseScreen {

    private TextButton button;
    private Music backgroundMusic;

    public StartScreen(final SkrrGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        initButtons();
        initSound();
    }

    private void initSound() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/start_bg.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.4f);
        backgroundMusic.play();
    }

    private void initButtons() {
        button = new TextButton("Play", getSkin());
        button.setX(Gdx.graphics.getWidth() / 2.0f);
        button.setY(Gdx.graphics.getHeight() / 2.0f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setScreen(new PlayScreen(getGame()));
            }
        });
        addActor(button);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void pause() {
        backgroundMusic.setVolume(0.0f);
    }

    @Override
    public void resume() {
        backgroundMusic.setVolume(1.0f);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
