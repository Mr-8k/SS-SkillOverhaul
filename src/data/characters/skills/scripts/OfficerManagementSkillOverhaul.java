package data.characters.skills.scripts;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.CharacterStatsSkillEffect;
import com.fs.starfarer.api.characters.DescriptionSkillEffect;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.util.Misc;
import exerelin.utilities.StringHelper;
import data.scripts.plugins.SkillOverhaulPlugin;

public class OfficerManagementSkillOverhaul {
	
	public static float NUM_OFFICERS_BONUS = 2;
	public static float NUM_OFFICERS_BONUS2 = 2;
	public static float CP_BONUS = 2f;
	
	public static class Level0 implements DescriptionSkillEffect {
		public String getString() {
			int baseOfficers = (int)Global.getSector().getPlayerStats().getOfficerNumber().getBaseValue();
			
			return "The base maximum number of officers you're able to command is " + baseOfficers + ".";
		}
		public Color[] getHighlightColors() {
			Color h = Misc.getDarkHighlightColor();
			return new Color[] {h};
		}
		public String[] getHighlights() {
			String baseOfficers = "" + (int)Global.getSector().getPlayerStats().getOfficerNumber().getBaseValue();
			return new String [] {baseOfficers};
		}
		public Color getTextColor() {
			return null;
		}
	}
	public static class Level1  implements CharacterStatsSkillEffect {

		public void apply(MutableCharacterStatsAPI stats, String id, float level) {
			stats.getOfficerNumber().modifyFlat(id, NUM_OFFICERS_BONUS);
		}

		public void unapply(MutableCharacterStatsAPI stats, String id) {
			stats.getOfficerNumber().unmodify(id);
		}
		
		public String getEffectDescription(float level) {
			//return "Able to command up to " + (int) (max) + " officers";
			return "+" + (int)NUM_OFFICERS_BONUS + " to maximum number of officers you're able to command";
		}
		
		public String getEffectPerLevelDescription() {
			return null;
		}

		public ScopeDescription getScopeDescription() {
			return ScopeDescription.NONE;
		}
	}
	
	public static class Level1B implements CharacterStatsSkillEffect {

		public void apply(MutableCharacterStatsAPI stats, String id, float level) {
			stats.getCommandPoints().modifyFlat(id, CP_BONUS);
		}

		public void unapply(MutableCharacterStatsAPI stats, String id) {
			stats.getCommandPoints().unmodify(id);
		}
		
		public String getEffectDescription(float level) {
			return "+" + (int) CP_BONUS + " command points";
		}
		
		public String getEffectPerLevelDescription() {
			return null;
		}

		public ScopeDescription getScopeDescription() {
			return ScopeDescription.FLEET;
		}
	}

	public static class Level2  implements CharacterStatsSkillEffect {

		public void apply(MutableCharacterStatsAPI stats, String id, float level) {
			stats.getOfficerNumber().modifyFlat(id, NUM_OFFICERS_BONUS2);
		}

		public void unapply(MutableCharacterStatsAPI stats, String id) {
			stats.getOfficerNumber().unmodify(id);
		}

		public String getEffectDescription(float level) {
			//return "Able to command up to " + (int) (max) + " officers";
			return "+" + (int)NUM_OFFICERS_BONUS2 + " to maximum number of officers you're able to command";
		}

		public String getEffectPerLevelDescription() {
			return null;
		}

		public ScopeDescription getScopeDescription() {
			return ScopeDescription.NONE;
		}
	}

	public static class AgentBonus implements CharacterStatsSkillEffect {


		public static final int BONUS_AGENTS = 1;

		@Override
		public void apply(MutableCharacterStatsAPI stats, String id, float level) {
			if (SkillOverhaulPlugin.hasNexerelin) stats.getDynamic().getStat("nex_max_agents").modifyFlat(id, BONUS_AGENTS);
		}

		@Override
		public void unapply(MutableCharacterStatsAPI stats, String id) {
			if (SkillOverhaulPlugin.hasNexerelin) stats.getDynamic().getStat("nex_max_agents").unmodify(id);
		}

		@Override
		public String getEffectDescription(float level) {
			if (SkillOverhaulPlugin.hasNexerelin) return "+" + (int) BONUS_AGENTS + " " + StringHelper.getString("nex_agents", "skillBonusAgents");
			return "";
		}

		@Override
		public String getEffectPerLevelDescription() {
			return null;
		}

		@Override
		public ScopeDescription getScopeDescription() {
			return ScopeDescription.NONE;
		}
	}
}
