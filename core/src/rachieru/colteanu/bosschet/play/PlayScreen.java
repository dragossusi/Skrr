package rachieru.colteanu.bosschet.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

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

    //Button button;
    ProgressBar progressBar;
    Player me;
    List<Player> playerList = new ArrayList<Player>();
    SpriteBatch batch;

    public PlayScreen(SkrrGame game) {
        super(game);
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
                                playerList.add(new Player(
                                        object.getDouble("x"),
                                        object.getDouble("y")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("muie");
                            }
                        }
                    }
                });
    }

    @Override
    public void show() {
        super.show();
        //button = new Button(getSkin());
        //me.setX(0);
        //me.setY(0);
        //getStage().addActor(button);
        me = new Player();
        batch = new SpriteBatch();
        me.setX(0);
        me.setY(0);
        progressBar = new ProgressBar(0, 100, 1, false, getSkin());
        progressBar.setX(Gdx.graphics.getWidth() / 2);
        progressBar.setY(Gdx.graphics.getHeight() - 32);
        getStage().addActor(progressBar);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            me.setX(me.getX() + 10);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            me.setX(me.getX() - 10);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            me.setY(me.getY() + 10);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            me.setY(me.getY() - 10);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("x", me.getX());
            jsonObject.put("y", me.getY());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        batch.begin();
        me.draw(batch);
        for(Player player:playerList)
            player.draw(batch);
        batch.end();
        getGame().getSocket().emit("player_moved", jsonObject);
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
        getGame().getSocket().disconnect();
    }
}
