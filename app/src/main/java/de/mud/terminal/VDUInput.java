/*
 * This file is part of "JTA - Telnet/SSH for the JAVA(tm) platform".
 *
 * (c) Matthias L. Jugel, Marcus Meißner 1996-2005. All Rights Reserved.
 *
 * Please visit http://javatelnet.org/ for updates and contact.
 *
 * --LICENSE NOTICE--
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 * --LICENSE NOTICE--
 *
 */
package de.mud.terminal;

import java.util.Properties;

/**
 * An interface for a terminal that accepts input from keyboard and mouse.
 *
 * @author Matthias L. Jugel, Marcus Meißner
 * @version $Id: VDUInput.java,v 1.1 2010/03/15 22:02:06 zhang42 Exp $
 */
public interface VDUInput {

  int KEY_CONTROL = 0x01;
  int KEY_SHIFT = 0x02;
  int KEY_ALT = 0x04;
  int KEY_ACTION = 0x08;



  /**
   * Direct access to writing data ...
   * @param b
   */
  void write(byte b[]);

  /**
   * main keytyping event handler...
   * @param keyCode the key code
   * @param keyChar the character represented by the key
   * @param modifiers shift/alt/control modifiers
   */
  void keyPressed(int keyCode, char keyChar, int modifiers);

}
