package pertypanckes.skywars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class Skywars extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
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
        Scoreboard main = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective o = main.getObjective("kills");
        return o.getScore(p.getName()).getScore();
    }

    //void for get death of player in game
    private int getDeaths(Player p) {
        Scoreboard main = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective o = main.getObjective("deaths");
        if (o == null) o = main.registerNewObjective("deaths", "deathCount");
        return o.getScore(p.getName()).getScore();
    }

    //if entity death, this working
    @EventHandler
    public void m(EntityDeathEvent e) {
        Player p = e.getEntity().getKiller();
        if (p == null) return;
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
        statsObjective.getScore("deaths: " + getDeaths(p)).setScore(0);
        p.setScoreboard(scoreboard);
    }

    @EventHandler
    public void death(PlayerDeathEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            updateScoreboard(e.getEntity());
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("disabled");
    }

}
