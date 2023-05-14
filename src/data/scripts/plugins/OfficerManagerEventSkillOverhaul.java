package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.WeightedRandomPicker;

import java.util.Random;

/**
 *
 * @author Alex Mosolov
 *
 * extends BaseEventPlugin for in-dev savefile compatibility reasons only
 *
 * Copyright 2019 Fractal Softworks, LLC
 */
public class OfficerManagerEventSkillOverhaul extends OfficerManagerEvent {

		@Override
		protected AvailableOfficer createAdmin(MarketAPI market, Random random) {
//		WeightedRandomPicker<MarketAPI> marketPicker = new WeightedRandomPicker<MarketAPI>();
//		for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()) {
//			marketPicker.add(market, market.getSize());
//		}
//		MarketAPI market = marketPicker.pick();
			if (market == null) return null;

			WeightedRandomPicker<Integer> tierPicker = new WeightedRandomPicker<Integer>();
			tierPicker.add(0, 25);
			tierPicker.add(1, 25);
			tierPicker.add(2, 25);
			tierPicker.add(3, 25);

			int tier = tierPicker.pick();

			PersonAPI person = createAdmin(market.getFaction(), tier, random);
			person.setFaction(Factions.INDEPENDENT);

			String hireKey = "adminHireTier" + tier;
			int hiringBonus = Global.getSettings().getInt(hireKey);

			int salary = (int) Misc.getAdminSalary(person);

			AvailableOfficer result = new AvailableOfficer(person, market.getId(), hiringBonus, salary);
			return result;
		}
}










