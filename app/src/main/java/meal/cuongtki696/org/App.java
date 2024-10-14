package meal.cuongtki696.org;

import android.app.Application;

import com.tencent.mmkv.MMKV;

import meal.cuongtki696.org.utils.AssetsHelper;

public class App extends Application {
    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        AssetsHelper.setup(this);

        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
    }
}
