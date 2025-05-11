package not.savage.example.menus;

import lombok.Getter;
import not.savage.bukkit.config.ConfigItem;
import not.savage.bukkit.itemstack.vItemStack;
import not.savage.bukkit.menus.MenuBuilder;
import not.savage.bukkit.menus.MenuLayout;
import not.savage.bukkit.menus.assets.AssetBuilder;
import not.savage.bukkit.menus.configuration.MenuConfiguration;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.concurrent.TimeUnit;

/**
 * Simple menu example with 2 items. 1 Static item, and 1 dynamic item.
 * Menus managed by Commons once opened. Menus can be shared by multiple viewers if applicable.
 * - Closing/Freeing the menu is handled automatically.
 * - Items are automatically maps to executors and tasks.
 * - Failover items are automatically managed - both showing & replacing.
 * - Dynamic items update in-menu and refresh on an interval.
 * - Handles the actual drawing of the menu, its layout, background items, etc.
 */
public class SimpleMenu {

    /**
     * A simple in-game "Menu" or chest inventory.
     */
    public SimpleMenu(Player player, Config config) {
        final MenuBuilder builder = config.getLayout().asMenuBuilder();

        // Adding a simple menu item. Any Click is handled, cancelling the click, closing and sending a message
        builder.addAsset(
                new AssetBuilder()
                        .setItemBuilder(config.getActionItem().asVirtualStack()) // The item to display
                        .setSlot(config.getActionItemSlot())
                        .setGeneralClickHandler(wrappedEvent -> {
                            wrappedEvent.cancelClickAndClose();
                            player.sendMessage("You clicked the item!");
                        })
                        .build()
        );

        // Dynamic items update in-menu on an interval
        // It's a lot to outline - but its also animated, self updating, automatically handles clicks,
        // and shows different items without managing tasks.
        builder.addAsset(
                new AssetBuilder()
                        .asDynamicAsset(player) // Target/Reference used when updating the item
                        .setUpdateInterval(1L) // Update once per-second.
                        .setUpdateFunction(viewer -> { // Update function. Generates the item when updating.
                            if (viewer.getStatistic(Statistic.TOTAL_WORLD_TIME) > TimeUnit.DAYS.toSeconds(1)) {
                                return new vItemStack()
                                        .material(Material.GREEN_STAINED_GLASS_PANE)
                                        .displayName("<green>You've played for over a day!")
                                        .lore("<#FFFFFF>That's a lot of time!", "<#FFFFFF>Click to claim reward!");
                            } else {
                                return new vItemStack()
                                        .material(Material.RED_STAINED_GLASS_PANE)
                                        .displayName("<red>You've played for less than a day!")
                                        .lore("<#FFFFFF>Keep playing to claim rewards!");
                            }
                        })
                        .setGeneralClickHandler(we -> { // Click handler for the item
                            if (((Player) we.getEvent().getWhoClicked()).getStatistic(Statistic.TOTAL_WORLD_TIME) > TimeUnit.DAYS.toSeconds(1)) {
                                we.cancelClickAndClose();
                                we.getEvent().getWhoClicked().sendMessage("You clicked the item!");
                            } else {
                                we.cancel(); // always cancel the click so the players cant grab the item.
                                we.fail(); // Shows the failed action item outlined below.
                            }
                        })
                        .setFailedShowTime(10) // Seconds to show the failed click icon
                        .setFailedItem( // Item shows when fail() is used.
                                new vItemStack(Material.ORANGE_STAINED_GLASS_PANE)
                                        .displayName("<red>You've played for less than a day!")
                                        .lore("<#FFFFFF>Keep playing to claim rewards!")
                        )
                        .build()
        );

        // Build the menu into an actual inventory, and open it for the player.
        builder.build().deploy(player);
    }

    /**
     * A Configuration example for what components would be modifiable
     */
    @ConfigSerializable @Getter
    @SuppressWarnings("FieldMayBeFinal") // Configurate requires this to be mutable
    public static class Config {
        private MenuConfiguration layout = new MenuConfiguration.Builder()
                .title("<red>Example Menu")
                .rows(4)
                .layout(MenuLayout.FILLED)
                .fillerItem(ConfigItem.DEFAULT_MENU_FILLER)
                .overrideItem(
                        5,
                        new ConfigItem.Builder()
                                .material(Material.RED_STAINED_GLASS_PANE)
                                .displayName("<#FFFFFF>Special Background item!")
                                .build()
                )
                .build();

        private ConfigItem actionItem = new ConfigItem.Builder()
                .material(Material.DIAMOND_SWORD)
                .displayName("<#FFFFFF>Click me!")
                .lore("<#FFFFFF>Click to execute a command")
                .build();

        private int actionItemSlot = 13; // The slot to place the item in
    }

}
