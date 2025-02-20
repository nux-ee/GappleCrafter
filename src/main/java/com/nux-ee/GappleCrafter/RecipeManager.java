package com.nux.ee.GappleCrafter;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class RecipeManager {

    private static JavaPlugin plugin;

    public static void setup(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        loadRecipes();
    }

    public static void loadRecipes() {
        plugin.reloadConfig();
        List<Map<?, ?>> recipes = plugin.getConfig().getMapList("recipes");

        for (Map<?, ?> recipeData : recipes) {
            String result = (String) recipeData.get("result");
            int amount = (int) recipeData.get("amount");
            List<String> shape = (List<String>) recipeData.get("shape");
            Map<String, String> ingredients = (Map<String, String>) recipeData.get("ingredients");
            List<Map<String, Object>> effects = (List<Map<String, Object>>) recipeData.get("effects");

            // Create the result item
            Material resultMaterial = Material.getMaterial(result);
            if (resultMaterial == null) {
                plugin.getLogger().warning("Invalid material: " + result);
                continue;
            }
            ItemStack resultItem = new ItemStack(resultMaterial, amount);

            // Create the recipe
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

            // Register the recipe
            plugin.getServer().addRecipe(recipe);

            // Apply the effects
            applyEffects(resultItem, effects);
        }
    }

    public static void applyEffects(ItemStack item, List<Map<String, Object>> effects) {
        if (effects != null) {
            for (Map<String, Object> effectData : effects) {
                String effectType = (String) effectData.get("type");
                int duration = (int) effectData.get("duration");
                int amplifier = (int) effectData.get("amplifier");

                PotionEffectType potionEffectType = getPotionEffectType(effectType);
                if (potionEffectType != null) {
                    // Here you would apply the effect on item use (PlayerItemConsumeEvent or similar)
                    // We're just logging the effect for now
                    plugin.getLogger().info("Effect applied: " + effectType + " for " + duration + " ticks.");
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
