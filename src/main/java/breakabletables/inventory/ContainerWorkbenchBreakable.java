package breakabletables.inventory;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import breakabletables.blocks.BlockWorkbenchBreakable;

public class ContainerWorkbenchBreakable extends Container
{
    private InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    private IInventory craftResult = new InventoryCraftResult();
	private final World world;
	private final BlockPos pos;
	
	public ContainerWorkbenchBreakable(InventoryPlayer invo, final World world, final BlockPos pos)
	{
		this.world = world;
		this.pos = pos;
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
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.world));
    }
	
	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.world.isRemote)
        {
            for (int i = 0; i < 9; ++i)
            {
                ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

                if (itemstack != null)
                {
                    playerIn.dropItem(itemstack, false);
                }
            }
        }
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.world.getBlockState(this.pos).getBlock() instanceof BlockWorkbenchBreakable && player.getDistanceSq(pos) < (16 * 16);
	}
	
    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 10 && index < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (index >= 37 && index < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }
    
    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}
