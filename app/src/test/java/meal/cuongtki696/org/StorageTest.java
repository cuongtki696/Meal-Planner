package meal.cuongtki696.org;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import meal.cuongtki696.org.ui.storage.StorageItem;
import meal.cuongtki696.org.ui.storage.StorageListActivity;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {24}, manifest = Config.NONE)  // Disable manifest file loading and resource handling
public class StorageTest {

    private StorageListActivity activity;
    private List<StorageItem> storageItems;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() throws ParseException {
        activity = Robolectric.buildActivity(StorageListActivity.class).create().get();

        storageItems = new ArrayList<>();
        storageItems.add(new StorageItem("Milk", 1, "L", sdf.parse("2024-10-20")));  // Expires tomorrow
        storageItems.add(new StorageItem("Eggs", 12, "pcs", sdf.parse("2024-10-18")));  // Expired
        storageItems.add(new StorageItem("Butter", 2, "g", sdf.parse("2024-10-30")));  // Expires in 10 days
    }

    @Test
    public void testItemsExpiringSoon() {
        List<StorageItem> expiringSoon = activity.getItemsExpiringSoon(3);

        assertEquals(1, expiringSoon.size());
        assertEquals("Milk", expiringSoon.get(0).getName());  // Only "Milk" should be in the list
    }
}
