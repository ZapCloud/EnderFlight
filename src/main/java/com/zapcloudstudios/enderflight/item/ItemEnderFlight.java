package com.zapcloudstudios.enderflight.item;

import java.util.List;

import com.zapcloudstudios.enderflight.EnderFlight;
import com.zapcloudstudios.enderflight.ITipItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEnderFlight extends Item implements ITipItem
{
	private String[] tip = null;
	private String[] shifttip = null;
	
	public ItemEnderFlight()
	{
		super();
	}
	
	public ItemEnderFlight setTip(String... tip)
	{
		this.tip = tip;
		return this;
	}
	
	public ItemEnderFlight setShiftTip(String... tip)
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
	{
		EnderFlight.addInformation(this, stack, player, info, advanced);
	}
}
