package noqcpistons.mixins;

import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import noqcpistons.fakes.ToggleAbleQC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlockMixin implements ToggleAbleQC {
	@Unique
	private boolean isQCEnabled = true;

	@Inject(
		method = "shouldExtend",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;isEmittingRedstonePower(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
			ordinal = 1
		),
		cancellable = true
	)
	private void cancelQC(World world, BlockPos pos, Direction pistonFace, CallbackInfoReturnable<Boolean> cir) {
		if (!this.isQCEnabled()) {
			cir.setReturnValue(false);
		}
	}

	@Override
	public boolean isQCEnabled() {
		return this.isQCEnabled;
	}

	@Override
	public void setQCEnabled(boolean enabled) {
		this.isQCEnabled = enabled;
	}
}
