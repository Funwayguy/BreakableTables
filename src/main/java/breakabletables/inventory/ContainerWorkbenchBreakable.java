package breakabletables.inventory;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import breakabletables.blocks.BlockWorkbenchBreakable;
import breakabletables.blocks.TileEntityWorkbenchBreakable;
import breakabletables.core.BT_Settings;

public class ContainerWorkbenchBreakable extends ContainerWorkbench
{
	private final World world;
	private final BlockPos pos;
	
	public ContainerWorkbenchBreakable(InventoryPlayer invo, TileEntityWorkbenchBreakable tile, final World world, final BlockPos pos)
	{
		super(invo, world, pos);
		this.world = world;
		this.pos = pos;
		
		this.inventorySlots.clear();
		this.inventoryItemStacks.clear();
		
		this.craftMatrix = new InventoryCraftingProxy(this, 3, 3, tile);
		
		this.addSlotToContainer(new SlotCrafting(invo.player, this.craftMatrix, this.craftResult, 0, 124, 35)
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
		
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }
        
        for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(invo, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }
        
        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(invo, l, 8 + l * 18, 142));
        }
        
        this.onCraftMatrixChanged(this.craftMatrix);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.world.getBlockState(this.pos).getBlock() instanceof BlockWorkbenchBreakable && player.getDistanceSq(pos) < (16 * 16);
	}

    /**
     * Called when the container is closed.
     */
	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        InventoryPlayer inventoryplayer = playerIn.inventory;
        
        if(inventoryplayer.getItemStack() != null)
        {
            playerIn.dropItem(inventoryplayer.getItemStack(), false);
            inventoryplayer.setItemStack((ItemStack)null);
        }
        
        if(!BT_Settings.holdItems && !this.world.isRemote)
        {
            for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i)
            {
                ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);
                
                if (itemstack != null)
                {
                    playerIn.dropItem(itemstack, false);
                }
            }
        }
    }
}
