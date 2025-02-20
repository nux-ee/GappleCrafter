package com.yourname.gapplecrafter;

import org.bukkit.plugin.java.JavaPlugin;

public class GappleCrafter extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("GappleCrafter has been enabled!");
        RecipeManager.addRecipes(this);  // Add custom crafting recipes
    }

    @Override
    public void onDisable() {
        getLogger().info("GappleCrafter has been disabled.");
    }
}
