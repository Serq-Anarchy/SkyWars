package pertypanckes.skywars;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyWarsCommand implements CommandExecutor {
    private final Skywars plugin;

    public SkyWarsCommand(Skywars plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (!p.hasPermission("gladmin")) {
            return true;
        } else if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "======" + " SkyWars " + "======");
            sender.sendMessage(ChatColor.WHITE + "/sw" + ChatColor.GRAY + " status" + ChatColor.YELLOW + " - информация о игре на текущем сервере");

        } else if (args[0].equalsIgnoreCase("status")) {
            sender.sendMessage(ChatColor.WHITE + "SkyWars" + ChatColor.YELLOW + " > " + ChatColor.RED + "Offline");
        }
        return true;
    }

}

