package breakabletables.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import breakabletables.core.BT_Settings;
import breakabletables.core.BreakableTables;

public class BlockWorkbenchBreakable extends Block implements ITileEntityProvider
{
    public static final PropertyInteger DAMAGE = PropertyInteger.create("damage", 0, 2);
    private final TableQuality quality;
    
	public BlockWorkbenchBreakable(TableQuality q)
	{
		super(Material.WOOD);
		this.setHardness(2.5F);
		this.setSoundType(SoundType.WOOD);
		this.setUnlocalizedName(BreakableTables.MODID + ".workbench_" + q.toString().toLowerCase());
		this.setDefaultState(getDefaultState().withProperty(DAMAGE, 0));
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.quality = q;
	}
	
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
    	return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(DAMAGE, MathHelper.clamp_int(meta, 0, 2));
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }
    
    @Override
    public int quantityDropped(Random rand)
    {
    	return this.quality == TableQuality.BASIC? 2 + rand.nextInt(1) : 1;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
    	return this.quality == TableQuality.BASIC? Items.STICK : Item.getItemFromBlock(BreakableTables.basicWorkbench);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(DAMAGE, MathHelper.clamp_int(meta, 0, 2));
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
    	return state.getValue(DAMAGE);
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
    	return new BlockStateContainer(this, new IProperty[]{DAMAGE});
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            playerIn.openGui(BreakableTables.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }
    
    public float getDamageChance()
    {
    	return BT_Settings.damageChance * quality.getDamageModifier();
    }
    
    public static enum TableQuality
    {
    	BASIC(1F),
    	GOOD(0.5F),
    	GREAT(0.1F);
    	
    	float mod = 1F;
    	
    	private TableQuality(float mod)
    	{
    		this.mod = mod;
    	}
    	
    	public float getDamageModifier()
    	{
    		return mod;
    	}
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityWorkbenchBreakable();
	}
}
