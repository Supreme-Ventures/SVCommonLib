package not.savage.example.listeners;

import not.savage.bukkit.events.EventSubscriber;
import not.savage.example.BasicPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EventListener {

    public static final UUID NOTCH = UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5");
    private final not.savage.example.BasicPlugin plugin;

    public EventListener(final BasicPlugin plugin) {
        this.plugin = plugin;
        EventSubscriber.of(PlayerJoinEvent.class) // Handles PlayerJoinEvents
                .filter(e -> e.getPlayer().getUniqueId().equals(NOTCH)) // Only handles if event.getPlayer().getUniqueId() == NOTCH
                .handle(e -> {
                    plugin.log("Notch has joined the server!!!");
                    // Let's wait to send him a message...
                    this.plugin.getScheduler().getSync().later(() -> {
                        final Player player = Bukkit.getPlayer(NOTCH);
                        if (player == null) {
                            return;
                        }
                        player.sendMessage("Hello, Notch! Welcome to our humble server!");
                    }, TimeUnit.SECONDS, 5);
                });
    }

}
