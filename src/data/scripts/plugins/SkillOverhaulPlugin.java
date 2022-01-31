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
        
        FIGHTER_BAYS_THRESHOLD = Global.getSettings().getFloat("SkillOverhaul_FIGHTER_BAYS_THRESHOLD");
        OP_THRESHOLD = Global.getSettings().getFloat("SkillOverhaul_OP_THRESHOLD");
        OP_LOW_THRESHOLD = Global.getSettings().getFloat("SkillOverhaul_OP_LOW_THRESHOLD");
        OP_ALL_LOW_THRESHOLD = Global.getSettings().getFloat("SkillOverhaul_OP_ALL_LOW_THRESHOLD");
        OP_ALL_THRESHOLD = Global.getSettings().getFloat("SkillOverhaul_OP_ALL_THRESHOLD");
        PHASE_OP_THRESHOLD = Global.getSettings().getFloat("SkillOverhaul_PHASE_OP_THRESHOLD");
        MILITARIZED_OP_THRESHOLD = Global.getSettings().getFloat("SkillOverhaul_MILITARIZED_OP_THRESHOLD");
        AUTOMATED_POINTS_THRESHOLD = Global.getSettings().getFloat("SkillOverhaul_AUTOMATED_POINTS_THRESHOLD");

        INSTANT_TRANSFER_DP = Global.getSettings().getFloat("SkillOverhaul_INSTANT_TRANSFER_DP");

        FUEL_USE_REDUCTION_MAX_FUEL = Global.getSettings().getFloat("SkillOverhaul_FUEL_USE_REDUCTION_MAX_FUEL");
        SUPPLY_USE_REDUCTION_MAX_UNITS = Global.getSettings().getFloat("SkillOverhaul_SUPPLY_USE_REDUCTION_MAX_UNITS");


        REPLACEMENT_RATE_PERCENT = Global.getSettings().getFloat("SkillOverhaul_REPLACEMENT_RATE_PERCENT");
        RECOIL_BONUS = Global.getSettings().getFloat("SkillOverhaul_RECOIL_BONUS");
        PHASE_SPEED_BONUS = Global.getSettings().getFloat("SkillOverhaul_PHASE_SPEED_BONUS");
        PEAK_TIME_BONUS = Global.getSettings().getFloat("SkillOverhaul_PEAK_TIME_BONUS");

        FUEL_USE_REDUCTION_MAX_PERCENT = Global.getSettings().getFloat("SkillOverhaul_FUEL_USE_REDUCTION_MAX_PERCENT");
        SUPPLY_USE_REDUCTION_MAX_PERCENT = Global.getSettings().getFloat("SkillOverhaul_SUPPLY_USE_REDUCTION_MAX_PERCENT");




    }
}
