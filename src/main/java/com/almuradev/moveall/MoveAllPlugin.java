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

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MoveAllPlugin extends JavaPlugin {

	private static MoveAllPlugin instance;

	public static MoveAllPlugin getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		super.onEnable();
		instance = this;

        // Read in default config.yml
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new MoveAllListener(this), this);
			
	}
}
