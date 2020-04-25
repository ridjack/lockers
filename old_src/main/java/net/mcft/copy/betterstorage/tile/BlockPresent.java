package io.github.tehstoneman.betterstorage.common.block;

import java.util.Random;

import io.github.tehstoneman.betterstorage.common.item.ItemBlockPresent;
import io.github.tehstoneman.betterstorage.common.tileentity.TileEntityPresent;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPresent extends BlockContainerBetterStorage
{
	public BlockPresent()
	{
		super( Material.WOOD );
		setCreativeTab( null );

		setHardness( 0.75f );
		setSoundType(SoundType.CLOTH);
	}

	@Override
	public AxisAlignedBB getBoundingBox( IBlockState state, IBlockAccess world, BlockPos pos )
	{
		return new AxisAlignedBB( 0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F );
	}

	@Override
	public boolean isOpaqueCube( IBlockState state )
	{
		return false;
	}

	@Override
	public boolean isFullCube( IBlockState state )
	{
		return false;
	}

	@Override
	@SideOnly( Side.CLIENT )
	public EnumBlockRenderType getRenderType( IBlockState state )
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public int quantityDropped( Random rand )
	{
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity( World world, int metadata )
	{
		return new TileEntityPresent();
	}
}
