package ua.matvienko_apps.imagecleaner;


import android.graphics.Bitmap;


public class СonvertImageToArray {

    public int[][] ArrayFromImage(Bitmap image) {
        int[][] imageArray = new int[image.getWidth()][image.getHeight()];

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                imageArray[i][j] = image.getPixel(i, j);
            }
        }
        return imageArray;
    }

    public Bitmap ArrayToImage(int[][] imageArray) {
        Bitmap image = Bitmap.createBitmap(imageArray.length, imageArray[0].length, Bitmap.Config.ARGB_4444);

        for (int i = 0; i < imageArray.length; i++) {
            for (int j = 0; j < imageArray[i].length; j++) {
                image.setPixel(i, j, imageArray[i][j]);
            }
        }

        return image;
    }
}