package us.thezircon.play.silkyspawnerslite;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import us.thezircon.play.silkyspawnerslite.commands.CheckSpawner;
import us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.Silky;
import us.thezircon.play.silkyspawnerslite.events.breakSpawner;
import us.thezircon.play.silkyspawnerslite.events.placeSpawner;
import us.thezircon.play.silkyspawnerslite.events.playerJoin;
import us.thezircon.play.silkyspawnerslite.events.renameSpawner;
import us.thezircon.play.silkyspawnerslite.nms.*;
import us.thezircon.play.silkyspawnerslite.utils.Metrics;
import us.thezircon.play.silkyspawnerslite.utils.UpdateConfigs;
import us.thezircon.play.silkyspawnerslite.utils.VersionChk;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public final class SilkySpawnersLITE extends JavaPlugin {

    private static nmsHandler nms;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    public boolean UP2Date = true;

    @Override
    public void onEnable() {
        //Create & Update Configs
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (configFile.exists()) {
            UpdateConfigs.config();
        }
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        createLangConfig();
        UpdateConfigs.lang();

        //NMS Setup & Checks
        setNMSVersion();

        //Check for vault
        if (!setupEconomy()) {
            //log.warning(String.format("[%s] - Some features will be disabled due to not having Vault installed!", getDescription().getName()));
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bSilky&6Spawners&8] &eSome features will be disabled due to not having Vault installed!"));
            if (getConfig().getBoolean("chargeOnBreak.enabled")) {
                getConfig().set("chargeOnBreak.enabled", false);
                saveConfig();
                reloadConfig();
            }
        }

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
        String pluginName = this.getName();
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    VersionChk.checkVersion(pluginName, 76103);
                } catch (UnknownHostException e) {
                    VersionChk.noConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.run();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private void setNMSVersion() {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        if (version.contains("1_15")) {
            nms = new NMS_1_15();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bSilky&6Spawners&8] &7Loading &cNMS&7 version &e1.15"));
        } else if (version.contains("1_14")) {
            nms = new NMS_1_14();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bSilky&6Spawners&8] &7Loading &cNMS&7 version &e1.14"));
        } else if (version.contains("1_13")) {
            nms = new NMS_1_13();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bSilky&6Spawners&8] &7Loading &cNMS&7 version &e1.13"));
        } else if (version.contains("1_12")) {
            nms = new NMS_1_12();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bSilky&6Spawners&8] &7Loading &cNMS&7 version &e1.12"));
        } else if (version.contains("1_16_R2")) {
            nms = new NMS_1_16_R2();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bSilky&6Spawners&8] &7Loading &cNMS&7 version &e1.16.2/1.16.3"));
        }  else if (version.contains("1_16_R3")) {
             nms = new NMS_1_16_R3();
             getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bSilky&6Spawners&8] &7Loading &cNMS&7 version &e1.16.4"));
        } else if (version.contains("1_16")) {
            nms = new NMS_1_16();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bSilky&6Spawners&8] &7Loading &cNMS&7 version &e1.16"));
        } else if (version.contains("1_17")) {
            nms = new NMS_1_17();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bSilky&6Spawners&8] &7Loading &cNMS&7 version &e1.17"));
        } else {
            nms = new NMS_1_17();
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bSilky&6Spawners&8] &4Unknown Version - Trying Latest &7Loading &cNMS&7 version &e1.17"));
        }
    }

    public static nmsHandler getNMS() {
        return nms;
    }

    //Lang.yml
    private File customLangFile;
    private FileConfiguration customLangConfig;

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

    public FileConfiguration getLangConfig() {
        return this.customLangConfig;
    }

    public void langReload(){
        customLangConfig = YamlConfiguration.loadConfiguration(customLangFile);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

}
