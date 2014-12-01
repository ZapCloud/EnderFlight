package com.zapcloudstudios.enderflight.client;

import com.zapcloudstudios.enderflight.client.render.block.RenderBlockEnderFlight;

public interface IRenderableBlock
{
	public Class<? extends RenderBlockEnderFlight> getRenderer();
	
	public RenderBlockEnderFlight getRendererInstance();
	
	public boolean shouldRender3D();
}
