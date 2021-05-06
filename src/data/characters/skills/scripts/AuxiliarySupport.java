package data.characters.skills.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FleetDataAPI;
import com.fs.starfarer.api.characters.CharacterStatsSkillEffect;
import com.fs.starfarer.api.characters.FleetTotalItem;
import com.fs.starfarer.api.characters.FleetTotalSource;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.SkillSpecAPI;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.SkillValueInformation;

public class AuxiliarySupport {
	
	public static float AUXILIARY_EFFECT_BONUS = Global.getSettings().getFloat("AUXILIARY_EFFECT_BONUS");
	
	public static class Level1 extends SkillValueInformation implements CharacterStatsSkillEffect, FleetTotalSource {
		
		public FleetTotalItem getFleetTotalItem() {
			return getMilitarizedOPTotal();
		}
		
		public void apply(MutableCharacterStatsAPI stats, String id, float level) {
			FleetDataAPI data = null;
			if (stats.getFleet() != null) data = stats.getFleet().getFleetData();
			float auxBonus = computeAndCacheThresholdBonus(data, stats, "aux_effect", AUXILIARY_EFFECT_BONUS, ThresholdBonusType.MILITARIZED_OP);
			stats.getDynamic().getMod(Stats.AUXILIARY_EFFECT_ADD_PERCENT).modifyFlat(id, auxBonus);
		}
		public void unapply(MutableCharacterStatsAPI stats, String id) {
			stats.getDynamic().getMod(Stats.AUXILIARY_EFFECT_ADD_PERCENT).unmodifyFlat(id);
		}
		
		public String getEffectDescription(float level) {
			return null;
		}
			
		public void createCustomDescription(MutableCharacterStatsAPI stats, SkillSpecAPI skill, 
											TooltipMakerAPI info, float width) {
			init(stats, skill);
			
			FleetDataAPI data = getFleetData(null);
			float auxBonus = computeAndCacheThresholdBonus(data, stats, "aux_effect", AUXILIARY_EFFECT_BONUS, ThresholdBonusType.MILITARIZED_OP);
			
			HullModSpecAPI mil = Global.getSettings().getHullModSpec(HullMods.MILITARIZED_SUBSYSTEMS);
			HullModSpecAPI ep = Global.getSettings().getHullModSpec(HullMods.ESCORT_PACKAGE);
			HullModSpecAPI ap = Global.getSettings().getHullModSpec(HullMods.ASSAULT_PACKAGE);
			
			info.addPara("+%s to combat effects of " + mil.getDisplayName() + ", " +
					ep.getDisplayName() + ", and " + ap.getDisplayName(), 0f, hc, hc,
					"" + (int) auxBonus + "%");
			//addMilitarizedOPThresholdInfo(info, data, stats);
			
			//info.addSpacer(5f);
		}
		
	}
	


}





