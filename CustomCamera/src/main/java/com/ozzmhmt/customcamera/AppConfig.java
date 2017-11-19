package com.ozzmhmt.customcamera;

import android.graphics.Bitmap.Config;

import java.io.File;



public class AppConfig {

    public static final boolean DEBUG = true;
    //DEBUG TAG
    public static final String TAG = "CustomCamera";


    public static final int ConnectTimeout = 5;
    public static final int WriteTimeout = 10;
    public static final int ReadTimeout = 10;
    public static final int ResendTime = 60;
    public static final String OkHttpTag = "default";
    public static final String APP_PATH_NAME = "CustomCamera";
    public static final String CACHE_PATH_NAME = "cache";
    public static final String CACHE_PATH_FRAMES_NAME = "cache_frames";
    public static final String TEMP_PATH_NAME = "temp";
    public static final String APK_PATH_NAME = "apk";
    public static final String PICTURE_PATH_NAME = "pictrue";
    public static final String VIDEO_FRAMES_NAME = "FRAMES";
    public static final String GIF_PATH_NAME = "Funny";
    public static final String FILTER_PATH_NAME = "filter";
    public static final String SCENE_PATH_NAME = "scene";
    public static final String tiezhiDownLoadpath = MyApplication.getInstance().getFilesDir().getPath();
    public static final String FresocCache = "fresoc";
    public static final String ImageLoaderCache = "imageloader";
    public static final String pic_format_png = ".png";
    public static final String pic_format_gif = ".gif";
    public static final String pic_format_jpeg = ".jpeg";
    public static final String fileApkSuffix = ".apk";
    public static final int ImageLoaderCacheSzie = 100 * 1024 * 1024;
    public static final int FresocCacheSzie = 100 * 1024 * 1024;
    public static final int ImageQuality = 90;
    public static final Config BitmapConfig = Config.ARGB_8888;
    public static final String MP4 = ".mp4";
    public static final int TOPIC_DETAI_TOP_LIMIT = 9;
    public static final int TOPIC_DETAIL_NOW_LIMIT = 15;
    public static final int ALL_TOPIC_LIMIT = 20;
    public static final int FILTER_COUNT = 8;
    public static final String GUIDEPIC = "GUIDEPIC";
    public static final String VERSION_KEY = "VERSION_KEY";
    public static final String IMAGE = "image";
    public static final String AVATAR = "avatar";
    public static final String SHAREACTION = "ShareAction";
    public static final int HOME_COMMENT_COUNT = 3;
    public static final int SCENE_COUNT = 3;
    public static final int SelectPhotoGridViewNum = 4;
    public static final int DefaultPlayTime = 5;
    public static final int PagerPreLoading = 5;
    public static final int PagerLoadNum = 20;
    public static final int CommentLoadNum = 20;
    public static final int BlackListLoadNum = 20;
    public static final int FollowListLoadNum = 20;
    public static final int FansListLoadNum = 20;
    public static final int MessageListLoadNum = 20;
    public static final int ChannelListLoadNum = 40;
    public static final int ChannelListAdvance = 10;
    public static final int DefaultListAdvance = 5;
    public static final int PagerCachePage = 5;
    public static final int DoubleClickTime = 300;
    public static final int ReportMaxLength = 100;
    public static final int GridImageLoadNum = 30;
    public static final int GridImageLoadAdvance = 15;
    public static final int CommentLoadAdvance = 3;
    public static final int FeedLoadNum = 30;
    public static final int FeedAdvance = 5;
    public static final int CommentReportMaxLength = 140;
    public static final int NickNameShowLength = 15;
    public static final int SignLength = 200;
    public static final int UploadImageNumber = 20;
    public static int BlackWhiteFilterPosition;

    public static final String VIDEO_PATH_NAME = "VIDEO";
    public static String FONT_PATH_NAME = "FONT_PATH" + File.separator;
    public static String FONT_CACHE_NAME = "FONT_CACHE" + File.separator;
    public static String CUTIE_PACKAGENAME = "com.funny.cutie";
    public static String IS_NOWIFI_LOADING = "IS_NOWIFI_LOADING";
    public static String VIDEO_TEMP_PATH_NAME = "VIDEO_TEMP_PATH_NAME";
}