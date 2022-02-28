package me.lavaflowglow.exampleGun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.dependency.DependsOn;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.permission.Permissions;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import me.lavaflowglow.exampleGun.commands.Command;
import me.lavaflowglow.exampleGun.commands.impl.CmdExampleGun;
import me.lavaflowglow.exampleGun.eventListeners.LsnrBugFixes;
import me.lavaflowglow.exampleGun.eventListeners.LsnrShootGun;
import me.lavaflowglow.exampleGun.managers.FileManager;
import me.lavaflowglow.exampleGun.managers.ItemManager;
import me.lavaflowglow.exampleGun.pojos.PojoConfig;

// Basic info
@org.bukkit.plugin.java.annotation.plugin.Plugin(name = "example gun", version = "0.0.1")
@Description("A simple plugin that adds a gun to the game")
@Author("lavaflowglow")
@Website("https://github.com/nathankassab")
@LogPrefix("ExampleGun")

// Dependencies
@DependsOn({
	
})

// Commands
@Commands({
	@org.bukkit.plugin.java.annotation.command.Command(name = "exampleGun", desc = "Gives you a gun", aliases = "gun", permission = "lava.example.gun", permissionMessage = "You do not have permission to run this command!", usage = "/<command>")
})

// Permissions
@Permissions({
	@Permission(name = "lava.example.gun", defaultValue = PermissionDefault.TRUE)
})

public class ExampleGun extends JavaPlugin {
	
	private static Plugin instance = null;
	
	public static Plugin getInstance() {
		return instance;
	}
	
	public static PojoConfig config = new PojoConfig();
	public static ArrayList<Command> commands = new ArrayList<>(Arrays.asList(new CmdExampleGun()));
	public static ArrayList<Listener> eventListeners = new ArrayList<>(Arrays.asList(new LsnrShootGun(), new LsnrBugFixes()));
	
	@Override
	public void onEnable() {
		
		// Set the instance
		instance = this;
		
		// Init the file manager
		FileManager.init();
		getLogger().info("Initialized the file manager");
		
		// Register Commands
		getLogger().info("Registering the commands...");
		for (Command command : commands) {
			getCommand(command.getName()).setExecutor(command);
			getLogger().info("Registered the \"" + command.getName() + "\" command");
		}
		getLogger().info("Successfully registered the commands");
		
		getLogger().info("Loading the itemstacks...");
		ItemManager.init();
		getLogger().info("Successfully loaded the itemstacks");
		
		getLogger().info("Registering the event listeners...");
		for (Listener listener : eventListeners) {
			getServer().getPluginManager().registerEvents(listener, this);
			getLogger().info("Class " + listener.getClass().getName() + " is now listening for events");
		}
		getLogger().info("Successfully registered the event listeners");
		
	}
	
}
