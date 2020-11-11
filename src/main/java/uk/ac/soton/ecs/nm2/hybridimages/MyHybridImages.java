package uk.ac.soton.ecs.nm2.hybridimages;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.Gaussian2D;

public class MyHybridImages {
    /**
     * Compute a hybrid image combining low-pass and high-pass filtered images
     *
     * @param lowImage
     *            the image to which apply the low pass filter
     * @param lowSigma
     *            the standard deviation of the low-pass filter
     * @param highImage
     *            the image to which apply the high pass filter
     * @param highSigma
     *            the standard deviation of the low-pass component of computing the
     *            high-pass filtered image
     * @return the computed hybrid image
     */
    public static MBFImage makeHybrid(MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma) {
        //implement your hybrid images functionality here.
        //Your submitted code must contain this method, but you can add
        //additional static methods or implement the functionality through
        //instance methods on the `MyHybridImages` class of which you can create
        //an instance of here if you so wish.
        //Note that the input images are expected to have the same size, and the output
        //image will also have the same height & width as the inputs.

        // This will be our low version of our high frequency image which we will subtract from the original
        // To produce a high frequency image
        MBFImage highImageCopy = highImage.clone();

        MyConvolution lowConvolution = new MyConvolution(createGaussianKernel(lowSigma));
        MyConvolution highConvolution = new MyConvolution(createGaussianKernel(highSigma));

        // Create two low frequency images
        MBFImage lowProcessedImage = lowImage.process(lowConvolution);
        MBFImage highProcessedImage = highImageCopy.process(highConvolution);

        DisplayUtilities.display(lowProcessedImage);

        // Produce high frequency image
        MBFImage highFreqHighImage = highImage.subtract(highProcessedImage);

        // Sum both low and high frequency images to create hybrid
        MBFImage hybridImage = lowProcessedImage.add(highFreqHighImage);

        return hybridImage;
    }

    private static float[][] createGaussianKernel(float sigma){
        int size = (int) (8.0f * sigma + 1.0f); // (this implies the window is +/- 4 sigmas from the centre of the Gaussian)
        if (size % 2 == 0) size++; // size must be odd

        float[][] kernel = Gaussian2D.createKernelImage(size, sigma).pixels;

        return kernel;
    }
}