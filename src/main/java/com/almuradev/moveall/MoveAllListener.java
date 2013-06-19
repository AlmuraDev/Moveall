/*
 * This file is part of SortStack.
 *
 * © 2013 AlmuraDev <http://www.almuradev.com/>
 * SortStack is licensed under the GNU General Public License.
 *
 * SortStack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SortStack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License. If not,
 * see <http://www.gnu.org/licenses/> for the GNU General Public License.
 */
package com.almuradev.moveall;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.keyboard.Keyboard;

public class MoveAllListener implements Listener {
    public final Logger log = Logger.getLogger("Minecraft");

	public MoveAllListener(MoveAllPlugin aThis) {
	}

	@EventHandler
	public void onKeyPressedEvent(KeyPressedEvent event) {

		Keyboard keypressed = event.getKey();
		if (!(keypressed.equals(Keyboard.KEY_S) || keypressed
				.equals("KEY_ESCAPE"))) {
			return;
		}

		Player sPlayer = event.getPlayer();
		ScreenType screentype = event.getScreenType();		
		Block targetblock = null;
		try {
			targetblock = sPlayer.getTargetBlock(null, 4);			
		} catch (Exception e) {

		}		

		// PLAYER_INVENTORY
		if (keypressed.equals(Keyboard.KEY_T) && VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.sort")	&& screentype == ScreenType.PLAYER_INVENTORY) {
		
		}

		// WORKBENCH - CRAFTING_INVENTORY SCREEN
		else if (keypressed.equals(Keyboard.KEY_T) && VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.sort") && screentype == ScreenType.WORKBENCH_INVENTORY) {
			//MoveInventory.sortPlayerInventoryItems(sPlayer);
			if (MoveAllPlugin.getInstance().getConfig().getBoolean("SORT_DISPLAYSORTARCHIEVEMENT", true)) {
				MoveInventory.sendNotification(sPlayer, "Items sorted.");
			}
		}

		// CHEST_INVENTORY SCREEN
		else if (screentype == ScreenType.CHEST_INVENTORY) {

			// CHEST or DOUBLECHEST
			if (keypressed.equals(Keyboard.KEY_T)
					&& VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.sort")) {

				InventoryView inventory = sPlayer.getOpenInventory();

				if (inventory.getTopInventory().getTitle().equalsIgnoreCase("container.chestDouble")) {
					Inventory sChest = inventory.getTopInventory();
					//MoveInventory.sortInventoryItems(sPlayer, sChest);
				}
				if (inventory.getTopInventory().getTitle().equalsIgnoreCase("container.chest")) {
					Inventory sChest = inventory.getTopInventory();
					//MoveInventory.sortInventoryItems(sPlayer, sChest);
				}

				if (inventory.getTopInventory().getTitle().equals("Backpack")) {
					Inventory sChest = inventory.getTopInventory();
					//MoveInventory.sortInventoryItems(sPlayer, sChest);
					//MoveInventory.sortPlayerInventoryItems(sPlayer);
				}

				//MoveInventory.sortPlayerInventoryItems(sPlayer);
				if (MoveAllPlugin.getInstance().getConfig().getBoolean("SORT_DISPLAYSORTARCHIEVEMENT", true)) {
					//MoveInventory.sendNotification(sPlayer, "Items sorted.");
				}
			}		
		}		
	}
}