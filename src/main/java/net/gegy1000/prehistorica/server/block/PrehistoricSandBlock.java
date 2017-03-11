package net.gegy1000.prehistorica.server.block;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.gegy1000.prehistorica.server.api.item.SubtypeBlock;
import net.gegy1000.prehistorica.server.tab.TabRegistry;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class PrehistoricSandBlock extends BlockFalling implements SubtypeBlock {
    public static final PropertyEnum<TimePeriod> PERIOD = PropertyEnum.create("period", TimePeriod.class);
    public static final PropertyEnum<BlockSand.EnumType> VARIANT = PropertyEnum.create("variant", BlockSand.EnumType.class);

    public PrehistoricSandBlock() {
        this.setUnlocalizedName("prehistoric_sand");
        this.setCreativeTab(TabRegistry.BLOCKS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(PERIOD, TimePeriod.CRETACEOUS).withProperty(VARIANT, BlockSand.EnumType.SAND));
        this.setHardness(0.5F);
        this.setSoundType(SoundType.SAND);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> stacks) {
        for (BlockSand.EnumType variant : BlockSand.EnumType.values()) {
            for (TimePeriod period : TimePeriod.values()) {
                IBlockState state = this.getDefaultState().withProperty(VARIANT, variant).withProperty(PERIOD, period);
                stacks.add(new ItemStack(item, 1, this.damageDropped(state)));
            }
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).getMetadata() & 1 | (state.getValue(PERIOD).getMetadata() << 1);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        BlockSand.EnumType variant = BlockSand.EnumType.byMetadata(meta & 1);
        TimePeriod period = TimePeriod.from(meta >> 1);
        return this.getDefaultState().withProperty(VARIANT, variant).withProperty(PERIOD, period);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.getMetaFromState(state);
    }

    @Override
    public MapColor getMapColor(IBlockState state) {
        return state.getValue(VARIANT).getMapColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getDustColor(IBlockState state) {
        return state.getValue(VARIANT).getDustColor();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PERIOD, VARIANT);
    }

    @Override
    public String getSubtypeName(IBlockState state) {
        return state.getValue(PERIOD).getName() + "_" + state.getValue(VARIANT).getName();
    }

    public static IBlockState from(TimePeriod period) {
        return BlockRegistry.PREHISTORIC_SAND.getDefaultState().withProperty(PERIOD, period);
    }
}
