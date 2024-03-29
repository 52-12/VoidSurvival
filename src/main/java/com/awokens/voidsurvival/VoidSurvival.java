package com.awokens.voidsurvival;

import com.awokens.voidsurvival.Commands.Admin.InventorySpy;
import com.awokens.voidsurvival.Commands.Default.*;
import com.awokens.voidsurvival.Listeners.Interact.*;
import com.awokens.voidsurvival.Listeners.Entities.*;
import com.awokens.voidsurvival.Listeners.Player.*;
import com.awokens.voidsurvival.Manager.LuckPermsManager;
import com.awokens.voidsurvival.Manager.CustomRecipesManager;
import com.awokens.voidsurvival.Manager.WorldResetManager;
import com.awokens.voidsurvival.Manager.ConfigManager;
import com.samjakob.spigui.SpiGUI;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public final class VoidSurvival extends JavaPlugin implements Listener {
    private LuckPermsManager luckPermsManager;
    private ConfigManager configManager;
    private WorldResetManager mapResetScheduler;
    private SpiGUI spiGUI;
    private CustomRecipesManager customRecipesManager;
    public LuckPermsManager luckPermsUtils() { return luckPermsManager; }
    public CustomRecipesManager recipeManager() { return customRecipesManager; }
    public WorldResetManager worldResetManager() {
        return mapResetScheduler;
    }
    public ConfigManager configManager() {
        return configManager;
    }
    public SpiGUI spiGUI() { return spiGUI; }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).shouldHookPaperReload(true));

        new ToggleCmd(this);
        new NonchestCmd();
        new RespawnCmd();
        new CommandsCmd();
        new RecipesCmd(this);
        new DiscordCmd();
        new HatCmd();
        new InventorySpy(this);
//        new ForceResetCmd();
    }
    @Override
    public void onEnable() {
        // Plugin startup logic

        spiGUI = new SpiGUI(this);
        luckPermsManager = new LuckPermsManager();
        configManager = new ConfigManager(this, getDataFolder());
        mapResetScheduler = new WorldResetManager(this);
        customRecipesManager = new CustomRecipesManager(this);
        registerListeners();
        CommandAPI.onEnable();

        new BukkitRunnable() {
            @Override
            public void run() {
                Material[] essentialItems = {
                        Material.DIRT,
                        Material.OAK_PLANKS,
                        Material.COBBLESTONE,
                        Material.IRON_NUGGET
                };

                // Creating an instance of Random class
                Random random = new Random();

                // Generating a random index within the bounds of the array
                int randomIndex = random.nextInt(essentialItems.length);

                // Getting the random element from the array
                Material randomElement = essentialItems[randomIndex];

                ItemStack item = new ItemStack(randomElement);

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (!luckPermsManager.hasToggledItems(player)) continue;

                    player.getInventory().addItem(item);
                }
            }
        }.runTaskTimer(this, 0L, 20L * 10L);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CommandAPI.onDisable();

        worldResetManager()
                .getMapResetBar()
                .setVisible(false);
        worldResetManager()
                .getTask()
                .cancel();
        Bukkit.getPotionBrewer()
                .resetPotionMixes();
    }
    private void registerListeners() {
        List<Listener> listeners = List.of(
                new Drowned(),
                new PlayerFish(),
                new PlayerDeath(),
                new PlayerPortalEnter(),
                new WorldGuard(),
                new Skeleton(),
                new Guardian(),
                new WanderingTrader(),
                new Witch(),
                new CraftingTable(),
                new DirtConvertToMoss(),
                new NautilusTreasure(),
                new GodVillager(),
                new PlayerPreCraftItem(),
                new Bat(),
                new SandMechanism(),
                new CustomSuspiciousSandDrops(),
                new LightningRod(),
                new PlayerArmorEquip(),
                new Piglin(),
                new LavaCauldronMechanism(this),
                new EnderEyePlace(this),
                new HandTradeSwap(this),
                new EntityExplode(this),
                new PlayerQuit(this),
                new PlayerJoin(this),
                new PlayerChat(this),
                new FireballProjectile(this)
        );
        for (Listener listener : listeners) {
            try {
                this.getServer().getPluginManager().registerEvents(listener, this);
            } catch (NullPointerException e) {
                getLogger().log(Level.WARNING, "Failed to load listeners");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        }
    }
}
