package uk.ac.soton.ecs.nm2.hybridimages;

import org.openimaj.image.FImage;
import org.openimaj.image.processor.SinglebandImageProcessor;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage> {
    private float[][] kernel;

    public MyConvolution(float[][] kernel) {
        //note that like the image pixels kernel is indexed by [row][column]
        this.kernel = kernel;
    }

    // Convolve image with kernel and store result back in image
    @Override
    public void processImage(FImage image) {
        // Pad the image to avoid reducing it's dimensions once convolved
        int heightToPad = this.kernel.length;
        int widthToPad = this.kernel[0].length;
        FImage tempImage = new FImage(image.width + widthToPad, image.height + heightToPad);
        tempImage.internalAssign(image.padding(widthToPad, heightToPad, 0f));
    }
}