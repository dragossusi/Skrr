package rachieru.colteanu.bosschet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import rachieru.colteanu.bosschet.start.StartScreen;

public class SkrrGame extends Game {
    private Socket socket;

    @Override
    public void create() {
        try {
            socket = IO.socket("http://localhost:3000");
            socket.connect();
        } catch (URISyntaxException e) {
            Gdx.app.exit();
        }
        setScreen(new StartScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        socket.disconnect();
        socket.close();
    }

    public Socket getSocket() {
        return socket;
    }
}