package com.nuxxxxx.GappleCrafter;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;  // Add this import
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
                addCustomRecipe(recipeKey, recipeData);
            }
        }
    }

    private static void addNotchAppleRecipe() {
        ItemStack notchApple = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemMeta meta = notchApple.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6Notch Apple");
            notchApple.setItemMeta(meta);
        }

        // Use NamespacedKey instead of the deprecated method
        NamespacedKey key = new NamespacedKey(plugin, "notch_apple");

        ShapedRecipe recipe = new ShapedRecipe(key, notchApple);  // Pass the NamespacedKey
        recipe.shape("GGG", "GAG", "GGG");
        recipe.setIngredient('G', Material.GOLD_BLOCK);  // Changed to GOLD_BLOCK
        recipe.setIngredient('A', Material.APPLE);

        plugin.getServer().addRecipe(recipe);
        plugin.getLogger().info("Notch Apple recipe has been enabled and added.");
    }

    private static void addCustomRecipe(String key, Map<String, Object> recipeData) {
        String result = (String) recipeData.get("result");
        int amount = (int) recipeData.get("amount");
        List<String> shape = (List<String>) recipeData.get("shape");
        Map<String, String> ingredients = (Map<String, String>) recipeData.get("ingredients");
        List<Map<String, Object>> effects = (List<Map<String, Object>>) recipeData.get("effects");

        Material resultMaterial = Material.getMaterial(result);
        if (resultMaterial == null) {
            plugin.getLogger().warning("Invalid material: " + result);
            return;
        }

        // Create the result item
        ItemStack resultItem = new ItemStack(resultMaterial, amount);

        // Use NamespacedKey instead of the deprecated method
        NamespacedKey recipeKey = new NamespacedKey(plugin, key);  // Generate the NamespacedKey

        // Create the shaped recipe
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, resultItem);  // Pass the NamespacedKey
        recipe.shape(shape.toArray(new String[0]));

        // Set the ingredients for the recipe
        for (Map.Entry<String, String> entry : ingredients.entrySet()) {
            char ingredientKey = entry.getKey().charAt(0);
            Material ingredientMaterial = Material.getMaterial(entry.getValue());
            if (ingredientMaterial != null) {
                recipe.setIngredient(ingredientKey, ingredientMaterial);
            } else {
                plugin.getLogger().warning("Invalid material: " + entry.getValue());
            }
        }

        // Add the recipe to the server
        plugin.getServer().addRecipe(recipe);

        // Apply the effects when the item is used
        applyEffects(resultItem, effects);
    }

    private static void applyEffects(ItemStack item, List<Map<String, Object>> effects) {
        if (effects != null) {
            for (Map<String, Object> effectData : effects) {
                String effectType = (String) effectData.get("type");
                int duration = (int) effectData.get("duration");
                int amplifier = (int) effectData.get("amplifier");

                PotionEffectType potionEffectType = getPotionEffectType(effectType);
                if (potionEffectType != null) {
                    plugin.getLogger().info("Applying effect: " + effectType);
                    // In practice, apply effects when the item is consumed, etc.
                    // For example: player.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier));
                }
            }
        }
    }

    private static PotionEffectType getPotionEffectType(String effectType) {
        switch (effectType.toUpperCase()) {
            case "REGENERATION":
                return PotionEffectType.REGENERATION;
            case "SPEED":
                return PotionEffectType.SPEED;
            case "STRENGTH":
                return PotionEffectType.STRENGTH;  // Correct constant for Strength
            case "JUMP":
                return PotionEffectType.JUMP_BOOST;  // Correct constant for Jump Boost
            default:
                return null;
        }
    }
}
