package com.nuxxxxx.GappleCrafter;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;

public class ItemEffectListener implements Listener {

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        // Get the item that was consumed
        Material consumedItem = event.getItem().getType();

        // Check if there are any effects for the consumed item
        if (RecipeManager.getItemEffectsMap().containsKey(consumedItem)) {
            // Get the list of effects for this item
            for (PotionEffect effect : RecipeManager.getItemEffectsMap().get(consumedItem)) {
                // Apply each effect to the player
                event.getPlayer().addPotionEffect(effect);
            }
        }
    }
}
