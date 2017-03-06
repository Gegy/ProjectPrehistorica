package net.gegy1000.prehistorica.server.block;

import net.gegy1000.prehistorica.server.api.TimePeriod;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class PrehistoricSoilBlock extends Block implements SubtypeBlock {
    public static final PropertyEnum<TimePeriod> PERIOD = PropertyEnum.create("period", TimePeriod.class);

    public PrehistoricSoilBlock() {
        super(Material.GROUND);
        this.setUnlocalizedName("prehistoric_soil");
        this.setCreativeTab(TabRegistry.BLOCKS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(PERIOD, TimePeriod.CRETACEOUS));
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GROUND);
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
        return state.getValue(PERIOD).getName() + "_soil";
    }
}
