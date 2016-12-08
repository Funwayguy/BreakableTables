package breakabletables.handlers;

import breakabletables.client.GuiWorkbenchBreakable;
import breakabletables.inventory.ContainerWorkbenchBreakable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			return new ContainerWorkbenchBreakable(player.inventory, world, new BlockPos(x, y, z));
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			return new GuiWorkbenchBreakable(player.inventory, world, new BlockPos(x, y, z));
		}
		
		return null;
	}
}
