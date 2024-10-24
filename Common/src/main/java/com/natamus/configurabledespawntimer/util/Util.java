package com.natamus.configurabledespawntimer.util;

import com.natamus.collective.functions.DataFunctions;
import com.natamus.collective.functions.NumberFunctions;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Util {
	private static final String dirpath = DataFunctions.getConfigDirectory() + File.separator + Reference.MOD_ID;
	private static final File dir = new File(dirpath);
	private static final File file = new File(dirpath + File.separator + "specific_despawn_times.txt");

	public static HashMap<Item, Integer> itemSpecificDespawnTime = new HashMap<Item, Integer>();

	private static boolean loadedItemConfig = false;

	public static void attemptToLoadItemConfig(Level level) {
		if (loadedItemConfig) {
			return;
		}

		try {
			loadItemConfig(level);
		} catch (Exception ex) {
			System.out.println("[" + Reference.NAME + "] Error on loading the item config file.");
		}

		loadedItemConfig = true;
	}

	public static void loadItemConfig(Level level) throws IOException {
		itemSpecificDespawnTime = new HashMap<Item, Integer>();

		Registry<Item> itemRegistry = level.registryAccess().lookupOrThrow(Registries.ITEM);

		PrintWriter writer = null;
		if (!dir.isDirectory() || !file.isFile()) {
			dir.mkdirs();
			writer = new PrintWriter(dirpath + File.separator + "specific_despawn_times.txt", StandardCharsets.UTF_8);
		}
		else {
			String configcontent = Files.readString(Paths.get(dirpath + File.separator + "specific_despawn_times.txt"));
			for (String line : configcontent.split("\n")) {
				if (line.trim().endsWith(",")) {
					line = line.trim();
					line = line.substring(0, line.length() - 1).trim();
				}

				if (line.startsWith("//")) {
					continue;
				}

				if (!line.contains("' : ")) {
					continue;
				}

				String[] linespl = line.split("' : ");
				if (linespl.length < 2) {
					continue;
				}

				String itemResourceLocation = linespl[0].substring(1).trim();
				String despawnTimeString = linespl[1].trim();
				if (!NumberFunctions.isNumeric(despawnTimeString)) {
					continue;
				}

				int despawnTime = Integer.parseInt(despawnTimeString);
				if (despawnTime < 0) {
					continue;
				}

				Optional<Holder.Reference<Item>> itemOptionalReference = itemRegistry.get(ResourceLocation.parse(itemResourceLocation));
				if (itemOptionalReference.isEmpty()) {
					continue;
				}

				itemSpecificDespawnTime.put(itemOptionalReference.get().value(), despawnTime);
			}
		}

		if (writer != null) {
			List<String> itemStringList = new ArrayList<String>();
			for (ResourceLocation resourceLocation : itemRegistry.keySet()) {
				if (resourceLocation == null) {
					continue;
				}

				if (resourceLocation.toString().equals("minecraft:air")) {
					continue;
				}

				itemStringList.add(resourceLocation.toString());
			}

			Collections.sort(itemStringList);

			writer.println("// In this file you can set a specific despawn time for any item.");
			writer.println("// -1: Use global config time, 0: Never despawn, >1: despawn time in ticks.");
			for (String itemString : itemStringList) {
				writer.println("'" + itemString + "'" + " : -1,");
			}

			writer.close();
		}
	}
}
