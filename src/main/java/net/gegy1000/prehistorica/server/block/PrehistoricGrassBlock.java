package net.gegy1000.prehistorica.server.block;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.api.item.ColoredBlock;
import net.gegy1000.prehistorica.server.api.item.SubtypeBlock;
import net.gegy1000.prehistorica.server.tab.TabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class PrehistoricGrassBlock extends Block implements SubtypeBlock, ColoredBlock {
    public static final PropertyEnum<TimePeriod> PERIOD = PropertyEnum.create("period", TimePeriod.class);

    public PrehistoricGrassBlock() {
        super(Material.GROUND);
        this.setUnlocalizedName("prehistoric_grass");
        this.setCreativeTab(TabRegistry.BLOCKS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(PERIOD, TimePeriod.CRETACEOUS));
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GROUND);
        this.setTickRandomly(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> stacks) {
        for (TimePeriod period : TimePeriod.values()) {
            IBlockState state = this.getDefaultState().withProperty(PERIOD, period);
            stacks.add(new ItemStack(item, 1, this.damageDropped(state)));
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PERIOD).getMetadata();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        TimePeriod period = TimePeriod.from(meta);
        return this.getDefaultState().withProperty(PERIOD, period);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.getMetaFromState(state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PERIOD);
    }

    @Override
    public String getSubtypeName(IBlockState state) {
        return state.getValue(PERIOD).getName() + "_grass";
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote) {
            if (world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2) {
                world.setBlockState(pos, BlockRegistry.PREHISTORIC_SOIL.getStateFromMeta(this.getMetaFromState(state)));
            } else {
                if (world.getLightFromNeighbors(pos.up()) >= 9) {
                    for (int i = 0; i < 4; ++i) {
                        BlockPos randomNeighbour = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (!(randomNeighbour.getY() >= 0 && randomNeighbour.getY() < 256 && !world.isBlockLoaded(randomNeighbour))) {
                            IBlockState selectedAboveState = world.getBlockState(randomNeighbour.up());
                            IBlockState selectedState = world.getBlockState(randomNeighbour);

                            boolean samePeriod = selectedState.getBlock() == BlockRegistry.PREHISTORIC_SOIL && selectedState.getValue(PrehistoricSoilBlock.PERIOD) == state.getValue(PERIOD);
                            boolean hasLight = world.getLightFromNeighbors(randomNeighbour.up()) >= 4 && selectedAboveState.getLightOpacity(world, pos.up()) <= 2;

                            if (samePeriod && hasLight) {
                                world.setBlockState(randomNeighbour, state);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlockRegistry.PREHISTORIC_SOIL);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return tintIndex == 0 ? ColorizerGrass.getGrassColor(0.5, 0.8) : 0xFFFFFF;
    }

    @Override
    public int getColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
        if (tintIndex == 0) {
            return BiomeColorHelper.getGrassColorAtPos(world, pos);
        }
        return 0xFFFFFF;
    }

    public static IBlockState from(TimePeriod period) {
        return BlockRegistry.PREHISTORIC_GRASS.getDefaultState().withProperty(PERIOD, period);
    }
}
