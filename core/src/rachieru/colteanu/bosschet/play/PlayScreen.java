package rachieru.colteanu.bosschet.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import rachieru.colteanu.bosschet.SkrrGame;
import rachieru.colteanu.bosschet.base.BaseScreen;

/**
 * Created by Dragos on 28.03.2018.
 */

public class PlayScreen extends BaseScreen {

    Button button;

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

                    }
                });
    }

    @Override
    public void show() {
        super.show();
        button = new Button(getSkin());
        button.setX(0);
        button.setY(0);
        getStage().addActor(button);
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
