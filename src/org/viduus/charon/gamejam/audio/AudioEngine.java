package org.viduus.charon.gamejam.audio;

import org.viduus.charon.global.AbstractGameSystems;
import org.viduus.charon.global.AbstractGameSystems.PauseType;
import org.viduus.charon.global.GameInfo;
import org.viduus.charon.global.util.ResourceLoader;
import org.viduus.charon.global.util.logging.ErrorHandler;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class AudioEngine extends org.viduus.charon.global.audio.AudioEngine{

	public static Sound INTRO_SOUND;
	public static Music MENU_MUSIC;
	public static Music LEVEL1_MUSIC;
	
    public AudioEngine() {
	}
	
	@Override
	protected void onLoadEngine(AbstractGameSystems game_systems) {
		TinySound.init();
		
		INTRO_SOUND = ErrorHandler.tryRunThrow(() -> TinySound.loadSound(ResourceLoader.loadResource("resources/audio/sfx/intro/viduus.ogg")));
		MENU_MUSIC = TinySound.loadMusic(ErrorHandler.tryRunThrow(() -> ResourceLoader.loadResourceWithError("resources/audio/music/menu/main_menu.ogg")));
		
		new Thread(new Runnable() {
			public void run() {
				ErrorHandler.tryRunThrow(() -> {
					LEVEL1_MUSIC = TinySound.loadMusic(ErrorHandler.tryRunThrow(() -> ResourceLoader.loadResourceWithError("resources/audio/music/game/gamejam_level1_theme.ogg")));
				});
			}
		}).start();
	}
	
	@Override
	protected void onLoadGame(GameInfo game_info) {
		
	}
	
	@Override
	protected void onSaveAndDisposeGame() {
		
	}
	
	@Override
	protected void onSaveAndDisposeEngine() {
		INTRO_SOUND.unload();
		MENU_MUSIC.unload();
		LEVEL1_MUSIC.unload();
		TinySound.shutdown();
	}
	
	@Override
	public void onStartGame() {
		
	}
	
	@Override
	public void onPauseGame(PauseType pause_type) {
		
	}
	
	@Override
	public void onContinueGame() {
		
	}
	
	@Override
	public void onStopGame() {
		
	}
	
}
