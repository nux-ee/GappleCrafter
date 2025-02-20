package com.nux.ee.GappleCrafter;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeManager {

    private static JavaPlugin plugin;

    public static void setup(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        loadRecipes();
    }

    public static void loadRecipes() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false); // Save the default config if it doesn't exist
        }

        plugin.reloadConfig();
        List<Map<String, Object>> recipes = (List<Map<String, Object>>) plugin.getConfig().getList("recipes");

        for (Map<String, Object> recipeData : recipes) {
            String result = (String) recipeData.get("result");
            int amount = (int) recipeData.get("amount");
            List<String> shape = (List<String>) recipeData.get("shape");
            Map<String, String> ingredients = (Map<String, String>) recipeData.get("ingredients");
            List<Map<String, Object>> effects = (List<Map<String, Object>>) recipeData.get("effects");

            // Parse the result item (e.g., "POTATO")
            Material resultMaterial = Material.getMaterial(result);
            if (resultMaterial == null) {
                plugin.getLogger().warning("Invalid material: " + result);
                continue;
            }

            // Create the result item
            ItemStack resultItem = new ItemStack(resultMaterial, amount);

            // Create the shaped recipe
            ShapedRecipe recipe = new ShapedRecipe(resultItem);
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
    }

    public static void applyEffects(ItemStack item, List<Map<String, Object>> effects) {
        // Apply potion effects based on the item's use
        if (effects != null) {
            for (Map<String, Object> effectData : effects) {
                String effectType = (String) effectData.get("type");
                int duration = (int) effectData.get("duration");
                int amplifier = (int) effectData.get("amplifier");

                PotionEffectType potionEffectType = getPotionEffectType(effectType);
                if (potionEffectType != null) {
                    // Here you can apply effects based on item usage, such as PlayerItemConsumeEvent
                    // For simplicity, we're just logging the effects for now
                    plugin.getLogger().info("Applying effect: " + effectType);
                    // This is just a demonstration, in practice you'd apply these when the player consumes or uses the item.
                    // e.g., player.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier));
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
                return PotionEffectType.INCREASE_DAMAGE;
            case "JUMP":
                return PotionEffectType.JUMP;
            default:
                return null;
        }
    }
}
