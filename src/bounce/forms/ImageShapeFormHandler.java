package bounce.forms;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import bounce.ImageRectangleShape;
import bounce.NestingShape;
import bounce.Shape;
import bounce.ShapeModel;
import bounce.forms.util.Form;
import bounce.forms.util.FormHandler;

public class ImageShapeFormHandler implements FormHandler {

    private ShapeModel _model;
    private NestingShape _parentOfNewShape;


    public ImageShapeFormHandler(ShapeModel model, NestingShape parent) {
        _model = model;
        _parentOfNewShape = parent;
    }

    @Override
    public void processForm(Form form) {

        long startTime = System.currentTimeMillis();
        //create the SwingWorker to handle the image loading event
        SwingWorker<Shape, Void> worker = new SwingWorker<Shape, Void>() {

            @Override
            protected Shape doInBackground() throws Exception {
                // Read field values from the form.
                File imageFile = (File) form.getFieldValue(File.class, ImageFormElement.IMAGE);
                int width = form.getFieldValue(Integer.class, ShapeFormElement.WIDTH);
                int deltaX = form.getFieldValue(Integer.class, ShapeFormElement.DELTA_X);
                int deltaY = form.getFieldValue(Integer.class, ShapeFormElement.DELTA_Y);

                // Load the original image (ImageIO.read() is a blocking call).
                BufferedImage fullImage = null;
                try {
                    fullImage = ImageIO.read(imageFile);
                } catch (
                        IOException | NullPointerException e) {
                    System.out.println("Error loading image.");
                }

                int fullImageWidth = fullImage.getWidth();
                int fullImageHeight = fullImage.getHeight();

                BufferedImage scaledImage = fullImage;

                // Scale the image if necessary.
                if (fullImageWidth > width) {
                    double scaleFactor = (double) width / (double) fullImageWidth;
                    int height = (int) ((double) fullImageHeight * scaleFactor);

                    scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = scaledImage.createGraphics();

                    // Method drawImage() scales an already loaded image. The
                    // ImageObserver argument is null because we don't need to monitor
                    // the scaling operation.
                    g.drawImage(fullImage, 0, 0, width, height, null);
                }
                // Create the new Shape and add it to the model.
                ImageRectangleShape imageShape = new ImageRectangleShape(deltaX, deltaY, scaledImage);
                _model.add(imageShape, _parentOfNewShape);
                return imageShape;
            }

            @Override
            protected void done() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                System.out.println("Image loading ans scaling took " + elapsedTime + "ms.");
            }
        };
        //execute the worker
        worker.execute();
    }
}


