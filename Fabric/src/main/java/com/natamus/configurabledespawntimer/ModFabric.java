package com.natamus.configurabledespawntimer;

import com.natamus.collective.check.RegisterMod;
import com.natamus.configurabledespawntimer.util.Reference;
import net.fabricmc.api.ModInitializer;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {

	}

	private static void setGlobalConstants() {

	}
}
