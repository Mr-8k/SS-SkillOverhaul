package data.scripts.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

public class SkillOverhaulPlugin extends BaseModPlugin {

    public static boolean hasNexerelin;

    @Override
    public void onApplicationLoad() {
        final boolean hasNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
    }
}
