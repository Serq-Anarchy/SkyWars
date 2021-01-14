package pertypanckes.skywars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class Skywars extends JavaPlugin implements Listener {


    private Scoreboard mainScoreboard;

    @Override
    public void onEnable() {
        mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective kills = mainScoreboard.getObjective("kills");
        if (kills != null) {
            kills.unregister();
        }
        mainScoreboard.registerNewObjective("kills", "dummy");
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("sw").setExecutor(new SkyWarsCommand(this));
        getLogger().info("enabled");
    }

    //creating scoreboard on join for player
    @EventHandler
    public void j(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (p != null) {
            updateScoreboard(p);
        }
    }

    //void for get kills if player kill someone in game
    private int getKills(Player p) {
        Objective o = mainScoreboard.getObjective("kills");
        return o.getScore(p.getName()).getScore();
    }

    //if entity death, this working
    @EventHandler
    public void m(PlayerDeathEvent e) {
        Player p = e.getEntity().getKiller();
        if (p == null) return;
        int previousValue = mainScoreboard.getObjective("kills").getScore(e.getEntity().getName()).getScore();
        mainScoreboard.getObjective("kills").getScore(e.getEntity().getName()).setScore(previousValue + 1);
        updateScoreboard(p);
    }

    //updating scoreboard when player killing someone
    private void updateScoreboard(Player p) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.clearSlot(DisplaySlot.SIDEBAR);
        Objective statsObjective = scoreboard.registerNewObjective("stats", "dummy");
        statsObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        statsObjective.setDisplayName("stats");
        statsObjective.getScore("kills: " + getKills(p)).setScore(1);
        p.setScoreboard(scoreboard);
    }
    
    @EventHandler
    public void quit(PlayerQuitEvent event) {
        Scoreboard main = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective o = main.getObjective("kills");
        o.getScore(event.getPlayer().getName()).setScore(0);
    }

    @Override
    public void onDisable() {
        getLogger().info("disabled");
    }

}
