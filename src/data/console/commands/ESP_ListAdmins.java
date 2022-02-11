package data.console.commands;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.listeners.ListenerUtil;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI.SkillLevelAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;

import java.util.*;

public class ESP_ListAdmins implements BaseCommand {


    @Override
    public CommandResult runCommand(String args, CommandContext context) {

        int T0 = 0, T1 = 0, T2 = 0, T3 = 0, count = 0;
        List<PersonAPI> ids = new ArrayList<>();

        //iterate over all markets
        List<MarketAPI> markets = Global.getSector().getEconomy().getMarketsCopy();
        for (MarketAPI market : markets) {
            //update market
            ESP_ListAdmins.updateMarket(market);
            List<PersonAPI> people = market.getPeopleCopy();
            //iterate over all people on market
            for (PersonAPI person : people) {
                //proceed only if admin
                if (person.getMemoryWithoutUpdate().getBoolean("$ome_isAdmin")) {
                    // put 'em all in a list
                    ids.add(person);
                    Collections.sort(ids, new Comparator<PersonAPI>() {
                        @Override
                        public int compare(PersonAPI o1, PersonAPI o2) {
                            return o1.getMemoryWithoutUpdate().getInt("$ome_adminTier") -
                                    o2.getMemoryWithoutUpdate().getInt("$ome_adminTier");
                        }
                    });
                }
            }
        }


        //for everyone on the list, do stuff that outputs to console
        for (PersonAPI person: ids) {
            int tier = person.getMemoryWithoutUpdate().getInt("$ome_adminTier");
            
            // tier 0/3 have none or all of the skills, skip them
            if (tier == 0 || tier == 3) {
                Console.showMessage(
                        "Tier " + tier +
                                " admin " + person.getNameString() +
                                " located on " + person.getMarket().getName() +
                                " in the " + person.getMarket().getStarSystem().getName() +
                                " | Hidden?: " + person.getMarket().isHidden());
            } else {
                SkillLevelAPI skill1 = null;
                SkillLevelAPI skill2 = null;
                List<SkillLevelAPI> skills = person.getStats().getSkillsCopy();
                //get skills and apply to proper variable
                for (SkillLevelAPI skill : skills) {
                    //ignore skills with no levels
                    if (skill.getLevel() == 0 || !skill.getSkill().isAdminSkill()) {
                        continue;
                    }
                    if (skill1 == null) {
                        skill1 = skill;
                    } else {
                        skill2 = skill;
                    }
                }
                //need 2 cases since skill2 can be null
                if (skill2 != null) {
                    Console.showMessage("Tier " + tier +
                            " admin " + person.getNameString() +
                            " located on " + person.getMarket().getName() +
                            " in the " + person.getMarket().getStarSystem().getName() +
                            " | Hidden?: " + person.getMarket().isHidden() +
                            " | Skills: " + skill1 +
                            ", " + skill2);
                } else {
                    Console.showMessage("Tier " + tier +
                            " admin " + person.getNameString() +
                            " located on " + person.getMarket().getName() +
                            " in the " + person.getMarket().getStarSystem().getName() +
                            " | Hidden?: " + person.getMarket().isHidden() +
                            " | Skills: " + skill1);
                }

            }
            //sum them up
            count++;
            //now again, individually by tier
            switch (person.getMemoryWithoutUpdate().getInt("$ome_adminTier")) {
                case 0:
                    T0++;
                    break;
                case 1:
                    T1++;
                    break;
                case 2:
                    T2++;
                    break;
                case 3:
                    T3++;
                    break;
            }
        } // end list operations
        // format statistics in console
        Console.showMessage(
                "T0: " + T0 + " | percentage: " + (float) T0 / count * 100 + "%" + '\n' +
                "T1: " + T1 + " | percentage: " + (float) T1 / count * 100 + "%" + '\n' +
                "T2: " + T2 + " | percentage: " + (float) T2 / count * 100 + "%" + '\n' +
                "T3: " + T3 + " | percentage: " + (float) T3 / count * 100 + "%"
        );
        Console.showMessage("Total admins: " + count);
        //command successful
        return CommandResult.SUCCESS;
    }

    //stuff used to update markets' admins and officers
    public static void updateMarket(MarketAPI market) {
        updateOfficers(market);
        updateSubmarkets(market);
        ListenerUtil.reportPlayerOpenedMarketAndCargoUpdated(market);
    }

    private static void updateOfficers(MarketAPI market) {
        List<OfficerManagerEvent> managers = Global.getSector().getListenerManager().getListeners(OfficerManagerEvent.class);
        for (OfficerManagerEvent manager : managers) {
            manager.reportPlayerOpenedMarket(market);
        }
    }

    private static void updateSubmarkets(MarketAPI market) {
        for (SubmarketAPI submarket : market.getSubmarketsCopy()) {
            submarket.getPlugin().updateCargoPrePlayerInteraction();
            ListenerUtil.reportSubmarketCargoAndShipsUpdated(submarket);
        }
    }
}