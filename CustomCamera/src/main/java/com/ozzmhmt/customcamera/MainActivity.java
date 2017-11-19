package com.ozzmhmt.customcamera;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.ozzmhmt.customcamera.activity.ShowPicActivity;
import com.ozzmhmt.customcamera.base.DefaultBaseActivity;
import com.ozzmhmt.customcamera.utils.CameraUtil;

public class MainActivity extends DefaultBaseActivity {

    private Button btn_camera;

    @Override
    protected void initialize() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        btn_camera = (Button)findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUtil.getInstance().camera(MainActivity.this);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != AppConstant.RESULT_CODE.RESULT_OK){
            return;
        }

        if(requestCode == AppConstant.REQUEST_CODE.CAMERA){
            String img_path = data.getStringExtra(AppConstant.KEY.IMG_PATH);

            int picWidth = data.getIntExtra(AppConstant.KEY.PIC_WIDTH, 0);
            int picHeight = data.getIntExtra(AppConstant.KEY.PIC_HEIGHT, 0);
            Intent intent = new Intent(activity, ShowPicActivity.class);
            intent.putExtra(AppConstant.KEY.PIC_WIDTH, picWidth);
            intent.putExtra(AppConstant.KEY.PIC_HEIGHT, picHeight);
            intent.putExtra(AppConstant.KEY.IMG_PATH, img_path);
            startActivity(intent);
        }
    }
}
