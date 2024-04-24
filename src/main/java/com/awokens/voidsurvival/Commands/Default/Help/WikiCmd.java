package com.awokens.voidsurvival.Commands.Default.Help;

import com.awokens.voidsurvival.Manager.WikiManager;
import com.awokens.voidsurvival.VoidSurvival;
import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class WikiCmd {

    private final VoidSurvival plugin;
    public WikiCmd(VoidSurvival plugin) {
        this.plugin = plugin;

        WikiManager wiki = new WikiManager();
        wiki.setTitle("Wiki");
        wiki.setAuthor("Awokens");

        List<String> pages = new ArrayList<>();
        pages.add("<bold>      VOID WIKI</bold><newline><newline>\n" +
                "Here you will find every custom feature made on here " +
                "<gold>The Spring Edition</gold>.<newline><newline>Remember," +
                " you can hover some text for more info if displayed.<newline>" +
                "<newline>Enjoy reading,<newline><blue>Awokens</blue>");
        pages.add("""
                <bold>THE BASICS</bold>
                • You are given 1 out of 4 essential items every 10 seconds.\
                 What are these exactly? Cobblestone, Iron nuggets, Dirt and Oak Planks.\

                • Enter /commands to view what commands you have access to""");
        pages.add("""
                <bold>MOB MECHANICS</bold>\

                • <hover:show_text:'Bats drop leather upon death'>Bats</hover>\

                • <hover:show_text:'Drowned will not spawn with equipment'>Drowned</hover>\

                • <hover:show_text:'Cure a Zombie Nitwit with potion of weakness. \
                And right click the Zombie Nitwit with enchanted golden apple to cure a villager\
                 with special trades'>Zombie Nitwit</hover>\

                • <hover:show_text:'Gives Luck 3 if killed while having Luck 2. \
                Can also uncommonly drop Heart of The Sea. But looting can increase the drop rate'>Elder Guardian</hover>\

                • <hover:show_text:'Gives luck 2 while having luck 1'>Guardian</hover>\

                • <hover:show_text:'Convert a Zombified Piglin with potion of strength. \
                And right click the Zombified Piglin with enchanted golden apple to convert to a Piglin for bartering'>Zombified Piglin</hover>\

                • <hover:show_text:'Piglins may not spawn naturally'>Piglin</hover>\

                • <hover:show_text:'Will drop an emerald upon death. Looting can increase the drop count'>Wandering Trader</hover>\

                • <hover:show_text:'Can rarely drop a netherwart. Looting can increase the drop rate'>Witch</hover>""");
        pages.add("""
                <bold>PLAYER MECHANICS</bold>\

                • <hover:show_text:'Bone meal rooted dirt to make a grass block'>Rooted Dirt</hover>\

                • <hover:show_text:'Sneak right-click while holding a crafting table to virtual open a crafting bench as your disposal.'>Crafting Bench</hover>
                • <hover:show_text:'Enter [item] in chat to comment a snapshot of your held item.

                Enter [inv] in chat to comment a snapshot of your inventory snapshot.\s
                Others may also click once to view details of your inventory snapshot.'>Chat Hover Preview</hover>
                • <hover:show_text:'Death messages are sent through the action bar right above your hotbar.'>Death Messages</hover>
                • <hover:show_text:'Right click Mud with a water bottle to make Clay.\s
                Then again for sand or uncommonly make sus sand.'>Sand Making</hover>
                • <hover:show_text:'1. Place 1 lit campfire.
                2. Place 1 cauldron above campfire
                3. Sneak right-click cauldron with 32 cobbled deepslate
                4. Watch as the cauldron smelts down
                 the cobble deepslate for a bit.
                5. Vuala! You have lava.'>Lava Making</hover>
                • <hover:show_text:'Ignite TNT to cause a snake trail explosion.
                The length of the trail can destroy up to 270 blocks.
                However if the block has a higher block
                hardness, it may destroy less.'>Trail TNT</hover>
                • <hover:show_text:'Revive the dead bush with bone meal back into a tree sapling type'>Dead Bush</hover>
                • <hover:show_text:'Similar to trail TNT, but this time it's a shooting fireball, that's crazy bro!'>Trail Fireball</hover>
                • <hover:show_text:'Right click a nautilus shell to be given shipwreck treasure loot'>Nautilus Shells</hover>
                • <hover:show_text:'Right click lightning rod with copper ingot whilst in the
                rain to summon lightning at the clicked lightning rod'>Lightning</hover>
                • <hover:show_text:'Fish up these uncommon mobs:\s
                Drowned, Elder Guardian or Guardian'>Fishing</hover>
                • <hover:show_text:'Trade with others by sneak right-clicking each other.
                 Which will swap both each other's held items.'>Hand Swap Trading</hover>
                """);
        pages.add("""
                <bold>PLAYER MECHANICS</bold>\

                • <hover:show_text:'You may only wear 1 piece of diamond armor at a time.'>Armor</hover>
                • <hover:show_text:'Right click on dirt to make moss'>Moss Making</hover>
                • <hover:show_text:'Place an eye of ender on an end portal frame
                to be teleported to the End in an animated way.'>End Teleporting</hover>
                • <hover:show_text:'Brush suspicious sand to commonly
                 get basic armor trims, a dead bush or other miscellaneous items. Rarely, you may
                find diamonds when brushing'>Suspicious Sand</hover>
                • <hover:show_text:'Burn a skeleton whilst on soul soil to
                summon a wither skeleton upon death'>Wither Skeleton</hover>
                • <hover:show_text:'Burn a skeleton whilst on blackstone to
                summon a blaze upon death'>Blaze</hover>
                • <hover:show_text:'Break any leaves to probably drop an apple'>Apple</hover>""");
        pages.add("""
                <bold>OVERWORLD</bold>\

                • Keep inventory on
                • Smells like Awokens
                • Plenty of farming
                • Clears periodically
                • Lots of wataaaaaaa!
                • Random border size
                • Cannot <bold>place</bold> blocks above Y32""");
        pages.add("""
                <bold>THE END</bold>\

                • Keep inventory off\s
                • Smells like Htxx
                • Plenty of farming
                • Does not clear
                • Lots of water
                • Return the same way you came in""");
        pages.add("""
                <bold>NETHER</bold>\

                • Keep inventory off
                • Smells like Awokens
                • Gold farms, yay!
                • Does not clear
                • Lack of water
                • Return the same way you came in""");
        pages.add("""
                <bold>POWERSKULLS</bold>\
                
                • <hover:show_text:'Counter arrow projectile damage significantly'>Skeleton</hover>\st
                • <hover:show_text:'Saturated and disguised as a mob'>Zombie</hover>
                • <hover:show_text:'Damage others like Mike Tyson'>Piglin</hover>
                """);

        wiki.setPages(pages);

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setAuthor(wiki.getAuthor());
        bookMeta.setTitle(wiki.getTitle());

        for (String page : wiki.getPages()) {
            bookMeta.addPages(MiniMessage.miniMessage().deserialize(page));
        }
        book.setItemMeta(bookMeta);

        new CommandAPICommand("wiki")
                .withAliases("help", "tutorial")
                .withFullDescription("Void Survival Book guide")
                .executesPlayer((player, args) -> {
                    player.openBook(book);
                    player.playSound(player, Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1.0F, 1.0F);
                }).register();

    }
}
