package com.zapcloudstudios.enderflight.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public abstract class RenderBlockEnderFlight
{
	protected Tessellator tess;
	
	public RenderBlockEnderFlight()
	{
		this.tess = Tessellator.instance;
	}
	
	public void doWorldBrightness(IBlockAccess world, int x, int y, int z, Block block)
	{
		this.tess.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		this.tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		
	}
	
	public abstract void renderInventoryBlock(Block block, int metadata, RenderBlocks renderer);
	
	public abstract boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer);
}
