package com.zapcloudstudios.enderflight.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.zapcloudstudios.enderflight.EnderFlight;
import com.zapcloudstudios.enderflight.client.player.PlayerClientData;
import com.zapcloudstudios.enderflight.client.render.EntityRendererGhost;
import com.zapcloudstudios.enderflight.entity.EntityEnderFlightPearl;
import com.zapcloudstudios.enderflight.player.PlayerData;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class EventHooks
{
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event)
	{
		Entity entity = event.entity;
		if (entity instanceof EntityEnderPearl)
		{
			Entity replace = EntityEnderFlightPearl.getPearlFromVanilla((EntityEnderPearl) entity, event.world);
			event.setCanceled(true);
			event.world.spawnEntityInWorld(replace);
		}
	}

	public void onRenderTick(RenderTickEvent event)
	{
		EnderFlight.proxy.useGhostRender(this.isClientGhost());
	}

	public boolean isClientGhost()
	{
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		PlayerClientData data = (PlayerClientData) EnderFlight.getPlayerData(player);
		return data.isClientGhost(player);
	}

	@SubscribeEvent
	public void onRenderHand(RenderHandEvent event)
	{
		if (this.isClientGhost())
		{
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayPre(RenderGameOverlayEvent.Pre event)
	{
		if (this.isClientGhost())
		{
			if (EntityRendererGhost.overlaysToCancel.contains(event.type))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onDrawBlockHighlight(DrawBlockHighlightEvent event)
	{
		if (this.isClientGhost())
		{
			event.setCanceled(true);
		}
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
		PlayerClientData data = (PlayerClientData) EnderFlight.getPlayerData(player);
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
			PlayerData data = EnderFlight.getPlayerData(player);
			data.runTick(player);
		}
	}
}
