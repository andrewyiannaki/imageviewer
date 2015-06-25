package imageviewer2;

import java.awt.Color;

/**
 * Filter is an abstract superclass for all image filters in this
 * application. Filters can be applied to OFImages by invoking the apply 
 * method.
 * 
 * @author Michael Kölling and David J. Barnes.
 * @version 1.0
 */
public abstract class Filter
{
    private String name;

    /**
     * Create a new filter with a given name.
     * @param name The name of the filter.
     */
    public Filter(String name)
    {
        this.name = name;
    }
    
    /**
     * Return the name of this filter.
     * 
     * @return  The name of this filter.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Apply this filter to an image.
     * 
     * @param  image  The image to be changed by this filter.
     */
    public abstract void apply(OFImage image);
    
    public void applyFilter(OFImage image, Object filterObject)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if (filterObject instanceof DarkerFilter) {
                    image.setPixel(x, y, image.getPixel(x, y).darker());
                }
                if (filterObject instanceof LighterFilter) {
                    image.setPixel(x, y, image.getPixel(x, y).brighter());
                }
                
                if (filterObject instanceof ThresholdFilter) {
                    Color pixel = image.getPixel(x, y);
                    int brightness = (pixel.getRed() + pixel.getBlue() + pixel.getGreen()) / 3;
                    if(brightness <= 85) {
                        image.setPixel(x, y, Color.BLACK);
                    }
                    else if(brightness <= 170) {
                        image.setPixel(x, y, Color.GRAY);
                    }
                    else {
                        image.setPixel(x, y, Color.WHITE);
                    }
                }
                
                if (filterObject instanceof GrayscaleFilter) {
                    Color pixel = image.getPixel(x, y);
                    int average = (pixel.getRed() + pixel.getBlue() + pixel.getGreen()) / 3;
                    
                    image.setPixel(x, y, new Color(average, average, average));
                }
                
                if (filterObject instanceof FlipHorizontal) {
                    if (x  < 0.5*width) {
                        Color pixelA = image.getPixel(x, y);
                        image.setPixel(x, y, image.getPixel(width - 1 - x, y));
                        image.setPixel(width - 1 - x, y, pixelA);
                        
                    }  
                }
                
                if (filterObject instanceof InvertFilter) {
                    Color pixel = image.getPixel(x, y);
                
                    int RGBValue = pixel.getRGB();
                    int invertedRGBValue = (0x00FFFFFF - (RGBValue | 0xFF000000)) | (RGBValue & 0xFF000000);

                    image.setPixel(x, y, new Color(invertedRGBValue));
                }
                
                if (filterObject instanceof SmoothFilter) {                 
                    /*Color pixel = image.getPixel(x, y);
                
                    int RGBValue = pixel.getRGB();
                    
                    
                    int averageRGBValue =  image.getPixel(x-1, y-1) + image.getPixel(x, y-1) + image.getPixel(x+1, y-1) + image.getPixel(x-1, y) + image.getPixel(x, y) + image.getPixel(x+1, y)
                                            + image.getPixel(x-1, y+1) + image.getPixel(x, y+1) + image.getPixel(x+1, y+1)/ 9
                    
                    QFImage imageCopy = image;
                    
                    
                    */
                }
            }
        }
    }

}
