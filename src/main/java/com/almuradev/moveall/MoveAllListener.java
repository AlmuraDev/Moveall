/*
 * This file is part of MoveAll.
 *
 * © 2013 AlmuraDev <http://www.almuradev.com/>
 * MoveAll is licensed under the GNU General Public License.
 *
 * MoveAll is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MoveAll is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License. If not,
 * see <http://www.gnu.org/licenses/> for the GNU General Public License.
 */
package com.almuradev.moveall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

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
		if (!(keypressed.equals(Keyboard.KEY_T))) {
			if (!(keypressed.equals(Keyboard.KEY_R))) {
				if (keypressed.equals("KEY_ESCAPE")) {
					System.out.println("Exited");
					return;
				}
			}
		}
			
		Player sPlayer = event.getPlayer();
		ScreenType screentype = event.getScreenType();		
		if (!sPlayer.hasPermission("moveall.all")) {
			return;
		}
		
		// CHEST_INVENTORY SCREEN
		if (screentype == ScreenType.CHEST_INVENTORY) {

			// CHEST or DOUBLECHEST
			if (keypressed.equals(Keyboard.KEY_T)) {
				InventoryView inventory = sPlayer.getOpenInventory();				
				Inventory to = inventory.getBottomInventory();				
				Inventory from = inventory.getTopInventory();				
				final List<ItemStack> adjustedContents = new ArrayList<ItemStack>();
				for (ItemStack i : from.getContents()) {
					if (i == null || i.getType() == Material.AIR) {
						continue;
					}
					adjustedContents.add(i);
				}
				final Map<Integer, ItemStack> unmergable = to.addItem(adjustedContents.toArray(new ItemStack[adjustedContents.size()]));
				from.clear();
				for (Map.Entry<Integer, ItemStack> entry : unmergable.entrySet()) {					
					from.setItem(entry.getKey(), entry.getValue());					
				}

				MoveInventory.sendNotification(sPlayer, "Items Moved.");

			}

			// CHEST or DOUBLECHEST
			if (keypressed.equals(Keyboard.KEY_R)) {
				InventoryView transaction = sPlayer.getOpenInventory();				
				final List<ItemStack> adjustedContents = new ArrayList<ItemStack>();
				ItemStack[] contents;
				// Bottom -> Top				
				contents = transaction.getBottomInventory().getContents();				
				//Step 1 - Remove nulls/AIR
				for (int i = 9; i < contents.length; i ++) {					
					if (contents[i] == null || contents[i].getType() == Material.AIR) {
						continue;
					}
					adjustedContents.add(new ItemStack(contents[i]));
					contents[i].setType(Material.AIR);
				}
				transaction.getBottomInventory().setContents(contents);			
				
				//Step 2 - Perform merge
				final Map<Integer, ItemStack> unmergable = transaction.getTopInventory().addItem(adjustedContents.toArray(new ItemStack[adjustedContents.size()]));
				
				//Step 3 - Handle leftovers
				for (Map.Entry<Integer, ItemStack> entry : unmergable.entrySet()) {
					transaction.getBottomInventory().setItem(entry.getKey(), entry.getValue());
				}
				MoveInventory.sendNotification(sPlayer, "Items Moved.");

			}		
		}		
	}
}