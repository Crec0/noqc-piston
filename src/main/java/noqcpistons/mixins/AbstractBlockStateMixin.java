package noqcpistons.mixins;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

	@Shadow
	public abstract Block getBlock();

	@Inject(method = "isOf",at = @At("HEAD"),cancellable = true)
	private void swapNoQCAndVanilla(Block block, CallbackInfoReturnable<Boolean> cir) {
		if (this.getBlock() instanceof PistonBlock && block instanceof PistonBlock) {
			cir.setReturnValue(
				Registry.BLOCK.getId(block).getPath().equals(Registry.BLOCK.getId(getBlock()).getPath())
			);
		}
	}
}
