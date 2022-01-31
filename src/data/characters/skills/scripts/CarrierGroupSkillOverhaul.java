package data.characters.skills.scripts;

import com.fs.starfarer.api.campaign.FleetDataAPI;
import com.fs.starfarer.api.characters.FleetTotalItem;
import com.fs.starfarer.api.characters.FleetTotalSource;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.ShipSkillEffect;
import com.fs.starfarer.api.characters.SkillSpecAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.Strings;
import com.fs.starfarer.api.impl.campaign.skills.BaseSkillEffectDescription;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class CarrierGroupSkillOverhaul {
	
	public static float REPLACEMENT_RATE_PERCENT = 50;
	public static float FIGHTER_DAMAGE_REDUCTION = 15f;
	
	public static float OFFICER_MULT = 1.5f;
	public static boolean isOfficer(MutableShipStatsAPI stats) {
		if (stats.getEntity() instanceof ShipAPI) {
			ShipAPI ship = (ShipAPI) stats.getEntity();
			if (ship == null) return false;
			return !ship.getCaptain().isDefault();
		} else {
			FleetMemberAPI member = stats.getFleetMember();
			if (member == null) return false;
			return !member.getCaptain().isDefault();
		}
	}
	public static class Level1 extends BaseSkillEffectDescription implements ShipSkillEffect, FleetTotalSource {
		
		public FleetTotalItem getFleetTotalItem() {
			return getFighterBaysTotal();
		}
		
		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			if (hasFighterBays(stats)) {
				float rateBonus = computeAndCacheThresholdBonus(stats, "cg_rep_rate", REPLACEMENT_RATE_PERCENT, ThresholdBonusType.FIGHTER_BAYS);
				if (isOfficer(stats)) rateBonus *= OFFICER_MULT;
				float timeMult = 1f / ((100f + rateBonus) / 100f);
				stats.getFighterRefitTimeMult().modifyMult(id, timeMult);
			}
		}
		
		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getFighterRefitTimeMult().unmodifyMult(id);
		}
		
		public String getEffectDescription(float level) {
			return null;
		}
			
		public void createCustomDescription(MutableCharacterStatsAPI stats, SkillSpecAPI skill, 
											TooltipMakerAPI info, float width) {
			init(stats, skill);
			
			FleetDataAPI data = getFleetData(null);
			float rateBonus = computeAndCacheThresholdBonus(data, stats, "cg_rep_rate", REPLACEMENT_RATE_PERCENT, ThresholdBonusType.FIGHTER_BAYS);
			
			info.addPara("+%s faster fighter replacement rate", 0f, hc, hc,
					//"" + (int) rateBonus + "%",
					"" + (int) REPLACEMENT_RATE_PERCENT + "%");
			//addFighterBayThresholdInfo(info, data);
			info.addPara(indent + "Effect increased by %s for ships with offcers, including flagship",
					0f, tc, hc, 
					"" + Misc.getRoundedValueMaxOneAfterDecimal(OFFICER_MULT) + Strings.X);
		}
		
		public ScopeDescription getScopeDescription() {
			return ScopeDescription.ALL_CARRIERS;
		}

	}

	public static class Level2 implements ShipSkillEffect {
		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			stats.getHullDamageTakenMult().modifyMult(id, 1f - FIGHTER_DAMAGE_REDUCTION / 100f);
			stats.getArmorDamageTakenMult().modifyMult(id, 1f - FIGHTER_DAMAGE_REDUCTION / 100f);
			stats.getShieldDamageTakenMult().modifyMult(id, 1f - FIGHTER_DAMAGE_REDUCTION / 100f);
		}

		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getHullDamageTakenMult().unmodify(id);
			stats.getArmorDamageTakenMult().unmodify(id);
			stats.getShieldDamageTakenMult().unmodify(id);
		}

		public String getEffectDescription(float level) {
			return "-" + (int)(FIGHTER_DAMAGE_REDUCTION) + "% damage taken by fighters";
		}

		public String getEffectPerLevelDescription() {
			return null;
		}

		public ScopeDescription getScopeDescription() {
			return ScopeDescription.ALL_FIGHTERS;
		}
	}


}





