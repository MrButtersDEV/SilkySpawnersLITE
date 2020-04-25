package us.thezircon.play.silkyspawnerslite.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.logging.Logger;

public class UpdateConfigs {

    private static final SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    public static void config() {
        //Config.yml
        YamlConfiguration conf = (YamlConfiguration) plugin.getConfig().getDefaults();
        Random r = new Random();
        for (String key : conf.getKeys(true)) {
            if (!plugin.getConfig().getKeys(true).contains(key)) {
                File oldConf = new File(plugin.getDataFolder(), "config.yml");
                File newConf = new File(plugin.getDataFolder(), "outdated-config-" + LocalDate.now() + " ("+r.nextInt(1000)+")"+".yml");

                oldConf.renameTo(newConf);
                plugin.getConfig().options().copyDefaults();
                plugin.saveDefaultConfig();

                log.severe("[SilkySpawnersLITE] Outdated Config for SilkySpawners! Please check your old config as a new one has been generated");

                break;
            }
        }
    }

    public static void lang() {
        //Lang.yml
        Random r = new Random();
        InputStream input = plugin.getResource("lang.yml");
        YamlConfiguration lang = YamlConfiguration.loadConfiguration(new InputStreamReader(input));
        for (String key : lang.getKeys(true)) {
            if (!plugin.getLangConfig().getKeys(true).contains(key)) {
                File oldLang = new File(plugin.getDataFolder(), "lang.yml");
                File newLang = new File(plugin.getDataFolder(),"outdated-lang-"+ LocalDate.now()+" ("+r.nextInt(1000)+")"+".yml");
                oldLang.renameTo(newLang);
                plugin.saveResource("lang.yml", false);
                plugin.langReload();

                log.severe("[SilkySpawnersLITE] Outdated Lang file for SilkySpawners! Please check your old config as a new one has been generated");

                break;
            }
        }
    }
}