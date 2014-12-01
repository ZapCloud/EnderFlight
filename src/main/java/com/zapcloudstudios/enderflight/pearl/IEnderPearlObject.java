package com.zapcloudstudios.enderflight.pearl;

import net.minecraft.nbt.NBTTagCompound;

public interface IEnderPearlObject
{
	public void writeEnderData(NBTTagCompound nbt);

	public void readEnderData(NBTTagCompound nbt);
}
