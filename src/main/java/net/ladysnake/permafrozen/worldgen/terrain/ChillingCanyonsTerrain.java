package net.ladysnake.permafrozen.worldgen.terrain;

import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.ladysnake.permafrozen.util.JitteredGrid;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.random.AbstractRandom;

import java.util.Random;

public class ChillingCanyonsTerrain extends Terrain {
	protected ChillingCanyonsTerrain(Biome biome, Random random) {
		super(biome);
		this.topHillsNoise = new OpenSimplexNoise(random);
		this.bottomHillsNoise = new OpenSimplexNoise(random);
		this.canyonsNoise = new OpenSimplexNoise(random);
	}

	// https://en.wikipedia.org/wiki/Pingo#/media/File:Closed_pingos_diagram.jpg Could later add water or ice to make it more realistic
	private final OpenSimplexNoise topHillsNoise;
	private final OpenSimplexNoise bottomHillsNoise;
	private final OpenSimplexNoise canyonsNoise;

	@Override
	public double sampleHeight(int x, int z) {
		return 3 * this.bottomHillsNoise.sample(x * 0.03, z * 0.03) + 6 * this.bottomHillsNoise.sample(x * 0.008, z * 0.008) + 75;
	}

	@Override
	public void buildSurface(Chunk chunk, AbstractRandom random, int x, int z, int height, int seaLevel) {
		double canyonsSample = this.canyonsNoise.sample(x * 0.006, z * 0.006);

		if (canyonsSample > 0.1 && canyonsSample < 0.125 || canyonsSample < -0.2 && canyonsSample > -0.215) {
			buildDefaultSurface(chunk, x, z, height, seaLevel, PermafrozenBlocks.MOSSY_PERMAFROST.getDefaultState(), PermafrozenBlocks.PERMAFROST.getDefaultState());
		} else {
			buildDefaultSurface(chunk, x, z, (int) this.topHillsNoise.sample(x * 0.01, z * 0.01) * 4 + 120, seaLevel, PermafrozenBlocks.SHIVERSLATE.getDefaultState(), PermafrozenBlocks.SHIVERSLATE.getDefaultState());
		}
	}
}