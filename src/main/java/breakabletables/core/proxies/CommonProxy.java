package breakabletables.core.proxies;

import breakabletables.core.BreakableTables;
import breakabletables.handlers.EventHandler;
import breakabletables.handlers.GuiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		EventHandler handler = new EventHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		NetworkRegistry.INSTANCE.registerGuiHandler(BreakableTables.instance, new GuiHandler());
	}

	public void registerRenderers()
	{
	}
}
