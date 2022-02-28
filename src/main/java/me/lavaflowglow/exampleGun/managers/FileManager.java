package me.lavaflowglow.exampleGun.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.nodes.Tag;

import me.lavaflowglow.exampleGun.ExampleGun;
import me.lavaflowglow.exampleGun.pojos.PojoConfig;

public class FileManager {
	
	public static File 	
		
		// Dirs
		pluginFolder = ExampleGun.getInstance().getDataFolder(),
		
		// Files
		mainConfig = new File(pluginFolder, "config.yml");
	
	public static void init() {
		
		if (!pluginFolder.exists()) {
			pluginFolder.mkdirs();
		}
		
		if (mainConfig.exists()) {
			ExampleGun.getInstance().getLogger().info("Found config file, loading it...");
			try {
				ExampleGun.config = readObj(mainConfig, PojoConfig.class);
				ExampleGun.getInstance().getLogger().info("Successfully loaded config");
			} catch (Exception e) {
				e.printStackTrace();
				ExampleGun.getInstance().getLogger().info("Failed to load config");
			}
		}else {
			write(mainConfig, ExampleGun.config);
			ExampleGun.getInstance().getLogger().info("Config file not found, created a new one");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T readObj(File file, Class<T> clazz) {
		return (T) new Yaml(new CustomClassLoaderConstructor(FileManager.class.getClassLoader())).loadAs(readString(file), clazz);
	}
	
	public static String readString(File file) {
		return new String(readBytes(file));
	}
	
	public static byte[] readBytes(File file) {
		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			
		}
		return new byte[0];
	}
	
	public static void write(File file, Object obj) {
		Yaml yaml = new Yaml();
		write(file, yaml.dumpAs(obj, Tag.MAP, FlowStyle.AUTO));
	}
	
	public static void write(File file, String string) {
		write(file, string.getBytes());
	}
	
	public static void write(File file, byte[] bytes) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					
				}
			}
		}
	}
	
}
