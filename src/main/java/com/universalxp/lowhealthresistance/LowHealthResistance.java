package com.universalxp.lowhealthresistance;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Gives a player infinite Resistance while they are at 1 heart (2 HP or less).
 * The effect is removed once they heal up to 2 hearts (4 HP or more).
 * Between those two values the current state is kept, so it does not flicker.
 */
public class LowHealthResistance implements ModInitializer {

	/** 1 heart = 2 HP. Effect is applied at or below this. */
	private static final float APPLY_AT_OR_BELOW = 2.0f;

	/** 2 hearts = 4 HP. Effect is removed at or above this. */
	private static final float REMOVE_AT_OR_ABOVE = 4.0f;

	/** 0 = Resistance I, 1 = Resistance II, 2 = Resistance III ... Change this to taste. */
	private static final int AMPLIFIER = 1;

	@Override
	public void onInitialize() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				tick(player);
			}
		});
	}

	private static void tick(ServerPlayerEntity player) {
		if (!player.isAlive()) {
			return;
		}

		float health = player.getHealth();
		StatusEffectInstance current = player.getStatusEffect(StatusEffects.RESISTANCE);
		boolean hasOurEffect = current != null
				&& current.getDuration() == StatusEffectInstance.INFINITE
				&& current.getAmplifier() == AMPLIFIER;

		if (health <= APPLY_AT_OR_BELOW) {
			if (!hasOurEffect) {
				// infinite duration, no particles, but show the icon in the HUD
				player.addStatusEffect(new StatusEffectInstance(
						StatusEffects.RESISTANCE,
						StatusEffectInstance.INFINITE,
						AMPLIFIER,
						false,
						false,
						true
				));
			}
		} else if (health >= REMOVE_AT_OR_ABOVE && hasOurEffect) {
			player.removeStatusEffect(StatusEffects.RESISTANCE);
		}
	}
}
