package com.zapcloudstudios.enderflight.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.zapcloudstudios.enderflight.entity.EntityEnderFlightPearl;

public class PlayerData implements IExtendedEntityProperties
{
	public EntityEnderFlightPearl flyPearl = null;

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{

	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{

	}

	@Override
	public void init(Entity entity, World world)
	{

	}

	public void runTick(EntityPlayer player)
	{
		if (this.flyPearl != null && !this.flyPearl.isEntityAlive())
		{
			this.flyPearl = null;
		}
	}
}
