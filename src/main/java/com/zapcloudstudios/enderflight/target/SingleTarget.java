package com.zapcloudstudios.enderflight.target;

import java.util.Collection;

public abstract class SingleTarget extends Target
{
	@Override
	public void getAllTargets(Collection<SingleTarget> targets)
	{
		targets.add(this);
	}
}
