package data.console.commands;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import org.apache.log4j.Logger;
import org.lazywizard.console.BaseCommand;

import java.util.List;

public class ESP_zcleanUpPeople implements BaseCommand {

    public static Logger log = Global.getLogger(ESP_zcleanUpPeople.class);

    @Override
    public BaseCommand.CommandResult runCommand(String args, BaseCommand.CommandContext context) {

        cleanUpPeople();

        return BaseCommand.CommandResult.SUCCESS;
    }

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
