package com.rena.cyberware.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WorldUtil {

    /**
     * checks whether the Position is loaded or not
     */
    public static boolean isBlockLoaded(@Nullable IBlockReader world, @Nonnull BlockPos pos) {
        if (world == null || !World.isInWorldBounds(pos)) {
            return false;
        } else if (world instanceof IWorldReader) {
            //Note: We don't bother checking if it is a world and then isBlockPresent because
            // all that does is also validate the y value is in bounds, and we already check to make
            // sure the position is valid both in the y and xz directions
            return ((IWorldReader) world).isAreaLoaded(pos, 0);
        }
        return true;
    }


    /**
     * gets the TileEntity when the chunk is loaded
     */

    @Nullable
    public static TileEntity getTileEntity(IBlockReader world, BlockPos pos) {
        if(!isBlockLoaded(world, pos)) {
            return null;
        }
        return world.getBlockEntity(pos);
    }
    /**
     * gets the TileEntity when the chunk is loaded
     * @param <T> - the Type of the TileEntity
     * @param clazz - the class the TileEntity at this position should have
     * @param world - the world we are in
     * @param pos - the position we are searching for the Tile Entity
     * @return the TileEntity or null if there isnt one or it cant be cast
     */
    @Nullable
    public static <T extends TileEntity> T getTileEntity(@Nonnull Class<T> clazz, @Nullable IBlockReader world, @Nonnull BlockPos pos) {
        TileEntity te = getTileEntity(world, pos);
        if(te == null)
            return null;
        else if(clazz.isInstance(te)) {
            return clazz.cast(te);
        }
        return null;
    }

    /**
     *
     * @param clazz - the class u wanna cast the entity to
     * @param world - the world we are curently at
     * @param entityId - the id of the entity
     * @param <T> - the Entity class
     * @return the Entity from the world with the specific id and the specific class, can be null when it cant find entity or cant cast it
     */
    @Nullable
    public static <T extends Entity> T getEntity(@Nonnull Class<T> clazz, @Nullable World world, int entityId){
        Entity e = world.getEntity(entityId);
        if (e == null)
            return null;
        else if(clazz.isInstance(e))
            return clazz.cast(e);
        return null;
    }

    /**
     *
     * @param toSearch - the TileEntity to start searching from
     * @param world - the world
     * @param <T> - the Specific TileEntity
     * @return a list of TileEntities of the same type which r neighborsof each other
     */
    public static <T extends TileEntity> List<T> depthSearch(T toSearch, IBlockReader world){
        ArrayList<T> finished = new ArrayList<>();
        Stack<T> stack = new Stack<>();
        stack.add(toSearch);
        while (!stack.isEmpty()){
            T te = stack.pop();
            for(Direction d : Direction.values()){
                T found = (T) getTileEntity(toSearch.getClass(),world, toSearch.getBlockPos().relative(d));
                if(found != null){
                    stack.add(found);
                }
            }
            finished.add(te);
        }

        return finished;
    }
}
