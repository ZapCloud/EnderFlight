package com.zapcloudstudios.enderflight.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;

import com.zapcloudstudios.enderflight.CommonProxy;
import com.zapcloudstudios.enderflight.EnderFlight;
import com.zapcloudstudios.enderflight.client.render.EntityRendererGhost;
import com.zapcloudstudios.enderflight.client.render.RenderBlockEnderFlightHandler;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	private EntityRenderer renderer;
	private EntityRenderer prevRenderer;

	public EntityPlayerGhostView ghost = null;

	@Override
	public void useGhostRender(boolean use)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (use)
		{
			if (this.ghost == null)
			{
				EntityPlayer p = mc.thePlayer;
				this.ghost = new EntityPlayerGhostView(p.worldObj);
				this.ghost.setPositionAndRotation(p.posX, p.posY + p.eyeHeight, p.posZ, p.cameraYaw, p.cameraPitch);
				mc.theWorld.spawnEntityInWorld(this.ghost);
			}
			mc.renderViewEntity = this.ghost;
			if (this.renderer == null)
			{
				this.renderer = new EntityRendererGhost(mc);
			}
			if (mc.entityRenderer != this.renderer)
			{
				this.prevRenderer = mc.entityRenderer;
				mc.entityRenderer = this.renderer;
			}
		}
		else if (this.prevRenderer != null)
		{
			if (this.ghost != null)
			{
				this.ghost.isDead = true;
				this.ghost = null;
			}
			mc.renderViewEntity = mc.thePlayer;
			mc.entityRenderer = this.prevRenderer;
		}
	}

	@Override
	public void registerRenderers()
	{
		RenderingRegistry.registerBlockHandler(EnderFlight.block2dItemRenderId, new RenderBlockEnderFlightHandler(EnderFlight.block2dItemRenderId, false));
		RenderingRegistry.registerBlockHandler(EnderFlight.block3dItemRenderId, new RenderBlockEnderFlightHandler(EnderFlight.block3dItemRenderId, true));
	}
}
