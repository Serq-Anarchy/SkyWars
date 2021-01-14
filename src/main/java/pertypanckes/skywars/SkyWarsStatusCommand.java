package pertypanckes.skywars;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyWarsStatusCommand implements CommandExecutor {
    private final Skywars plugin;

    public SkyWarsStatusCommand(Skywars plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("gladmin")) {
            sender.sendMessage("1");
            return true;
        } else {
            sender.sendMessage("0");
            return true;
        }
    }
}