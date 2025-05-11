package not.savage.example.commands;

import not.savage.bukkit.commands.cmd.CommandContext;
import not.savage.bukkit.commands.cmd.aCommand;
import not.savage.example.commands.complex.ComplexSubCommandOne;
import not.savage.example.commands.complex.ComplexSubCommandTwo;

import java.util.Arrays;

public class ComplexCommand extends aCommand {

    // Creates a "Complex" or command with sub command paths.
    // /root complex-command-one
    // /root complex-command-two
    public ComplexCommand() {
        addAlias("root");
        setPermission("root.required.permission");
        getSubCommands().addAll(
                Arrays.asList(
                        new ComplexSubCommandOne(),
                        new ComplexSubCommandTwo()
                )
        );

        // RootExecutor is used to execute specific logic if no sub-commands are provided.
        // Otherwise, a default sub-command help message will be auto generated.
        // Commons also automatically generates a `/root help` command to access
        // the help message for commands if root executor is enabled.
        setRootExecutor(true);
    }

    @Override
    public void execute(CommandContext context) {

    }
}
