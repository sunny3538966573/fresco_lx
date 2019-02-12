package com.bw.fresco_lx.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.QualityInfo;

/**
 * Created by 1607c王晴
 * date 2019/2/12
 * Describe:
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 设置渐进式JPEG Config
         * */
        ProgressiveJpegConfig config = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return 0;
            }

            @Override
            public QualityInfo getQualityInfo(int scanNumber) {
                return null;
            }
        };
        /**
         * 直接控制ImagePipeline Config
         * */
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(config)
                .setDownsampleEnabled(true)
                .build();
        /**
         * 初始化使得Fresco支持渐进式JPEG的加载
         * */
        Fresco.initialize(this,imagePipelineConfig);
    }
}
