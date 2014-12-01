package com.zapcloudstudios.enderflight.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEnderFlightPearl extends EntityEnderFlightProjectile
{
	public EntityEnderFlightPearl(World world)
	{
		super(world);
	}

	@Override
	public void onImpact(MovingObjectPosition hit)
	{
	}

	@Override
	public boolean isEntityImpactValid(Entity hit)
	{
		return false;
	}
}
