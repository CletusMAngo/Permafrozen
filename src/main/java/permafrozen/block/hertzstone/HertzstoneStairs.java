package permafrozen.block.hertzstone;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import java.util.function.Supplier;

public class HertzstoneStairs extends StairsBlock {

    public HertzstoneStairs(Supplier<BlockState> state) {

        super(state, Properties
                .create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .sound(SoundType.STONE)
                .setRequiresTool()
        );

    }

}