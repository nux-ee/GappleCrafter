package com.nux.ee.GappleCrafter;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemEffectListener implements Listener {

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.POTATO) {
            // Apply effects when the enchanted potato is eaten
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));  // Regeneration II
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1)); // Speed II
        }
    }
}
