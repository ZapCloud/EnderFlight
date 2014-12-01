package com.zapcloudstudios.enderflight.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class EntityEnderFlightProjectile extends Entity implements IProjectile
{
	public float slowFactor = 0.99F;
	public float waterSlowFactor = 0.8F;
	public float gravity = 0.03F;
	public float angleFollowFactor = 0.2F;
	private int flightTicks = 0;

	public EntityEnderFlightProjectile(World world)
	{
		super(world);
	}

	@Override
	public void setThrowableHeading(double xvel, double yvel, double zvel, float velocity, float randomFactor)
	{
		float normal = MathHelper.sqrt_double(xvel * xvel + yvel * yvel + zvel * zvel);
		xvel /= normal;
		yvel /= normal;
		zvel /= normal;
		xvel += this.rand.nextGaussian() * 0.0075D * randomFactor;
		yvel += this.rand.nextGaussian() * 0.0075D * randomFactor;
		zvel += this.rand.nextGaussian() * 0.0075D * randomFactor;
		xvel *= velocity;
		yvel *= velocity;
		zvel *= velocity;
		this.motionX = xvel;
		this.motionY = yvel;
		this.motionZ = zvel;
		float flatnormal = MathHelper.sqrt_double(xvel * xvel + zvel * zvel);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(xvel, zvel) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(yvel, flatnormal) * 180.0D / Math.PI);
	}

	protected float getGravityVelocity()
	{
		return this.gravity;
	}

	public int getFlightTicks()
	{
		return this.flightTicks;
	}

	public abstract boolean isEntityImpactValid(Entity hit);

	public abstract void onImpact(MovingObjectPosition hit);

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	@Override
	public void onUpdate()
	{
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();

		Vec3 position = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 nextposition = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition hit = this.worldObj.rayTraceBlocks(position, nextposition);
		position = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		nextposition = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (hit != null)
		{
			nextposition = Vec3.createVectorHelper(hit.hitVec.xCoord, hit.hitVec.yCoord, hit.hitVec.zCoord);
		}

		if (!this.worldObj.isRemote)
		{
			Entity entityhit = null;
			List<? extends Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double lasthitdist = 0.0D;

			for (Entity posiblehitentity : list)
			{
				if (posiblehitentity.canBeCollidedWith() && this.isEntityImpactValid(posiblehitentity))
				{
					float expand = 0.3F;
					AxisAlignedBB axisalignedbb = posiblehitentity.boundingBox.expand(expand, expand, expand);
					MovingObjectPosition entityhitposition = axisalignedbb.calculateIntercept(position, nextposition);

					if (entityhitposition != null)
					{
						double hitdist = position.distanceTo(entityhitposition.hitVec);

						if (hitdist < lasthitdist || lasthitdist == 0.0D)
						{
							entityhit = posiblehitentity;
							lasthitdist = hitdist;
						}
					}
				}
			}

			if (entityhit != null)
			{
				hit = new MovingObjectPosition(entityhit);
			}
		}

		if (hit != null)
		{
			if (hit.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlock(hit.blockX, hit.blockY, hit.blockZ) == Blocks.portal)
			{
				this.setInPortal();
			}
			else
			{
				this.onImpact(hit);
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		this.rotationPitch = (float) (Math.atan2(this.motionY, f1) * 180.0D / Math.PI);

		while (this.rotationPitch - this.prevRotationPitch < -180.0F)
		{
			this.prevRotationPitch -= 360.0F;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
		{
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
		{
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
		{
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * this.angleFollowFactor;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * this.angleFollowFactor;
		float slow = this.slowFactor;
		float grav = this.getGravityVelocity();

		if (this.isInWater())
		{
			for (int i = 0; i < 4; ++i)
			{
				float behindFactor = 0.25F;
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX * behindFactor, this.posY - this.motionY * behindFactor, this.posZ - this.motionZ * behindFactor, this.motionX, this.motionY, this.motionZ);
			}

			slow = this.waterSlowFactor;
		}

		this.motionX *= slow;
		this.motionY *= slow;
		this.motionZ *= slow;
		this.motionY -= grav;

		this.flightTicks++;

		this.setPosition(this.posX, this.posY, this.posZ);
	}

	@Override
	protected void entityInit()
	{
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		this.flightTicks = nbt.getInteger("FlightTicks");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("FlightTicks", this.flightTicks);
	}
}
