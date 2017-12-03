package breakabletables.inventory;

import javax.annotation.Nullable;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryCraftingProxy extends InventoryCrafting
{
	private final IInventory invo;
	private final Container eventHandler;
	
	public InventoryCraftingProxy(Container eventHandlerIn, int width, int height, IInventory invo)
	{
		super(eventHandlerIn, width, height);
		this.invo = invo;
		this.eventHandler = eventHandlerIn;
	}

    /**
     * Returns the number of slots in the inventory.
     */
	@Override
    public int getSizeInventory()
    {
        return invo.getSizeInventory();
    }

    /**
     * Returns the stack in the given slot.
     */
    @Nullable
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return invo.getStackInSlot(index);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return invo.removeStackFromSlot(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack = invo.decrStackSize(index, count);

        if (itemstack != null)
        {
            this.eventHandler.onCraftMatrixChanged(this);
        }

        return itemstack;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        this.invo.setInventorySlotContents(index, stack);
        this.eventHandler.onCraftMatrixChanged(this);
    }
    
    @Override
    public void clear()
    {
        invo.clear();
    }
}
