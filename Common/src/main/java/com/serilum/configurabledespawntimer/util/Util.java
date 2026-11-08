package com.serilum.configurabledespawntimer.util;

import com.natamus.collective.functions.DataFunctions;
import com.natamus.collective.functions.NumberFunctions;
import com.serilum.configurabledespawntimer.data.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
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
			Constants.logger.warn("[" + Reference.NAME + "] Error on loading the item config file.");
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

				String itemIdentifier = linespl[0].substring(1).trim();
				String despawnTimeString = linespl[1].trim();
				if (!NumberFunctions.isNumeric(despawnTimeString)) {
					continue;
				}

				int despawnTime = Integer.parseInt(despawnTimeString);
				if (despawnTime < 0) {
					continue;
				}

				Optional<Holder.Reference<Item>> itemOptionalReference = itemRegistry.get(Identifier.parse(itemIdentifier));
				if (itemOptionalReference.isEmpty()) {
					continue;
				}

				itemSpecificDespawnTime.put(itemOptionalReference.get().value(), despawnTime);
			}
		}

		if (writer != null) {
			List<String> itemStringList = new ArrayList<String>();
			for (Identifier identifier : itemRegistry.keySet()) {
				if (identifier == null) {
					continue;
				}

				if (identifier.toString().equals("minecraft:air")) {
					continue;
				}

				itemStringList.add(identifier.toString());
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

	public static String getSpecificDespawnTimes() {
		List<String> despawnTimes = new ArrayList<>();
		for (String line : getConfigLines()) {
			String itemId = getConfigLineItemId(line);
			String despawnTime = getConfigLineDespawnTime(line);
			if (itemId == null || !NumberFunctions.isNumeric(despawnTime)) {
				continue;
			}

			if (Double.parseDouble(despawnTime) >= 0) {
				despawnTimes.add(itemId + "=" + despawnTime);
			}
		}
		return String.join(",", despawnTimes);
	}

	public static void setSpecificDespawnTimes(MinecraftServer server, String despawnTimesText) {
		HashMap<String, String> despawnTimes = getDespawnTimesFromText(despawnTimesText);

		List<String> lines = new ArrayList<>();
		for (String line : getConfigLines()) {
			String itemId = getConfigLineItemId(line);
			if (itemId == null) {
				lines.add(line);
				continue;
			}

			lines.add(getConfigLine(itemId, despawnTimes.getOrDefault(itemId, "-1")));
			despawnTimes.remove(itemId);
		}

		for (String itemId : despawnTimes.keySet()) {
			lines.add(getConfigLine(itemId, despawnTimes.get(itemId)));
		}

		try {
			PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
			for (String line : lines) {
				writer.println(line);
			}
			writer.close();
		}
		catch (IOException ignored) {
			return;
		}

		reloadItemConfig(server);
	}

	private static HashMap<String, String> getDespawnTimesFromText(String despawnTimesText) {
		HashMap<String, String> despawnTimes = new HashMap<>();
		for (String entry : despawnTimesText.split(",")) {
			String[] entryParts = entry.split("=");
			if (entryParts.length != 2 || !NumberFunctions.isNumeric(entryParts[1].strip())) {
				continue;
			}

			String wholeTicks = String.valueOf(Math.round(Double.parseDouble(entryParts[1].strip())));
			despawnTimes.put(entryParts[0].strip(), wholeTicks);
		}
		return despawnTimes;
	}

	private static void reloadItemConfig(MinecraftServer server) {
		if (server == null) {
			loadedItemConfig = false;
			return;
		}

		try {
			loadItemConfig(server.overworld());
		}
		catch (IOException ignored) { }
	}

	private static List<String> getConfigLines() {
		try {
			return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		}
		catch (IOException ignored) {
			return new ArrayList<>();
		}
	}

	private static String getConfigLineItemId(String line) {
		String trimmedLine = line.trim();
		if (trimmedLine.startsWith("//") || !trimmedLine.startsWith("'") || !trimmedLine.contains("' : ")) {
			return null;
		}
		return trimmedLine.split("' : ")[0].substring(1).trim();
	}

	private static String getConfigLineDespawnTime(String line) {
		String trimmedLine = line.trim();
		if (trimmedLine.endsWith(",")) {
			trimmedLine = trimmedLine.substring(0, trimmedLine.length() - 1).trim();
		}

		String[] lineParts = trimmedLine.split("' : ");
		if (lineParts.length < 2) {
			return "";
		}
		return lineParts[1].trim();
	}

	private static String getConfigLine(String itemId, String despawnTime) {
		return "'" + itemId + "' : " + despawnTime + ",";
	}
}
