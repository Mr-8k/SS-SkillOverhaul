package data.characters.skills.scripts;

import com.fs.starfarer.api.characters.ShipSkillEffect;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class TargetAnalysisSkillOverhaul {
	
	public static final float DAMAGE_TO_MODULES_BONUS = 100;
//	public static final float DAMAGE_TO_SHIELDS_BONUS = 15;
//	public static final float HIT_STRENGTH_BONUS = 50;
	
	public static final float DAMAGE_BONUS = 15;
	public static final float DAMAGE_TO_CRUISERS = 15;
	public static final float DAMAGE_TO_CAPITALS = 15;


	public static class Level1 implements ShipSkillEffect {
		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			stats.getBallisticWeaponDamageMult().modifyPercent(id, DAMAGE_BONUS);
			stats.getEnergyWeaponDamageMult().modifyPercent(id, DAMAGE_BONUS);
			stats.getMissileWeaponDamageMult().modifyPercent(id, DAMAGE_BONUS);
		}

		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getBallisticWeaponDamageMult().unmodify(id);
			stats.getEnergyWeaponDamageMult().unmodify(id);
			stats.getMissileWeaponDamageMult().unmodify(id);
		}

		public String getEffectDescription(float level) {
			return "+" + (int)(DAMAGE_BONUS) + "% weapon damage";
		}

		public String getEffectPerLevelDescription() {
			return null;
		}

		public ScopeDescription getScopeDescription() {
			return ScopeDescription.PILOTED_SHIP;
		}
	}




	public static class Level4 implements ShipSkillEffect {

		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			stats.getDamageToTargetEnginesMult().modifyPercent(id, DAMAGE_TO_MODULES_BONUS);
			stats.getDamageToTargetWeaponsMult().modifyPercent(id, DAMAGE_TO_MODULES_BONUS);
		}
		
		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getDamageToTargetEnginesMult().unmodify(id);
			stats.getDamageToTargetWeaponsMult().unmodify(id);
		}
		
		public String getEffectDescription(float level) {
			return "+" + (int)(DAMAGE_TO_MODULES_BONUS) + "% damage to weapons and engines";
		}
		
		public String getEffectPerLevelDescription() {
			return null;
		}
		
		public ScopeDescription getScopeDescription() {
			return ScopeDescription.PILOTED_SHIP;
		}
	}


	
}
