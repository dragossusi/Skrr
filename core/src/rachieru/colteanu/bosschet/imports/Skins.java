package rachieru.colteanu.bosschet.imports;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Map;

/**
 * Created by Dragos on 28.03.2018.
 */

public class Skins {
    private Map<String,Skin> skinMap;

    public Skin getSkin(String skinName) {
        return skinMap.get(skinName);
    }

}
