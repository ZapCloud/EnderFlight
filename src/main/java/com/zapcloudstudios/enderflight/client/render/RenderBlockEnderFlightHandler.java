package com.zapcloudstudios.enderflight.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.zapcloudstudios.enderflight.client.IRenderableBlock;
import com.zapcloudstudios.enderflight.client.render.block.RenderBlockEnderFlight;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockEnderFlightHandler implements ISimpleBlockRenderingHandler
{
	public final int id;
	private final boolean item3d;
	
	public RenderBlockEnderFlightHandler(int id, boolean item3d)
	{
		this.id = id;
		this.item3d = item3d;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		RenderBlockEnderFlight render = this.getRender(block);
		if (render != null)
		{
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			Tessellator.instance.startDrawingQuads();
			render.renderInventoryBlock(block, metadata, renderer);
			Tessellator.instance.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		RenderBlockEnderFlight render = this.getRender(block);
		if (render != null)
		{
			render.renderWorldBlock(world, x, y, z, block, renderer);
		}
		return false;
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return this.item3d;
	}
	
	@Override
	public int getRenderId()
	{
		return this.id;
	}
	
	private RenderBlockEnderFlight getRender(Block block)
	{
		if (block instanceof IRenderableBlock)
		{
			IRenderableBlock renderable = (IRenderableBlock) block;
			return renderable.getRendererInstance();
		}
		return null;
	}
}
