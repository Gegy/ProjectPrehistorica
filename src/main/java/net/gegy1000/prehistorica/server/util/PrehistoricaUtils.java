package net.gegy1000.prehistorica.server.util;

import net.minecraft.util.math.Vec3d;

public class PrehistoricaUtils {
    public static int blendColor(int first, int second, float ratio) {
        float invertedRatio = 1.0F - ratio;

        int firstRed = (first & 0xFF0000) >> 16;
        int firstGreen = (first & 0xFF00) >> 8;
        int firstBlue = first & 0xFF;

        int secondRed = (second & 0xFF0000) >> 16;
        int secondGreen = (second & 0xFF00) >> 8;
        int secondBlue = second & 0xFF;

        int red = (int) (firstRed * invertedRatio + secondRed * ratio);
        int green = (int) (firstGreen * invertedRatio + secondGreen * ratio);
        int blue = (int) (firstBlue * invertedRatio + secondBlue * ratio);

        return red << 16 | green << 8 | blue;
    }

    public static Vec3d blendColor(Vec3d first, Vec3d second, float ratio) {
        float invertedRatio = 1.0F - ratio;
        double red = first.xCoord * invertedRatio + second.xCoord * ratio;
        double green = first.yCoord * invertedRatio + second.yCoord * ratio;
        double blue = first.zCoord * invertedRatio + second.zCoord * ratio;

        return new Vec3d(red, green, blue);
    }
}
