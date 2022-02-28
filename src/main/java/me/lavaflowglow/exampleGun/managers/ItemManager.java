package me.lavaflowglow.exampleGun.managers;

import java.util.ArrayList;
import java.util.Arrays;

import me.lavaflowglow.exampleGun.ExampleGun;
import me.lavaflowglow.exampleGun.items.ItemHelper;

public class ItemManager {
	
	public static ArrayList<ItemHelper> items = new ArrayList<>(Arrays.asList());
	
	public static void init() {
		for (ItemHelper itemHelper : items) {
			itemHelper.generate();
			ExampleGun.getInstance().getLogger().info("Generated the \"" + itemHelper.getItemStack().getItemMeta().getDisplayName() + "\" item");
		}
	}
	
}
