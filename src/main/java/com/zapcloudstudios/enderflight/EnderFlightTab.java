package com.zapcloudstudios.enderflight;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class EnderFlightTab extends CreativeTabs
{
	String name;
	private Item icon;
	
	public EnderFlightTab(int par1, String name)
	{
		super(par1, name);
		this.name = name;
	}
	
	public void setIcon(Item icon)
	{
		this.icon = icon;
	}
	
	@Override
	public String getTranslatedTabLabel()
	{
		return this.name;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return this.icon;
	}
}