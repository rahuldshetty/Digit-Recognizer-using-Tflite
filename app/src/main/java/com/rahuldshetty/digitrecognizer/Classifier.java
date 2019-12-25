package com.rahuldshetty.digitrecognizer;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.SystemClock;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Classifier {

    private Context context;
    private Activity activity;
    private Interpreter interpreter = null;
    private int[] shape;
    private int width,height;
    private float[][] results = new float[1][10];
    private int[] mImagePixels = new int[28*28];
    private ByteBuffer byteBuffer;

    public Classifier(Context applicationContext, MainActivity mainActivity) {
        sClassifier(applicationContext,mainActivity);
    }


    public void sClassifier(Context context,Activity activity){
        this.context = context;
        this.activity = activity;
        byteBuffer = ByteBuffer.allocateDirect(4*1*28*28*1);
        byteBuffer.order(ByteOrder.nativeOrder());
        try {
            interpreter = new Interpreter(loadModelFile(activity));
            shape = interpreter.getInputTensor(0).shape();
            width = shape[1];
            height = shape[2];
        }
        catch (Exception e){

        }
    }

    private MappedByteBuffer loadModelFile(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor =  activity.getAssets().openFd("mobile.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }

    public Result classify(Bitmap map){
        long startTime = SystemClock.uptimeMillis();
        Bitmap newmap = Bitmap.createScaledBitmap(map,width,height,true);
        byteBuffer.rewind();
        newmap.getPixels(mImagePixels,0,newmap.getWidth(),0,0,newmap.getWidth(),newmap.getWidth());

        int pixel = 0;
        for(int i = 0; i<28;i++)
            for(int j=0;j<28;j++)
            {
                int value = mImagePixels[pixel++];
                byteBuffer.putFloat(convertPixel(value));
            }
        interpreter.run(byteBuffer, results);
        long endTime = SystemClock.uptimeMillis();
        long timeCost = endTime - startTime;
        return new Result(results[0],timeCost);
    }

    private static float convertPixel(int color) {
        return (255 - (((color >> 16) & 0xFF) * 0.299f
                + ((color >> 8) & 0xFF) * 0.587f
                + (color & 0xFF) * 0.114f)) / 255.0f;
    }

}
