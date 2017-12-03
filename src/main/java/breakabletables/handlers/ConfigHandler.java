package breakabletables.handlers;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import breakabletables.core.BT_Settings;
import breakabletables.core.BreakableTables;
import com.google.gson.JsonArray;

public class ConfigHandler
{
	public static Configuration config;
	static JsonArray defComs = new JsonArray();
	
	public static void initConfigs()
	{
		if(config == null)
		{
			BreakableTables.logger.log(Level.ERROR, "Config attempted to be loaded before it was initialised!");
			return;
		}
		
		config.load();
		
		BT_Settings.holdItems = config.getBoolean("Hold Items", Configuration.CATEGORY_GENERAL, false, "Enables tables to retain held items");
		BT_Settings.hideUpdates = config.getBoolean("Hide Updates", Configuration.CATEGORY_GENERAL, false, "Hides the update notifications");
		BT_Settings.damageChance = config.getFloat("Damage Chance", Configuration.CATEGORY_GENERAL, 10F, 0.01F, 100F, "% chance that a crafting table will take damage on use (max damage = 3, better tables scale relatively)");
		
		config.save();
		
		BreakableTables.logger.log(Level.INFO, "Configs loaded.");
	}
}
