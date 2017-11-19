package com.ozzmhmt.customcamera;

import android.app.Application;
import android.content.Context;
import com.code.library.share.utils.DirectoryUtils;
import com.code.library.utils.DeviceInfoUtils;
import com.code.library.utils.LogUtils;

import org.ffmpeg.android.FfmpegController;

import java.io.File;
import java.io.IOException;

public class MyApplication extends Application {


    private static MyApplication instance;
    private Context context;
    private int screenWidth;
    private int screenHeight;
    private long maxMemory;
    private String BASE_PATH;
    private String APP_PATH;
    private String CACHE_PATH;
    private String CACHE_PATH_FRAMES;
    private String TEMP_PATH;
    private String VIDEO_TEMP_PATH;
    private String PICTURE_PATH;
    private String FONT_CACHE_PATH;
    private FfmpegController fc;

    public String getVIDEO_PATH() {
        return VIDEO_PATH;
    }

    private String VIDEO_PATH;
    private String VIDEO_FRAMES_PATH;
    private int uid = 0;
    //token
    private String token;
    private static int titleBarHeight = 0;
    private String GIF_PATH;
    private String APK_PATH;
    private String FILTER_PATH;
    private String SCENE_PATH;
    private String FONT_PATH;
    private int textHeight;
    private int textWidth;
    private boolean is_first = false;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = this.getApplicationContext();
        LogUtils.init(this, AppConfig.TAG, AppConfig.DEBUG);
        screenWidth = DeviceInfoUtils.getScreenWidth(this);
        screenHeight = DeviceInfoUtils.getScreenHeight(this);
        initDir();
        initMemorySize();
        initFFmpeg();
    }

    private void initFFmpeg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File fileAppRoot = new File(
                        getApplicationInfo().dataDir);
                try {
                    fc = new FfmpegController(
                            context, fileAppRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getFILTER_PATH() {
        return FILTER_PATH;
    }

    private void initDir() {
        BASE_PATH = DirectoryUtils.getDefaultStoragePath(this);
        APP_PATH = BASE_PATH + File.separator + AppConfig.APP_PATH_NAME;
        LogUtils.i("应用存储根目录:" + APP_PATH);
        CACHE_PATH = MyApplication.getInstance().getExternalFilesDir(null) + File.separator + AppConfig.CACHE_PATH_NAME;
        CACHE_PATH_FRAMES = MyApplication.getInstance().getExternalFilesDir(null) + File.separator + AppConfig.CACHE_PATH_FRAMES_NAME;
        LogUtils.i("缓存目录:" + CACHE_PATH);
        PICTURE_PATH = APP_PATH + File.separator + AppConfig.PICTURE_PATH_NAME;
        TEMP_PATH = APP_PATH + File.separator + AppConfig.TEMP_PATH_NAME;

        VIDEO_TEMP_PATH = MyApplication.getInstance().getFilesDir() + File.separator + AppConfig.VIDEO_TEMP_PATH_NAME;
        VIDEO_PATH = APP_PATH + File.separator + AppConfig.VIDEO_PATH_NAME;
        VIDEO_FRAMES_PATH = MyApplication.getInstance().getFilesDir() + File.separator + AppConfig.VIDEO_FRAMES_NAME;

        /*TEMP_PATH = APP_PATH + File.separator + AppConfig.TEMP_PATH_NAME;
        VIDEO_PATH = APP_PATH + File.separator + AppConfig.VIDEO_PATH_NAME;
        VIDEO_FRAMES_PATH = APP_PATH + File.separator + AppConfig.VIDEO_FRAMES_NAME;*/

        GIF_PATH = APP_PATH + File.separator + AppConfig.GIF_PATH_NAME;
        APK_PATH = APP_PATH + File.separator + AppConfig.APK_PATH_NAME;
        FILTER_PATH = MyApplication.getInstance().getFilesDir() + File.separator + AppConfig.FILTER_PATH_NAME;
        SCENE_PATH = MyApplication.getInstance().getFilesDir() + File.separator + AppConfig.SCENE_PATH_NAME;
        FONT_PATH = APP_PATH + File.separator + AppConfig.FONT_PATH_NAME;
        FONT_CACHE_PATH = APP_PATH + File.separator + AppConfig.FONT_CACHE_NAME;
        File dir = null;

    }

    private void initMemorySize() {
        maxMemory = Runtime.getRuntime().maxMemory();
        LogUtils.i("最大申请内存:" + Long.toString(maxMemory / (1024 * 1024)) + "MB");
    }

    public String getTempFileNameForExtension(String extension) {
        return getTempPath() + File.separator + System.currentTimeMillis() + extension;
    }

    public String getTempFileName() {
        return getTempPath() + File.separator + System.currentTimeMillis();
    }

    public void exit() {
        uid = 0;
        token = "";
    }

    public FfmpegController getFc() {
        return fc;
    }

    public void setFc(FfmpegController fc) {
        this.fc = fc;
    }

    public boolean is_first() {
        return is_first;
    }

    public void setIs_first(boolean is_first) {
        this.is_first = is_first;
    }

    public Context getContext() {
        return context;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public String getBasePath() {
        return BASE_PATH;
    }

    public String getAppPath() {
        return APP_PATH;
    }

    public String getCachePath() {
        return CACHE_PATH;
    }

    public String getCACHE_PATH_FRAMES() {
        return CACHE_PATH_FRAMES;
    }

    public String getTempPath() {
        return TEMP_PATH;
    }

    public String getVIDEO_TEMP_PATH() {
        return VIDEO_TEMP_PATH;
    }

    public void setVIDEO_TEMP_PATH(String VIDEO_TEMP_PATH) {
        this.VIDEO_TEMP_PATH = VIDEO_TEMP_PATH;
    }

    public String getPicturePath() {
        return PICTURE_PATH;
    }

    public String getGifPath() {
        return GIF_PATH;
    }

    public String getApkPath() {
        return APK_PATH;
    }

    public int getUid() {
        return uid;
    }

    public int getTitleBarHeight() {
        return titleBarHeight;
    }

    public void setTitleBarHeight(int titleBarHeight) {
        this.titleBarHeight = titleBarHeight;
    }

    public String getSCENE_PATH() {
        return SCENE_PATH;
    }

    public void setTextHeight(int textHeight) {
        this.textHeight = textHeight;
    }

    public void setTextWidth(int textWidth) {
        this.textWidth = textWidth;
    }

    public int getTextWidth() {
        return textWidth;
    }
}
