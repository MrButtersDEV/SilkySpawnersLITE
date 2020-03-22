package us.thezircon.play.silkyspawnerslite;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.Silky;
import us.thezircon.play.silkyspawnerslite.events.breakSpawner;
import us.thezircon.play.silkyspawnerslite.events.placeSpawner;
import us.thezircon.play.silkyspawnerslite.events.renameSpawner;
import us.thezircon.play.silkyspawnerslite.utils.Metrics;
import us.thezircon.play.silkyspawnerslite.utils.VersionChk;

import java.io.File;
import java.io.IOException;

public final class SilkySpawnersLITE extends JavaPlugin {

    @Override
    public void onEnable() {

        //Create Configs
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        createLangConfig();

        //Commands
        getCommand("silky").setExecutor(new Silky());

        //Events & Listeners
        getServer().getPluginManager().registerEvents(new breakSpawner(), this);
        getServer().getPluginManager().registerEvents(new placeSpawner(), this);
        getServer().getPluginManager().registerEvents(new renameSpawner(), this);

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
