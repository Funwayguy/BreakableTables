package breakabletables.core.proxies;

import breakabletables.QuickRegister;
import breakabletables.core.BreakableTables;

public class ClientProxy extends CommonProxy
{
	@Override
	public boolean isClient()
	{
		return true;
	}
	
	@Override
	public void registerHandlers()
	{
		super.registerHandlers();
	}
	
	@Override
	public void registerRenderers()
	{
		QuickRegister.registerBlockModel(BreakableTables.basicWorkbench, "workbench_basic_intact", "workbench_basic_slightly_damaged", "workbench_basic_very_damaged");
		QuickRegister.registerBlockModel(BreakableTables.goodWorkbench, "workbench_good_intact", "workbench_good_slightly_damaged", "workbench_good_very_damaged");
		QuickRegister.registerBlockModel(BreakableTables.greatWorkbench, "workbench_great_intact", "workbench_great_slightly_damaged", "workbench_great_very_damaged");
		super.registerRenderers();
	}
}
