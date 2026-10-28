package com.serilum.configurabledespawntimer.events;

import com.serilum.configurabledespawntimer.util.Util;
import net.minecraft.world.level.Level;

public class DespawnEvents {
	public static void onWorldLoad(Level level) {
		Util.attemptToLoadItemConfig(level);
	}
}
