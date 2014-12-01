package com.zapcloudstudios.enderflight.client.render;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.AIR;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.ARMOR;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.CROSSHAIRS;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.EXPERIENCE;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.FOOD;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.HEALTH;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.HEALTHMOUNT;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.HOTBAR;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.JUMPBAR;
import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.PORTAL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityRendererGhost extends EntityRenderer
{
	public final static ImmutableSet<ElementType> overlaysToCancel = Sets.immutableEnumSet(CROSSHAIRS, ARMOR, HEALTH, FOOD, AIR, HOTBAR, EXPERIENCE, HEALTHMOUNT, JUMPBAR, PORTAL);

	private Minecraft mc;

	public EntityRendererGhost(Minecraft minecraft, IResourceManager p_i45076_2_)
	{
		super(minecraft, p_i45076_2_);
		this.mc = minecraft;
	}

	@Override
	public void getMouseOver(float p_78473_1_)
	{
		this.mc.pointedEntity = null;
		this.mc.objectMouseOver = null;
	}
}
