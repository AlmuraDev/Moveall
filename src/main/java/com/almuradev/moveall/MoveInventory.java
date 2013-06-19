package com.almuradev.moveall;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.getspout.spoutapi.player.SpoutPlayer;

public class MoveInventory {
	
		private static void stackInventoryItems(Player sPlayer,	Inventory inventory) {
		int i, j;
		for (i = 0; i < inventory.getSize(); i++) {
			for (j = i + 1; j < inventory.getSize(); j++) {
				moveitemInventory(sPlayer, inventory, j, i);
			}
		}
	}



		private static void moveitemInventory(Player sPlayer, Inventory inventory, int fromslot, int toslot) {

			ItemStack fromitem = inventory.getItem(fromslot);
			ItemStack toitem = inventory.getItem(toslot);

			//inventory.setItem(toslot, fromitem);				
			//inventory.getItem(toslot).setAmount(fromitem.getAmount());
			//inventory.clear(fromslot);


		}
	
	public static void sendNotification(Player sPlayer, String string) {
		if (((SpoutPlayer) sPlayer).isSpoutCraftEnabled() && (sPlayer instanceof SpoutPlayer)) {
			if (string.length() < 25) {
				((SpoutPlayer) sPlayer).sendNotification(sPlayer.getName(), string, Material.LOCKED_CHEST);
			} else {
				((SpoutPlayer) sPlayer).sendNotification(sPlayer.getName(), string.substring(0, 25), Material.LOCKED_CHEST);
			}
		} else {
			sPlayer.sendMessage(string);
		}
	}
}
