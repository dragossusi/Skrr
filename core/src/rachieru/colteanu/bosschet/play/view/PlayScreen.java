package rachieru.colteanu.bosschet.play.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rachieru.colteanu.bosschet.SkrrGame;
import rachieru.colteanu.bosschet.base.BaseScreen;
import rachieru.colteanu.bosschet.base.HorizontalDirection;
import rachieru.colteanu.bosschet.base.VerticalDirection;
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
    Map<String, Player> players;
    Player me;
    SpriteBatch batch;
    PlayPresenter presenter;
    boolean connected = false;
    Texture playerTexture = new Texture(Gdx.files.internal("player.png"));
    private Dialog dialogError;
    private ShapeRenderer sr;
    private Rectangle bounds = new Rectangle(-100f, -100f, 840f, 680f);

    public PlayScreen(SkrrGame game) {
        super(game);
        gameCamera = new OrthographicCamera();
        gameCamera.setToOrtho(false, 640, 480);
        //viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),gameCamera);
        viewport = new ScreenViewport(gameCamera);
        players = new HashMap<String, Player>();
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
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
        button = new TextButton("eroare e", getSkin());
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (dialogError != null)
                    dialogError.hide();
            }
        });
//        button.setX(0);
//        button.setY(0);
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
        gameCamera.update();
        batch.setProjectionMatrix(gameCamera.combined);
        if (connected) {
            gameCamera.position.x = me.getX() + 480;
            gameCamera.position.y = me.getY() + 240;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//                me.setX(me.getX() + 10);
                me.rotate(HorizontalDirection.RIGHT);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
//                me.setX(me.getX() - 10);
                me.rotate(HorizontalDirection.LEFT);
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
//                me.setY(me.getY() + 10);
                me.move(VerticalDirection.FORWARD);
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
//                me.setY(me.getY() - 10);
                me.move(VerticalDirection.BACKWARD);
            batch.begin();
            me.draw(batch);
            for (Player player : players.values())
                player.draw(batch);
            batch.end();
            presenter.emitMoved(me);
//            if(!me.getBoundingRectangle().overlaps(bounds)) { fixme
//                me.move(VerticalDirection.BACKWARD);
//            }

            sr.setProjectionMatrix(gameCamera.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setColor(new Color(0f, 0f, 1f, 0));
            sr.rect(bounds.x,bounds.y,bounds.width,bounds.height);
            sr.end();
//            Rectangle rectangle = me.getBoundingRectangle();
//            sr.begin(ShapeRenderer.ShapeType.Filled);
//            sr.setColor(new Color(1f, 0f, 0f, 0.5f));
//            sr.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
//            sr.end();
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
    public void onPlayerMoved(String id, double x, double y, Vector2 direction) {
        players.get(id).setX((float) x);
        players.get(id).setY((float) y);
        players.get(id).setDirection(direction);
    }

    @Override
    public void onPlayersReceived(Map<String, Player> players) {
        this.players = players;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        dialogError = new Dialog("Warning", getSkin(), "dialog");
        dialogError.text(e.getMessage());
        dialogError.show(getStage());
        dialogError.button(button);
    }

    @Override
    public void onConnected(String id) {
        me = new Player(playerTexture, id);
        connected = true;
    }

    @Override
    public void newPlayer(Player player) {
        players.put(player.getId(), player);
    }

    @Override
    public void dispose() {
        presenter.dispose();
        super.dispose();
    }
}
