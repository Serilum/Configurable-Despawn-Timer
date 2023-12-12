package com.natamus.configurabledespawntimer.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.configurabledespawntimer.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 1, max = 2147483647) public static int despawnTimeInTicks = 12000;

	public static void initConfig() {
		configMetaData.put("despawnTimeInTicks", Arrays.asList(
			"The delay in ticks when an item should despawn, called the lifespan. Minecraft's default time is 6000 ticks. 1 second is 20 ticks."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}