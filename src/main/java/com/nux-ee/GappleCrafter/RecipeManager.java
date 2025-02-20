package com.nux.ee.GappleCrafter;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class RecipeManager {

    public static void addRecipes(JavaPlugin plugin) {
        // Enchanted Golden Apple (Notch Apple) Recipe
        ItemStack enchantedGoldenApple = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1); // 1 refers to the Enchanted variant
        
        // Create recipe for the vanilla Enchanted Golden Apple (Notch Apple)
        ShapedRecipe recipe = new ShapedRecipe(enchantedGoldenApple);
        recipe.shape("GGG", "GAG", "GGG");
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('A', Material.APPLE);

        plugin.getServer().addRecipe(recipe);
    }
}
