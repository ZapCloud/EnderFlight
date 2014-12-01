package com.zapcloudstudios.enderflight.target;

import java.util.Collection;

import net.minecraft.nbt.NBTTagCompound;

public class NullTarget extends Target
{
	public static final NullTarget nullTarget = new NullTarget();

	private NullTarget()
	{

	}

	@Override
	public void getAllTargets(Collection<SingleTarget> targets)
	{
	}

	@Override
	protected void readFromNBT(NBTTagCompound nbt)
	{
	}

	@Override
	protected void writeToNBT(NBTTagCompound nbt)
	{
	}

	@Override
	protected String getType()
	{
		return "NULL";
	}
}
