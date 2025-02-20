package com.nux.ee.GappleCrafter;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Map;

public class RecipeManager {

    private static JavaPlugin plugin;

    public static void setup(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        loadRecipes();
    }

    public static void loadRecipes() {
        // Load the recipes and enable/disable settings from config files
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false); // Save the default config if it doesn't exist
        }

        plugin.reloadConfig();

        // Load custom recipes from recipe_config.yml
        File recipesFile = new File(plugin.getDataFolder(), "recipe_config.yml");
        if (!recipesFile.exists()) {
            plugin.saveResource("recipe_config.yml", false); // Save default recipes if not present
        }

        plugin.reloadConfig();
        Map<String, Object> recipes = plugin.getConfig().getConfigurationSection("recipes").getValues(false);

        // Hardcoded Notch Apple
        if (plugin.getConfig().getBoolean("recipes.notch_apple", false)) {
            addNotchAppleRecipe();
        }

        // Load and add custom recipes based on config
        for (String recipeKey : recipes.keySet()) {
            if (plugin.getConfig().getBoolean("recipes.custom_recipes." + recipeKey, false)) {
                Map<String, Object> recipeData = (Map<String, Object>) recipes.get(recipeKey);
                addCustomRecipe(recipeKey,
