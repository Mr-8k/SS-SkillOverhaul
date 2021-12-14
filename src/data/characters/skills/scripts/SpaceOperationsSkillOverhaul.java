package data.characters.skills.scripts;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.MarketSkillEffect;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.SkillSpecAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.campaign.skills.BaseSkillEffectDescription;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class SpaceOperationsSkillOverhaul {
	
	public static final float ACCESS = 0.3f;
	public static final float FLEET_SIZE = 25f;


	public static class Level0 extends BaseSkillEffectDescription implements MarketSkillEffect {
		public void apply(MarketAPI market, String id, float level) {
			//market.getAccessibilityMod().modifyFlat(id, ACCESS, "Space operations");
		}

		public void unapply(MarketAPI market, String id) {
			//market.getAccessibilityMod().unmodifyFlat(id);
		}

		public void createCustomDescription(MutableCharacterStatsAPI stats, SkillSpecAPI skill,
											TooltipMakerAPI info, float width) {
			init(stats, skill);
			float opad = 10f;
			Color c = Misc.getBasePlayerColor();
			//info.addPara("Affects: %s", opad + 5f, Misc.getGrayColor(), c, "fleet");
			info.addPara("Affects: %s", opad + 5f, Misc.getGrayColor(), c, "governed colony");
			info.addSpacer(opad);
			//String stuff = "+" + (int)Math.round(ACCESS * 100f) + "% accessibility\n" + "+" + (int)Math.round(FLEET_SIZE) + "% fleet size";
/*			String stuff = "+" + (int)Math.round(ACCESS * 100f) + "% accessibility";
			info.addPara((String) stuff, 0f, hc, hc,
					"" + "");*/
		}

		public String getEffectDescription(float level) {
			return null;
		}

		public String getEffectPerLevelDescription() {
			return null;
		}

		public ScopeDescription getScopeDescription() {
			return null;
		}
	}

	public static class Level1 implements MarketSkillEffect {
		public void apply(MarketAPI market, String id, float level) {
			market.getAccessibilityMod().modifyFlat(id, ACCESS, "Space operations");
		}

		public void unapply(MarketAPI market, String id) {
			market.getAccessibilityMod().unmodifyFlat(id);
		}

		public String getEffectDescription(float level) {
			return "+" + (int)Math.round(ACCESS * 100f) + "% accessibility";
		}
		
		public String getEffectPerLevelDescription() {
			return null;
		}

		public ScopeDescription getScopeDescription() {
			return ScopeDescription.GOVERNED_OUTPOST;
		}
	}
	
	public static class Level2 implements MarketSkillEffect {
		public void apply(MarketAPI market, String id, float level) {
			market.getStats().getDynamic().getMod(Stats.COMBAT_FLEET_SIZE_MULT).modifyFlat(id, FLEET_SIZE / 100f, "Fleet logistics");
		}
		
		public void unapply(MarketAPI market, String id) {
			market.getStats().getDynamic().getMod(Stats.COMBAT_FLEET_SIZE_MULT).unmodifyFlat(id);
		}
		
		public String getEffectDescription(float level) {
			//return "" + (int)Math.round(FLEET_SIZE) + "% larger fleets";
			return "+" + (int)Math.round(FLEET_SIZE) + "% fleet size";
		}
		
		public String getEffectPerLevelDescription() {
			return null;
		}
		
		public ScopeDescription getScopeDescription() {
			return ScopeDescription.GOVERNED_OUTPOST;
		}
	}
}


