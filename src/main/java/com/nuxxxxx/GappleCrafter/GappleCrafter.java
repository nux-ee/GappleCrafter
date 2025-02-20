package com.nuxxxxx.GappleCrafter;

import org.bukkit.plugin.java.JavaPlugin;

public class GappleCrafter extends JavaPlugin {

@Override
public void onEnable() {
    getLogger().info("GappleCrafter has been enabled!");
    saveDefaultConfig();  // Ensure the default config exists
    RecipeManager.setup(this);  // Load recipes and effects from the config
    getServer().getPluginManager().registerEvents(new ItemEffectListener(), this);  // Register the listener
}



    @Override
    public void onDisable() {
        getLogger().info("GappleCrafter has been disabled.");
    }
}
