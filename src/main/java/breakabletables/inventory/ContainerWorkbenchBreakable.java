package breakabletables.inventory;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import breakabletables.blocks.BlockWorkbenchBreakable;

public class ContainerWorkbenchBreakable extends ContainerWorkbench
{
	private final World world;
	private final BlockPos pos;
	
	public ContainerWorkbenchBreakable(InventoryPlayer invo, final World world, final BlockPos pos)
	{
		super(invo, world, pos);
		this.world = world;
		this.pos = pos;
		this.inventorySlots.set(0, new SlotCrafting(invo.player, this.craftMatrix, this.craftResult, 0, 124, 35)
        {
        	@Override
        	public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
            {
                final IBlockState iblockstate = world.getBlockState(pos);
    			int i = iblockstate.getValue(BlockWorkbenchBreakable.DAMAGE);
    			
        		super.onPickupFromSlot(playerIn, stack);
        		
        		if(!playerIn.worldObj.isRemote && !playerIn.capabilities.isCreativeMode && iblockstate.getBlock() instanceof BlockWorkbenchBreakable && playerIn.getRNG().nextFloat() * 100F < ((BlockWorkbenchBreakable)iblockstate.getBlock()).getDamageChance())
        		{
        			final int n = i + 1;
        			
        			world.getMinecraftServer().addScheduledTask(new Runnable()
        			{
						@Override
						public void run()
						{
		        			if(n > 2)
		        			{
		        				world.destroyBlock(pos, false);
		        				
		        				if(!world.isRemote)
		        				{
		        					world.playEvent(1021, pos, 0);
		        				}
		        			} else
		        			{
		        				world.setBlockState(pos, iblockstate.withProperty(BlockWorkbenchBreakable.DAMAGE, n), 2);
		        			}
						}
        			});
        		}
            }
        });
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.world.getBlockState(this.pos).getBlock() instanceof BlockWorkbenchBreakable && player.getDistanceSq(pos) < (16 * 16);
	}
}
