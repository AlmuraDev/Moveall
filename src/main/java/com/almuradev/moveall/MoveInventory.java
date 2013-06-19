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

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MoveInventory {
		
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
