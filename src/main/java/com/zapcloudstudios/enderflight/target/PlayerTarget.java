package com.zapcloudstudios.enderflight.target;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PlayerTarget extends SingleTarget
{
	private EntityPlayer theTarget;
	private String targetName;

	public PlayerTarget(EntityPlayer player)
	{
		this.theTarget = player;
		this.targetName = player.getCommandSenderName();
	}

	PlayerTarget()
	{

	}

	public String getPlayerName()
	{
		if (this.theTarget != null)
		{
			this.targetName = this.theTarget.getCommandSenderName();
		}
		return this.targetName;
	}

	public EntityPlayer getPlayer(World world)
	{
		if (this.theTarget == null && this.targetName != null)
		{
			this.theTarget = world.getPlayerEntityByName(this.targetName);
		}
		return this.theTarget;
	}

	@Override
	protected void readFromNBT(NBTTagCompound nbt)
	{
		String player = nbt.getString("player");
		if (!player.isEmpty())
		{
			this.targetName = player;
		}
	}

	@Override
	protected void writeToNBT(NBTTagCompound nbt)
	{
		String player = this.getPlayerName();
		if (player != null)
		{
			nbt.setString("player", player);
		}
	}

	@Override
	protected String getType()
	{
		if (this.targetName == null && this.theTarget == null)
		{
			return "NULL";
		}
		return "PLAYER";
	}
}
