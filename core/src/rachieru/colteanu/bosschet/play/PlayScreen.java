package rachieru.colteanu.bosschet.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;
import rachieru.colteanu.bosschet.SkrrGame;
import rachieru.colteanu.bosschet.base.BaseScreen;
import rachieru.colteanu.bosschet.ui.player.Player;

/**
 * Created by Dragos on 28.03.2018.
 */

public class PlayScreen extends BaseScreen {

    Button button;
    ProgressBar progressBar;
    private OrthographicCamera gameCamera;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    List<Player> players;

    public PlayScreen(SkrrGame game) {
        super(game);
        gameCamera = new OrthographicCamera();
        spriteBatch = new SpriteBatch();
        //viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),gameCamera);
        viewport = new ScreenViewport(gameCamera);
        players = new ArrayList<Player>();
        game.getSocket()
                .connect()
                .on("player_moved", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {

                    }
                })
                .on("get_players", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONArray jsonArray = (JSONArray) args[0];
                        for(int i=0;i<jsonArray.length();++i) {
                            try {
                                JSONObject object = jsonArray.getJSONObject(i);
                                players.add(new Player(
                                        object.getInt("x"),
                                        object.getInt("y"),
                                        object.getString("nume"),
                                        object.getString("id")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void show() {
        super.show();
        button = new Button(getSkin());
        progressBar = new ProgressBar(0,100,1,false,getSkin());
        progressBar.setX(Gdx.graphics.getWidth()/2);
        progressBar.setY(Gdx.graphics.getHeight()/2);
        button.setX(0);
        button.setY(0);
        addActor(button);
        addActor(progressBar);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            button.setX(button.getX() + 10);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            button.setX(button.getX() - 10);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            button.setY(button.getY() + 10);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            button.setY(button.getY() - 10);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("x", button.getX());
            jsonObject.put("y", button.getY());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getGame().getSocket().emit("player_moved",jsonObject);
        spriteBatch.begin();
        for(Player player: players)
            player.draw(spriteBatch);
        spriteBatch.end();
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
