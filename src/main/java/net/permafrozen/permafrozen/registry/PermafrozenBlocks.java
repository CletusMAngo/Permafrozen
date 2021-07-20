package net.permafrozen.permafrozen.registry;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terraform.wood.block.*;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.block.util.PermafrozenPlantBlock;
import net.permafrozen.permafrozen.block.util.PermafrozenSaplingBlock;
import net.permafrozen.permafrozen.mixin.BlocksAccessor;
import net.permafrozen.permafrozen.worldgen.tree.FirSaplingGenerator;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

public class PermafrozenBlocks {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
	
	public static final Block STRIPPED_FIR_LOG = create("stripped_fir_log", new PillarBlock(copyOf(Blocks.OAK_LOG)), true);
	public static final Block STRIPPED_FIR_WOOD = create("stripped_fir_wood", new PillarBlock(copyOf(STRIPPED_FIR_LOG)), true);
	public static final Block FIR_LOG = create("fir_log", new StrippableLogBlock(() -> STRIPPED_FIR_LOG, MapColor.BROWN, copyOf(STRIPPED_FIR_LOG)), true);
	public static final Block FIR_WOOD = create("fir_wood", new StrippableLogBlock(() -> STRIPPED_FIR_WOOD, MapColor.BROWN, copyOf(STRIPPED_FIR_LOG)), true);
	public static final Block FIR_LEAVES = create("fir_leaves", BlocksAccessor.callCreateLeavesBlock(BlockSoundGroup.GRASS), true);
	public static final Block FIR_SAPLING = create("fir_sapling", new PermafrozenSaplingBlock(new FirSaplingGenerator(), copyOf(Blocks.OAK_SAPLING)), true);
	public static final Block POTTED_FIR_SAPLING = create("potted_fir_sapling", new FlowerPotBlock(FIR_SAPLING, copyOf(Blocks.POTTED_OAK_SAPLING)), false);
	public static final Block FIR_PLANKS = create("fir_planks", new Block(copyOf(Blocks.OAK_PLANKS)), true);
	public static final Block FIR_STAIRS = create("fir_stairs", new TerraformStairsBlock(FIR_PLANKS, copyOf(Blocks.OAK_STAIRS)), true);
	public static final Block FIR_SLAB = create("fir_slab", new SlabBlock(copyOf(Blocks.OAK_SLAB)), true);
	public static final Block FIR_FENCE = create("fir_fence", new FenceBlock(copyOf(Blocks.OAK_FENCE)), true);
	public static final Block FIR_FENCE_GATE = create("fir_fence_gate", new FenceGateBlock(copyOf(Blocks.OAK_FENCE_GATE)), true);
	public static final Block FIR_PRESSURE_PLATE = create("fir_pressure_plate", new TerraformPressurePlateBlock(copyOf(Blocks.OAK_PRESSURE_PLATE)), true);
	public static final Block FIR_BUTTON = create("fir_button", new TerraformButtonBlock(copyOf(Blocks.OAK_BUTTON)), true);
	public static final Block FIR_TRAPDOOR = create("fir_trapdoor", new TerraformTrapdoorBlock(copyOf(Blocks.OAK_TRAPDOOR)), true);
	public static final Block FIR_DOOR = create("fir_door", new TerraformDoorBlock(copyOf(Blocks.OAK_DOOR)), false);
	public static final Block SAPPHIRE_SAND = create("sapphire_sand", new GravelBlock(copyOf(Blocks.GRAVEL)), true);
	private static final Identifier FIR_SIGN_TEXTURE = new Identifier(Permafrozen.MOD_ID, "entity/sign/fir");
	public static final TerraformSignBlock FIR_SIGN = create("fir_sign", new TerraformSignBlock(FIR_SIGN_TEXTURE, copyOf(Blocks.OAK_SIGN)), false);
	public static final Block FIR_WALL_SIGN = create("fir_wall_sign", new TerraformWallSignBlock(FIR_SIGN_TEXTURE, copyOf(Blocks.OAK_WALL_SIGN)), false);
	public static final Block SPECTRAL_CAP = create("spectral_cap", new PermafrozenPlantBlock(AbstractBlock.Settings.of(Material.PLANT, MapColor.LAPIS_BLUE).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).luminance((state) -> {
		return 11;
	})), true);

	private static <T extends Block> T create(String name, T block, boolean createItem) {
		BLOCKS.put(block, new Identifier(Permafrozen.MOD_ID, name));
		if (createItem) {
			ITEMS.put(new BlockItem(block, new Item.Settings().group(Permafrozen.GROUP)), BLOCKS.get(block));
		}
		return block;
	}
	
	public static void init() {
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
		FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
		fuelRegistry.add(FIR_FENCE, 300);
		fuelRegistry.add(FIR_FENCE_GATE, 300);
		FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableRegistry.add(STRIPPED_FIR_LOG, 5, 5);
		flammableRegistry.add(STRIPPED_FIR_WOOD, 5, 5);
		flammableRegistry.add(FIR_LOG, 5, 5);
		flammableRegistry.add(FIR_WOOD, 5, 5);
		flammableRegistry.add(FIR_LEAVES, 30, 60);
		flammableRegistry.add(FIR_PLANKS, 5, 20);
		flammableRegistry.add(FIR_STAIRS, 5, 20);
		flammableRegistry.add(FIR_SLAB, 5, 20);
		flammableRegistry.add(FIR_FENCE, 5, 20);
		flammableRegistry.add(FIR_FENCE_GATE, 5, 20);
		CompostingChanceRegistry compostRegistry = CompostingChanceRegistry.INSTANCE;
		compostRegistry.add(FIR_LEAVES, 0.3f);
		compostRegistry.add(FIR_SAPLING, 0.3f);
	}
}