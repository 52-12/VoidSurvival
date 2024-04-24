package com.awokens.voidsurvival.Commands.Default.Help;

import com.awokens.voidsurvival.VoidSurvival;
import dev.jorel.commandapi.CommandAPICommand;

public class RecipesCmd {

    public RecipesCmd(VoidSurvival plugin) {
        new CommandAPICommand("recipes")
                .withFullDescription("View custom recipes")
                .executesPlayer((player, args) -> {
                    player.openInventory(plugin.recipeManager().getRecipeMenu());
                }).register();
    }
}
