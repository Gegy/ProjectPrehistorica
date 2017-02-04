package net.gegy1000.prehistorica.server.api.item;

public interface DefaultRenderedItem {
    default String getResource(String unlocalizedName) {
        return unlocalizedName;
    }
}
