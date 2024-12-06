package com.natamus.configurabledespawntimer.events;

import com.natamus.configurabledespawntimer.util.Util;
import net.minecraft.world.level.Level;

public class DespawnEvents {
	public static void onWorldLoad(Level level) {
		Util.attemptToLoadItemConfig(level);
	}
}
