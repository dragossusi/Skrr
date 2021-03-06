package rachieru.colteanu.bosschet.play.model;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class PlayModel {

    private Socket socket;

    public PlayModel(String host) throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.timeout = 5000;
        socket = IO.socket(host,options);
    }

    public void getPlayers(Emitter.Listener listener) {
        socket.on("get_players", listener);
    }

    public void playerMoved(Emitter.Listener listener) {
        socket.on("player_moved", listener);
    }

    public void emitMoved(JSONObject object) {
        socket.emit("player_moved", object);
    }

    public void onNewPlayer(Emitter.Listener listener) {
        socket.on("new_player", listener);
    }

    public void connect(Emitter.Listener listener) {
        socket.connect();
        socket.on("socket_id", listener);
    }
}
