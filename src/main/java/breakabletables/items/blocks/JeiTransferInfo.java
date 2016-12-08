package breakabletables.items.blocks;

import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import breakabletables.inventory.ContainerWorkbenchBreakable;

public class JeiTransferInfo implements IRecipeTransferInfo
{
	@Override
	public Class<? extends Container> getContainerClass()
	{
		return ContainerWorkbenchBreakable.class;
	}
	
	@Override
	public String getRecipeCategoryUid()
	{
		return VanillaRecipeCategoryUid.CRAFTING;
	}
	
	@Override
	public List<Slot> getRecipeSlots(Container container)
	{
		List<Slot> slots = new ArrayList<Slot>();
		for(int i = 1; i < 10; i++)
		{
			slots.add(container.getSlot(i));
		}
		return slots;
	}
	
	@Override
	public List<Slot> getInventorySlots(Container container)
	{
		List<Slot> slots = new ArrayList<Slot>();
		for(int i = 10; i < container.inventorySlots.size(); i++)
		{
			slots.add(container.getSlot(i));
		}
		return slots;
	}
}
