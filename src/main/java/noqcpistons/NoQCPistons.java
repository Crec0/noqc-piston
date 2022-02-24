package noqcpistons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.block.PistonBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import noqcpistons.fakes.ToggleAbleQC;

public class NoQCPistons implements ModInitializer {

	public static final PistonBlock PISTON = makePistonBlock(false);
	public static final PistonBlock STICKY_PISTON = makePistonBlock(true);
	public static final String NAMESPACE = "noqcpistons";
	public static final Identifier pistonId = new Identifier(NAMESPACE, "piston");
	public static final Identifier stickyPistonId = new Identifier(NAMESPACE, "sticky_piston");

	@Override
	public void onInitialize() {
		FabricItemSettings noQcPistonItemSettings = new FabricItemSettings().group(ItemGroup.REDSTONE);
		Registry.register(Registry.BLOCK, pistonId, PISTON);
		Registry.register(Registry.ITEM, pistonId, new BlockItem(PISTON, noQcPistonItemSettings));
		Registry.register(Registry.BLOCK, stickyPistonId, STICKY_PISTON);
		Registry.register(Registry.ITEM, stickyPistonId, new BlockItem(STICKY_PISTON, noQcPistonItemSettings));
	}

	private static PistonBlock makePistonBlock(boolean sticky) {
		AbstractBlock.ContextPredicate isRetracted = (state, world, pos) -> !state.get(PistonBlock.EXTENDED);
		FabricBlockSettings settings = FabricBlockSettings.of(Material.PISTON)
														  .strength(1.5F)
														  .solidBlock((state, world, pos) -> false)
														  .suffocates(isRetracted)
														  .blockVision(isRetracted);
		PistonBlock piston = new PistonBlock(sticky, settings);
		((ToggleAbleQC) piston).setQCEnabled(false);
		return piston;
	}
}
