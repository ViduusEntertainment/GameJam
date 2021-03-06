package org.viduus.charon.gamejam.world.objects.character.nonplayable;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.global.GameConstants.Property;
import org.viduus.charon.global.world.AbstractWorldEngine;

public class TankEnemy extends Enemy{

	private static final float SPEED = 5.0f;
	private static final float HEALTH = 800;
	
	public TankEnemy(AbstractWorldEngine world_engine, String name, Vector2 location) {
		super(world_engine, name, location, SPEED, HEALTH, 0, HEALTH, 0, "vid:animation:enemies/enemies", "tank", "walk_l", 200);
		set(Property.INITIAL_LINEAR_VELOCITY, new Vector2(-100, 0));
	}
}
