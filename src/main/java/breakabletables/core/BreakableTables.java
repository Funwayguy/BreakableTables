package breakabletables.core;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.Logger;
import breakabletables.QuickRegister;
import breakabletables.blocks.BlockWorkbenchBreakable;
import breakabletables.blocks.BlockWorkbenchBreakable.TableQuality;
import breakabletables.core.proxies.CommonProxy;
import breakabletables.handlers.ConfigHandler;
import breakabletables.items.blocks.ItemBlockWorkbench;

@Mod(modid = BreakableTables.MODID, version = BreakableTables.VERSION, name = BreakableTables.NAME, guiFactory = BreakableTables.MODID + ".handlers.ConfigGuiFactory")
public class BreakableTables
{
    public static final String MODID = "breakabletables";
    public static final String VERSION = "1.0.2";
    public static final String NAME = "Breakable Tables";
    public static final String PROXY = MODID + ".core.proxies";
    public static final String CHANNEL = "BT_CHAN";
	
	@Instance(MODID)
	public static BreakableTables instance;
	
	@SidedProxy(clientSide = PROXY + ".ClientProxy", serverSide = PROXY + ".CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network ;
	public static Logger logger;
	
	public static Block basicWorkbench = new BlockWorkbenchBreakable(TableQuality.BASIC);
	public static Block goodWorkbench = new BlockWorkbenchBreakable(TableQuality.GOOD);
	public static Block greatWorkbench = new BlockWorkbenchBreakable(TableQuality.GREAT);
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);
    	ConfigHandler.config = new Configuration(event.getSuggestedConfigurationFile(), true);
    	ConfigHandler.initConfigs();
    	
    	proxy.registerHandlers();
    }
    
	@EventHandler
    public void init(FMLInitializationEvent event)
    {
		QuickRegister.registerBlock(basicWorkbench, new ItemBlockWorkbench(basicWorkbench), "workbench_basic");
		QuickRegister.registerBlock(goodWorkbench, new ItemBlockWorkbench(goodWorkbench), "workbench_good");
		QuickRegister.registerBlock(greatWorkbench, new ItemBlockWorkbench(greatWorkbench), "workbench_great");
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(basicWorkbench), new String[]{"PP", "PP"}, 'P', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(goodWorkbench), new String[]{"PPP", "PTP", "PPP"}, 'P', "plankWood", 'T', new ItemStack(basicWorkbench)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(greatWorkbench), new String[]{"PPP", "PTP", "PPP"}, 'P', "ingotIron", 'T', new ItemStack(goodWorkbench)));
		
    	proxy.registerRenderers();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
