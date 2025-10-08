package luomo.miku.waypointsync.loader;

import java.util.*;

import net.minecraft.command.ICommand;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import luomo.miku.waypointsync.command.CommandSyncWaypoint;

public class CommandLoader {

    private static final Map<String, ICommand> commands = new HashMap<String, ICommand>();
    private static boolean initialized = false;

    public static synchronized void init() {
        if (initialized) return;

        registerCommand(new CommandSyncWaypoint());

        initialized = true;
    }

    private static void registerCommand(ICommand command) {
        if (command == null || command.getCommandName() == null) {
            throw new NullPointerException("command is null");
        }

        String commandName = command.getCommandName();

        if (commands.containsKey(commandName)) {
            throw new IllegalArgumentException("command " + commandName + " is already registered");
        }

        commands.put(commandName, command);
    }

    public static void registerAllCommand(FMLServerStartingEvent event) {
        init();
        for (ICommand command : commands.values()) {
            event.registerServerCommand(command);
        }

    }

    public static ICommand getCommand(String commandName) {
        return commands.get(commandName.toLowerCase());
    }

    public static Set<String> getCommandNames() {
        return Collections.unmodifiableSet(commands.keySet());
    }

    public static int getCommandCount() {
        return commands.size();
    }
}
