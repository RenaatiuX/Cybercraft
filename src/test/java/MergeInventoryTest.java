import com.google.common.annotations.VisibleForTesting;
import com.rena.cybercraft.common.tileentities.MergeInventory;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class MergeInventoryTest {

    IInventory inv1, inv2;

    @BeforeTest
    public void init(){
        inv1 = new Inventory(10);
        inv1.setItem(5, new ItemStack(ItemInit.ARM_LEFT.get()));
        inv2 = new Inventory(20);
        inv2.setItem(6, new ItemStack(Items.APPLE));
        inv2.setItem(13, new ItemStack(Items.GRASS));
    }
    @Test
    public void test(){
        MergeInventory merge = new MergeInventory();
        merge.appendInventory(inv1);
        merge.appendInventory(inv2);
        assertFalse(merge.isEmpty());
        assertEquals(merge.getItem(5).getItem(), inv1.getItem(5).getItem());
        assertEquals(merge.getItem(inv1.getContainerSize() + 6).getItem(), inv2.getItem(6).getItem());
    }
}
