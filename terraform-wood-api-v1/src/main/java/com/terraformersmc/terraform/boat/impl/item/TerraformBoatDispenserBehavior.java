package com.terraformersmc.terraform.boat.impl.item;

import java.util.function.Supplier;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.TerraformBoatEntity;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * A {@linkplain DispenserBehavior dispenser behavior} that spawns a {@linkplain TerraformBoatEntity boat entity} with a given {@linkplain TerraformBoatType Terraform boat type}.
 */
public class TerraformBoatDispenserBehavior extends ItemDispenserBehavior {
	private static final DispenserBehavior FALLBACK_BEHAVIOR = new ItemDispenserBehavior();
	private static final float OFFSET_MULTIPLIER = 1.125F;

	private final Supplier<TerraformBoatType> boatSupplier;

	/**
	 * @param boatSupplier a {@linkplain Supplier supplier} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this dispenser behavior
	 */
	public TerraformBoatDispenserBehavior(Supplier<TerraformBoatType> boatSupplier) {
		this.boatSupplier = boatSupplier;
	}

	@Override
	public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		Direction facing = pointer.getBlockState().get(DispenserBlock.FACING);

		double x = pointer.getX() + facing.getOffsetX() * OFFSET_MULTIPLIER;
		double y = pointer.getY() + facing.getOffsetY() * OFFSET_MULTIPLIER;
		double z = pointer.getZ() + facing.getOffsetZ() * OFFSET_MULTIPLIER;

		World world = pointer.getWorld();
		BlockPos pos = pointer.getPos().offset(facing);

		if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
			y += 1;
		} else if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.down()).isIn(FluidTags.WATER)) {
			return FALLBACK_BEHAVIOR.dispense(pointer, stack);
		}

		TerraformBoatEntity boatEntity = new TerraformBoatEntity(world, x, y, z);

		boatEntity.setTerraformBoat(this.boatSupplier.get());
		boatEntity.setYaw(facing.asRotation());

		world.spawnEntity(boatEntity);

		stack.decrement(1);
		return stack;
	}
}
