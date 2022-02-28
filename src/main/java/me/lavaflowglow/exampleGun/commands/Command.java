package me.lavaflowglow.exampleGun.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class Command implements CommandExecutor {

	private String name;

	public String getName() {
		return name;
	}
	
	public Command(String name) {
		this.name = name;
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		sender.sendMessage("The command \"" + name + "\" has not been set up");
		return false;
	}

}
