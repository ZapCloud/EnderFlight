package com.zapcloudstudios.enderflight.target;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.google.common.collect.Lists;

public class MultiTarget extends Target
{
	public List<Target> targets;

	public MultiTarget(Target... targets)
	{
		this.targets = new ArrayList<Target>();
		this.targets.addAll(Lists.newArrayList(targets));
	}

	@Override
	public void getAllTargets(Collection<SingleTarget> targets)
	{
		for (Target t : this.targets)
		{
			t.getAllTargets(targets);
		}
	}

	@Override
	protected void readFromNBT(NBTTagCompound nbt)
	{
		NBTTagList list = nbt.getTagList("data", 10);
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound sub = list.getCompoundTagAt(i);
			Target t = Target.read(sub);
			if (t != NullTarget.nullTarget)
			{
				this.targets.add(t);
			}
		}
	}

	@Override
	protected void writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList list = new NBTTagList();
		for (Target t : this.targets)
		{
			list.appendTag(Target.write(t));
		}
		nbt.setTag("data", list);
	}

	@Override
	protected String getType()
	{
		return "MULTI";
	}
}
