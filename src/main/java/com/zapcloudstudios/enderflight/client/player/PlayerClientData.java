package com.zapcloudstudios.enderflight.client.player;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import com.zapcloudstudios.enderflight.player.PlayerData;

public class PlayerClientData extends PlayerData
{
	public boolean isClientFlying(EntityClientPlayerMP player)
	{
		return false;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		super.saveNBTData(compound);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		super.loadNBTData(compound);
	}
}
