package me.lavaflowglow.exampleGun.items.impl;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.lavaflowglow.exampleGun.ExampleGun;
import me.lavaflowglow.exampleGun.items.ItemHelper;

public class ItemGun extends ItemHelper {
	
	public Player player;
	
	public ItemGun(Player player) {
		this.player = player;
	}
	
	@Override
	public void generate() {
		ItemStack stack = new ItemStack(Material.GOLD_AXE);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ExampleGun.config.getGunName() + " (" + ExampleGun.config.getMaxAmmo() + "/" + ExampleGun.config.getMaxAmmo() + ")");
		meta.setLore(Arrays.asList(ExampleGun.config.getGunLore()));
		stack.setItemMeta(meta);
		NBTItem nbt = new NBTItem(stack);
		nbt.setString("gun-serial-number", player.getName() + "-" + player.getUniqueId() + "-" + System.currentTimeMillis() + "-" + System.nanoTime());
		nbt.setInteger("ammo", ExampleGun.config.getMaxAmmo());
		nbt.setBoolean("reloading", false);
		nbt.setLong("time", 0L);
		setStack(nbt.getItem());
	}
	
}
