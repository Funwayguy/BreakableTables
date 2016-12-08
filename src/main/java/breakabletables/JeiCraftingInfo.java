package breakabletables;

import breakabletables.items.blocks.JeiTransferInfo;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class JeiCraftingInfo implements IModPlugin
{
	@Override
	public void register(IModRegistry registry)
	{
		registry.getRecipeTransferRegistry().addRecipeTransferHandler(new JeiTransferInfo());
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
	{
	}
}
