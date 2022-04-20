package data.console.commands;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.listeners.ListenerManagerAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import data.scripts.plugins.OfficerManagerEventSkillOverhaul;
import data.scripts.plugins.OfficerManagerEventSkillOverhaul2;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;

public class ESP_zCheckScripts implements BaseCommand {


    @Override
    public CommandResult runCommand(String args, CommandContext context) {

        SectorAPI sector = Global.getSector();
        ListenerManagerAPI listeners = sector.getListenerManager();

        if (sector.hasScript(OfficerManagerEvent.class)) {
            Console.showMessage("Vanilla script present");
        }
        if (listeners.hasListenerOfClass(OfficerManagerEvent.class)) {
            Console.showMessage("Vanilla listener present");
        }

/// for save compatibility with 1.1.8
        if (sector.hasScript(OfficerManagerEventSkillOverhaul.class)) {
            Console.showMessage("OfficerManagerEventSkillOverhaul script present, should not be");
        }
        if (listeners.hasListenerOfClass(OfficerManagerEventSkillOverhaul.class)) {
            Console.showMessage("OfficerManagerEventSkillOverhaul listener present, should not be");
        }
//put the good stuff in
        if (sector.hasScript(OfficerManagerEventSkillOverhaul2.class)) {
            Console.showMessage("OfficerManagerEventSkillOverhaul2 script present");
        }
        if (listeners.hasListenerOfClass(OfficerManagerEventSkillOverhaul2.class)) {
            Console.showMessage("OfficerManagerEventSkillOverhaul2 listener present");
        }
        listeners.addListener(OfficerManagerEventSkillOverhaul2.class);

        return CommandResult.SUCCESS;
    }
}
