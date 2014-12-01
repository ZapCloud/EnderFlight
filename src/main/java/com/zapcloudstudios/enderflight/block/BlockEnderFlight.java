package com.zapcloudstudios.enderflight.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.zapcloudstudios.enderflight.ITipItem;

public class BlockEnderFlight extends Block implements ITipItem
{
	private String[] tip = null;
	private String[] shifttip = null;
	
	public BlockEnderFlight(Material material)
	{
		super(material);
	}
	
	public BlockEnderFlight setTip(String... tip)
	{
		this.tip = tip;
		return this;
	}
	
	public BlockEnderFlight setShiftTip(String... tip)
	{
		this.shifttip = tip;
		return this;
	}
	
	@Override
	public String[] getTip(EntityPlayer player, ItemStack stack)
	{
		return this.tip;
	}
	
	@Override
	public String[] getShiftTip(EntityPlayer player, ItemStack stack)
	{
		return this.shifttip;
	}
	
	@Override
	public int getRenderType()
	{
		return 0;
	}
}
