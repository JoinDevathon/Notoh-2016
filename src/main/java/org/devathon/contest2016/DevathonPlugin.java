package org.devathon.contest2016.main;

/*
Copyright (c) 2016 TheyCallMeNotoh

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.util.DevathonUtil;

public final class DevathonPlugin extends JavaPlugin implements Listener {

	
	private ArrayList<Player> players;
	private ArrayList<OfflinePlayer> ops;
	private ArrayList<OfflinePlayer> staff;
	boolean FreezeAll;
	
	public enum HealthToggle {
		TRUE, FALSE, DEALT
	}
	
	HealthToggle toggle;
	
	public DevathonPlugin() {
		this.players = new ArrayList<>(50);
		this.ops = new ArrayList<>();
		this.staff = new ArrayList<>();
		this.FreezeAll = false;
		this.toggle = HealthToggle.FALSE;
	}
	
    @Override
    public void onEnable() {
        getLogger().info("Devathon Hype");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    
    @Override
    public void onDisable() {
        getLogger().info("Plugin Disabled");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
    	if(!(sender instanceof Player)) { //verify's player
    		return false;
    	}
    	Player pla = (Player) sender; //cast's player
    	
    	if(cmd.getName().equalsIgnoreCase("notohbot")) { //base command
    		if(args.length == 0) {
    			pla.sendMessage(DevathonUtil.devathonprefix + "You must specify arguments! Do /notohbot help for commands!");
    		}
    		if(args[0].equalsIgnoreCase("help")) {
    			/*
    			 * INDEX OF COMMANDS HERE
    			 * help: generates all commands
    			 * ban: Bans a player
    			 * kick: kicks a player
    			 * getplayers: Returns players
    			 * freezeall
    			 * getlocation
    			 * getlocation (Other player)
    			 * heal player
    			 * heal
    			 * togglehealthannounce
    			 * 
    			 * 
    			 */
    			pla.sendMessage(DevathonUtil.devathonprefix + "List of Notohbot Commands: ");
    			pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot helpop <message>");
    			pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot help"); 
    			pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot getlocation");
    		}
    		if(args[0].equalsIgnoreCase("getplayers") && pla.hasPermission("notohbot.mod")) {
    			for(Player p : getPlayers()) {
    				pla.sendMessage(DevathonUtil.devathonprefix + "" + p.getName());
    			}
    		}
    		if(args[0].equalsIgnoreCase("getops") && pla.hasPermission("notohbot.op")) {
    			for(OfflinePlayer p : getOps()) {
    				pla.sendMessage(DevathonUtil.devathonprefix + "" + p.getName());
    			}
    		}
    		if(args[0].equalsIgnoreCase("getstaff")) {
    			for(OfflinePlayer p : getOps()) {
    				pla.sendMessage(DevathonUtil.devathonprefix + "" + p.getName());
    			}
    		}
    		if(args[0].equalsIgnoreCase("freezeall") && pla.hasPermission("notohbot.op")) {
    			if(FreezeAll == false) {
    				FreezeAll = true;
        			pla.sendMessage("You froze all players!");
    			}
    			if(FreezeAll == true) {
    				FreezeAll = false;
    				pla.sendMessage(DevathonUtil.devathonprefix + "You unfroze all players!");
    			}
    		}
    		if(args[0].equalsIgnoreCase("helpop")) {
    			pla.sendMessage(DevathonUtil.devathonprefix + "You sent a helpop message!");
    			for(Player p : getStaff()) {
    				if(args.length == 2) {
    					p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1]);
    				}
    				if(args.length == 3) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2]);
    				}
    				if(args.length == 4) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3]);
    				}
    				if(args.length == 5) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4]);
    				}
    				if(args.length == 6) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5]);
    				}
    				if(args.length == 7) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5] + " " + args[6]);
    				}
    				if(args.length == 8) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5] + " " + args[6] + " " + args[7]);
    				}
    				if(args.length == 9) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5] + " " + args[6] + " " + args[7] + " " + args[8]); 
    				}
    				if(args.length == 10) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5] + " " + args[6] + " " + args[7] + " " + args[8] + " " + args[9]);
    				}
    				if(args.length == 11) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5] + " " + args[6] + " " + args[7] + " " + args[8] + " " + args[9] + " " + args[10]);
    				}
    				if(args.length == 12) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5] + " " + args[6] + " " + args[7] + " " + args[8] + " " + args[9] + " " + args[10] + " " + args[11]);
    				}
    				if(args.length == 13) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5] + " " + args[6] + " " + args[7] + " " + args[8] + " " + args[9] + " " + args[10] + " " + args[11] + " " + args[12]);
    				}
    				if(args.length == 14) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5] + " " + args[6] + " " + args[7] + " " + args[8] + " " + args[9] + " " + args[10] + " " + args[11] + " " + args[12] + " " + args[13]);
    				}
    				if(args.length == 15) {
        				p.sendMessage(DevathonUtil.helpopprefix + pla.getName() + " sent you this message: " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5] + " " + args[6] + " " + args[7] + " " + args[8] + " " + args[9] + " " + args[10] + " " + args[11] + " " + args[12] + " " + args[13] + " " + args[14]);
    				}
    			}
    		}
    		if(args[0].equalsIgnoreCase("getlocation")) {
    			if(args.length == 0) {
    			pla.sendMessage(DevathonUtil.devathonprefix + "You are at " + "world: " + pla.getWorld() + ", x: " + pla.getLocation().getX() + ", y: " + pla.getLocation().getY() + ", z: " + pla.getLocation().getZ());
    		}
    			if(args.length == 1 && pla.hasPermission("notohbot.locationfinder")) {
    				Player target = Bukkit.getPlayerExact(args[0]);
    				pla.sendMessage(DevathonUtil.devathonprefix + target + " is at x: " + target.getWorld() + ", x: " + pla.getLocation().getX() + ", y: " + pla.getLocation().getY() + ", z: " + pla.getLocation().getZ());
    			}
    	}  		
    	}
    	if(cmd.getName().equalsIgnoreCase("TESTCOM") && sender.hasPermission("notohbot.testcom")) {
    		sender.sendMessage("Yo, this works! WHAT DO YOU KNOW?!");
    	}
    	
    	return true;
    }
    
    public Player[] getStaff() {
		return staff.toArray(new Player[staff.size()]);
	}

	public Player[] getPlayers() {
    	return players.toArray(new Player[players.size()]);
    }
    
    public void addPlayer(Player pla) {
    	players.add(pla);
    }
    
    public void checkPlayer(Player pla) {
    	if(players.contains(pla)) {
    		pla.sendMessage(DevathonUtil.devathonprefix + "You are indeed a part of our player list.");
    	}
    }
    
    public Player[] getOps() {
    	return ops.toArray(new Player[ops.size()]);
    }
    
    public void addOp(Player pla) {
    	
    	if(pla.hasPermission("notohbot.op")) {
        	ops.add(pla);
    	}
    }
    
	
	public void setState(HealthToggle toggle) {
		this.toggle = toggle;
	}
    
    public void addStaff(Player pla) {
    	staff.add(pla);
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
    	Player pla = e.getPlayer();
    	
    	if(!players.contains(pla)) {
    		players.add(pla);
    	}
    	if(pla.hasPermission("notohbot.op")) {
    		ops.add(pla);
    	}
    	if(pla.hasPermission("notohbot.op") || pla.hasPermission("notohbot.mod") || pla.hasPermission("notohbot.helper")) {
    		staff.add(pla);
    	}
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
    	Player pla = e.getPlayer();
    	
    	if(players.contains(pla)) {
    		players.remove(pla);
    	}
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
    	Player pla = e.getPlayer();
    	
    	if(FreezeAll == true) {
    		if(!pla.hasPermission("notohbot.antifreeze")) { //antifreeze perm
    			e.setCancelled(true);
    		} else {
    			return;
    		}
    	}
    }
    @EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player pla = (Player) e.getEntity();
		Entity damager = e.getDamager();

		if(toggle == HealthToggle.TRUE && damager instanceof Player && pla instanceof Player) {
			damager.sendMessage(DevathonUtil.devathonprefix + pla.getName() + ChatColor.AQUA + " is on " + ChatColor.RED + (pla.getHealth() - e.getFinalDamage()) + ChatColor.DARK_GREEN + " HP" + ChatColor.AQUA + "!");
		}
		if(toggle == HealthToggle.DEALT && damager instanceof Player && pla instanceof Player) {
	            damager.sendMessage(DevathonUtil.devathonprefix + "You damaged " + pla.getName() + " for " + ChatColor.RED +  e.getFinalDamage() + ChatColor.AQUA + " damage and now their health is on " + ChatColor.DARK_GREEN + (pla.getHealth() - e.getFinalDamage()) + ChatColor.AQUA + " HP!");
		}
    }    
    public static Plugin getPlugin() {
    	return Bukkit.getServer().getPluginManager().getPlugin("Notohbot");
    }
    
    
}

