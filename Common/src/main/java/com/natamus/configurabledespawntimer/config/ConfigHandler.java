package com.natamus.configurabledespawntimer.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.configurabledespawntimer.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 1, max = 2147483647) public static int globalItemDespawnTimeInTicks = 12000;
	@Entry(min = 1, max = 2147483647) public static int globalExperienceOrbDespawnTimeInTicks = 12000;
	@Entry public static boolean preventDespawnForPlayerItems = false;

	public static void initConfig() {
		configMetaData.put("globalItemDespawnTimeInTicks", Arrays.asList(
			"The delay in ticks when an item should despawn. Minecraft's default time is 6000 ticks. 1 second is 20 ticks."
		));
		configMetaData.put("globalExperienceOrbDespawnTimeInTicks", Arrays.asList(
			"The delay in ticks when experience orbs should despawn. Minecraft's default time is 6000 ticks. 1 second is 20 ticks."
		));
		configMetaData.put("preventDespawnForPlayerItems", Arrays.asList(
			"Whether items thrown/dropped by players should not despawn at all. Works for manual dropping and also on death. Overrides the other time settings."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}