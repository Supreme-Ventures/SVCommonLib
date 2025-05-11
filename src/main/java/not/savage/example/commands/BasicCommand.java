package not.savage.example.commands;

import not.savage.bukkit.commands.cmd.CommandContext;
import not.savage.bukkit.commands.cmd.aCommand;
import not.savage.example.BasicPlugin;
import not.savage.example.model.DataProfile;
import org.bukkit.entity.Player;

import java.util.Optional;

public class BasicCommand extends aCommand {

    private final BasicPlugin plugin;

    // /basic-command <player> <amount> [notify]
    public BasicCommand(final BasicPlugin plugin) {
        this.plugin = plugin;
        addAlias("basic-command"); // -- /basic-command
        setPermission("example.required.permission"); // Permission required to use or see.
        setDescription("This is a basic command example."); // The description shown in help messages.
        addRequiredArg("player", Player.class); // A required argument, of type Player
        addRequiredArg("amount", Integer.class); // A required argument, of type Integer
        addOptionalArg("notify", Boolean.class, executor -> "false"); // An optional argument, of type Boolean. "false" if not provided.
        setSenderMustBePlayer(true); // CommandExecutor must be a Player
    }

    @Override
    public void execute(CommandContext context) {
        // Since this argument is "required" it will always be present. Argument failure is handled
        // before this method is called. Optional<?> wraps both Required & Optional arguments.
        final Player playerArgument = context.getArgs().get(0).parse(Player.class).get();

        // Get or Fail - No callback or waiting example.
        final Optional<DataProfile> profile = plugin.getCache().getCachedOrAsyncLoad(playerArgument.getUniqueId());
        if (profile.isEmpty()) {
            context.reply("<red>Loading your profile, please try again.");
            return;
        }
        // ...

        // Get or Load - Callback when ready.
        plugin.getCache().getAsync(playerArgument.getUniqueId())
                .thenAcceptSync(result -> { // Called on the next available Bukkit tick.
                    // Bukkit Primary Thread - implemented via BukkitCompletableFuture<?>
                    if (result.isEmpty()) {
                        return;
                    }

                    final DataProfile data = profile.get();
                    final Player dataPlayer = data.getPlayer();
                    if (dataPlayer == null || !dataPlayer.isOnline()) {
                        // Player is offline now.
                        return;
                    }

                    // ...
                });

    }
}
