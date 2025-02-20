package com.nux.ee.GappleCrafter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class RecipeManager {
    public static void addRecipes(JavaPlugin plugin) {
        ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE);
        
        ShapedRecipe recipe = new ShapedRecipe(goldenApple);
        recipe.shape("GGG", "GAG", "GGG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('A', Material.APPLE);
        
        plugin.getServer().addRecipe(recipe);
    }
}
