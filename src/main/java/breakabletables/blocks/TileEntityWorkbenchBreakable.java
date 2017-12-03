package breakabletables.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityWorkbenchBreakable extends TileEntity implements IInventory
{
	private final ItemStack[] items = new ItemStack[9];
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < this.items.length; ++i)
        {
            if (this.items[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.items[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        
        tag.setTag("Items", nbttaglist);
		
		return tag;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
        NBTTagList nbttaglist = tag.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < this.items.length)
            {
                this.items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }
	}
	
	@Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return oldState.getBlock() == newSate.getBlock();
    }

	@Override
	public String getName()
	{
		return Blocks.CRAFTING_TABLE.getUnlocalizedName();
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public int getSizeInventory()
	{
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if(index < 0 || index >= items.length)
		{
			return null;
		}
		
		return items[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(items, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if(index < 0 || index >= items.length)
		{
			return;
		}
		
		items[index] = stack;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return player.getDistance(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ()) <= 16;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for(int i = 0; i < items.length; i++)
		{
			items[i] = null;
		}
	}
}
