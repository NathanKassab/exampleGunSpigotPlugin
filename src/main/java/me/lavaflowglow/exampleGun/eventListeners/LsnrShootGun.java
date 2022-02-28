package me.lavaflowglow.exampleGun.eventListeners;

import org.bukkit.Sound;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import me.lavaflowglow.exampleGun.ExampleGun;

public class LsnrShootGun implements Listener {
	
	@EventHandler
	public void rightClick1(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			handleRightClick(e.getPlayer());
		}
		else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			handleLeftClick(e.getPlayer());
		}
	}
	
	public static void handleLeftClick(Player player) {
		if (player.getItemInHand() == null) {
			return;
		}
		NBTItem nbt = new NBTItem(player.getItemInHand());
		if (nbt.getString("gun-serial-number").isEmpty()) {
			return;
		}
		if (nbt.getInteger("ammo") >= ExampleGun.config.getMaxAmmo()) {
			return;
		}
		if (nbt.getBoolean("reloading")) {
			return;
		}
		nbt.setBoolean("reloading", true);
		player.setItemInHand(nbt.getItem());
		long reloadStart = System.currentTimeMillis();
		new Thread(() -> {
			ItemStack originalItem = player.getItemInHand();
			while (System.currentTimeMillis() - reloadStart < ExampleGun.config.getReloadTimeInMillis()) {
				ItemStack heldItem = player.getItemInHand();
				if (!heldItem.equals(originalItem)) {
					nbt.setBoolean("reloading", false);
					player.setItemInHand(nbt.getItem());
					return;
				}
				double percent = (double)(System.currentTimeMillis() - reloadStart) / ExampleGun.config.getReloadTimeInMillis();
				heldItem.setDurability((short)(heldItem.getType().getMaxDurability() * (1 - percent)));
				player.setItemInHand(heldItem);
				originalItem = heldItem;
				player.getWorld().playSound(player.getLocation(), Sound.PISTON_EXTEND, 1, 3);
				try {
					Thread.sleep(50);
				} catch (Exception e) {
					
				}
			}
			nbt.setBoolean("reloading", false);
			nbt.setInteger("ammo", ExampleGun.config.getMaxAmmo());
			ItemStack stack = nbt.getItem();
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ExampleGun.config.getGunName() + " ("+ ExampleGun.config.getMaxAmmo() + "/" + ExampleGun.config.getMaxAmmo() + ")");
			stack.setItemMeta(meta);
			player.setItemInHand(stack);
		}).start();
	}
	
	public static void handleRightClick(Player player) {
		if (player.getItemInHand() == null) {
			return;
		}
		NBTItem nbt = new NBTItem(player.getItemInHand());
		if (nbt.getString("gun-serial-number").isEmpty()) {
			return;
		}
		if (System.currentTimeMillis() - nbt.getLong("time") < ExampleGun.config.getFireDelayInMillis()) {
			return;
		}
		if (nbt.getInteger("ammo") <= 0) {
			player.getWorld().playSound(player.getLocation(), Sound.PISTON_EXTEND, 1, 0.2f);
			return;
		}
		if (nbt.getBoolean("reloading")) {
			return;
		}
		
		nbt.setInteger("ammo", nbt.getInteger("ammo") - 1);
		if (nbt.getInteger("ammo") > ExampleGun.config.getMaxAmmo()) {
			nbt.setInteger("ammo", ExampleGun.config.getMaxAmmo());
		}
		nbt.setLong("time", System.currentTimeMillis());
		
		ItemStack stack = nbt.getItem();
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ExampleGun.config.getGunName() + " (" + nbt.getInteger("ammo") + "/" + ExampleGun.config.getMaxAmmo() + ")");
		stack.setItemMeta(meta);
		
		player.setItemInHand(stack);
		
		// Shoot gun
		SmallFireball bullet = player.getWorld().spawn(player.getLocation().add(0, player.getEyeHeight(), 0).add(player.getLocation().getDirection().multiply(player.getLocation().getPitch() >= 52 ? 2.5 : 1.5)), 
				SmallFireball.class);
		bullet.setBounce(true);
		bullet.setVelocity(player.getLocation().getDirection().multiply(5));
		bullet.setShooter(player);
		bullet.setCustomName("bullet");
		bullet.setCustomNameVisible(false);
		
		// Play sound effect
		player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1, 3);
		player.getWorld().playSound(player.getLocation(), Sound.PISTON_RETRACT, 1, 3);
		
	}
	
}
