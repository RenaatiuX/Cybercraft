package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntityBeaconPost;
import com.rena.cybercraft.common.util.WorldUtil;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Comparator;

public class BeaconPostBlock extends ContainerBlock {

    /**
     * Whether this fence connects in the northern direction
     */
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    /**
     * Whether this fence connects in the eastern direction
     */
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    /**
     * Whether this fence connects in the southern direction
     */
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    /**
     * Whether this fence connects in the western direction
     */
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public static final IntegerProperty TRANSFORMED = IntegerProperty.create("transformed", 0, 2);

    protected static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[]{new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    public static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1D, 0.625D);
    public static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.625D, 0.625D, 1D, 1.0D);
    public static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.375D, 1D, 0.625D);
    public static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1D, 0.375D);
    public static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.625D, 0.0D, 0.375D, 1.0D, 1D, 0.625D);

    public BeaconPostBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).strength(5f, 10f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops());
    }

    @Override
    public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        if (!world.isClientSide()) {
            findAdjacentTowers(blockPos, world);
            BlockPos master = WorldUtil.getTileEntity(TileEntityBeaconPost.class, world, blockPos).getMaster();
            System.out.println(canComplete(world, master));
            if (canComplete(world, master)) {
                tryComplete(world, master);
            }
        }
    }

    private void findAdjacentTowers(BlockPos pos, World world) {
        TileEntityBeaconPost current = WorldUtil.getTileEntity(TileEntityBeaconPost.class, world, pos);
        for (Direction dir : Direction.values()) {
            TileEntityBeaconPost te = WorldUtil.getTileEntity(TileEntityBeaconPost.class, world, pos.relative(dir));
            if (te != null) {
                if (te.getMaster().getY() < pos.getY()) {
                    te.updateMaster(pos);
                } else {
                    te.addSlave(pos);
                    current.setMasterLoc(te.getMaster());
                }
                return;
            }
        }
        current.setMasterLoc(pos);
    }

    public boolean tryComplete(World world, BlockPos masterPos) {
        boolean canComplete = canComplete(world, masterPos);
        setCompleted(world, masterPos, canComplete);
        return canComplete;
    }

    private boolean canComplete(World world, BlockPos start) {
        TileEntityBeaconPost master = WorldUtil.getTileEntity(TileEntityBeaconPost.class, world, start);
        for (int y = 0; y >= -9; y--) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (y > -7 && (x != 0 || z != 0))
                        continue;

                    BlockPos newPos = start.offset(x, y, z);

                    BlockState state = world.getBlockState(newPos);
                    Block block = state.getBlock();
                    if (block != this || state.getValue(TRANSFORMED) != 0 || !master.getSlaves().contains(newPos)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void setCompleted(World world, BlockPos masterPos, boolean completed) {
        for (int y = 0; y >= -9; y--) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (y > -7 && (x != 0 || z != 0)) {
                        continue;
                    }
                    BlockPos newPos = masterPos.offset(x, y, z);
                    if (world.getBlockState(newPos).getBlock() == this) {
                        if (completed) {
                            if (newPos.equals(masterPos)) {
                                world.setBlock(newPos, world.getBlockState(newPos).setValue(TRANSFORMED, 2), 2);
                                System.out.println("set the value to 2");
                            } else {
                                world.setBlock(newPos, world.getBlockState(newPos).setValue(TRANSFORMED, 1), 2);
                                System.out.println("set value to 1");
                            }
                        } else {
                            world.setBlock(newPos, world.getBlockState(newPos).setValue(TRANSFORMED, 0), 2);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newSTate, boolean bool) {
        if (!world.isClientSide() && !state.is(newSTate.getBlock())) {
            TileEntityBeaconPost te = WorldUtil.getTileEntity(TileEntityBeaconPost.class, world, pos);
            if (te.isMaster()) {
                te.removeSlave(pos);
                BlockPos max = te.getSlaves().stream().max(Comparator.comparing(BlockPos::getY)).orElse(null);
                if (max != null)
                    te.updateMaster(max);
                tryComplete(world, max);
            } else {
                te.removeSlave(pos);
                tryComplete(world, te.getMaster());
            }
        }
        super.onRemove(state, world, pos, newSTate, bool);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return state.getValue(TRANSFORMED) == 0 ? BlockRenderType.MODEL : BlockRenderType.INVISIBLE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return new TileEntityBeaconPost();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TRANSFORMED, EAST, WEST, SOUTH, NORTH);
    }
}
