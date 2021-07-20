package net.permafrozen.permafrozen.registry;

import net.fabricmc.fabric.api.biome.v1.NetherBiomes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.entity.living.AuroraFaeEntity;
import net.permafrozen.permafrozen.worldgen.biome.PermafrozenBiomeSource;
import net.permafrozen.permafrozen.worldgen.biome.PermafrozenSurfaceBuilders;
import net.permafrozen.permafrozen.worldgen.feature.PermafrozenBiomeFeatures;

public class PermafrozenBiomes {
	public static final Biome BOREAL_FOREST = createBorealForest();
	public static final Biome GLACIAL_OCEAN = createGlacialOcean();
	public static final RegistryKey<Biome> BOREAL_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Permafrozen.MOD_ID, "boreal_forest"));
	public static final RegistryKey<Biome> GLACIAL_OCEAN_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Permafrozen.MOD_ID, "glacial_ocean"));
	
	private static Biome createBorealForest() {
		// We specify what entities spawn and what features generate in the biome.
		// Aside from some structures, trees, rocks, plants and
		//   custom entities, these are mostly the same for each biome.
		// Vanilla configured features for biomes are defined in DefaultBiomeFeatures.
		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
		spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.AURORA_FAE, 1, 0, 2)).spawnCost(PermafrozenEntities.AURORA_FAE, 1.0D, 0.12D);
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
		generationSettings.surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
		DefaultBiomeFeatures.addSweetBerryBushesSnowy(generationSettings);
		DefaultBiomeFeatures.addForestGrass(generationSettings);
		DefaultBiomeFeatures.addMossyRocks(generationSettings);
		PermafrozenBiomeFeatures.addFirTrees(generationSettings);
		PermafrozenBiomeFeatures.addSpectralCaps(generationSettings);
		DefaultBiomeFeatures.addLargeFerns(generationSettings);
		PermafrozenBiomeFeatures.addWaterLakes(generationSettings);
		DefaultBiomeFeatures.addSprings(generationSettings);
		return new Biome.Builder().precipitation(Biome.Precipitation.SNOW).category(Biome.Category.TAIGA).depth(0.1F).scale(0.2F).temperature(0.0F).downfall(0.6F).effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(0.6F)).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
	}
	private static Biome createGlacialOcean() {
		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
		spawnSettings.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.NUDIFAE, 2, 1, 2)).spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(PermafrozenEntities.FATFISH, 8, 1, 4)).spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(PermafrozenEntities.LUNAR_KOI, 3, 0, 2)).spawnCost(PermafrozenEntities.LUNAR_KOI, 0.8D, 0.32D).spawnCost(PermafrozenEntities.FATFISH, 0.6D, 0.32D).spawnCost(PermafrozenEntities.NUDIFAE, 1.0D, 0.64D);
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
		generationSettings.surfaceBuilder(PermafrozenSurfaceBuilders.GLACIAL_OCEAN);
		DefaultBiomeFeatures.addOceanCarvers(generationSettings);
		DefaultBiomeFeatures.addBlueIce(generationSettings);
		DefaultBiomeFeatures.addIcebergs(generationSettings);
		PermafrozenBiomeFeatures.addWaterLakes(generationSettings);
		DefaultBiomeFeatures.addSprings(generationSettings);
		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
		return new Biome.Builder().precipitation(Biome.Precipitation.SNOW).category(Biome.Category.OCEAN).depth(-1.8F).scale(0.2F).temperature(0.0F).downfall(0.6F).effects(new BiomeEffects.Builder().waterColor(0x45ADF2).waterFogColor(0x041633).fogColor(12638463).skyColor(getSkyColor(0.6F)).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
	}
	
	private static int getSkyColor(float temperature) {
		float f = temperature / 3.0F;
		f = MathHelper.clamp(f, -1.0F, 1.0F);
		return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
	}
	
	public static void init() {
		Registry.register(BuiltinRegistries.BIOME, BOREAL_FOREST_KEY.getValue(), BOREAL_FOREST);
		Registry.register(BuiltinRegistries.BIOME, GLACIAL_OCEAN_KEY.getValue(), GLACIAL_OCEAN);
		Registry.register(Registry.BIOME_SOURCE, new Identifier(Permafrozen.MOD_ID, "biome_source"), PermafrozenBiomeSource.CODEC);
	}
}