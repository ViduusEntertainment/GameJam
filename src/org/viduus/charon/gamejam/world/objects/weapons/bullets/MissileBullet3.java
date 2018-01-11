package org.viduus.charon.gamejam.world.objects.weapons.bullets;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.global.event.events.HitByWeaponEvent;
import org.viduus.charon.global.physics.twodimensional.filters.Bullet2DFilter;
import org.viduus.charon.global.util.identification.Uid;
import org.viduus.charon.global.world.AbstractWorldEngine;
import org.viduus.charon.global.world.objects.twodimensional.weapon.Weapon2D;
import org.viduus.charon.global.world.regions.BaseRegion;

public class MissileBullet3 extends Bullet {

	/**
	 * @param world_engine
	 * @param name
	 * @param location
	 * @param sprite_map
	 */
	public MissileBullet3(AbstractWorldEngine world_engine, Uid uid, String name, Weapon2D owner, Vector2 location, float damage) {
		super(world_engine, uid, name, owner, location, new Vector2(800, 0), "vid:animation:objects/bullets", "player_missile_3", damage);
		setCollisionFilter(new Bullet2DFilter(this));
	}

	@Override
	protected void onHitByWeapon(HitByWeaponEvent hit_by_weapon_event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onObjectAdded(BaseRegion region) {}
}
