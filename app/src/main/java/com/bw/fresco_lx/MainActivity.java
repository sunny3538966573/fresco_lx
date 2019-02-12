package com.bw.fresco_lx;

import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.disk.NoOpDiskTrimmableRegistry;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.ByteConstants;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_yuanjiao)
    Button btnYuanjiao;
    @BindView(R.id.btn_yuanxing)
    Button btnYuanxing;
    @BindView(R.id.btn_bili)
    Button btnBili;
    @BindView(R.id.btn_jianjin)
    Button btnJianjin;
    @BindView(R.id.btn_cipan)
    Button btnCipan;
    @BindView(R.id.btn_gif)
    Button btnGif;
    @BindView(R.id.btn_jianting)
    Button btnJianting;
    @BindView(R.id.btn_okhttp)
    Button btnOkhttp;
    @BindView(R.id.simpledraweeview)
    SimpleDraweeView simpledraweeview;
    private Uri uri;
    int width=1000;
    int height=699;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        uri = Uri.parse("http://img4.imgtn.bdimg.com/it/u=2584422743,1020351689&fm=26&gp=0.jpg");

    }


    @OnClick({R.id.btn_yuanjiao, R.id.btn_yuanxing, R.id.btn_bili, R.id.btn_jianjin, R.id.btn_cipan, R.id.btn_gif, R.id.btn_jianting, R.id.btn_okhttp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_yuanjiao://圆角
                GenericDraweeHierarchy yjhierarchy=GenericDraweeHierarchyBuilder.newInstance(getResources())
                        .setRoundingParams(RoundingParams.fromCornersRadius(20))
                        .build();
                simpledraweeview.setHierarchy(yjhierarchy);
                DraweeController controller= Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .build();
                simpledraweeview.setController(controller);
                break;
            case R.id.btn_yuanxing:
                RoundingParams roundingParams = new RoundingParams();
                roundingParams.setRoundAsCircle(true);
                GenericDraweeHierarchy hierarchy=GenericDraweeHierarchyBuilder.newInstance(getResources())
                        .setRoundingParams(roundingParams)
                        .build();
                simpledraweeview.setHierarchy(hierarchy);
               DraweeController yjcontroller= Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .build();
                simpledraweeview.setController(yjcontroller);

                break;
            case R.id.btn_bili:
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setResizeOptions(new ResizeOptions(width, height))
                        .build();
                PipelineDraweeController bilicontroller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setOldController(simpledraweeview.getController())
                        .setImageRequest(request)
                        .build();
                simpledraweeview.setController(bilicontroller);


                break;
            case R.id.btn_jianjin:
                ImageRequest jianjinrequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse("http://pooyak.com/p/progjpeg/jpegload.cgi"))
                        .setProgressiveRenderingEnabled(true) //设置支持渐进式JPEG
                        .build();
                DraweeController progressiveJPEGController = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(jianjinrequest)
                        .setOldController(simpledraweeview.getController())
                        .build();
                simpledraweeview.setController(progressiveJPEGController);
                break;
            case R.id.btn_cipan:

                break;
            case R.id.btn_gif:
                ControllerListener controllerListener = new BaseControllerListener() {
                    @Override
                    public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                        if (animatable != null) {
                            animatable.start();
                        }
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "图片加载失败", Toast.LENGTH_SHORT).show();
                    }
                };
                DraweeController gifController = Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse("http://img.huofar.com/data/jiankangrenwu/shizi.gif"))
                        .setOldController(simpledraweeview.getController())
                        .setControllerListener(controllerListener)
                        .build();
                simpledraweeview.setController(gifController);

                break;
            case R.id.btn_jianting:
                ControllerListener jianjincontrollerListener = new BaseControllerListener() {

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {
                        super.onIntermediateImageFailed(id, throwable);
                        // 设置图片为渐进式的时候，加载失败，会执行onIntermediateImageFailed方法
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        super.onFailure(id, throwable);
                        // 图片加载失败时候，会执行onFailure方法
                    }

                    @Override
                    public void onSubmit(String id, Object callerContext) {
                        super.onSubmit(id, callerContext);
                    }

                    @Override
                    public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        // 图片加载成功时候会执行onFinalImageSet方法
                    }
                };

                DraweeController jianjincontroller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setControllerListener(jianjincontrollerListener)
                        .build();
                simpledraweeview.setController(jianjincontroller);




                break;
            case R.id.btn_okhttp:
                break;
        }
    }
}
