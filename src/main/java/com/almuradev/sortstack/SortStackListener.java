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
/*
 * This file is part of Realms.
 *
 * © 2013 AlmuraDev <http://www.almuradev.com/>
 * Realms is licensed under the GNU General Public License.
 *
 * Realms is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Realms is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License. If not,
 * see <http://www.gnu.org/licenses/> for the GNU General Public License.
 */
package com.almuradev.sortstack;

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

public class SortStackListener implements Listener {
    private SortStackPlugin instance;

	public final Logger log = Logger.getLogger("Minecraft");

	public SortStackListener(SortStackPlugin aThis) {
		instance = aThis;
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
		if (keypressed.equals(Keyboard.KEY_S)
				&& VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.sort")
				&& screentype == ScreenType.PLAYER_INVENTORY) {
			SortStackInventory.sortPlayerInventoryItems(sPlayer);
            SortStackPlugin.getInstance().getConfig().getString("SortSEQ", "STONE,COBBLESTONE,DIRT,WOOD");
			if (SortStackPlugin.getInstance().getConfig().getBoolean("SORT_DISPLAYSORTARCHIEVEMENT", true)) {
				SortStackInventory.sendNotification(sPlayer, "Items sorted.");
			}
		}

		// WORKBENCH - CRAFTING_INVENTORY SCREEN
		else if (keypressed.equals(Keyboard.KEY_S)
				&& VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.sort")
				&& screentype == ScreenType.WORKBENCH_INVENTORY) {
			SortStackInventory.sortPlayerInventoryItems(sPlayer);
			if (SortStackPlugin.getInstance().getConfig().getBoolean("SORT_DISPLAYSORTARCHIEVEMENT", true)) {
				SortStackInventory.sendNotification(sPlayer, "Items sorted.");
			}
		}

		// CHEST_INVENTORY SCREEN
		else if (screentype == ScreenType.CHEST_INVENTORY) {

			// CHEST or DOUBLECHEST
			if (keypressed.equals(Keyboard.KEY_S)
					&& VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.sort")) {

				InventoryView inventory = sPlayer.getOpenInventory();

				if (inventory.getTopInventory().getTitle().equalsIgnoreCase("container.chestDouble")) {
					Inventory sChest = inventory.getTopInventory();
					SortStackInventory.sortInventoryItems(sPlayer, sChest);
				}
				if (inventory.getTopInventory().getTitle().equalsIgnoreCase("container.chest")) {
					Inventory sChest = inventory.getTopInventory();
					SortStackInventory.sortInventoryItems(sPlayer, sChest);
				}

				if (inventory.getTopInventory().getTitle().equals("Backpack")) {
					Inventory sChest = inventory.getTopInventory();
					SortStackInventory.sortInventoryItems(sPlayer, sChest);
					SortStackInventory.sortPlayerInventoryItems(sPlayer);
				}

				SortStackInventory.sortPlayerInventoryItems(sPlayer);
				if (SortStackPlugin.getInstance().getConfig().getBoolean("SORT_DISPLAYSORTARCHIEVEMENT", true)) {
					SortStackInventory.sendNotification(sPlayer, "Items sorted.");
				}

			} else if (targetblock != null) { 
				if (SortStackInventory.isBookshelf(targetblock)) {
					// TODO: Sort Bookshelfs?
				}
			}
		}

		// FURNACE_INVENTORY SCREEN
		else if (keypressed.equals(Keyboard.KEY_S)
				&& VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.sort")
				&& screentype == ScreenType.FURNACE_INVENTORY) {
			SortStackInventory.sortPlayerInventoryItems(sPlayer);
			if (SortStackPlugin.getInstance().getConfig().getBoolean("SORT_DISPLAYSORTARCHIEVEMENT", true)) {
				SortStackInventory.sendNotification(sPlayer, "Items sorted.");
			}
		}

		// DISPENCER_INVENTORY SCREEN
		else if (keypressed.equals(Keyboard.KEY_S)
				&& VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.sort")
				&& screentype == ScreenType.DISPENSER_INVENTORY) {
			if (targetblock != null) {
				if (targetblock.getType() == Material.DISPENSER) {
					Dispenser dispenser = (Dispenser) targetblock.getState();
					Inventory inventory = dispenser.getInventory();
					SortStackInventory.sortInventoryItems(sPlayer, inventory);
					SortStackInventory.sortPlayerInventoryItems(sPlayer);
					if (SortStackPlugin.getInstance().getConfig().getBoolean("SORT_DISPLAYSORTARCHIEVEMENT", true)) {
						SortStackInventory.sendNotification(sPlayer, "Items sorted.");
					}
				}
			}
		}
	}
}