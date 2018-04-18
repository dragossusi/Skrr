package rachieru.colteanu.bosschet.play.presenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;
import rachieru.colteanu.bosschet.base.Disposable;
import rachieru.colteanu.bosschet.play.model.PlayModel;
import rachieru.colteanu.bosschet.play.view.IPlayViewDelegate;
import rachieru.colteanu.bosschet.ui.player.Player;

public class PlayPresenter implements Disposable {

    PlayModel model;
    IPlayViewDelegate viewDelegate;

    public PlayPresenter(IPlayViewDelegate viewDelegate) throws URISyntaxException {
        this.viewDelegate = viewDelegate;
        model = new PlayModel();
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
                List<Player> players = new ArrayList<Player>();
                try {
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        players.add(new Player(
                                object.getInt("x"),
                                object.getInt("y"),
                                object.getString("nume"),
                                object.getString("id")
                        ));
                    }
                    viewDelegate.onPlayersReceived(players);
                } catch (JSONException e) {
                    viewDelegate.onError(e);
                }
            }
        });
    }

    public void emitMoved(Player me) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("x", me.getX());
            jsonObject.put("y", me.getY());
            model.emitMoved(jsonObject);
        } catch (JSONException e) {
            viewDelegate.onError(e);
        }
    }
}
