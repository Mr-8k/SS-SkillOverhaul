package data.scripts.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.ListenerManagerAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import org.apache.log4j.Logger;

import java.util.List;

import static com.fs.starfarer.api.impl.campaign.skills.BaseSkillEffectDescription.*;
import static com.fs.starfarer.api.impl.campaign.skills.NeuralLinkScript.INSTANT_TRANSFER_DP;
import static data.characters.skills.scripts.CarrierGroupSkillOverhaul.REPLACEMENT_RATE_PERCENT;
import static data.characters.skills.scripts.ContainmentProceduresSkillOverhaul.FUEL_USE_REDUCTION_MAX_FUEL;
import static data.characters.skills.scripts.ContainmentProceduresSkillOverhaul.FUEL_USE_REDUCTION_MAX_PERCENT;
import static data.characters.skills.scripts.GunneryImplantsSkillOverhaul.RECOIL_BONUS;
import static data.characters.skills.scripts.MakeshiftEquipmentSkillOverhaul.SUPPLY_USE_REDUCTION_MAX_PERCENT;
import static data.characters.skills.scripts.MakeshiftEquipmentSkillOverhaul.SUPPLY_USE_REDUCTION_MAX_UNITS;
import static data.characters.skills.scripts.PhaseCorpsSkillOverhaul.PEAK_TIME_BONUS;
import static data.characters.skills.scripts.PhaseCorpsSkillOverhaul.PHASE_SPEED_BONUS;


public class SkillOverhaulPlugin extends BaseModPlugin {

    public static boolean hasNexerelin;
    public static Logger log = Global.getLogger(SkillOverhaulPlugin.class);
    //boolean onEnabled = false;

    @Override
    public void onApplicationLoad() {
//nex check
        hasNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
    }

//on first run, clean people for the new script
    @Override
    public void onEnabled(boolean wasEnabledBefore) {
        cleanUpPeople();
    }

    @Override
    public void onGameLoad(boolean newGame) {

        SectorAPI sector = Global.getSector();
        ListenerManagerAPI listeners = sector.getListenerManager();

//remove vanilla script + listener
        if (sector.hasScript(OfficerManagerEvent.class) && !sector.hasScript(OfficerManagerEventSkillOverhaul2.class)) {
            sector.removeScriptsOfClass(OfficerManagerEvent.class);
            log.info("Removed vanilla script");
        }

        if (listeners.hasListenerOfClass(OfficerManagerEvent.class) && !listeners.hasListenerOfClass(OfficerManagerEventSkillOverhaul2.class)) {
            listeners.removeListenerOfClass(OfficerManagerEvent.class);
            log.info("Removed vanilla listener");
        }

/// for save compatibility with 1.1.8
        if (sector.hasScript(OfficerManagerEventSkillOverhaul.class)) {
            sector.removeScriptsOfClass(OfficerManagerEventSkillOverhaul.class);
            cleanUpPeople();
            log.info("Removed script for compatibility with 1.1.8");
        }
        if (listeners.hasListenerOfClass(OfficerManagerEventSkillOverhaul.class)) {
            listeners.removeListenerOfClass(OfficerManagerEventSkillOverhaul.class);
            //cleanUpPeople();
            log.info("Removed listener for compatibility with 1.1.8");
        }
//put the good stuff in
        if (!sector.hasScript(OfficerManagerEventSkillOverhaul2.class)) {
            cleanUpPeople();
            sector.addScript(new OfficerManagerEventSkillOverhaul2());
            log.info("Added OfficerManagerEventSkillOverhaul2 script");
        }
        if (!listeners.hasListenerOfClass(OfficerManagerEventSkillOverhaul2.class)) {
            listeners.addListener(new OfficerManagerEventSkillOverhaul2());
            //cleanUpPeople();
            log.info("Added OfficerManagerEventSkillOverhaul2 listener");
        }

//when loading a save with no time pass, meaning the first made when the game starts, officers do not populate, this is an attempt to fix this
        boolean doNotReset = false;

        List<MarketAPI> markets = Global.getSector().getEconomy().getMarketsCopy();
        mainLoop:
        for (MarketAPI market : markets) {
            List<PersonAPI> people = market.getPeopleCopy();
            for (PersonAPI person : people) {
                if (person.getMemoryWithoutUpdate().getBoolean("$ome_hireable")) {
                    doNotReset = true;
                    break mainLoop;
                }
            }
        }

        if (!doNotReset) {
            if (sector.hasScript(OfficerManagerEventSkillOverhaul2.class)) {
                sector.removeScriptsOfClass(OfficerManagerEventSkillOverhaul2.class);
                sector.addScript(new OfficerManagerEventSkillOverhaul2());
                log.info("ESP doNotReset has triggered");
            }
        }

//oops
        //USE_RECOVERY_COST = false;

//Get the numbers from the settings file
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

//wipe away all officers/mercs/admins
    public void cleanUpPeople() {

        List<MarketAPI> markets = Global.getSector().getEconomy().getMarketsCopy();
        for (MarketAPI market : markets) {
            List<PersonAPI> people = market.getPeopleCopy();
            for (PersonAPI person : people) {
                if (person.getMemoryWithoutUpdate().getBoolean("$ome_hireable")) {
                    removePerson(market, person);
                }
            }
        }
    }

    public void removePerson (MarketAPI market, PersonAPI person){
        market.getCommDirectory().removePerson(person);
        market.removePerson(person);
        person.getMemoryWithoutUpdate().unset("$ome_hireable");
        person.getMemoryWithoutUpdate().unset("$ome_eventRef");
        person.getMemoryWithoutUpdate().unset("$ome_hiringBonus");
        person.getMemoryWithoutUpdate().unset("$ome_salary");
        log.info("Removed " + person.getPost() + " " + person.getNameString() + " from market " + market.getName() + " of faction " + market.getFaction().getId());
    }
}