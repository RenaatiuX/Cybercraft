package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntityBeaconPost;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BeaconPostBlock extends ContainerBlock {

    /** Whether this fence connects in the northern direction */
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    /** Whether this fence connects in the eastern direction */
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    /** Whether this fence connects in the southern direction */
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    /** Whether this fence connects in the western direction */
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public static final IntegerProperty TRANSFORMED = IntegerProperty.create("transformed", 0, 2);

    protected static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[] {new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    public static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1D, 0.625D);
    public static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.625D, 0.625D, 1D, 1.0D);
    public static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.375D, 1D, 0.625D);
    public static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1D, 0.375D);
    public static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.625D, 0.0D, 0.375D, 1.0D, 1D, 0.625D);

    protected BeaconPostBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {

        BlockPos complete = complete(world, blockPos);

        if (complete != null)
        {

        }
    }

    private BlockPos complete(World world, BlockPos pos)
    {
        for (int y = -9; y <= 0; y++)
        {
            for (int x = -1; x <= 1; x++)
            {
                for (int z = -1; z <= 1; z++)
                {
                    BlockPos start = pos.offset(x, y, z);

                    BlockPos result = complete(world, pos, start);
                    if (result != null)
                    {
                        return result;
                    }
                }
            }
        }

        return null;
    }

    private BlockPos complete(World world, BlockPos pos, BlockPos start)
    {
        for (int y = 0; y <= 9; y++)
        {
            for (int x = -1; x <= 1; x++)
            {
                for (int z = -1; z <= 1; z++)
                {
                    if (y > 3 && (x != 0 || z != 0))
                    {
                        continue;
                    }

                    BlockPos newPos = start.offset(x, y, z);

                    BlockState state = world.getBlockState(newPos);
                    Block block = state.getBlock();
                    if (block != this || state.getValue(TRANSFORMED) != 0)
                    {
                        return null;
                    }
                }
            }
        }
        for (int y = 0; y <= 9; y++)
        {
            for (int x = -1; x <= 1; x++)
            {
                for (int z = -1; z <= 1; z++)
                {
                    if (y > 3 && (x != 0 || z != 0))
                    {
                        continue;
                    }

                    BlockPos newPos = start.offset(x, y, z);


                    if (newPos.equals(start))
                    {
                        world.setBlock(newPos, world.getBlockState(newPos).setValue(TRANSFORMED, 2), 2);

                        TileEntityBeaconPost post = (TileEntityBeaconPost) world.getBlockEntity(newPos);
                    }
                    else
                    {
                        world.setBlock(newPos, world.getBlockState(newPos).setValue(TRANSFORMED, 1), 2);

                        TileEntityBeaconPost post = (TileEntityBeaconPost) world.getBlockEntity(newPos);
                        post.setMasterLoc(start);
                    }
                }
            }
        }
        return start;
    }



    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return null;
    }
}
