/**
 * Copyright 2017, Viduus Entertainment LLC, All rights reserved.
 * 
 * Created on Jan 6, 2018 by Ethan Toney
 */
package org.viduus.charon.gamejam.world;

import java.util.Arrays;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Settings;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.viduus.charon.gamejam.event.WorldEventEngine;
import org.viduus.charon.gamejam.physics.twodimensional.WorldBounds;
import org.viduus.charon.gamejam.physics.twodimensional.filters.WorldFilter;
import org.viduus.charon.gamejam.physics.twodimensional.listeners.BoundsListener;
import org.viduus.charon.gamejam.world.regions.Level1;
import org.viduus.charon.global.GameConstants.Property;
import org.viduus.charon.global.GameInfo;
import org.viduus.charon.global.graphics.util.Size;
import org.viduus.charon.global.physics.twodimensional.listeners.CollisionListener;
import org.viduus.charon.global.util.identification.Uid;
import org.viduus.charon.global.world.AbstractWorldEngine;
import org.viduus.charon.global.world.objects.twodimensional.character.playable.PlayableCharacter2D;
import org.viduus.charon.global.world.regions.BaseRegion;

/**
 * 
 *
 * @author Ethan Toney
 */
public class WorldEngine extends AbstractWorldEngine {
	
	private Size world_size;
	
	/**
	 * @param fps
	 */
	public WorldEngine(int fps) {
		super(fps, new WorldEventEngine());
	}

	/* (non-Javadoc)
	 * @see org.viduus.charon.global.world.AbstractWorldEngine#getNpcResolverClassPath()
	 */
	@Override
	protected String getNpcResolverClassPath() {
		return "org.viduus.charon.gamejam.world.objects.character.nonplayable";
	}

	/* (non-Javadoc)
	 * @see org.viduus.charon.global.world.WorldEngine#getRegionResolverClassPath()
	 */
	@Override
	protected String getRegionResolverClassPath() {
		return "org.viduus.charon.gamejam.world.regions";
	}

	/* (non-Javadoc)
	 * @see org.viduus.charon.global.world.WorldEngine#getCharacterResolverClassPath()
	 */
	@Override
	protected String getCharacterResolverClassPath() {
		return "org.viduus.charon.gamejam.world.objects.character.playable";
	}
	
	/* (non-Javadoc)
	 * @see org.viduus.charon.global.world.WorldEngine#getWeaponResolverClassPath()
	 */
	@Override
	protected String getWeaponResolverClassPath() {
		return "org.viduus.charon.gamejam.world.objects.weapons";
	}
	
	/* (non-Javadoc)
	 * @see org.viduus.charon.global.world.WorldEngine#getBulletResolverClassPath()
	 */
	@Override
	protected String getBulletResolverClassPath() {
		return "org.viduus.charon.gamejam.world.objects.weapons.bullets";
	}

	@Override
	protected String getEffectResolverClassPath() {
		return "org.viduus.charon.gamejam.world.objects.effects";
	}

	/* (non-Javadoc)
	 * @see org.viduus.charon.global.GameEngine#onLoadGame(org.viduus.charon.global.GameInfo)
	 */
	@Override
	protected void onLoadGame(GameInfo game_info) {
		super.onLoadGame(game_info);
		
		// Create all the regions
		BaseRegion[] regions = new BaseRegion[] {
			new Level1(this, game_systems.graphics_engine, game_info.party)
		};
		Arrays.stream(regions).forEach(region -> {
			insert(region);
			region.load();
		});
		
		// Load in demo Main Character information
		PlayableCharacter2D main_character = game_info.party.get(0);
		main_character.set(Property.CURRENT_REGION_ID, new Uid("vid:region:level_1"));
		
		// Load demo region and put player in region
		BaseRegion character_region = (BaseRegion) resolve((Uid) main_character.get(Property.CURRENT_REGION_ID));
		for (PlayableCharacter2D party_member : game_info.party) {
			character_region.queueEntityForAddition(party_member);
		}
	}

	/* (non-Javadoc)
	 * @see org.viduus.charon.global.GameEngine#onSaveAndDisposeEngine()
	 */
	@Override
	protected void onSaveAndDisposeEngine() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected World createWorld() {
		float world_height = game_systems.graphics_engine.getScreenSize().height;
		float world_width = game_systems.graphics_engine.getScreenSize().width;
		
		World world = new World(new WorldBounds(new AABB(0, 0, 2000, world_height)));
		world.setGravity(new Vector2(0, org.viduus.charon.global.GameConstants.PIXELS_PER_METER * 9.8));
		world.addListener(new CollisionListener(this));
		world.addListener(new BoundsListener(this));
		
		Settings settings = new Settings();
		settings.setAutoSleepingEnabled(false);
		settings.setMaximumTranslation(Double.MAX_VALUE);
		
		world.setSettings(settings);
		
		world_size = new Size(world_width, world_height);
		
		Body body = new Body(4);
		BodyFixture left_fixture = body.addFixture(Geometry.createPolygon(
				new Vector2(-10, 0),
				new Vector2(0, 0),
				new Vector2(0, world_height),
				new Vector2(-10, world_height)
		));
		left_fixture.setFilter(new WorldFilter());
		BodyFixture up_fixture = body.addFixture(Geometry.createPolygon(
				new Vector2(0, -10),
				new Vector2(world_width, -10),
				new Vector2(world_width, 0),
				new Vector2(0, 0)
		));
		up_fixture.setFilter(new WorldFilter());
		BodyFixture down_fixture = body.addFixture(Geometry.createPolygon(
				new Vector2(0, world_height),
				new Vector2(world_width, world_height),
				new Vector2(world_width, world_height + 10),
				new Vector2(0, world_height + 10)
		));
		down_fixture.setFilter(new WorldFilter());
		BodyFixture right_fixture = body.addFixture(Geometry.createPolygon(
				new Vector2(world_width, 0),
				new Vector2(world_width + 10, 0),
				new Vector2(world_width + 10, world_height),
				new Vector2(world_width, world_height)
		));
		right_fixture.setFilter(new WorldFilter());
		body.setMass(MassType.INFINITE);
		
		world.addBody(body);
		
		return world;
	}

	public Size getWorldSize() {
		return this.world_size;
	}
}
