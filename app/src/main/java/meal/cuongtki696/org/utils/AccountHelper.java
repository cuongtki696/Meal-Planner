package meal.cuongtki696.org.utils;

import com.tencent.mmkv.MMKV;

import java.util.HashSet;
import java.util.Set;

public class AccountHelper {
    private static MMKV mmkv = MMKV.defaultMMKV();

    private static String KEY_ACCOUNT_ACTIVE = "account_active";
    private static String KEY_ACCOUNT_REGISTER = "account_register";
    private static String KEY_HISTORY = "history";
    private static String KEY_COLLECT = "collect";
    private static String KEY_SHOPPING = "shopping";

    public static boolean saveActiveAccount(String account) {
        return mmkv.encode(KEY_ACCOUNT_ACTIVE, account);
    }

    public static String getActiveAccount() {
        return mmkv.decodeString(KEY_ACCOUNT_ACTIVE, null);
    }

    public static boolean isAccountExisted(String account) {
        return mmkv.containsKey(KEY_ACCOUNT_REGISTER + ":" + account);
    }

    public static boolean registerAccount(String account, String password) {
        return mmkv.encode(KEY_ACCOUNT_REGISTER + ":" + account, password);
    }

    public static String getAccountPassword(String account) {
        return mmkv.decodeString(KEY_ACCOUNT_REGISTER + ":" + account, null);
    }

    public static boolean addHistory(String mealId) {
        Set<String> ids = getHistory();
        ids.add(mealId);
        return mmkv.encode(getActiveAccount() + ":" + KEY_HISTORY, ids);
    }

    public static Set<String> getHistory() {
        return mmkv.decodeStringSet(getActiveAccount() + ":" + KEY_HISTORY, new HashSet<>());
    }

    public static boolean addCollect(String mealId) {
        Set<String> ids = getCollect();
        ids.add(mealId);
        return mmkv.encode(getActiveAccount() + ":" + KEY_COLLECT, ids);
    }

    public static boolean removeCollect(String mealId) {
        Set<String> ids = getCollect();
        ids.remove(mealId);
        return mmkv.encode(getActiveAccount() + ":" + KEY_COLLECT, ids);
    }

    public static Set<String> getCollect() {
        return mmkv.decodeStringSet(getActiveAccount() + ":" + KEY_COLLECT, new HashSet<>());
    }

    public static boolean addShopping(String item) {
        Set<String> items = getShopping();
        items.add(item);
        return mmkv.encode(getActiveAccount() + ":" + KEY_SHOPPING, items);
    }

    public static boolean removeShopping(String item) {
        Set<String> items = getShopping();
        items.remove(item);
        return mmkv.encode(getActiveAccount() + ":" + KEY_SHOPPING, items);
    }

    public static Set<String> getShopping() {
        return mmkv.decodeStringSet(getActiveAccount() + ":" + KEY_SHOPPING, new HashSet<>());
    }
}
