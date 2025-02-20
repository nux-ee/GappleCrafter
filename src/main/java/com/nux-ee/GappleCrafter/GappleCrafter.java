package com.nux.ee.GappleCrafter;

import org.bukkit.plugin.java.JavaPlugin;

public class GappleCrafter extends JavaPlugin {

@Override
public void onEnable() {
    getLogger().info("GappleCrafter has been enabled!");
    saveDefaultConfig();  // Save the default config if it doesn't exist
    RecipeManager.setup(this);  // Load recipes from the config
}


    @Override
    public void onDisable() {
        getLogger().info("GappleCrafter has been disabled.");
    }
}
