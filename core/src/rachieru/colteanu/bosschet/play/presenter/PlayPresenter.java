package rachieru.colteanu.bosschet.play.presenter;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import io.socket.emitter.Emitter;
import rachieru.colteanu.bosschet.base.Disposable;
import rachieru.colteanu.bosschet.play.model.PlayModel;
import rachieru.colteanu.bosschet.play.view.IPlayViewDelegate;
import rachieru.colteanu.bosschet.ui.player.Player;

public class PlayPresenter implements Disposable {

    PlayModel model;
    IPlayViewDelegate viewDelegate;
    Texture playerTexture;
    Gson gson;

    public PlayPresenter(IPlayViewDelegate viewDelegate, Texture texture) throws URISyntaxException {
        this.viewDelegate = viewDelegate;
        model = new PlayModel();
        gson = new Gson();
        playerTexture = texture;
    }

    @Override
    public void dispose() {

    }

    public void init() {
        model.connect(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject object = (JSONObject) args[0];
                try {
                    viewDelegate.onConnected(object.getString("id"));
                } catch (JSONException e) {
                    viewDelegate.onError(e);
                }
            }
        });
        model.playerMoved(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject object = (JSONObject) args[0];
                try {
                    viewDelegate.onPlayerMoved(
                            object.getString("id"),
                            object.getDouble("x"),
                            object.getDouble("y")
                    );
                } catch (JSONException e) {
                    viewDelegate.onError(e);
                }
            }
        });
        model.getPlayers(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray jsonArray = (JSONArray) args[0];
                Map<String,Player> players = new HashMap<String, Player>();
                try {
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        players.put(
                                object.getString("id"),
                                new Player(
                                        playerTexture,
                                        object.getString("id"),
                                        object.getDouble("x"),
                                        object.getDouble("y"),
                                        object.getDouble("dirX"),
                                        object.getDouble("dirY"),
                                        object.getString("nume")
                                ));
                    }
                    viewDelegate.onPlayersReceived(players);
                } catch (JSONException e) {
                    viewDelegate.onError(e);
                }
            }
        });
        model.onNewPlayer(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject object = (JSONObject) args[0];
                    viewDelegate.newPlayer(new Player(
                            playerTexture,
                            object.getString("id"),
                            object.getDouble("x"),
                            object.getDouble("y"),
                            object.getDouble("dirX"),
                            object.getDouble("dirY"),
                            object.getString("nume")
                    ));
                } catch (JSONException e) {
                    viewDelegate.onError(e);
                }
            }
        });
    }

    public void emitMoved(Player me) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", me.getId());
            jsonObject.put("x", me.getX());
            jsonObject.put("y", me.getY());
            model.emitMoved(jsonObject);
        } catch (JSONException e) {
            viewDelegate.onError(e);
        }
    }
}
