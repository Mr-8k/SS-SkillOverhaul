package data.console.commands;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import data.scripts.plugins.OfficerManagerEventSkillOverhaul2;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;

public class zESP_ResetScripts implements BaseCommand {

    @Override
    public CommandResult runCommand(String args, CommandContext context) {

        SectorAPI sector = Global.getSector();

        if (sector.hasScript(OfficerManagerEventSkillOverhaul2.class)) {
            Console.showMessage("OfficerManagerEventSkillOverhaul2 script present");
            sector.removeScriptsOfClass(OfficerManagerEventSkillOverhaul2.class);
            Console.showMessage("OfficerManagerEventSkillOverhaul2 script removed \nSAVE AND RELOAD YOUR GAME NOW");
        }

        return CommandResult.SUCCESS;
    }
}
