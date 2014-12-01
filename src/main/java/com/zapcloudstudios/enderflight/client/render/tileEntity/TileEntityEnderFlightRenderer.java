package com.zapcloudstudios.enderflight.client.render.tileEntity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import com.zapcloudstudios.enderflight.utils.draw.ITextureBinder;

public abstract class TileEntityEnderFlightRenderer extends TileEntitySpecialRenderer implements ITextureBinder
{
	@Override
	public void bindTexture(ResourceLocation loc)
	{
		super.bindTexture(loc);
	}
}
