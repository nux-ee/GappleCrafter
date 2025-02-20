package com.nuxxxxx.GappleCrafter;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class ItemEffectListener implements Listener {

    private JavaPlugin plugin;

    public ItemEffectListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        Material itemType = event.getItem().getType();

        // Check if this item has custom effects based on the recipe config
        Map<String, Object> recipes = plugin.getConfig().getConfigurationSection("recipes.custom_recipes").getValues(false);
        for (String recipeKey : recipes.keySet()) {
            if (plugin.getConfig().getBoolean("recipes.custom_recipes." + recipeKey, false)) {
                Map<String, Object> recipeData = (Map<String, Object>) recipes.get(recipeKey);
                String result = (String) recipeData.get("result");
                if (Material.getMaterial(result) == itemType) {
                    List<Map<String, Object>> effects = (List<Map<String, Object>>) recipeData.get("effects");
                    if (effects != null) {
                        applyEffects(event, effects);
                    }
                }
            }
        }
    }

    private void applyEffects(PlayerItemConsumeEvent event, List<Map<String, Object>> effects) {
        for (Map<String, Object> effectData : effects) {
            String effectType = (String) effectData.get("type");
            int duration = (int) effectData.get("duration");
            int amplifier = (int) effectData.get("amplifier");

            PotionEffectType potionEffectType = getPotionEffectType(effectType);
            if (potionEffectType != null) {
                event.getPlayer().addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier));
            }
        }
    }

    private PotionEffectType getPotionEffectType(String effectType) {
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
}
