package com.nuxxxxx.GappleCrafter;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeManager {

    private static JavaPlugin plugin;
    private static Map<Material, List<PotionEffect>> itemEffectsMap = new HashMap<>();

    public static void setup(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        loadRecipes();
    }

    public static void loadRecipes() {
        // Load custom recipes and effects from recipe_config.yml
        File recipesFile = new File(plugin.getDataFolder(), "recipe_config.yml");
        if (!recipesFile.exists()) {
            plugin.saveResource("recipe_config.yml", false); // Save default recipes if not present
        }

        plugin.reloadConfig();
        Map<String, Object> recipes = plugin.getConfig().getConfigurationSection("recipes").getValues(false);

        // Load and store effects for each custom recipe
        for (String recipeKey : recipes.keySet()) {
            Map<String, Object> recipeData = (Map<String, Object>) recipes.get(recipeKey);

            // Check if the recipe has effects
            List<Map<String, Object>> effects = (List<Map<String, Object>>) recipeData.get("effects");
            if (effects != null && !effects.isEmpty()) {
                Material resultMaterial = Material.getMaterial((String) recipeData.get("result"));
                if (resultMaterial != null) {
                    List<PotionEffect> potionEffects = loadEffects(effects);
                    itemEffectsMap.put(resultMaterial, potionEffects);
                }
            }
        }
    }

    // Load effects from the configuration
    private static List<PotionEffect> loadEffects(List<Map<String, Object>> effectsData) {
        List<PotionEffect> effects = new ArrayList<>();
        for (Map<String, Object> effectData : effectsData) {
            String effectTypeStr = (String) effectData.get("type");
            int duration = (int) effectData.get("duration");
            int amplifier = (int) effectData.get("amplifier");

            PotionEffectType effectType = getPotionEffectType(effectTypeStr);
            if (effectType != null) {
                effects.add(new PotionEffect(effectType, duration, amplifier));
            }
        }
        return effects;
    }

    // Convert the effect type from String to PotionEffectType
    private static PotionEffectType getPotionEffectType(String effectType) {
        switch (effectType.toUpperCase()) {
            case "REGENERATION":
                return PotionEffectType.REGENERATION;
            case "SPEED":
                return PotionEffectType.SPEED;
            case "STRENGTH":
                return PotionEffectType.STRENGTH;
            case "JUMP":
                return PotionEffectType.JUMP_BOOST;
            default:
                return null;
        }
    }

    // Provide access to the effects map
    public static Map<Material, List<PotionEffect>> getItemEffectsMap() {
        return itemEffectsMap;
    }
}
