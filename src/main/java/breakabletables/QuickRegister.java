package breakabletables;

import breakabletables.core.BreakableTables;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class QuickRegister
{
    public static void registerBlock(Block b, String name)
    {
    	registerBlock(b, new ItemBlock(b), name);
    }
    
    public static void registerBlock(Block b, ItemBlock i, String name)
    {
    	ResourceLocation res = new ResourceLocation(BreakableTables.MODID + ":" + name);
    	
    	if(i.getRegistryName() == null)
    	{
    		i.setRegistryName(res);
    	}
    	
    	GameRegistry.register(b, res);
        GameRegistry.register(i);
    }
    
    public static void registerItem(Item i, String name)
    {
    	ResourceLocation res = new ResourceLocation(BreakableTables.MODID + ":" + name);
        GameRegistry.register(i.setRegistryName(res));
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerBlockModel(Block block)
    {
    	registerItemModel(Item.getItemFromBlock(block));
    }
    
	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockModel(Block block, String... names)
	{
		registerItemModel(Item.getItemFromBlock(block), names);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item, String... names)
	{
		for(int i = 0; i < names.length; i++)
		{
			registerItemModel(item, i, names[i]);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockModel(Block block, int meta, String name)
	{
		registerItemModel(Item.getItemFromBlock(block), meta, name);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item, int meta, String name)
	{
		ModelResourceLocation modRes = new ModelResourceLocation(BreakableTables.MODID + ":" + name, "inventory");
		
		if(!item.getRegistryName().toString().equals(name))
		{
			ModelBakery.registerItemVariants(item, modRes);
		}
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, modRes);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerColors(IItemColor color, Item... items)
	{
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, items);
	}
}
