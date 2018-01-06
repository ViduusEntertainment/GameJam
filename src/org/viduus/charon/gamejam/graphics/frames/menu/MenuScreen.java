/**
 * Copyright 2017, Viduus Entertainment LLC, All rights reserved.
 * 
 * Created on Jan 6, 2018 by Ethan Toney
 */
package org.viduus.charon.gamejam.graphics.frames.menu;

import org.viduus.charon.gamejam.GameSystems;
import org.viduus.charon.global.AbstractGameSystems;
import org.viduus.charon.global.GameInfo;
import org.viduus.charon.global.audio.AudioCategory;
import org.viduus.charon.global.audio.Sound;
import org.viduus.charon.global.graphics.AbstractGraphicsEngine;
import org.viduus.charon.global.graphics.GameFrame;
import org.viduus.charon.global.graphics.opengl.components.OpenGLButton;
import org.viduus.charon.global.graphics.screens.AbstractGameScreen;
import org.viduus.charon.global.input.controller.ControllerState;
import org.viduus.charon.global.player.PlayerParty;

/**
 * 
 *
 * @author Ethan Toney
 */
public class MenuScreen extends AbstractGameScreen {

	private OpenGLButton start_button;
	private OpenGLButton exit_button;
	private Sound menu_sound;

	/**
	 * @param game_frame
	 */
	public MenuScreen(GameFrame game_frame) {
		super(game_frame);
		
		game_frame.setDesiredFPS(30);
		
		start_button = new OpenGLButton("Start Game");
		start_button.setBackgroundColor(182, 182, 182);
		start_button.setHoverColor(83, 119, 215);
		add(start_button);
		
		exit_button = new OpenGLButton("Exit");
		exit_button.setBackgroundColor(182, 182, 182);
		exit_button.setHoverColor(83, 119, 215);
		add(exit_button);
	}
	
	@Override
	public void onControllerState(ControllerState e) {
		super.onControllerState(e);
		
		if(e.getKeyState(ControllerState.SELECT_KEY) == ControllerState.PRESSED_STATE) {
			if(start_button.hasFocus()) {
				PlayerParty party = new PlayerParty();
//				MainCharacter main_character = new MainCharacter((GameSystems) game_systems, "Sauran", new Vec2(100, 100), "vid:animation:eday_main_character", "character");
//				game_systems.world_engine.insert(main_character);
//				party.add(main_character);
				GameInfo game_info = new GameInfo(GameSystems.GAME, party);
				game_systems.startGame(game_info);
				game_systems.graphics_engine.showFrame(AbstractGraphicsEngine.GAME_SCREEN);
			}
			else if(this.exit_button.hasFocus()) {
				game_systems.closeApplication();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.viduus.charon.global.graphics.screens.AbstractGameScreen#onActivate(org.viduus.charon.global.AbstractGameSystems)
	 */
	@Override
	protected void onActivate(AbstractGameSystems game_systems) {
		menu_sound = game_systems.audio_engine.createSound(AudioCategory.MUSIC, "resources/audio/music/menu/start_menu.ogg", true);
		menu_sound.setToLoop(true);
		game_systems.audio_engine.playSound(menu_sound);
	}

	/* (non-Javadoc)
	 * @see org.viduus.charon.global.graphics.screens.AbstractGameScreen#onDeactivate(org.viduus.charon.global.AbstractGameSystems)
	 */
	@Override
	protected void onDeactivate(AbstractGameSystems game_systems) {
		game_systems.audio_engine.stopSound(menu_sound);
	}

	/* (non-Javadoc)
	 * @see org.viduus.charon.global.graphics.screens.AbstractGameScreen#updateLayout(int, int)
	 */
	@Override
	protected void updateLayout(int width, int height) {
		start_button.setBounds((width-200)/2, height/2, 200, 20);
		exit_button.setBounds((width-200)/2, height/2 + 35, 200, 20);
	}

}
