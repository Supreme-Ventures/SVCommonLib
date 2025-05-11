package not.savage.example.commands.complex;

import not.savage.bukkit.commands.cmd.CommandContext;
import not.savage.bukkit.commands.cmd.aCommand;

public class ComplexSubCommandOne extends aCommand {

    public ComplexSubCommandOne() {
        addAlias("complex-subcommand-one");
        setDescription("Subcommand one of the complex command");
    }

    @Override
    public void execute(CommandContext context) { }
}
