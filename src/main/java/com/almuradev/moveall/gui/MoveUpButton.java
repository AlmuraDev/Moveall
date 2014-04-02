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
package com.almuradev.moveall.gui;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.WidgetAnchor;

import com.almuradev.moveall.MoveInventory;

public class MoveUpButton extends GenericButton {

	public MoveUpButton() {
		this.setText("Move Up");
		this.setAuto(true);
		this.setAnchor(WidgetAnchor.CENTER_CENTER);
		this.setHeight(18).setWidth(50);
		this.shiftXPos(110).shiftYPos(25);
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		MoveInventory.moveUp(event.getPlayer());
	}
}
