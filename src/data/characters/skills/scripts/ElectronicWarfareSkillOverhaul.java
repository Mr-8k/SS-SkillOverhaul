package data.characters.skills.scripts;

import java.awt.Color;

import com.fs.starfarer.api.characters.DescriptionSkillEffect;
import com.fs.starfarer.api.characters.ShipSkillEffect;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.campaign.skills.BaseSkillEffectDescription;
import com.fs.starfarer.api.impl.campaign.skills.ElectronicWarfareScript;
import com.fs.starfarer.api.util.Misc;

public class ElectronicWarfareSkillOverhaul {
	
//	public static final float LEVEL_1_BONUS = 0f;
//	public static final float LEVEL_2_BONUS = 5f;
//	public static final float LEVEL_3_BONUS = 5f;
	
//	public static float PER_SHIP_BONUS = 2f;


	public static float getBase(HullSize hullSize) {
		float value = 0f;
		switch (hullSize) {
			case CAPITAL_SHIP: value = 4f; break;
			case CRUISER: value = 3f; break;
			case DESTROYER: value = 2f; break;
			case FRIGATE: value = 1f; break;
		}
		return value;
	}

	public static class Level0 implements DescriptionSkillEffect {
		public String getString() {
			String max = (int) ElectronicWarfareScript.BASE_MAXIMUM + "%";
//			String jammer = "+" + (int)ElectronicWarfareScript.PER_JAMMER + "%";
//			return "Sensor jammers grant " + jammer + " ECM rating. " +
//					"The uncapped total for each fleet is compared, " +
//					"and the losing side's weapon range is reduced by the difference, up to a maximum of " + max + " without skill. " +
//					"Does not apply to fighters; affects all weapons including missiles.";
//			return "Each fleet has an ECM rating, influenced by deployed sensor jammers (" + jammer + " each) " +
//					"and relevant hullmods. The total ECM rating for both fleets is compared, and the " +
//					"losing side's weapon range is reduced by the difference, up to an maximum of " +
//					"" + max + " without skills. Does not apply to fighters, affects all weapons including missiles.";
			return "*The total ECM rating for the deployed ships of both fleets is compared, and the " +
				   "losing side's weapon range is reduced by the difference, up to a maximum of " +
				   "" + max + ". Does not apply to fighters, affects all weapons including missiles.";
		}
		public Color[] getHighlightColors() {
			Color h = Misc.getHighlightColor();
			h = Misc.getDarkHighlightColor();
			return new Color[] {h, h};
		}
		public String[] getHighlights() {
			String max = (int)ElectronicWarfareScript.BASE_MAXIMUM + "%";
			String jammer = "+" + (int)ElectronicWarfareScript.PER_JAMMER + "%";
			return new String [] {jammer, max};
		}
		public Color getTextColor() {
			return null;
		}
	}
	
	public static class Level1A implements ShipSkillEffect {
		public void apply(MutableShipStatsAPI stats, HullSize hullSize, String id, float level) {
			if (!BaseSkillEffectDescription.isCivilian(stats)) {
				stats.getDynamic().getMod(Stats.ELECTRONIC_WARFARE_FLAT).modifyFlat(id, getBase(hullSize));
			}
		}
		public void unapply(MutableShipStatsAPI stats, HullSize hullSize, String id) {
			stats.getDynamic().getMod(Stats.ELECTRONIC_WARFARE_FLAT).unmodify(id);
		}
		public String getEffectDescription(float level) {
			return "+1-4" + "% to ECM rating of ships, depending on ship size";
			//return "Every deployed ship increases ECM rating* of fleet by " + (int) PER_SHIP_BONUS + "%";
			//return "Every deployed combat ship grants +" + (int) PER_SHIP_BONUS + "% to ECM rating* of fleet";
		}
		public String getEffectPerLevelDescription() {
			return null;
		}
		public ScopeDescription getScopeDescription() {
			return ScopeDescription.ALL_SHIPS;
		}
	}
	
}
