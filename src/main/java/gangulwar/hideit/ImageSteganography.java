package gangulwar.hideit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSteganography {

    public static void hideMessage(String imagePath, String message, String outputImagePath) {
        try {

            BufferedImage image = ImageIO.read(new File(imagePath));

            encodeMessage(image, message);

            ImageIO.write(image, "png", new File(outputImagePath));

            System.out.println("Message hidden successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void encodeMessage(BufferedImage image, String message) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int imageSize = imageWidth * imageHeight;

        StringBuilder messageBinary = new StringBuilder();
        for (char c : message.toCharArray()) {
            messageBinary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        messageBinary.append("00000000");

        int messageIndex = 0;
        for (int i = 0; i < imageSize; i++) {
            int pixelX = i % imageWidth;
            int pixelY = i / imageWidth;
            int pixel = image.getRGB(pixelX, pixelY);

            int red = (pixel >> 16) & 0xFF;
            int green = (pixel >> 8) & 0xFF;
            int blue = pixel & 0xFF;

            if (messageIndex < messageBinary.length()) {
                int bit = Character.getNumericValue(messageBinary.charAt(messageIndex));

                red = (red & 0xFE) | bit;
                green = (green & 0xFE) | bit;
                blue = (blue & 0xFE) | bit;

                int modifiedPixel = (red << 16) | (green << 8) | blue;
                image.setRGB(pixelX, pixelY, modifiedPixel);

                messageIndex++;
            } else {
                break;
            }
        }
    }

    public static String retrieveMessage(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            return decodeMessage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decodeMessage(BufferedImage image) {
        StringBuilder message = new StringBuilder();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int imageSize = imageWidth * imageHeight;

        for (int i = 0; i < imageSize; i++) {
            int pixelX = i % imageWidth;
            int pixelY = i / imageWidth;
            int pixel = image.getRGB(pixelX, pixelY);

            int red = (pixel >> 16) & 0xFF;
            int green = (pixel >> 8) & 0xFF;
            int blue = pixel & 0xFF;

            int bit = (red & 0x01) & (green & 0x01) & (blue & 0x01);
            message.append(bit);

            if (message.length() >= 32) {
                String terminator = message.substring(message.length() - 32);
                if (terminator.equals("00000000000000000000000000000000")) {
                    message.delete(message.length() - 32, message.length());
                    break;
                }
            }
        }

        StringBuilder decodedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i += 8) {
            if (i + 8 <= message.length()) {
                String binaryChar = message.substring(i, i + 8);
                char decodedChar = (char) Integer.parseInt(binaryChar, 2);
                decodedMessage.append(decodedChar);
            }
        }

        return decodedMessage.toString();
    }
}
