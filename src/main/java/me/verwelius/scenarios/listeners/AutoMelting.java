package me.verwelius.scenarios.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;

public class AutoMelting implements Listener {

    @EventHandler
    private void onBlockDropItem(BlockDropItemEvent event) {
        List<Item> drop = event.getItems();
        drop.replaceAll(item -> {
            ItemStack stack = item.getItemStack();

            for(Recipe recipe : Bukkit.getRecipesFor(stack)) {
                if(recipe instanceof FurnaceRecipe furnaceRecipe) {
                    stack.setType(furnaceRecipe.getResult().getType());
                    item.setItemStack(stack);
                    break;
                }
            }

            return item;
        });
    }

}
