package com.serilum.configurabledespawntimer;

import com.natamus.collective.config.DuskConfigCustomFile;
import com.serilum.configurabledespawntimer.config.ConfigHandler;
import com.serilum.configurabledespawntimer.util.Reference;
import com.serilum.configurabledespawntimer.util.Util;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		DuskConfigCustomFile.addIdValues(Reference.MOD_ID, "specific_despawn_times.txt", "Specific Despawn Times", "Items with their own despawn time in ticks. 0 means they never despawn.", "item", "", Util::getSpecificDespawnTimes, Util::setSpecificDespawnTimes);
	}
}