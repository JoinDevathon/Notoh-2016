package org.devathon.contest2016;

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


import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

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
    			 * ban
    			 * kick
    			 * getplayers: Returns players
    			 * freezeall
    			 * getlocation
    			 * getlocation (Other player)s
    			 * heal players
    			 * heals
    			 * togglehealthannounce s
    			 * getip s
    			 * pickupline s
    			 * selfhelp s
    			 * getip s
    			 * banplayersip s
    			 * 
    			 */
    			pla.sendMessage(DevathonUtil.devathonprefix + "List of Notohbot Commands: ");
    			pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot helpop <message> - Message's all staff members.");
    			pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot help - Generates this message."); 
    			pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot getlocation - Get's your location.");
    			pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot getstaff - Gets all the staff and tells the player.");
				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot pickupline - Tells you a pickupline.");
				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot selfhelp - Gives you a selfhelp book.");
				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot teachmetoprogram - Teaches you how to program.");
				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot fanfiction - Gives you fanfiction");
    			if(pla.hasPermission("notohbot.mod")) {
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot kick <player> - Kicks a player.");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot ban <player> - Bans a playe.r");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot getplayers - Gets all the players on the server.");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot heal - Heals you.");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot heal <player> - Heals a certain player.");
       				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot getip <player> - Get's a player's ip.");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot getlocation <player> - Gets the location of a specified player.");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot testcom - Test command.");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot fireball - Launches a fireball!");
    			}
    			if (pla.hasPermission("notohbot.op")) {
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot getops - Gets all the ops.");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot kick <player> - Kicks a player.");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot freezeall - Freezes all players on the server.");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot sethealthannounce - Toggles health announce on or off.");
    				pla.sendMessage(DevathonUtil.devathonprefix + "/notohbot banplayersip <player> - Ban's a players ip.");
    			}
    				
    		}
    		if(args[0].equalsIgnoreCase("kick") && pla.hasPermission("notohbot.mod") && args.length == 2) {
    			
    			Player target = Bukkit.getPlayerExact(args[1]);
    			
    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kick " + target.getName());
    			pla.sendMessage(DevathonUtil.devathonprefix + target + " has been kicked by NotohBot.");
    		}
    		if(args[0].equalsIgnoreCase("ban") && pla.hasPermission("notohbot.mod") && args.length == 2) {
    			Player target = Bukkit.getPlayerExact(args[1]);
    			
    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"ban " + target.getName());
    			pla.sendMessage(DevathonUtil.devathonprefix + target + " has been banned by NotohBot.");
    		}
    		if(args[0].equalsIgnoreCase("teachmetoprogram")) {
    			ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
    			BookMeta meta = (BookMeta) item.getItemMeta();
    			meta.setAuthor("Alan  Turing");
    			meta.setTitle("How to Code");
    			
    			meta.setPages(Arrays.asList("So you want to learn to program, and here's how.",
    					"Step 1: Install Microsoft Word. Microsoft Word is the only true coding program, and it will make you a better programmer.",
    					"Step 2: Type random gibberish as all coding is gibberish. Because of this all good computer programs are only successful if the dev is lucky",
    					"And you might be wondering then how was the computer invented, and the secret is, Alan Turing, Inventor of the Computer was very high when he made his discovery.",
    					"Step 3: Get lucky. If you accidently type the right thing, rich and famous ez. The End"));
    			
    			item.setItemMeta(meta);
    			pla.getInventory().addItem(item);
    		}
    		if(args[0].equalsIgnoreCase("fanfiction")) {
    			ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
    			BookMeta meta = (BookMeta) item.getItemMeta();
    			meta.setAuthor("LE SEMAJ");
    			meta.setTitle("50 Shades of Blocks");
    			
    			meta.setPages(Arrays.asList("Alex walked up to the smoking (hot) creeper and slashed  at it with an enchanted diamond sword.",
    					"The creeper screamed in pain but as his green body shriveled up he said one phrase.",
    					"You're Hot.",
    					"Alex stared into his glowing eyes as he dissipated into embers.",
    					"Dang, you were pretty hot. I wonder if I can get Steve to roleplay as a creeper?",
    					"She knelt down to pick up the gunpowder and rose to skip back to the castle Steve made for her, As she opened the door Steve came out in refractive iron armour.",
    					"You like my new gear? This was the first time I ever enchanted and it turned out pretty bad, I mean Bane of Arthropods and Aqua Affinity? I tried it a second time on the diamond chest plate and got Protection 4! That's the piece for you.",
    					"He smiled and beckoned her in the house.",
    					"Steve goes up to Alex and says, I got magic powers that can resurrect creepers, wanna see?",
    					"Steve resurrects the Creeper and Alex realizes something. The Creeper is her soul mate.",
    					"They begin making love, and Steve is lonely. Steve goes up to ask to join in, but then the Creeper explodes. The End."));
    			
    			item.setItemMeta(meta);
    			pla.getInventory().addItem(item);
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
    			if(args.length == 1 && pla.hasPermission("notohbot.mod")) {
    				Player target = Bukkit.getPlayerExact(args[0]);
    				pla.sendMessage(DevathonUtil.devathonprefix + target + " is at x: " + target.getWorld() + ", x: " + pla.getLocation().getX() + ", y: " + pla.getLocation().getY() + ", z: " + pla.getLocation().getZ());
    			}
    		}
    		if(args[0].equalsIgnoreCase("sethealthannounce") && pla.hasPermission("notohbot.op")) {
    			if(args.length == 1) {
    				pla.sendMessage("You must specify on or off!");
    			}
    			if(args[1].equalsIgnoreCase("true") && pla.hasPermission("notohbot.op")) {
    				setState(HealthToggle.TRUE);
    			}
    			if(args[1].equalsIgnoreCase("false") && pla.hasPermission("notohbot.op")) {
    				setState(HealthToggle.FALSE);
    			}
    			if(args[1].equalsIgnoreCase("dealt") && pla.hasPermission("notohbot.op")) {
    				setState(HealthToggle.DEALT);
    			}
    		}
    		if(args[0].equalsIgnoreCase("heal") && pla.hasPermission("notohbot.mod")) {
    			if(args.length == 1) {
    				pla.setHealth(pla.getMaxHealth());
    			}
    			if(args.length == 3) {
    				Player target = Bukkit.getPlayerExact(args[1]);
    				
    				target.setHealth(target.getMaxHealth());
    			}
    		}
    		if(args[0].equalsIgnoreCase("pickupline")) {
    			if(args.length > 1) {
    				pla.sendMessage(DevathonUtil.devathonprefix + "Too many args!");
    				return false;
    			}
    			Random r = new Random();
    			String[] pickuplines = new String[] { "There are 8 planets, but there will only be 7 once I destroy Uranus.", 
    					"If you think Chewbacca is hairy, wait until you see my wookie!",
    					"Do you have a Diamond Pickaxe? Because I'm as hard as Obsidian.",
    					"I must be gravel, because I'm falling for you.",
    					"Are you sitting on the F5 key? Cause your ass is refreshing. ",
    					"Are your pants a compressed file? Because I'd love to unzip them!",
    					"WebMD says your love is contagious. ",
    					"Isn't your e-mail address beautifulgirl@mydreams.com? ",
    					"Your eyes are as blue as the sea I dumped my ex’s body in.﻿",
    					"I heard you’re good in algebra, can you replace my X without asking Y",
    					"My mom thinks I`m gay, can you help me prove her wrong?",
    					"I’m no organ donor but I’d be happy to give you my heart.",
    					"You look familiar, didn’t we take a class together? I could’ve sworn we had chemistry.",
    					"What's wrong? You're looking a little sad and gloomy. What you need is some vitamin me.",
    					"Are your legs tired? Because you been running through my mind ALL day long. ",
    					"Did you know they changed the alphabet? They put U and I together. ",
    					"I wish you were a Pony Carousel outside Walmart, so I could ride you all day long for a quarter. ",
    					"Is your father a thief? Because he stole the sparkle from the stars, and put it in your eyes. ",
    					"Can I see that label? I just wanted to know if you were made in heaven. ",
    					"I think you're the most beautiful girl I've seen - on a Wednesday",
    					"You remind me of an ice cold Pepsi - I've just gotta have it. ",
    					"Can I have your picture so I can show Santa what I want for Christmas?",
    					"Is your dad an art thief? Because you're a masterpiece."
    					
    			};
    			
    		int pickupNum = r.nextInt(0) + 1;
    		
    		for(int i = 0; i < pickupNum; i++) {
    				String line = pickuplines[r.nextInt(pickuplines.length)];
    				
    				pla.sendMessage(DevathonUtil.devathonprefix + line);
    				}
    		}
    		if(args[0].equalsIgnoreCase("selfhelp")) {
    			ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
    			BookMeta meta = (BookMeta) item.getItemMeta();
    			meta.setAuthor("Some dev in his basement");
    			meta.setTitle("Self help for dummies.");
    			
    			meta.setPages(Arrays.asList("So you are reading this because you need help. YOU ARE READING A SELF HELP BOOK ON A LEGO GAME MADE BY SOME DEV IN HIS BASEMENT! You need help man.",
    					"But I will help you improve your life, step 1: Get 43 cats. 43 specifically. They will make you happy and successful. ",
    					"And by Succesful, I mean you have 43 cats in your house so you can achieve nuclear fusion via the heat the cats generate.",
    					"You then get famous off science and are now rich. The End."));
    			
    			item.setItemMeta(meta);
    			pla.getInventory().addItem(item);
    		}
    		if(args[0].equalsIgnoreCase("getip") && pla.hasPermission("notohbot.mod")) {
    			if(args.length == 1) {
    				pla.sendMessage(DevathonUtil.devathonprefix + "You must specify a player!");
    				return true;
    			} else if (args.length > 2) {
    				pla.sendMessage(DevathonUtil.devathonprefix + "Too many args!");
    			} else {
    				Player target = Bukkit.getPlayerExact(args[1]);
    				InetSocketAddress ip = target.getAddress();
    				pla.sendMessage(DevathonUtil.devathonprefix + ip.toString());
    			}
    		}
    		if(args[0].equalsIgnoreCase("banplayersip") && pla.hasPermission("notohbot.op")) {
    			if(args.length == 1) {
    				pla.sendMessage(DevathonUtil.devathonprefix + "You must specify a player!");
    			} else if (args.length > 2) {
    				pla.sendMessage(DevathonUtil.devathonprefix + "Too many args!");
    			} else {
    				Player target = Bukkit.getPlayerExact(args[1]);
    				InetSocketAddress ip = target.getAddress();
    				Bukkit.banIP(ip.toString());
    				pla.sendMessage(DevathonUtil.devathonprefix + "Banned IP " + ip.toString());
    			}
    		}
    		if(args[0].equalsIgnoreCase("fireball") && pla.hasPermission("notohbot.mod")) {
    			pla.launchProjectile(Fireball.class);
    		}
    		if(args[0].equalsIgnoreCase("TESTCOM") && sender.hasPermission("notohbot.mod")) {
    		sender.sendMessage(DevathonUtil.devathonprefix + "Yo, this works! WHAT DO YOU KNOW?!");
    		}
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
