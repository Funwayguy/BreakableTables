package breakabletables.handlers;

import breakabletables.blocks.TileEntityWorkbenchBreakable;
import breakabletables.client.GuiWorkbenchBreakable;
import breakabletables.inventory.ContainerWorkbenchBreakable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		
		if(ID == 0 && tile instanceof TileEntityWorkbenchBreakable)
		{
			return new ContainerWorkbenchBreakable(player.inventory, (TileEntityWorkbenchBreakable)tile, world, pos);
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		
		if(ID == 0 && tile instanceof TileEntityWorkbenchBreakable)
		{
			return new GuiWorkbenchBreakable(player.inventory, (TileEntityWorkbenchBreakable)tile, world, new BlockPos(x, y, z));
		}
		
		return null;
	}
}
