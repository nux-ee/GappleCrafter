package com.nuxxxxx.GappleCrafter;

import org.bukkit.plugin.java.JavaPlugin;

public class GappleCrafter extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("GappleCrafter has been enabled!");
        saveDefaultConfig();  // Ensure the default config exists

        // Load recipes and effects from the config
        RecipeManager.setup(this);  

        // Register the event listener with the plugin instance passed
        getServer().getPluginManager().registerEvents(new ItemEffectListener(this), this);  
    }

    @Override
    public void onDisable() {
        getLogger().info("GappleCrafter has been disabled.");
        // You can add additional cleanup tasks if necessary
    }
}
