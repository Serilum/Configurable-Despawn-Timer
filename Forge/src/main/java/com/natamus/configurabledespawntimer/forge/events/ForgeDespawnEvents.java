package com.natamus.configurabledespawntimer.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.configurabledespawntimer.cmd.CommandCdt;
import com.natamus.configurabledespawntimer.events.DespawnEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeDespawnEvents {
	@SubscribeEvent
	public void onWorldLoad(LevelEvent.Load e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		DespawnEvents.onWorldLoad((ServerLevel)level);
	}

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent e) {
    	CommandCdt.register(e.getDispatcher());
    }
}
