package data.scripts.plugins;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import org.apache.log4j.Logger;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignClockAPI;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.PlayerMarketTransaction;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.ColonyInteractionListener;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.AdminData;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI.SkillLevelAPI;
import com.fs.starfarer.api.characters.OfficerDataAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.SkillSpecAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.rulecmd.AddRemoveCommodity;
import com.fs.starfarer.api.impl.campaign.rulecmd.CallEvent.CallableEvent;
import com.fs.starfarer.api.plugins.OfficerLevelupPlugin;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Misc.Token;
import com.fs.starfarer.api.util.TimeoutTracker;
import com.fs.starfarer.api.util.WeightedRandomPicker;

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










