package us.thezircon.play.silkyspawnerslite;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import us.thezircon.play.silkyspawnerslite.commands.CheckSpawner;
import us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.Silky;
import us.thezircon.play.silkyspawnerslite.events.breakSpawner;
import us.thezircon.play.silkyspawnerslite.events.placeSpawner;
import us.thezircon.play.silkyspawnerslite.events.playerJoin;
import us.thezircon.play.silkyspawnerslite.events.renameSpawner;
import us.thezircon.play.silkyspawnerslite.nms.*;
import us.thezircon.play.silkyspawnerslite.utils.Metrics;
import us.thezircon.play.silkyspawnerslite.utils.VersionChk;

import java.io.File;
import java.io.IOException;

public final class SilkySpawnersLITE extends JavaPlugin {

    private nmsHandler nms;

    public boolean UP2Date = true;

    @Override
    public void onEnable() {

        //Create Configs
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        createLangConfig();

        //NMS Setup & Checks
        setNMSVersion();

        //Commands
        getCommand("silky").setExecutor(new Silky());
        getCommand("checkspawner").setExecutor(new CheckSpawner());

        //Events & Listeners
        getServer().getPluginManager().registerEvents(new breakSpawner(), this);
        getServer().getPluginManager().registerEvents(new placeSpawner(), this);
        getServer().getPluginManager().registerEvents(new renameSpawner(), this);
        getServer().getPluginManager().registerEvents(new playerJoin(), this);

        //bStats
        Metrics metrics = new Metrics(this, 6579);

        //Version Check
        try {
        VersionChk.checkVersion(this.getName(),76103);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private void setNMSVersion() {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        if (version.contains("1_15")) {
            nms = new NMS_1_15();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[&bSilky&6Spawners&7] &7Loading &cNMS&7 version &e1.15"));
        } else if (version.contains("1_14")) {
            nms = new NMS_1_14();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[&bSilky&6Spawners&7] &7Loading &cNMS&7 version &e1.14"));
        } else if (version.contains("1_13")) {
            nms = new NMS_1_13();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[&bSilky&6Spawners&7] &7Loading &cNMS&7 version &e1.13"));
        }  if (version.contains("1_12")) {
            nms = new NMS_1_12();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[&bSilky&6Spawners&7] &7Loading &cNMS&7 version &e1.12"));
        } else {
            nms = new NMS_1_15();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[&bSilky&6Spawners&7] &4Unknown Version - Trying Latest &7Loading &cNMS&7 version &e1.15"));
        }
    }

    public nmsHandler getNMS() {
        return  nms;
    }

    //Lang.yml
    private File customLangFile;
    private FileConfiguration customLangConfig;

    public FileConfiguration getLangConfig() {
        return this.customLangConfig;
    }

    private void createLangConfig() {
        customLangFile = new File(getDataFolder(), "lang.yml");
        if (!customLangFile.exists()) {
            customLangFile.getParentFile().mkdirs();
            saveResource("lang.yml", false);
        }

        customLangConfig= new YamlConfiguration();
        try {
            customLangConfig.load(customLangFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void langReload(){
        customLangConfig = YamlConfiguration.loadConfiguration(customLangFile);
    }

}
