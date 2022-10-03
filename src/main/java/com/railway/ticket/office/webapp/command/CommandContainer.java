package com.railway.ticket.office.webapp.command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static Map<String, Command> commands = new HashMap<>();

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    @Override
    public String toString() {
        return "CommandContainer commands : [ " + commands + " ]";
    }
}
