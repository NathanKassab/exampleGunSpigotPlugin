package me.lavaflowglow.exampleGun.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;

public class LsnrBugFixes implements Listener {
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		ItemStack stack = e.getItemDrop().getItemStack();
		NBTItem nbt = new NBTItem(stack);
		if (nbt.getString("gun-serial-number").isEmpty()) {
			return;
		}
		if (nbt.getBoolean("reloading")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void switchItem(PlayerItemHeldEvent e) {
		ItemStack stack = e.getPlayer().getInventory().getItem(e.getPreviousSlot());
		if (stack == null) {
			return;
		}
		NBTItem nbt = new NBTItem(stack);
		if (nbt.getString("gun-serial-number").isEmpty()) {
			return;
		}
		if (nbt.getBoolean("reloading")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void damageItem(PlayerItemDamageEvent e) {
		ItemStack stack = e.getItem();
		NBTItem nbt = new NBTItem(stack);
		if (!nbt.getString("gun-serial-number").isEmpty()) {
			e.setCancelled(true);
		}
	}
	
}
