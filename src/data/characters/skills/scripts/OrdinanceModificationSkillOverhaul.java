package data.characters.skills.scripts;

import com.fs.starfarer.api.characters.ShipSkillEffect;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class OrdinanceModificationSkillOverhaul {

	public static final float DAMAGE_TO_SHIELDS_BONUS = 10;
	public static final float HIT_STRENGTH_BONUS = 50;
	public static final float WEAPON_HITPOINTS_BONUS = 25;


	public static class Level1 implements ShipSkillEffect {

		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			stats.getWeaponHealthBonus().modifyPercent(id, WEAPON_HITPOINTS_BONUS);
		}

		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getWeaponHealthBonus().unmodify(id);
		}

		public String getEffectDescription(float level) {
			return "+" + (int)(WEAPON_HITPOINTS_BONUS) + "% weapon hitpoints";
		}

		public String getEffectPerLevelDescription() {
			return null;
		}

		public ScopeDescription getScopeDescription() {
			return ScopeDescription.PILOTED_SHIP;
		}

	}

	public static class Level2 implements ShipSkillEffect {
		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			stats.getDamageToTargetShieldsMult().modifyPercent(id, DAMAGE_TO_SHIELDS_BONUS);
		}
		
		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getDamageToTargetShieldsMult().unmodify(id);
		}
		
		public String getEffectDescription(float level) {
			return "+" + (int)(DAMAGE_TO_SHIELDS_BONUS) + "% damage to shields";
		}
		
		public String getEffectPerLevelDescription() {
			return null;
		}
		
		public ScopeDescription getScopeDescription() {
			return ScopeDescription.PILOTED_SHIP;
		}
	}
	
	public static class Level3 implements ShipSkillEffect {

		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			stats.getHitStrengthBonus().modifyPercent(id, HIT_STRENGTH_BONUS);
		}
		
		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getHitStrengthBonus().unmodify(id);
		}	
		
		public String getEffectDescription(float level) {
			return "+" + (int)(HIT_STRENGTH_BONUS) + "% hit strength for armor damage reduction calculation only";
		}
		
		public String getEffectPerLevelDescription() {
			return null;
		}
		
		public ScopeDescription getScopeDescription() {
			return ScopeDescription.PILOTED_SHIP;
		}
	}
	
	
}
