package net.gegy1000.prehistorica.server.world.generator;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class SchematicGenerator extends WorldGenerator {
    private Schematic[] schematics;

    public SchematicGenerator(Schematic... schematics) {
        this.schematics = schematics;
    }

    @Override
    public boolean generate(World world, Random random, BlockPos position) {
        Rotation rotation = Rotation.values()[random.nextInt(4)];
        Schematic schematic = this.schematics[random.nextInt(this.schematics.length)];
        return this.generateSchematic(world, schematic, random, position, rotation);
    }

    protected abstract boolean generateSchematic(World world, Schematic schematic, Random random, BlockPos position, Rotation rotation);
}
