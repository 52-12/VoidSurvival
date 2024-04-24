//package com.awokens.voidsurvival.Commands.Admin;
//
//import com.awokens.voidsurvival.VoidSurvival;
//import de.tr7zw.changeme.nbtapi.NBTFile;
//import dev.jorel.commandapi.BukkitExecutable;
//import dev.jorel.commandapi.CommandAPICommand;
//import dev.jorel.commandapi.arguments.ArgumentSuggestions;
//import dev.jorel.commandapi.arguments.StringArgument;
//import org.bukkit.OfflinePlayer;
//import org.bukkit.entity.Player;
//
//import java.io.File;
//import java.io.IOException;
//
//public class InventoryCache {
//
//    private final VoidSurvival plugin;
//
//    public NBTFile getPlayerData(OfflinePlayer player) throws IOException {
//        return new NBTFile(new File( "world/playerdata/" + player.getUniqueId() + ".dat"));
//    }
//
//    public InventoryCache(VoidSurvival plugin) {
//        this.plugin = plugin;
//        new CommandAPICommand("inventorycache")
//                .withArguments(new StringArgument("type")
//                        .replaceSuggestions(ArgumentSuggestions.strings(
//                                "inventory", "enderchest"
//                        )))
//                .executesPlayer((player, args) -> {
//
//                    String type =
//
//                }).register();
//    }
//}
