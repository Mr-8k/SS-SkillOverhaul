package data.characters.skills.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FleetDataAPI;
import com.fs.starfarer.api.characters.FleetTotalItem;
import com.fs.starfarer.api.characters.FleetTotalSource;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.ShipSkillEffect;
import com.fs.starfarer.api.characters.SkillSpecAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.SkillValueInformation;

public class FluxRegulation {
	
	public static float DISSIPATION_PERCENT = Global.getSettings().getFloat("DISSIPATION_PERCENT");
	public static float CAPACITY_PERCENT = Global.getSettings().getFloat("CAPACITY_PERCENT");
	public static final float VENT_RATE_BONUS = 25f;
	
	public static class Level1 extends SkillValueInformation implements ShipSkillEffect, FleetTotalSource {
		
		public FleetTotalItem getFleetTotalItem() {
			return getCombatOPTotal();
		}
		
		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			if (!isCivilian(stats)) {
				float disBonus = computeAndCacheThresholdBonus(stats, "fr_dis", DISSIPATION_PERCENT, ThresholdBonusType.OP);
				float capBonus = computeAndCacheThresholdBonus(stats, "fr_cap", CAPACITY_PERCENT, ThresholdBonusType.OP);
				stats.getFluxDissipation().modifyPercent(id, disBonus);
				stats.getFluxCapacity().modifyPercent(id, capBonus);
			}
		}
		
		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getFluxDissipation().unmodifyPercent(id);
			stats.getFluxCapacity().unmodifyPercent(id);
		}
		
		public String getEffectDescription(float level) {
			return null;
		}
		
//			float op = getTotalOP(data, cStats);
//			bonus = getThresholdBasedRoundedBonus(DISSIPATION_PERCENT, op, OP_THRESHOLD);
//			
//			float op = getTotalOP(data, cStats);
//			bonus = getThresholdBasedRoundedBonus(CAPACITY_PERCENT, op, FIGHTER_BAYS_THRESHOLD);
			
			
		public void createCustomDescription(MutableCharacterStatsAPI stats, SkillSpecAPI skill, 
											TooltipMakerAPI info, float width) {
			init(stats, skill);
			
			FleetDataAPI data = getFleetData(null);
			float disBonus = computeAndCacheThresholdBonus(data, stats, "fr_dis", DISSIPATION_PERCENT, ThresholdBonusType.OP);
			float capBonus = computeAndCacheThresholdBonus(data, stats, "fr_cap", CAPACITY_PERCENT, ThresholdBonusType.OP);
			
			info.addPara("+%s flux dissipation for combat ships", 0f, hc, hc,
					"" + (int) disBonus + "%");
			
//			addFighterBayThresholdInfo(info, data);
//			info.addSpacer(5f);
			
			info.addPara("+%s flux capacity for combat ships", 0f, hc, hc,
					"" + (int) capBonus + "%");
			//addOPThresholdInfo(info, data, stats);
			
			//info.addSpacer(5f);
		}
		
		public ScopeDescription getScopeDescription() {
			return ScopeDescription.ALL_SHIPS;
		}
	}

	public static class Level2 implements ShipSkillEffect {
		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			stats.getVentRateMult().modifyFlat(id, VENT_RATE_BONUS * 0.01f);
		}

		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getVentRateMult().unmodify(id);
		}

		public String getEffectDescription(float level) {
			return "Affects: piloted ship" + "\n" + "+" + (int) VENT_RATE_BONUS + "% flux dissipation rate while venting";
		}

		public String getEffectPerLevelDescription() {
			return null;
		}

		public ScopeDescription getScopeDescription() {
			return ScopeDescription.PILOTED_SHIP;
		}
	}
	


}





