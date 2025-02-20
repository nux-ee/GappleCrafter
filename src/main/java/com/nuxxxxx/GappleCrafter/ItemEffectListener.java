package com.nuxxxxx.GappleCrafter;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class ItemEffectListener implements Listener {

    private final JavaPlugin plugin;

    // Constructor that accepts the plugin instance
    public ItemEffectListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        // Check if the consumed item is part of the configured custom items
        Material itemType = event.getItem().getType();
        
        // Get the effects map for the item from the config
        ConfigurationSection effectsSection = plugin.getConfig().getConfigurationSection("item_effects." + itemType.name());

        if (effectsSection != null) {
            // Iterate through effects defined in the config for this item
            for (String effectKey : effectsSection.getKeys(false)) {
                String effectType = effectsSection.getString(effectKey + ".type");
                int duration = effectsSection.getInt(effectKey + ".duration");
                int amplifier = effectsSection.getInt(effectKey + ".amplifier");

                PotionEffectType potionEffectType = PotionEffectType.getByName(effectType);
                if (potionEffectType != null) {
                    // Apply the potion effect to the player
                    event.getPlayer().addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier));
                }
            }
        }
    }
}
