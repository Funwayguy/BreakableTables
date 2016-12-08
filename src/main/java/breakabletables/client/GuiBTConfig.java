package breakabletables.client;

import breakabletables.core.BreakableTables;
import breakabletables.handlers.ConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiBTConfig extends GuiConfig
{
	public GuiBTConfig(GuiScreen parent)
	{
		super(parent, new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), BreakableTables.MODID, false, false, BreakableTables.NAME);
	}
}
