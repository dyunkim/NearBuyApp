package com.appwrench.nearbuy.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("1", "Item 1", "Joe's Books", "This is the address", "Joe", "type thing"));
        addItem(new DummyItem("2", "Item 2", "a", "b", "c", "d"));
        addItem(new DummyItem("3", "Item 3", "e", "f", "g", "h"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String content;
        public String storeName;
        public String storeAddress;
        public String storeOwner;
        public String storeType;

        public DummyItem(String id, String content, String nm, String loc, String owner, String type) {
            this.id = id;
            this.content = content;
            this.storeAddress = loc;
            this.storeName = nm;
            this.storeOwner = owner;
            this.storeType = type;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
