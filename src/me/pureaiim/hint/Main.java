package me.pureaiim.hint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	private Ultities ult = new Ultities(this);

	private String word = ult.gHVH();
	private Integer delay = getConfig().getInt("delay-between-hints");
	private BukkitTask run;

	public void onEnable() {
		if (!getDataFolder().exists()) {
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("hint"))
			return false;
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /hint <start/stop>");
			return true;
		} else if (args[0].equalsIgnoreCase("start")) {
			startHint();
			sender.sendMessage(ChatColor.GREEN + "You've started the hinting process..");
			return true;
		} else if (args[0].equalsIgnoreCase("stop")) {
			if (run != null) {
				run.cancel();
				sender.sendMessage(ChatColor.RED + "Task has been stopped successfully!");
				return true;
			} else {
				return false;
			}
		} else {
			sender.sendMessage("Usage: /hint <start/stop>");
			return false;
		}
	}

	public void startHint() {
		run = new BukkitRunnable() {

			List<Integer> vAC = new ArrayList<Integer>();

			@Override
			public void run() {

				int a = new Random().nextInt(getConfig().getString("hint").length());
				if (!vAC.contains(a)) {
					vAC.add(a);
				} else {
					do {
						if (!word.contains("_")) {
							this.cancel();
						}
						a = new Random().nextInt(getConfig().getString("hint").length());
					} while (vAC.contains(a));
				}
				if (!getConfig().getString("hint").substring(a, a + 1).equals("_")) {

					String replaceWith = getConfig().getString("hint").split("")[a];

					StringBuilder builder = new StringBuilder(word);
					builder.replace(a, a + 1, replaceWith);
					word = builder.toString();

					for (Player p : Bukkit.getOnlinePlayers()) {
						p.setPlayerListHeader("Hint > " + ChatColor.BOLD + ChatColor.BLUE + word.toUpperCase());
					}
				}
			}
		}.runTaskTimer(this, 20L * delay, 20L * delay);
	}
}
