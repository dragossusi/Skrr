package rachieru.colteanu.bosschet.play.view;

import org.json.JSONException;

import java.util.List;

import rachieru.colteanu.bosschet.ui.player.Player;

public interface IPlayViewDelegate {
    void onPlayerMoved(String id, double x, double y);

    void onPlayersReceived(List<Player> players);

    void onError(Throwable e);

    void onConnected(String id);
}
