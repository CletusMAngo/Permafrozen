package net.permafrozen.permafrozen.worldgen.feature;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;

public class PermafrozenBiomeFeatures {
	public static void addFirTrees(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenConfiguredFeatures.TREES_FIR);
	}
	public static void addSpectralCaps(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, PermafrozenConfiguredFeatures.PATCH_SPECTRAL_CAP);
	}
	public static void addWaterLakes(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.LAKES, ConfiguredFeatures.LAKE_WATER);
	}
}