package com.mushroom.midnight.common.network;

public class GuiHandler {/*implements IGuiHandler {
    public static final int MIDNIGHT_FURNACE = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
        switch (ID) {
            case MIDNIGHT_FURNACE:
                TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileEntityMidnightFurnace) {
                    return new MidnightFurnaceContainer(player.inventory, (TileEntityMidnightFurnace) tile);
                }
                break;
            default:
                LOGGER.warn("Invalid gui id, received : " + ID);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
        switch (ID) {
            case MIDNIGHT_FURNACE:
                TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
                if (tile instanceof TileEntityMidnightFurnace) {
                    return new MidnightFurnaceScreen(player.inventory, (TileEntityMidnightFurnace) tile);
                }
                break;
            default:
                LOGGER.warn("Invalid gui id, received : " + ID);
        }
        return null;
    }*/
}
