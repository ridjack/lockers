package io.github.tehstoneman.betterstorage.misc;

import io.github.tehstoneman.betterstorage.item.ItemDrinkingHelmet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SmallPotionEffect extends PotionEffect
{
	public SmallPotionEffect( Potion potionID, int duration, int amplifier )
	{
		super( potionID, Math.max( 1, duration / ItemDrinkingHelmet.maxUses ), amplifier );
	}

	public SmallPotionEffect( PotionEffect effect, PotionEffect active )
	{
		super( effect.getPotion(), getDuration( effect, active ), effect.getAmplifier() );
	}

	private static int getDuration( PotionEffect effect, PotionEffect active )
	{
		return effect.getDuration() / ItemDrinkingHelmet.maxUses + ( active != null ? active.getDuration() : 0 );
	}

	@Override
	public void performEffect( EntityLivingBase entity )
	{
		final int smallEffect = 6 * ( getAmplifier() + 1 ) / ItemDrinkingHelmet.maxUses;
		// Potion potion = Potion.potionTypes[getPotion()];
		/*
		 * if (entity.isEntityUndead() ? (potion == Potion.heal) : (potion == Potion.harm))
		 * entity.attackEntityFrom(DamageSource.magic, smallEffect);
		 * else if (potion == Potion.heal) entity.heal(smallEffect);
		 * else
		 */ super.performEffect( entity );
	}
}
