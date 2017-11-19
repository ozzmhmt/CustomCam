package com.ozzmhmt.customcamera.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.ViewFlipper;

import com.ozzmhmt.customcamera.Adapter.IconAdapter;
import com.ozzmhmt.customcamera.Adapter.PhotoEditionView1;
import com.ozzmhmt.customcamera.Adapter.PhotoEditionView2;
import com.ozzmhmt.customcamera.AppConstant;
import com.ozzmhmt.customcamera.R;
import com.ozzmhmt.customcamera.Manager.FrameParser;
import com.ozzmhmt.customcamera.Manager.MagicwandParser;
import com.ozzmhmt.customcamera.Manager.PendantParser;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ShowPicActivity extends Activity {

    private ImageView img;
    private int picWidth;
    private int picHeight;
    private ImageView frameBtn;


    private static enum Surface1 {NONE, FILTER, FRAME, HELP1}
    private Surface1 surface1;

    private static enum Surface2 {NONE, DECORATE, HELP2}
    private Surface2 surface2;
    private Animation pushupinanim;
    private Animation pushdownoutanim;
    private Animation popinanim;
    private Animation popoutanim;
    private Animation pushleftinanim;
    private Animation pushleftoutanim;
    private Animation pushrightinanim;
    private Animation pushrightoutanim;
    private int icon4Size;
    private PhotoEditionView1 pev1;
    private GridView  framegv;
    private Button back1btn;
    private Button next1btn;
    private int saveSize;
    private float adjust;
    private int viewWidth;
    private int viewHeight;
    private int frameSize;
    private Bitmap originalPhoto;
    private ViewFlipper viewflipper;

	private ImageButton framebtn;
	private IconAdapter filterAdapter;
	private IconAdapter frameAdapter;
	private LinearLayout decoratell;
	private GridView  pendantgv;
	private GridView  magicwandgv;
	private LinearLayout pendantbar;
	private LinearLayout magicwandbar;
	private ImageView help2iv;
	private ImageButton decoratebtn;
	private Button pendantbtn;
	private Button magicwandbtn;
	private ImageButton undobtn;
	private ImageButton clearbtn;
	private ImageButton redobtn;
	private ImageButton help2btn;
	private Button back2btn;
	private Button next2btn;
	private IconAdapter pendantAdapter;
	private IconAdapter magicwandAdapter;
	private HashMap<Button, View> tabMap;
	private static enum PendantControl{SCALE, ROTATE}
	private PendantControl pendantControl;
	private boolean pendantControlOpen;
	private ImageView pendantiv;
	private ImageView pendantdispiv1;
	private SeekBar pendantseekbar;
	private ImageView pendantdispiv2;
	private ImageButton pendantswitchbtn;
	private static enum MagicwandControl{START, STOP}
	private MagicwandControl magicwandControl;
	private boolean magicwandControlOpen;
	private ImageView magicwandiv;
	private SeekBar magicwandseekbar;
	private ImageButton magicwandswitchbtn;
	private ImageButton magicwanddeletebtn;


    private int icon5Size;
    private PhotoEditionView2 pev2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_pic);
        frameBtn= (ImageView) findViewById(R.id.FrameBtn2);
        /*picWidth = getIntent().getIntExtra(AppConstant.KEY.PIC_WIDTH, 0);
        picHeight = getIntent().getIntExtra(AppConstant.KEY.PIC_HEIGHT, 0);
        img = (ImageView)findViewById(R.id.img);
        img.setImageURI(Uri.parse(getIntent().getStringExtra(AppConstant.KEY.IMG_PATH)));
        img.setLayoutParams(new RelativeLayout.LayoutParams(picWidth, picHeight));*/
        saveSize = 640;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        adjust = (float)metrics.widthPixels / 320;

        initOriginalPhotoTest(getIntent().getStringExtra(AppConstant.KEY.IMG_PATH), metrics);
        pushupinanim = AnimationUtils.loadAnimation(this, R.anim.push_up_in);
        pushdownoutanim = AnimationUtils.loadAnimation(this, R.anim.push_down_out);
        popinanim = AnimationUtils.loadAnimation(this, R.anim.pop_in);
        popoutanim = AnimationUtils.loadAnimation(this, R.anim.pop_out);
        pushleftinanim = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        pushleftoutanim = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
        pushrightinanim = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
        pushrightoutanim = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
        initiateView1();
        initiateView2();
        frameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(surface1 == Surface1.FRAME)
                    setSurface1(Surface1.NONE);
                else
                    setSurface1(Surface1.FRAME);

            }
        });
    }
    private void initOriginalPhoto(String photopath, DisplayMetrics metrics) {
        Bitmap photo = BitmapFactory.decodeFile(photopath);

        if(photo == null) {
            viewWidth = 0;
            viewHeight = 0;
            return;
        }

        if(originalPhoto != null && !originalPhoto.isRecycled())
            originalPhoto.recycle();
        originalPhoto = photo;

        float scale = Math.min((float)metrics.widthPixels / originalPhoto.getWidth(),
                (float)(metrics.heightPixels - 100 * adjust) / originalPhoto.getHeight());
        viewWidth = (int) (originalPhoto.getWidth() * scale);
        viewHeight = (int) (originalPhoto.getHeight() * scale);
        frameSize = (int) Math.min(metrics.widthPixels, metrics.heightPixels - 100 * adjust);
        Log.i("view",viewWidth + "," + viewHeight);
    }
    private void initOriginalPhotoTest(String photopath, DisplayMetrics metrics) {
        Bitmap photo = null;
        try {
            InputStream is = this.getAssets().open(photopath);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            photo = (Bitmap)BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(photo == null) {
            viewWidth = 0;
            viewHeight = 0;
            return;
        }

        if(originalPhoto != null && !originalPhoto.isRecycled())
            originalPhoto.recycle();
        originalPhoto = photo;

        float scale = Math.min((float)metrics.widthPixels / originalPhoto.getWidth(),
                (float)(metrics.heightPixels - 100 * adjust) / originalPhoto.getHeight());
        viewWidth = (int) (originalPhoto.getWidth() * scale);
        viewHeight = (int) (originalPhoto.getHeight() * scale);
        frameSize = (int) Math.min(metrics.widthPixels, metrics.heightPixels - 100 * adjust);
        Log.i("view",viewWidth + "," + viewHeight);
    }
    private void setSurface1(Surface1 newsurface) {
        switch(surface1) {
            case FRAME:
                frameBtn.setSelected(false);
                framegv.startAnimation(pushdownoutanim);
                framegv.setVisibility(View.GONE);
                break;
        }

        switch(newsurface) {
            case FRAME:
                frameBtn.setSelected(true);
                framegv.setVisibility(View.VISIBLE);
                framegv.startAnimation(pushupinanim);
                break;
        }

        surface1 = newsurface;
    }
    private void setSurface2(Surface2 newsurface) {
        switch(surface2) {
            case NONE:
                break;
            case DECORATE:
                decoratebtn.setSelected(false);
                decoratell.startAnimation(pushdownoutanim);
                decoratell.setVisibility(View.GONE);
                break;
            case HELP2:
                help2btn.setSelected(false);
                help2iv.startAnimation(popoutanim);
                help2iv.setVisibility(View.GONE);
                break;
        }

        switch(newsurface) {
            case NONE:
                break;
            case DECORATE:
                decoratebtn.setSelected(true);
                decoratell.setVisibility(View.VISIBLE);
                decoratell.startAnimation(pushupinanim);
                break;
            case HELP2:
                help2btn.setSelected(true);
                help2iv.setVisibility(View.VISIBLE);
                help2iv.startAnimation(popinanim);
                break;
        }

        surface2 = newsurface;
    }
    private void initiateView1() {
        icon4Size = (int)(58 * adjust);

        FrameParser frameparser = new FrameParser(this, "frame");
        try {

            pev1 = (PhotoEditionView1) findViewById(R.id.pev1);
            pev1.loadOriginalPhoto(originalPhoto);
        }catch (Exception e){
            Log.e("Hata",e.getMessage());
        }
        frameAdapter = new IconAdapter(this, frameparser.getIdIcon(), frameparser.getIdName(), icon4Size);
        framegv = (GridView) this.findViewById(R.id.framegv);
        framegv.setAdapter(frameAdapter);
        framegv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub
                frameAdapter.setSelected(position);
                pev1.setFrame(frameAdapter.getIconId(position));
                pev1.update();
                setSurface1(Surface1.NONE);
            }});


        ImageButton framebtn = (ImageButton) this.findViewById(R.id.FrameBtn2);
        framebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(surface1 == Surface1.FRAME)
                    setSurface1(Surface1.NONE);
                else
                    setSurface1(Surface1.FRAME);
            }});


        frameAdapter.setSelected(0);
        surface1 = Surface1.NONE;
    }
    private void initiateView2() {
        icon5Size = (int)(38 * adjust);

        PendantParser pendantparser = new PendantParser(this, "pendant");
        MagicwandParser magicwandparser = new MagicwandParser(this, "magicwand");

        pev2 = (PhotoEditionView2) this.findViewById(R.id.pev2);
        pev2.initiate(viewWidth, viewHeight, frameSize, adjust,
                pendantparser.getIdPendant(), magicwandparser.getIdGadget());

        pendantAdapter = new IconAdapter(this, pendantparser.getIdIcon(), icon5Size);
        magicwandAdapter = new IconAdapter(this, magicwandparser.getIdIcon(), icon5Size);

        decoratell = (LinearLayout) this.findViewById(R.id.decoratell);
        pendantgv = (GridView) this.findViewById(R.id.pendantgv);
        pendantgv.setAdapter(pendantAdapter);
        pendantgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub
                pendantAdapter.setSelected(position);
                String iconid = pendantAdapter.getIconId(position);
                pev2.addPendant(iconid);
                setSurface2(Surface2.NONE);
            }});
        pendantbar = (LinearLayout) this.findViewById(R.id.pendantbar);

        magicwandgv = (GridView) this.findViewById(R.id.magicwandgv);
        magicwandgv.setAdapter(magicwandAdapter);
        magicwandgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub
                magicwandAdapter.setSelected(position);
                String iconid = magicwandAdapter.getIconId(position);
                pev2.addMagicwand(iconid);
                setMagicwandControl(MagicwandControl.STOP);
                setSurface2(Surface2.NONE);
            }});
        magicwandbar = (LinearLayout) this.findViewById(R.id.magicwandbar);



        pendantbtn = (Button) this.findViewById(R.id.pendantbtn);
        pendantbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setTab(pendantbtn);
            }});

        magicwandbtn = (Button) this.findViewById(R.id.magicwandbtn);
        magicwandbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setTab(magicwandbtn);
            }});

        pendantiv = (ImageView) this.findViewById(R.id.pendantiv);
        pendantiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pev2.addCurrPendant();
            }});




        tabMap = new HashMap<Button, View>();
        tabMap.put(pendantbtn, pendantgv);
        tabMap.put(magicwandbtn, magicwandgv);
        setTab(pendantbtn);

        pendantAdapter.setSelected(-1);
        magicwandAdapter.setSelected(-1);
        surface2 = Surface2.NONE;

        pendantControlOpen = false;
        setPendantControl(PendantControl.SCALE);
        magicwandControlOpen = false;
        setMagicwandControl(MagicwandControl.STOP);
        pev2.setParentHandler(new PhotoEditionHandler(Looper.myLooper()));
    }
    private void setTab(Button tabbtn) {
        for(Button btn : tabMap.keySet()) {
            if(btn.isSelected()) {
                btn.setSelected(false);
                tabMap.get(btn).setVisibility(View.GONE);
            }
        }//for btn

        tabbtn.setSelected(true);
        tabMap.get(tabbtn).setVisibility(View.VISIBLE);
    }

    private void setPendantControl(PendantControl pendantcontrol) {
        switch(pendantcontrol) {
            case SCALE:
                break;
            case ROTATE:
                break;
        }
        pendantControl = pendantcontrol;
    }
    private void setMagicwandControl(MagicwandControl magicwandcontrol) {
        switch(magicwandcontrol) {
            case START:
                break;
            case STOP:
                break;
        }
        magicwandControl = magicwandcontrol;
    }

    private String savePhoto() {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return null;

        Bitmap savedbmp = pev2.getPhoto(saveSize);
        String dirpath = Environment.getExternalStorageDirectory().toString() + "/digu-PhotoEdition2";
        File saveddir = new File(dirpath);
        if(!saveddir.exists())
            saveddir.mkdirs();
        String filepath = dirpath + "/saved_photo.jpg";
        File savedfile = new File(filepath);

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(savedfile));
            savedbmp.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        }
        catch(Exception e) {
            e.getMessage();
            return null;
        }

        if(savedbmp != null && !savedbmp.isRecycled()) {
            savedbmp.recycle();
            savedbmp = null;
        }

        return filepath;
    }

    //开启挂件控制条
    private void openPendantControl() {
        if(!pendantControlOpen) {
            pendantbar.setVisibility(View.VISIBLE);
            pendantbar.startAnimation(pushupinanim);
            pendantControlOpen = true;
        }

        //更新当前挂件图片
        pendantiv.setImageBitmap(pev2.getCurrPendantBmp());
    }
    //更新挂件控制条
    private void updatePendantSeekBar() {
        int scale = -1;
        switch(pendantControl) {
            case SCALE:
                scale = pev2.getPendantScale();
                break;

            case ROTATE:
                scale = pev2.getPendantRotate();
                break;
        }
        if(scale >= 0)
            pendantseekbar.setProgress(scale);
    }
    //关闭挂件控制条
    private void closePendantControl() {
        if(pendantControlOpen) {
            pendantbar.startAnimation(pushdownoutanim);
            pendantbar.setVisibility(View.GONE);
            pendantControlOpen = false;
        }
    }

    //开启魔术棒控制条
    private void openMagicwandControl() {
        if(!magicwandControlOpen) {
            magicwandbar.setVisibility(View.VISIBLE);
            magicwandbar.startAnimation(pushupinanim);
            magicwandControlOpen = true;
        }

        //更新当前挂件图片
        magicwandiv.setImageBitmap(pev2.getCurrMagicwandBmp());
    }
    //更新魔术棒控制条
    private void updateMagicwandSeekBar() {
        int scale = pev2.getMagicwandScale();
        if(scale >= 0)
            magicwandseekbar.setProgress(scale);
    }
    //关闭魔术棒控制条
    private void closeMagicwandControl() {
        if(magicwandControlOpen) {
            magicwandbar.startAnimation(pushdownoutanim);
            magicwandbar.setVisibility(View.GONE);
            setMagicwandControl(MagicwandControl.START);
            magicwandControlOpen = false;
        }
    }

    public static final int OPEN_PENDANT_CONTROL = 0;
    public static final int UPDATE_PENDANT_CONTROL = 1;
    public static final int CLOSE_PENDANT_CONTROL = 2;
    public static final int OPEN_MAGICWAND_CONTROL = 3;
    public static final int CLOSE_MAGICWAND_CONTROL = 4;
    //处理消息句柄
    private class PhotoEditionHandler extends Handler {

        public PhotoEditionHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case OPEN_PENDANT_CONTROL:
                    closeMagicwandControl();
                    openPendantControl();
                    updatePendantSeekBar();
                    break;
                case UPDATE_PENDANT_CONTROL:
                    updatePendantSeekBar();
                    break;
                case CLOSE_PENDANT_CONTROL:
                    closePendantControl();
                    break;
                case OPEN_MAGICWAND_CONTROL:
                    closePendantControl();
                    openMagicwandControl();
                    //updateMagicwandSeekBar();
                    break;
                case CLOSE_MAGICWAND_CONTROL:
                    closeMagicwandControl();
                    break;
            }
        }
    }

    //回收内存
    public void recycleAll() {
        if(originalPhoto != null && !originalPhoto.isRecycled())
            originalPhoto.recycle();
        originalPhoto = null;
        pev1.recycleAll();
        pev2.recycleAll();
        frameAdapter.recycleAll();
        pendantAdapter.recycleAll();
        magicwandAdapter.recycleAll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleAll();
        Log.i("recycleAll", "done.");
    }
    }