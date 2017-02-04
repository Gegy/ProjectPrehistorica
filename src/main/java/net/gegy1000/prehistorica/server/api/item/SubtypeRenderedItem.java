package net.gegy1000.prehistorica.server.api.item;

public interface SubtypeRenderedItem {
    int[] getUsedSubtypes();

    String getResource(String name, int metadata);
}
