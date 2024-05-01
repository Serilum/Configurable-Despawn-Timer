package com.natamus.configurabledespawntimer.neoforge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.configurabledespawntimer.cmd.CommandCdt;
import com.natamus.configurabledespawntimer.events.DespawnEvents;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

@EventBusSubscriber
public class NeoForgeDespawnEvents {
	@SubscribeEvent
	public static void onWorldLoad(LevelEvent.Load e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		DespawnEvents.onWorldLoad(level);
	}

	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent e) {
		CommandCdt.register(e.getDispatcher());
	}
}
