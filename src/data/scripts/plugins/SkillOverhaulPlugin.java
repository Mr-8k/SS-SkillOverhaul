package data.scripts.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.listeners.ListenerManagerAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;

import static com.fs.starfarer.api.impl.campaign.skills.BaseSkillEffectDescription.*;
import static com.fs.starfarer.api.impl.campaign.skills.NeuralLinkScript.INSTANT_TRANSFER_DP;
import static data.characters.skills.scripts.CarrierGroupSkillOverhaul.REPLACEMENT_RATE_PERCENT;
import static data.characters.skills.scripts.GunneryImplantsSkillOverhaul.RECOIL_BONUS;
import static data.characters.skills.scripts.PhaseCorpsSkillOverhaul.PHASE_SPEED_BONUS;
import static data.characters.skills.scripts.PhaseCorpsSkillOverhaul.PEAK_TIME_BONUS;
import static data.characters.skills.scripts.ContainmentProceduresSkillOverhaul.FUEL_USE_REDUCTION_MAX_PERCENT;
import static data.characters.skills.scripts.ContainmentProceduresSkillOverhaul.FUEL_USE_REDUCTION_MAX_FUEL;
import static data.characters.skills.scripts.MakeshiftEquipmentSkillOverhaul.SUPPLY_USE_REDUCTION_MAX_PERCENT;
import static data.characters.skills.scripts.MakeshiftEquipmentSkillOverhaul.SUPPLY_USE_REDUCTION_MAX_UNITS;


public class SkillOverhaulPlugin extends BaseModPlugin {

    public static boolean hasNexerelin;

    @Override
    public void onApplicationLoad() {
        hasNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
    }

    @Override
    public void onGameLoad(boolean newGame){

        SectorAPI sector = Global.getSector();
        if (sector.hasScript(OfficerManagerEvent.class)) {
            sector.removeScriptsOfClass(OfficerManagerEvent.class);

        }
        ListenerManagerAPI listeners = sector.getListenerManager();
/*        if (listeners.hasListener(OfficerManagerEvent.class)){
            listeners.removeListener(OfficerManagerEvent.class);
        }*/
        if (listeners.hasListenerOfClass(OfficerManagerEvent.class)){
            listeners.removeListenerOfClass(OfficerManagerEvent.class);
        }
        if (!sector.hasScript(OfficerManagerEventSkillOverhaul.class)) {
            sector.addScript(new OfficerManagerEventSkillOverhaul());
        }

        USE_RECOVERY_COST = false;
        
        FIGHTER_BAYS_THRESHOLD = 99999;
        OP_THRESHOLD = 99999;
        OP_LOW_THRESHOLD = 99999;
        OP_ALL_LOW_THRESHOLD = 99999;
        OP_ALL_THRESHOLD = 99999;
        PHASE_OP_THRESHOLD = 99999;
        MILITARIZED_OP_THRESHOLD = 99999;
        AUTOMATED_POINTS_THRESHOLD = 99999;

        REPLACEMENT_RATE_PERCENT = 25f;
        RECOIL_BONUS = 25f;
        PHASE_SPEED_BONUS = 25f;
        PEAK_TIME_BONUS = 90f;
        INSTANT_TRANSFER_DP = 99999;
        FUEL_USE_REDUCTION_MAX_PERCENT = 25;
        SUPPLY_USE_REDUCTION_MAX_PERCENT = 25;
        FUEL_USE_REDUCTION_MAX_FUEL = 99999;
        SUPPLY_USE_REDUCTION_MAX_UNITS = 99999;

    }
}
