package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.TerraformHangingSign;

import net.minecraft.block.HangingSignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class TerraformHangingSignBlock extends HangingSignBlock implements TerraformHangingSign {
	private final Identifier texture;
	private final Identifier guiTexture;

	public TerraformHangingSignBlock(Identifier texture, Identifier guiTexture, Settings settings) {
		super(settings, SignType.OAK);
		this.texture = texture;
		this.guiTexture = guiTexture;
	}

	@Override
	public Identifier getTexture() {
		return texture;
	}

	@Override
	public Identifier getGuiTexture() {
		return guiTexture;
	}
}
