package com.nuxxxxx.GappleCrafter;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class ItemEffectListener implements Listener {

    private final JavaPlugin plugin;

    // Constructor now accepts a JavaPlugin instance
    public ItemEffectListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        // Get the name of the consumed item
        String itemName = event.getItem().getType().toString();

        // Check if the item has effects defined in the config
        Map<String, Object> itemEffects = plugin.getConfig().getConfigurationSection("recipes.effects").getValues(false);
        
        if (itemEffects != null && itemEffects.containsKey(itemName)) {
            // Apply the effects dynamically based on the config
            Map<String, Object> effects = (Map<String, Object>) itemEffects.get(itemName);
            
            // Iterate over the effects and apply them
            for (String effectType : effects.keySet()) {
                int duration = (int) effects.get("duration");
                int amplifier = (int) effects.get("amplifier");
                
                PotionEffectType potionEffectType = getPotionEffectType(effectType);
                if (potionEffectType != null) {
                    event.getPlayer().addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier));
                }
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
