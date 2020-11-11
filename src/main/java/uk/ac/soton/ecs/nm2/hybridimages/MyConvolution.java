package uk.ac.soton.ecs.nm2.hybridimages;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.processor.SinglebandImageProcessor;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage> {
    private float[][] kernel;

    public MyConvolution(float[][] kernel) {
        // flip the kernel
        float [][] flippedKernel = kernel.clone();

        for(int i = 0; i < kernel.length; i++){
            for(int j = 0; j < kernel[i].length; j++){
                flippedKernel[i][j] = kernel[kernel.length-1-i][kernel[i].length-1-j];
            }
        }

        this.kernel = flippedKernel;
    }

    // Convolve image with kernel and store result back in image
    @Override
    public void processImage(FImage image) {
        int kernelHeight = this.kernel.length;
        int kernelWidth = this.kernel[0].length;

        // Pad the image to avoid reducing it's dimensions once convolved
        int heightToPad = (int)Math.floor(kernelHeight / 2);
        int widthToPad = (int)Math.floor(kernelWidth / 2);
        FImage tempImage = new FImage(image.getHeight() + widthToPad, image.getWidth() + heightToPad);
        tempImage.internalAssign(image.padding(widthToPad, heightToPad, 0f));

        // Apply kernel to image
        for(int y = 0; y < image.getHeight() ; y++ ){
            for(int x = 0; x < image.getWidth(); x++) {
                float sum = 0;
                for(int ky = 0; ky < kernelHeight; ky++){
                    for(int kx = 0; kx < kernelWidth; kx++) {
                        sum += this.kernel[ky][kx] * tempImage.pixels[y + ky][x + kx];
                    }
                }
                image.pixels[y][x] = sum;
            }
        }
    }
}