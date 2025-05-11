package not.savage.example;

import lombok.Getter;
import not.savage.bukkit.config.BukkitConfigBuilder;
import not.savage.bukkit.model.LibSupportedPlugin;
import not.savage.cereal.impl.CerealAPI;
import not.savage.example.commands.BasicCommand;
import not.savage.example.commands.ComplexCommand;
import not.savage.example.listeners.EventListener;
import not.savage.example.model.DataProfilesCache;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("UnstableApiUsage")
@Getter
public class BasicPlugin extends LibSupportedPlugin {

    private DataProfilesCache cache;
    private SimpleConfig configuration;

    @Override
    public void enable() {
        log("Enabling " + getPluginMeta().getName() + " v" + getPluginMeta().getVersion());

        // How we initialize a DataSet
        cache = CerealAPI.getInstance().installCache(this, DataProfilesCache.class);
        cache.start();
        getScheduler().getAsync().repeating(() -> cache.saveAll(), TimeUnit.MINUTES, 1, 10);

        // Register Event Listeners
        new EventListener(this);

        // Register Commands - Make sure you add the root commands to the plugin.yml
        getCommandBuilder().addAllCommands(
                new BasicCommand(this),
                new ComplexCommand()
        );

        // Loading Configuration Files.
        configuration = loadConfig("config.yml", SimpleConfig.class); // loads from `/plugin/config.yml` if present, or creates default.

        // Also valid - Don't need both | one or the other.
        configuration = BukkitConfigBuilder.builder(SimpleConfig.class)
                .withPath(resolveResourcePath("config.yml"))
                .build();

        // Scheduler / Threading.
        getScheduler().getSync().later(() -> {}, TimeUnit.MINUTES, 1);
        getScheduler().getAsync().repeating(() -> {}, TimeUnit.MINUTES, 1, 10);
    }

    @Override
    public void disable() {
        log("Disabling " + getPluginMeta().getName() + " v" + getPluginMeta().getVersion());
    }
}
