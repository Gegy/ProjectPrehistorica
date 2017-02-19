package net.gegy1000.prehistorica.server.command;

import net.gegy1000.prehistorica.server.api.TimePeriod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import java.util.Locale;

public class PeriodTeleportCommand extends CommandBase {
    @Override
    public String getName() {
        return "tptime";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.tptime.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1) {
            if (sender instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) sender;

                TimePeriod period = TimePeriod.valueOf(args[0].toUpperCase(Locale.ENGLISH));

                if (period != null) {
                    DimensionType dimension = period.getDimension();

                    if (player.dimension == dimension.getId()) {
                        dimension = DimensionType.OVERWORLD;
                    }

                    PlayerList manager = server.getPlayerList();
                    WorldServer newWorld = server.worldServerForDimension(dimension.getId());
                    manager.transferPlayerToDimension(player, dimension.getId(), new Teleporter(newWorld) {
                        @Override
                        public void placeInPortal(Entity entity, float rotationYaw) {
                        }

                        @Override
                        public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
                            return true;
                        }

                        @Override
                        public boolean makePortal(Entity entity) {
                            return false;
                        }

                        @Override
                        public void removeStalePortalLocations(long worldTime) {
                        }
                    });
                }
            }
        }
    }
}
