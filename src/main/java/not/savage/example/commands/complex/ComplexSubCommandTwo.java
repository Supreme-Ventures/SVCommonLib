package not.savage.example.commands.complex;

import not.savage.bukkit.commands.cmd.CommandContext;
import not.savage.bukkit.commands.cmd.aCommand;

public class ComplexSubCommandTwo extends aCommand {

    public ComplexSubCommandTwo() {
        addAlias("complex-subcommand-two");
        setDescription("Subcommand two of the complex command");
    }

    @Override
    public void execute(CommandContext context) { }
}
