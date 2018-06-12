package rachieru.colteanu.bosschet.play.presenter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
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

    private PlayModel model;
    private IPlayViewDelegate viewDelegate;
    private Texture playerTexture;
    private Gson gson;

    public PlayPresenter(IPlayViewDelegate viewDelegate, String host,Texture texture) throws URISyntaxException {
        this.viewDelegate = viewDelegate;
        model = new PlayModel(host);
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
                            object.getDouble("y"),
                            new Vector2((float) object.getDouble("dirX"),
                                    (float) object.getDouble("dirY"))
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
                Map<String, Player> players = new HashMap<String, Player>();
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
            jsonObject.put("dirX", me.getDirection().x);
            jsonObject.put("dirY", me.getDirection().y);
            model.emitMoved(jsonObject);
        } catch (JSONException e) {
            viewDelegate.onError(e);
        }
    }
}
