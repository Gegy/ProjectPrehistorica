package net.gegy1000.prehistorica.server.world.generator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Schematic {
    private final IBlockState[][][] states;
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;
    private final BlockPos offset;

    private Schematic(IBlockState[][][] states, int sizeX, int sizeY, int sizeZ, BlockPos offset) {
        this.states = states;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.offset = offset;
    }

    public static Schematic load(ResourceLocation resource, IBlockState... mappings) throws IOException {
        Map<IBlockState, IBlockState> map = new HashMap<>();
        for (int i = 0; i < mappings.length; i += 2) {
            map.put(mappings[i], mappings[i + 1]);
        }
        try (InputStream input = Schematic.class.getResourceAsStream("/assets/" + resource.getResourceDomain() + "/" + resource.getResourcePath())) {
            NBTTagCompound compound = CompressedStreamTools.readCompressed(input);
            int sizeX = compound.getShort("Width");
            int sizeY = compound.getShort("Height");
            int sizeZ = compound.getShort("Length");

            byte[] blocks = compound.getByteArray("Blocks");
            byte[] data = compound.getByteArray("Data");

            IBlockState[][][] states = new IBlockState[sizeX][sizeY][sizeZ];

            for (int i = 0; i < blocks.length; i++) {
                int x = i % sizeX;
                int z = (i / sizeX) % sizeZ;
                int y = (i / sizeX / sizeZ) % sizeY;
                byte id = blocks[i];
                byte metadata = data[i];
                Block block = Block.getBlockById(id);
                if (block != Blocks.AIR) {
                    IBlockState state = block.getStateFromMeta(metadata);
                    states[x][y][z] = map.getOrDefault(state, state);
                }
            }

            BlockPos offset = new BlockPos(compound.getShort("WEOffsetX"), compound.getShort("WEOffsetY"), compound.getShort("WEOffsetZ"));
            return new Schematic(states, sizeX, sizeY, sizeZ, offset);
        }
    }

    public void generate(World world, BlockPos pos, Rotation rotation) {
        for (int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++) {
                for (int z = 0; z < this.sizeZ; z++) {
                    world.setBlockState(this.rotate(new BlockPos(x, y, z).add(this.offset), rotation).add(pos), this.states[x][y][z], 2);
                }
            }
        }
    }

    private BlockPos rotate(BlockPos pos, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_90:
                return new BlockPos(pos.getZ(), pos.getY(), pos.getZ());
            case CLOCKWISE_180:
                return new BlockPos(-pos.getX(), pos.getY(), -pos.getZ());
            case COUNTERCLOCKWISE_90:
                return new BlockPos(-pos.getZ(), pos.getY(), -pos.getZ());
        }
        return pos;
    }
}
