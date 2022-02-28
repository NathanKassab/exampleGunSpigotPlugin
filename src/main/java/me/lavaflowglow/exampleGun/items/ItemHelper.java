package me.lavaflowglow.exampleGun.items;

import org.bukkit.inventory.ItemStack;

public abstract class ItemHelper {

	private ItemStack itemStack;

	public ItemStack getItemStack() {
		return itemStack;
	}

	protected void setStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public abstract void generate();

}
