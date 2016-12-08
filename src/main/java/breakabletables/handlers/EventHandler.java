package breakabletables.handlers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import breakabletables.core.BreakableTables;

public class EventHandler
{
	@SubscribeEvent
	public void onBlockInteract(PlayerInteractEvent.LeftClickBlock event)
	{
		IBlockState state = event.getWorld().getBlockState(event.getPos());
		
		if(state.getBlock() == Blocks.CRAFTING_TABLE)
		{
			event.setCanceled(true);
			event.setUseBlock(Result.DENY);
			event.setUseItem(Result.DENY);
			
			int type = event.getWorld().rand.nextInt(100);
			
			if(type < 10)
			{
				
				event.getWorld().setBlockState(event.getPos(), BreakableTables.greatWorkbench.getDefaultState());
			} else
			{
				
				event.getWorld().setBlockState(event.getPos(), BreakableTables.goodWorkbench.getDefaultState());
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockInteract(PlayerInteractEvent.RightClickBlock event)
	{
		IBlockState state = event.getWorld().getBlockState(event.getPos());
		
		if(state.getBlock() == Blocks.CRAFTING_TABLE)
		{
			event.setCanceled(true);
			event.setUseBlock(Result.DENY);
			event.setUseItem(Result.DENY);
			
			int type = event.getWorld().rand.nextInt(100);
			
			if(type < 10)
			{
				
				event.getWorld().setBlockState(event.getPos(), BreakableTables.greatWorkbench.getDefaultState());
			} else
			{
				
				event.getWorld().setBlockState(event.getPos(), BreakableTables.goodWorkbench.getDefaultState());
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event)
	{
		if(event.getWorld().isRemote)
		{
			return;
		}
		
		for(int i = event.getDrops().size() - 1; i >= 0; i--)
		{
			ItemStack stack = event.getDrops().get(i);
			
			if(stack.getItem() == Item.getItemFromBlock(Blocks.CRAFTING_TABLE))
			{
				event.getDrops().set(i, new ItemStack(BreakableTables.basicWorkbench, stack.stackSize, 0));
			}
		}
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.getModID().equals(BreakableTables.MODID))
		{
			ConfigHandler.config.save();
			ConfigHandler.initConfigs();
		}
	}
}
