package com.zapcloudstudios.enderflight.target;

import java.util.Collection;

import net.minecraft.nbt.NBTTagCompound;

public abstract class Target
{
	public abstract void getAllTargets(Collection<SingleTarget> targets);

	protected abstract void readFromNBT(NBTTagCompound nbt);

	protected abstract void writeToNBT(NBTTagCompound nbt);

	protected static Target getTargetFromType(String type)
	{
		switch (type)
		{
			case "PLAYER":
				return new PlayerTarget();
			case "MULTI":
				return new MultiTarget();
			case "NULL":
			default:
				return NullTarget.nullTarget;
		}
	}

	protected abstract String getType();

	public static NBTTagCompound write(Target target)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("targetType", target.getType());
		target.writeToNBT(nbt);
		return nbt;
	}

	public static Target read(NBTTagCompound nbt)
	{
		Target target = getTargetFromType(nbt.getString("targetType"));
		target.readFromNBT(nbt);
		return target;
	}
}
