package me.lavaflowglow.exampleGun.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lavaflowglow.exampleGun.commands.Command;
import me.lavaflowglow.exampleGun.items.impl.ItemGun;

public class CmdExampleGun extends Command {

	public CmdExampleGun() {
		super("ExampleGun");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command is only runnable by players");
			return true;
		}
		
		Player player = (Player)sender;
		ItemGun gun = new ItemGun(player);
		gun.generate();
		player.getInventory().addItem(gun.getItemStack());
		player.sendMessage("Gave you a gun");
		
		return true;
	}
	
}
