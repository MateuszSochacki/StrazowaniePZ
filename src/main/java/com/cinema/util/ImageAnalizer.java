package com.cinema.util;
import com.cinema.controller.ChooseSeanceController;
import com.cinema.model.MovieEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


public class ImageAnalizer {


    public List<String> getColors(MovieEntity movie) {

//        File file = new File("C:\\Users\\Andrew\\Desktop\\myImage.gif");
//        ImageInputStream is = ImageIO.createImageInputStream(file);
//

//        controller.getBufferedImage(movie.getCover());
//        Iterator iter = ImageIO.getImageReaders(is);
//
//        if (!iter.hasNext())
//        {
//            System.out.println("Cannot load the specified file "+ file);
//            System.exit(1);
//        }
//        ImageReader imageReader = (ImageReader)iter.next();
//        imageReader.setInput(is);

        ChooseSeanceController controller = new ChooseSeanceController();
        BufferedImage image = controller.getBufferedImage(movie.getCover());

        int height = image.getHeight();
        int width = image.getWidth();

        Map m = new HashMap();
        for(int i=0; i < width ; i++)
        {
            for(int j=0; j < height ; j++)
            {
                int rgb = image.getRGB(i, j);
                int[] rgbArr = getRGBArr(rgb);
                // Filter out grays....
                if (!isGray(rgbArr)) {
                    Integer counter = (Integer) m.get(rgb);
                    if (counter == null)
                        counter = 0;
                    counter++;
                    m.put(rgb, counter);
                }
            }
        }

        String colourHex = getMostCommonColour(m);

        System.out.println(colourHex);
        List<String> colors = new ArrayList<>();
        colors.add(createPrimaryColor(colourHex));
        colors.add(createPrimaryDarkColor(colourHex));
        colors.add(createAccentColor(colourHex));
        return colors;
    }

    private String createAccentColor(String colourHex) {
        String[] list = new String[3];
        list = colourHex.split(" ");
        for (int i=0; i<list.length; i++) {
            if(list[i].length() == 1)
            {
                list[i] = "0"+list[i];
            }
        }
        int r = Integer.parseInt(list[0],16);
        int g = Integer.parseInt(list[1],16);
        int b = Integer.parseInt(list[2],16);

        if(r < 70)
            r=r+70;
        if(g < 70)
            g=g+70;
        if(b < 70)
            b+=70;

        Color color = new Color(g, r, b);

        String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        return hex;
    }

    private String createPrimaryDarkColor(String colourHex) {
        String[] list = new String[3];
        list = colourHex.split(" ");
        for (int i=0; i<list.length; i++) {
            if(list[i].length() == 1)
            {
                list[i] = "0"+list[i];
            }
        }



        int r = Integer.parseInt(list[0],16);
        int g = Integer.parseInt(list[1],16);
        int b = Integer.parseInt(list[2],16);

        if(r < 70)
        {
            r=r+70;
        }
        if(g < 70)
        {
            g=g+70;
        }
        if(b < 70)
        {
            b+=70;
        }

        Color color = new Color(r, g, b);
        color = color.darker();
        String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        return hex;
    }


    private String createPrimaryColor(String colourHex) {
        String[] list = new String[3];
        String hexValue ="#";
        list = colourHex.split(" ");
        for (String s: list) {
            if(s.length() == 1)
                s = "0"+s;
            hexValue += s;
        }
        return hexValue;
    }


    public static String getMostCommonColour(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map.Entry me = (Map.Entry )list.get(list.size()-1);
        int[] rgb= getRGBArr((Integer)me.getKey());
        return Integer.toHexString(rgb[0])+" "+Integer.toHexString(rgb[1])+" "+Integer.toHexString(rgb[2]);
    }

    public static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red,green,blue};

    }
    public static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance)
            if (rbDiff > tolerance || rbDiff < -tolerance) {
                return false;
            }
        return true;
    }
}