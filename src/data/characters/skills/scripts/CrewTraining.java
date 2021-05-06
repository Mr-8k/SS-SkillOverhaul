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

public class CrewTraining {
	
	public static float PEAK_SECONDS = Global.getSettings().getFloat("PEAK_SECONDS");
	public static float CR_PERCENT = Global.getSettings().getFloat("CR_PERCENT");
	
	public static class Level1 extends SkillValueInformation implements ShipSkillEffect, FleetTotalSource {
		
		public FleetTotalItem getFleetTotalItem() {
			return getCombatOPTotal();
		}
		
		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			if (!isCivilian(stats)) {
				float crBonus = computeAndCacheThresholdBonus(stats, "ct_cr", CR_PERCENT, ThresholdBonusType.OP);
				stats.getMaxCombatReadiness().modifyFlat(id, crBonus * 0.01f, "Crew training skill");
			}
		}
		
		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getMaxCombatReadiness().unmodify(id);
		}
		
		public String getEffectDescription(float level) {
			return null;
		}
			
		public void createCustomDescription(MutableCharacterStatsAPI stats, SkillSpecAPI skill, 
											TooltipMakerAPI info, float width) {
			init(stats, skill);
			
			FleetDataAPI data = getFleetData(null);
			float crBonus = computeAndCacheThresholdBonus(data, stats, "ct_cr", CR_PERCENT, ThresholdBonusType.OP);
			
			info.addPara("+%s maximum combat readiness for combat ships", 0f, hc, hc,
					"" + (int) crBonus + "%");
			//addOPThresholdInfo(info, data, stats, OP_THRESHOLD);
		}
	}
	public static class Level2 extends SkillValueInformation implements ShipSkillEffect, FleetTotalSource {
		
		public FleetTotalItem getFleetTotalItem() {
			return getCombatOPTotal();
		}
		
		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			if (!isCivilian(stats)) {
				float peakTimeBonus = computeAndCacheThresholdBonus(stats, "ct_peak", PEAK_SECONDS, ThresholdBonusType.OP);
				stats.getPeakCRDuration().modifyFlat(id, peakTimeBonus);
			}
		}
		
		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getPeakCRDuration().unmodifyFlat(id);
		}
		
		public String getEffectDescription(float level) {
			return null;
		}
		
		public void createCustomDescription(MutableCharacterStatsAPI stats, SkillSpecAPI skill, 
				TooltipMakerAPI info, float width) {
			init(stats, skill);
			
			FleetDataAPI data = getFleetData(null);
			float peakTimeBonus = computeAndCacheThresholdBonus(data, stats, "ct_peak", PEAK_SECONDS, ThresholdBonusType.OP);
			
			info.addPara("+%s seconds peak operating time for combat ships", 0f, hc, hc,
					"" + (int) peakTimeBonus);
			//addOPThresholdInfo(info, data, stats, OP_THRESHOLD);
		}
	}
	


}





