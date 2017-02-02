package ua.matvienko_apps.imagecleaner;

import android.graphics.Bitmap;
import java.util.Arrays;

/**
 * Created by alex_ on 08.04.2016.
 */
public class Filters {

    public Bitmap MedianFilter(Bitmap image, int matrix_size) {
        /**
         * image - Image, matrix_size - size of matrix,
         * on which we will sort out image array
         */
        СonvertImageToArray convert = new СonvertImageToArray();

        int[][] img_arr = convert.ArrayFromImage(image);

        int[] matrix_array = new int[(int) Math.pow(matrix_size, 2)]; /** array, where write and sort element of matrix */
        int index = 0;

        for (int start_index_row = 0; start_index_row < img_arr.length - (matrix_size - 1) ;
             start_index_row++) {
            /**
             * Here change the number column, from which start search matrix.
             */
            for (int start_index_col = 0; start_index_col < img_arr[0].length - (matrix_size - 1);
                 start_index_col++) {
                /**
                 * Here change the number range, from which start search matrix.
                 */
                for (int i = start_index_row; i < start_index_row + matrix_size; i++) {
                    for (int j = start_index_col; j < start_index_col + matrix_size; j++) {
                        matrix_array[index] = img_arr[i][j]; /** write each element oа matrix to array */
                        index++;
                    }
                }
                index = 0; /** assigned index to 0, for next using in next matrix */

                Arrays.sort(matrix_array); /** Sort matrix_array */

                img_arr[start_index_row + matrix_size / 2]
                        [start_index_col + matrix_size / 2]
                        = matrix_array[matrix_array.length / 2]; /** Find centre of matrix and
                 assigned it to array-median */

            }
        }
        return convert.ArrayToImage(img_arr);
    }

    public int[][] SmoothFilter(Bitmap image, int matrix_size) {
        /**
         * image - Image, matrix_size - size of matrix,
         * on which we will sort out image array
         */
        СonvertImageToArray convert = new СonvertImageToArray();


        int[][] img_arr = convert.ArrayFromImage(image);

        double average = 0;
        int[] matrix_array = new int[(int) Math.pow(matrix_size, 2)]; /** array, where write and sort element of matrix */
        int index = 0;

        for (int start_index_row = 0; start_index_row < img_arr.length - (matrix_size - 1) ;
             start_index_row++) {
            /**
             * Here change the number column, from which start search matrix.
             */
            for (int start_index_col = 0; start_index_col < img_arr[0].length - (matrix_size - 1);
                 start_index_col++) {
                /**
                 * Here change the number range, from which start search matrix.
                 */
                for (int i = start_index_row; i < start_index_row + matrix_size; i++) {
                    for (int j = start_index_col; j < start_index_col + matrix_size; j++) {
                        matrix_array[index] = img_arr[i][j]; /** write each element oа matrix to array */
                        index++;
                    }
                }
                index = 0; /** assigned index to 0, for next using in next matrix */

                /**find sum of array*/
                for(int i = 0; i < matrix_array.length; i++) {
                    average += matrix_array[i];
                }

                average = round(average , (matrix_size * matrix_size)); /** find average of array */

                img_arr[start_index_row + matrix_size / 2]
                        [start_index_col + matrix_size / 2]
                        = (int) average; /** Find average of matrix and
                 assigned it to array-median */

                average = 0;

            }
        }
        return img_arr;
    }

    private int round(double a, int b) {
        if (a % b != 0) {
            if (a % b > 5 ) {
                return (int) (((a/b) - ((a/b) - (int)(a/b)) + 1));
            } else {
                return (int) ((a/b) - ((a/b) - (int)(a/b)));
            }
        }
        return (int)a/b;
    }
}
