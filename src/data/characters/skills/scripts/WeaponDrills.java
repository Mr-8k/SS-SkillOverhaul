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

public class WeaponDrills {
	
	public static float DAMAGE_PERCENT = Global.getSettings().getFloat("DAMAGE_PERCENT");

	public static class Level1 extends SkillValueInformation implements ShipSkillEffect, FleetTotalSource {
		
		public FleetTotalItem getFleetTotalItem() {
			return getCombatOPTotal();
		}
		
		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			if (!isCivilian(stats)) {
				float damBonus = computeAndCacheThresholdBonus(stats, "wd_dam", DAMAGE_PERCENT, ThresholdBonusType.OP_LOW);
				stats.getBallisticWeaponDamageMult().modifyPercent(id, damBonus);
				stats.getEnergyWeaponDamageMult().modifyPercent(id, damBonus);
				stats.getMissileWeaponDamageMult().modifyPercent(id, damBonus);
			}
		}
			
		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getBallisticWeaponDamageMult().unmodifyPercent(id);
			stats.getEnergyWeaponDamageMult().unmodifyPercent(id);
			stats.getMissileWeaponDamageMult().unmodifyPercent(id);
		}
		
		public String getEffectDescription(float level) {
			return null;
		}
			
		public void createCustomDescription(MutableCharacterStatsAPI stats, SkillSpecAPI skill, 
											TooltipMakerAPI info, float width) {
			init(stats, skill);
			
			FleetDataAPI data = getFleetData(null);
			float damBonus = computeAndCacheThresholdBonus(data, stats, "wd_dam", DAMAGE_PERCENT, ThresholdBonusType.OP_LOW);
			
			info.addPara("+%f weapon damage for combat ships", 0f, hc, hc,
					"" + damBonus + "%");
			//addOPThresholdInfo(info, data, stats, OP_LOW_THRESHOLD);
			
			//info.addSpacer(5f);
		}
		
		public ScopeDescription getScopeDescription() {
			return ScopeDescription.ALL_SHIPS;
		}
	}
	


}





