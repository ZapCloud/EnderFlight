package com.zapcloudstudios.enderflight.events;

import net.minecraft.block.BlockFlower;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import com.zapcloudstudios.enderflight.EnderFlight;
import com.zapcloudstudios.enderflight.client.player.PlayerClientData;
import com.zapcloudstudios.enderflight.player.PlayerData;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHooks
{
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event)
	{
		
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) event.entity;
			player.registerExtendedProperties(EnderFlight.PLAYERDATA, new PlayerData());
		}
		else if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			player.registerExtendedProperties(EnderFlight.PLAYERDATA, new PlayerClientData());
		}
	}
	
	@SubscribeEvent
	public void getOffsetFOV(FOVUpdateEvent event)
	{
		float fov = event.fov;
		EntityPlayerSP player = event.entity;
		PlayerClientData data = (PlayerClientData) player.getExtendedProperties(EnderFlight.PLAYERDATA);
		//TODO Ender effects
		event.newfov = fov;
	}
	
	@SubscribeEvent
	public void entityUpdateEvent(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			
			PlayerData data = (PlayerData) player.getExtendedProperties(EnderFlight.PLAYERDATA);
		}
		/*
		else if (entity instanceof )
		{
		
		}
		*/
	}
}
