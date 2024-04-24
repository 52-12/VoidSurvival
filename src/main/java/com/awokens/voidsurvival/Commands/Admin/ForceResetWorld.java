package com.awokens.voidsurvival.Commands.Admin;

import com.awokens.voidsurvival.VoidSurvival;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.BooleanArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.net.InetSocketAddress;
import java.util.List;

public class ForceResetWorld {

    private final List<String> WHITELISTED = List.of( // Now it is name based
            "Awokens", "Hysteeri", "Pl4zmar"
    );

    private final VoidSurvival plugin;
    public ForceResetWorld(VoidSurvival plguin) {
        this.plugin = plguin;

        new CommandAPICommand("forceresetworld")
                .withPermission(CommandPermission.OP)
                .withArguments(new BooleanArgument("timer-refresh"))
                .executesPlayer((player, args) -> {

                    boolean confirmation = (boolean) args.get("timer-refresh");

                    if (!WHITELISTED.contains(player.getName())) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize(
                                "<red>You are not authorized to run this."
                        ));
                        return;
                    }

                    plugin.worldResetManager().reset( confirmation);
                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<red>You are force resetting the overworld"
                    ));

                }).register();

    }
}
