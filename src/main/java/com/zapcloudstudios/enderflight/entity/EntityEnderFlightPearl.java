package com.zapcloudstudios.enderflight.entity;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.zapcloudstudios.enderflight.EnderFlight;
import com.zapcloudstudios.enderflight.client.ClientProxy;
import com.zapcloudstudios.enderflight.player.PlayerData;
import com.zapcloudstudios.enderflight.target.NullTarget;
import com.zapcloudstudios.enderflight.target.PlayerTarget;
import com.zapcloudstudios.enderflight.target.SingleTarget;
import com.zapcloudstudios.enderflight.target.Target;

public class EntityEnderFlightPearl extends EntityEnderFlightProjectile
{
	protected Target theTarget = NullTarget.nullTarget;

	public EntityEnderFlightPearl(World world)
	{
		super(world);
	}

	public EntityEnderFlightPearl(World world, Target target)
	{
		super(world);
		this.theTarget = target;
	}

	public boolean doesPearlPullPlayers()
	{
		return true;
	}

	@Override
	public void onUpdate()
	{
		if (EnderFlight.proxy instanceof ClientProxy)
		{
			ClientProxy cp = (ClientProxy) EnderFlight.proxy;
			if (cp.ghost != null)
			{
				cp.ghost.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			}
		}

		ArrayList<SingleTarget> allTargets = new ArrayList<SingleTarget>();
		this.theTarget.getAllTargets(allTargets);
		for (SingleTarget t : allTargets)
		{
			if (t instanceof PlayerTarget)
			{
				EntityPlayer player = ((PlayerTarget) t).getPlayer(this.worldObj);
				PlayerData data = (PlayerData) player.getExtendedProperties(EnderFlight.PLAYERDATA);
				data.flyPearl = this;
			}
		}

		super.onUpdate();
	}

	@Override
	public void onImpact(MovingObjectPosition hit)
	{
		for (int i = 0; i < 32; ++i)
		{
			this.worldObj.spawnParticle("portal", this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
		}

		ArrayList<SingleTarget> allTargets = new ArrayList<SingleTarget>();
		this.theTarget.getAllTargets(allTargets);
		for (SingleTarget t : allTargets)
		{
			if (t instanceof PlayerTarget)
			{
				EntityPlayer player = ((PlayerTarget) t).getPlayer(this.worldObj);

				if (!this.worldObj.isRemote && player != null && player instanceof EntityPlayerMP)
				{
					EntityPlayerMP entityplayermp = (EntityPlayerMP) player;

					if (entityplayermp.playerNetServerHandler.func_147362_b().isChannelOpen() && entityplayermp.worldObj == this.worldObj)
					{
						EnderTeleportEvent event = new EnderTeleportEvent(entityplayermp, this.posX, this.posY, this.posZ, 5.0F);
						if (!MinecraftForge.EVENT_BUS.post(event))
						{
							if (player.isRiding())
							{
								player.mountEntity((Entity) null);
							}

							player.setPositionAndUpdate(event.targetX, event.targetY, event.targetZ);
							player.fallDistance = 0.0F;
							player.attackEntityFrom(DamageSource.fall, event.attackDamage);
						}
					}
				}

				PlayerData data = EnderFlight.getPlayerData(player);
				data.flyPearl = null;
			}
		}

		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}

	@Override
	public boolean isEntityImpactValid(Entity hit)
	{
		return false;
	}

	public static EntityEnderFlightPearl getPearlFromVanilla(EntityEnderPearl pearl, World world)
	{
		EntityEnderFlightPearl flightPearl = new EntityEnderFlightPearl(world);
		EntityLivingBase thrower = pearl.getThrower();
		if (thrower instanceof EntityPlayer)
		{
			flightPearl.theTarget = new PlayerTarget((EntityPlayer) thrower);
		}
		flightPearl.setPositionAndRotation(pearl.posX, pearl.posY, pearl.posZ, pearl.rotationYaw, pearl.prevRotationPitch);
		flightPearl.setVelocity(pearl.motionX, pearl.motionY, pearl.motionZ);
		return flightPearl;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.theTarget = Target.read(nbt.getCompoundTag("target"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setTag("target", Target.write(this.theTarget));
	}
}
