package rachieru.colteanu.bosschet.play.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rachieru.colteanu.bosschet.SkrrGame;
import rachieru.colteanu.bosschet.base.BaseScreen;
import rachieru.colteanu.bosschet.play.presenter.PlayPresenter;
import rachieru.colteanu.bosschet.ui.player.Player;

/**
 * Created by Dragos on 28.03.2018.
 */

public class PlayScreen extends BaseScreen implements IPlayViewDelegate {

    Button button;
    ProgressBar progressBar;
    private OrthographicCamera gameCamera;
    private Viewport viewport;
    Map<String,Player> players;
    Player me;
    SpriteBatch batch;
    PlayPresenter presenter;
    boolean connected = false;
    Texture playerTexture = new Texture(Gdx.files.internal("player.png"));

    public PlayScreen(SkrrGame game) {
        super(game);
        gameCamera = new OrthographicCamera();
        //viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),gameCamera);
        viewport = new ScreenViewport(gameCamera);
        players = new HashMap<String, Player>();
        batch = new SpriteBatch();
        try {
            presenter = new PlayPresenter(this, playerTexture);
            presenter.init();
        } catch (URISyntaxException e) {
            getGame().setScreen(new PlayScreen(getGame()));
        }
    }

    @Override
    public void show() {
        super.show();
        presenter.init();
        button = new Button(getSkin());
        button.setX(0);
        button.setY(0);
        addActor(button);
        //button = new Button(getSkin());
        //me.setX(0);
        //me.setY(0);
        //getStage().addActor(button);
        progressBar = new ProgressBar(0, 100, 1, false, getSkin());
        progressBar.setX(Gdx.graphics.getWidth() / 2);
        progressBar.setY(Gdx.graphics.getHeight() - 32);
        addActor(progressBar);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (connected) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                me.setX(me.getX() + 10);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                me.setX(me.getX() - 10);
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                me.setY(me.getY() + 10);
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                me.setY(me.getY() - 10);
            batch.begin();
            me.draw(batch);
            for (Player player : players.values())
                player.draw(batch);
            batch.end();
            presenter.emitMoved(me);
        }
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
    public void onPlayerMoved(String id, double x, double y) {
        players.get(id).setX((float) x);
        players.get(id).setY((float) y);
    }

    @Override
    public void onPlayersReceived(Map<String,Player> players) {
        this.players = players;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        Dialog dialog = new Dialog("Warning", getSkin(), "dialog");
        dialog.text(e.getMessage());
        dialog.show(getStage());
        dialog.button("Ok");
    }

    @Override
    public void onConnected(String id) {
        me = new Player(playerTexture,id);
        connected = true;
    }

    @Override
    public void newPlayer(Player player) {
        players.put(player.getId(),player);
    }

    @Override
    public void dispose() {
        presenter.dispose();
        super.dispose();
    }
}
