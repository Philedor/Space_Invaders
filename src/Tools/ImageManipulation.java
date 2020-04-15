package Tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;

public class ImageManipulation {

    public static Image flipHorizontally(String location) {
        Image image = new Image() {
            @Override
            public int getWidth(ImageObserver imageObserver) {
                return 0;
            }

            @Override
            public int getHeight(ImageObserver imageObserver) {
                return 0;
            }

            @Override
            public ImageProducer getSource() {
                return null;
            }

            @Override
            public Graphics getGraphics() {
                return null;
            }

            @Override
            public Object getProperty(String s, ImageObserver imageObserver) {
                return null;
            }
        };

        try {
            BufferedImage original = ImageIO.read(new File(location));
            BufferedImage flipped = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

            for(int y = 0; y < original.getHeight(); y++) {
                for(int x = 0; x < original.getWidth(); x++) {
                    flipped.setRGB(x, y, original.getRGB(x, original.getHeight()-y-1));
                }
            }
            image = flipped;
        } catch (Exception e) {
            System.err.println(e);
        }

        return image;
    }
}
