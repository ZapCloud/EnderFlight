package com.zapcloudstudios.enderflight.block;

import net.minecraft.block.material.Material;

import com.zapcloudstudios.enderflight.EnderFlight;
import com.zapcloudstudios.enderflight.client.IRenderableBlock;
import com.zapcloudstudios.enderflight.client.render.block.RenderBlockEnderFlight;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockEnderFlightComplex extends BlockEnderFlight implements IRenderableBlock
{
	private RenderBlockEnderFlight render;
	private Class<? extends RenderBlockEnderFlight> renderClass = null;
	
	private boolean shouldRender3D = true;
	
	private String itemTexture = null;
	
	public BlockEnderFlightComplex(Material material)
	{
		super(material);
	}
	
	public BlockEnderFlightComplex setShouldRender3D(boolean shouldRender3D)
	{
		this.shouldRender3D = shouldRender3D;
		return this;
	}
	
	public BlockEnderFlightComplex setRenderer(Class<? extends RenderBlockEnderFlight> renderer)
	{
		this.renderClass = renderer;
		return this;
	}
	
	@Override
	public Class<? extends RenderBlockEnderFlight> getRenderer()
	{
		return this.renderClass;
	}
	
	@Override
	public int getRenderType()
	{
		if (this.shouldRender3D())
		{
			return EnderFlight.block3dItemRenderId;
		}
		else
		{
			return EnderFlight.block2dItemRenderId;
		}
	}
	
	@Override
	public RenderBlockEnderFlight getRendererInstance()
	{
		if (this.render == null)
		{
			Class<? extends RenderBlockEnderFlight> rclass = this.getRenderer();
			if (rclass != null)
			{
				try
				{
					this.render = rclass.newInstance();
				}
				catch (InstantiationException | IllegalAccessException e)
				{
					
				}
			}
		}
		return this.render;
	}
	
	@Override
	public boolean shouldRender3D()
	{
		return this.shouldRender3D;
	}
	
	public BlockEnderFlight setBlockItemTextureName(String name)
	{
		this.itemTexture = name;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return this.itemTexture;
	}
}
