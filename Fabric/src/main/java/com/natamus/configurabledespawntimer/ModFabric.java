package com.natamus.configurabledespawntimer;

import com.natamus.collective.check.RegisterMod;
import com.natamus.configurabledespawntimer.cmd.CommandCdt;
import com.natamus.configurabledespawntimer.events.DespawnEvents;
import com.natamus.configurabledespawntimer.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		ServerWorldEvents.LOAD.register((MinecraftServer server, ServerLevel level) -> {
			DespawnEvents.onWorldLoad(level);
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			CommandCdt.register(dispatcher);
		});
	}

	private static void setGlobalConstants() {

	}
}
