package breakabletables.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockWorkbench extends ItemMultiTexture
{
    public ItemBlockWorkbench(Block block)
    {
        super(block, block, new String[] {"intact", "slightlyDamaged", "veryDamaged"});
    }
}
