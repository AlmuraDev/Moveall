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

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ScreenOpenEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.ScreenType;
import com.almuradev.moveall.gui.MoveDownButton;
import com.almuradev.moveall.gui.MoveUpButton;

public class MoveAllListener implements Listener {	
	GenericButton moveUp, moveDown;
	
	public MoveAllListener(MoveAllPlugin aThis) {}

	@EventHandler
	public void onScreenOpen(final ScreenOpenEvent e) {
		ScreenType st = e.getScreenType();
		if (!st.equals(ScreenType.CHEST_INVENTORY) || !e.getPlayer().hasPermission("moveall.all")) {
			return;
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(MoveAllPlugin.getInstance(), new Runnable() {
			public void run() {
				moveUp = new MoveUpButton();
				moveDown = new MoveDownButton();
				e.getPlayer().getCurrentScreen().attachWidgets(MoveAllPlugin.getInstance(), moveUp, moveDown);
			}
		}, 1L);
	}
}