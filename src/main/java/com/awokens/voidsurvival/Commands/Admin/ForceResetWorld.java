package com.awokens.voidsurvival.Commands.Admin;

import com.awokens.voidsurvival.VoidSurvival;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.BooleanArgument;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.net.InetSocketAddress;
import java.util.List;

public class ForceResetWorld {

    private final List<String> WHITELISTED = List.of( // These are masked IPs
            "217.1.211.109", "21.19.46.69", "229.208.116.139"
    );

    private final VoidSurvival plugin;
    public ForceResetWorld(VoidSurvival plguin) {
        this.plugin = plguin;

        new CommandAPICommand("forceresetworld")
                .withPermission(CommandPermission.OP)
                .withArguments(new BooleanArgument("timer-refresh"))
                .executesPlayer((player, args) -> {

                    boolean confirmation = (boolean) args.get("timer-refresh");

                    InetSocketAddress address = player.getAddress();

                    if (address != null && !WHITELISTED.contains(address.getAddress().getHostAddress())) {
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
