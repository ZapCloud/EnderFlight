package com.zapcloudstudios.enderflight.client;

import com.zapcloudstudios.enderflight.CommonProxy;
import com.zapcloudstudios.enderflight.EnderFlight;
import com.zapcloudstudios.enderflight.client.render.RenderBlockEnderFlightHandler;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{	
		RenderingRegistry.registerBlockHandler(EnderFlight.block2dItemRenderId, new RenderBlockEnderFlightHandler(EnderFlight.block2dItemRenderId, false));
		RenderingRegistry.registerBlockHandler(EnderFlight.block3dItemRenderId, new RenderBlockEnderFlightHandler(EnderFlight.block3dItemRenderId, true));
	}
}
