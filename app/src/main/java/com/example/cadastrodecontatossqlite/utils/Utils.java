package com.example.cadastrodecontatossqlite.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Utils {

    public static byte[] convertImageToByteArray(Context context, Uri imageUri) {

        //buscaremos a imagem no dispositivo do usuario pela uri
        try {

            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);

            // nele que iremos espalhar os dados do inputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024]; // 1KB
            int bytesRead;

            while((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public static Bitmap convertByteArrayToBitmap(byte[] byteArray){

        if(byteArray == null) return null;
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

    }

}
