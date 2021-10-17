package data.scripts.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.skills.PointDefense;

public class SkillOverhaulPlugin extends BaseModPlugin {

    public static boolean hasNexerelin;

    @Override
    public void onApplicationLoad() {
        hasNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
    }

    @Override
    public void onGameLoad(boolean newGame){
        PointDefense.FIGHTER_DAMAGE_BONUS = 50f;
    }
}
