package io.github.tehstoneman.betterstorage.tile.stand;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemArmorStand extends ItemBlock
{
	public ItemArmorStand( Block block )
	{
		super( block );
		setMaxStackSize( 1 );
	}

	@Override
	public EnumActionResult onItemUse( ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX,
			float hitY, float hitZ )
	{

		if( stack.stackSize == 0 )
			return EnumActionResult.FAIL;

		final Block blockClicked = world.getBlockState( pos ).getBlock();

		// If the block clicked is air or snow,
		// don't change the target coordinates, but set the side to 1 (top).
		// if ((blockClicked == Blocks.SNOW_LAYER) && ((world.getBlockMetadata(x, y, z) & 7) < 1)) side = 1;

		// If the block clicked is not replaceable,
		// adjust the coordinates depending on the side clicked.
		/*
		 * else if (!blockClicked.isReplaceable(world, x, y, z)) {
		 * switch (side) {
		 * case 0: y--; break;
		 * case 1: y++; break;
		 * case 2: z--; break;
		 * case 3: z++; break;
		 * case 4: x--; break;
		 * case 5: x++; break;
		 * }
		 * }
		 */

		// Return false if there's not enough world height left.
		if( pos.getY() >= world.getHeight() - 2 )
			return EnumActionResult.FAIL;

		final Block blockTop = world.getBlockState( pos.up() ).getBlock();

		// Return false if the block above isn't replaceable.
		if( !blockTop.isReplaceable( world, pos.up() ) )
			return EnumActionResult.FAIL;

		// Return false if the player can't edit any of the
		// two blocks the armor stand would occupy.
		if( !player.canPlayerEdit( pos, side, stack ) || !player.canPlayerEdit( pos.up(), side, stack ) )
			return EnumActionResult.FAIL;

		// Block block = field_150939_a;
		// Return false if there's an entity blocking the placement.
		// if (!world.canPlaceEntityOnSide(block, x, y, z, false, side, player, stack)) return false;

		// Actually place the block in the world,
		// play place sound and decrease stack size if successful.
		/*
		 * if (placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, 0)) {
		 * String sound = block.stepSound.func_150496_b();
		 * float volume = (block.stepSound.getVolume() + 1.0F) / 2.0F;
		 * float pitch = block.stepSound.getPitch() * 0.8F;
		 * world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5F, sound, volume, pitch);
		 * stack.stackSize--;
		 * }
		 */

		return EnumActionResult.SUCCESS;
	}
}
