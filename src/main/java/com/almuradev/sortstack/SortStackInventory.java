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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SortStackInventory {

	public static void sortInventoryItems(Player sPlayer, Inventory inventory) {
		stackInventoryItems(sPlayer, inventory);
		orderInventoryItems(inventory, 0);
	}

	public static void sortPlayerInventoryItems(Player sPlayer) {
		Inventory inventory = sPlayer.getInventory();

		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack item1 = inventory.getItem(i);
			if ((item1 == null) || 
					(item1.getAmount() == 64))
				continue;
			if ((i < 9) && ((item1.getAmount() == 0) || (isTool(item1)) || 
					(isWeapon(item1)) || (isArmor(item1)) || 
					(isFood(item1)) || (isBucket(item1)) || (isVehicle(item1)))) {
				continue;
			}
			for (int j = i + 1; j < inventory.getSize(); j++) {
				moveitemInventory(sPlayer, inventory, j, i);
			}

		}

		orderInventoryItems(inventory, 9);
	}

	public static void sortinventory(SpoutPlayer sPlayer, ScreenType screentype) {
		// sort the ordinary player inventory
		SortStackInventory.sortPlayerInventoryItems(sPlayer);
	}

	private static void stackInventoryItems(Player sPlayer,
			Inventory inventory) {
		int i, j;
		for (i = 0; i < inventory.getSize(); i++) {
			for (j = i + 1; j < inventory.getSize(); j++) {
				moveitemInventory(sPlayer, inventory, j, i);
			}
		}
	}



	private static void moveitemInventory(Player sPlayer, Inventory inventory,
			int fromslot, int toslot) {

		ItemStack fromitem = inventory.getItem(fromslot);
		ItemStack toitem = inventory.getItem(toslot);
		int from_amt;

		if (fromitem == null)
			from_amt = 0;
		else
			from_amt = fromitem.getAmount();
		int to_amt;

		if (toitem == null)
			to_amt = 0;
		else
			to_amt = toitem.getAmount();
		int total_amt = from_amt + to_amt;
		if (((from_amt != 0) || (to_amt != 0)) && (from_amt != 0))
		{
			if ((to_amt == 0) && (from_amt > 0)) {
				to_amt = total_amt;
				from_amt = 0;

				inventory.setItem(toslot, fromitem);
				inventory.getItem(toslot).setAmount(to_amt);
				inventory.clear(fromslot);
			} else {
				// Here is to_amt > and from_amt>0 so move all what's possible if
				// it is the same kind of item.
				if (VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.sort")) {
					// okay...

				} else if ((isTool(fromitem) && !VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.stack.tools"))
						|| (isWeapon(fromitem) && !VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.stack.weapons"))
						|| (isBucket(fromitem) && !VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.stack.buckets"))
						|| (isArmor(fromitem) && !VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.stack.armors"))
						|| (isFood(fromitem) && !VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.stack.foods"))
						|| (isVehicle(fromitem) && !VaultUtil.hasPermission(sPlayer.getName(), sPlayer.getWorld().getName(), "sortstack.stack.vehicles"))) {
					return;
				}
				if ((fromitem.getTypeId() == toitem.getTypeId()) && 
						(fromitem.getDurability() == toitem.getDurability())) {
					if ((fromitem.getData() != null) && (toitem.getData() != null)) {
						if (!fromitem.getData().equals(toitem.getData()))
						{
							return;
						}
						if (!fromitem.getEnchantments().equals(toitem.getEnchantments())) {
							return;
						}
					}
					if (total_amt > 64) {
						to_amt = 64;
						from_amt = total_amt - 64;				
						fromitem.setAmount(from_amt);
						toitem.setAmount(to_amt);
					} else {
						// total_amt is <= 64 so everything goes to toslot

								inventory.setItem(toslot, fromitem);
								inventory.getItem(toslot).setAmount(total_amt);
								inventory.clear(fromslot);
					}
				}
			}
		}
	}

	public static void orderInventoryItems(Inventory inventory, int startslot) {

		String SORT_SORTSEQ;
		String[] SORTSEQ;
		SORT_SORTSEQ = SortStackPlugin.getInstance().getConfig().getString("SortSEQ", "STONE,COBBLESTONE,DIRT,WOOD");
		SORTSEQ = SORT_SORTSEQ.split(",");

		int n = startslot;
		for (int m = 0; m < SORTSEQ.length; m++) {
			Material mat = Material.matchMaterial(SORTSEQ[m]);
			if (mat == null) {
				//Material not Found error Message.
				;
			} else if (inventory.contains(mat)) {
				for (int i = n; i < inventory.getSize(); i++) {
					if ((inventory.getItem(i) != null && inventory.getItem(i).getType() == mat)) {
						n++;
						// continue;
					} else {
						for (int j = i + 1; j < inventory.getSize(); j++) {
							if ((inventory.getItem(j) != null && inventory.getItem(j).getType() == mat)) {
								switchInventoryItems(inventory, i, j);
								n++;
								break;
							}
						}
					}
				}
			}
		}
	}

	private static void switchInventoryItems(Inventory inventory, int slot1, int slot2) {
		ItemStack item = inventory.getItem(slot1);
		inventory.setItem(slot1, inventory.getItem(slot2));
		inventory.setItem(slot2, item);
	}

	public static boolean isTool(ItemStack item) {

		String SORT_TOOLS;
		String[] tools;
		SORT_TOOLS = SortStackPlugin.getInstance().getConfig().getString("Tools", "256,257,258,269,270,271,273,274,275,277,278,279,284,285,286,290,291,292,293,294,346");
		tools = SORT_TOOLS.split(",");

		for (String i : tools) {
			if (item.getTypeId() == Integer.valueOf(i)) {
				return true;
			}
		}
		return false;
	}

	// Check if it is a weapon
	public static boolean isWeapon(ItemStack item) {

		String SORT_WEAPONS;
		String[] weapons;
		SORT_WEAPONS = SortStackPlugin.getInstance().getConfig().getString("Weapons", "267,268,272,276,283,261");
		weapons = SORT_WEAPONS.split(",");

		for (String i : weapons) {
			if (item.getTypeId() == Integer.valueOf(i)) {
				return true;
			}
		}
		return false;
	}

	// Check if it is a armor
	public static boolean isArmor(ItemStack item) {

		String SORT_ARMORS;
		String[] armors;
		SORT_ARMORS = SortStackPlugin.getInstance().getConfig().getString("Armors", "298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317");
		armors = SORT_ARMORS.split(",");

		for (String i : armors) {
			if (item.getTypeId() == Integer.valueOf(i)) {
				return true;
			}
		}
		return false;
	}

	// Check if it is food
	public static boolean isFood(ItemStack item) {

		String SORT_FOODS;
		String[] foods;
		SORT_FOODS = SortStackPlugin.getInstance().getConfig().getString("Foods", "260,282,297,319,320,322,349,350,354,357");
		foods = SORT_FOODS.split(",");

		for (String i : foods) {
			if (item.getTypeId() == Integer.valueOf(i)) {
				return true;
			}
		}
		return false;
	}

	// Check if it is a vehicles
	public static boolean isVehicle(ItemStack item) {

		String SORT_VEHICLES;
		String[] vehicles;
		SORT_VEHICLES = SortStackPlugin.getInstance().getConfig().getString("Sort.Vehicles", "328,333,342,343");
		vehicles = SORT_VEHICLES.split(",");

		for (String i : vehicles) {
			if (item.getTypeId() == Integer.valueOf(i)) {
				return true;
			}
		}
		return false;
	}

	// Check if it is buckets
	public static boolean isBucket(ItemStack item) {

		String SORT_BUCKETS;
		String[] buckets;
		SORT_BUCKETS = SortStackPlugin.getInstance().getConfig().getString("Sort.Buckets", "326,327,335");
		buckets = SORT_BUCKETS.split(",");

		for (String i : buckets) {
			if (item.getTypeId() == Integer.valueOf(i)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isChest(Block block) {
		if (block != null) {
			if (block.getType().equals(Material.CHEST)
					|| block.getType().equals(Material.LOCKED_CHEST)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isBookshelf(Block sBlock) {
		if (sBlock != null) {
			if (sBlock.getType().equals(Material.BOOKSHELF)) {
				return true;
			}
		}
		return false;
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
