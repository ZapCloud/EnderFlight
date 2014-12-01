package com.zapcloudstudios.enderflight.client.player;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.zapcloudstudios.enderflight.player.PlayerData;

public class PlayerClientData extends PlayerData
{
	public boolean isClientGhost(EntityClientPlayerMP player)
	{
		if (this.flyPearl == null)
		{
			return false;
		}
		return this.flyPearl.doesPearlPullPlayers();
	}

	@Override
	public void runTick(EntityPlayer player)
	{
		super.runTick(player);
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
