package utils;

import java.awt.*;
import java.lang.reflect.Field;

public class Utils {

    public static Color stringToColor(String colorString) {
        Color color;
        try {
            Field field = Color.class.getField(colorString);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = null; // Not defined
        }
        return color;
    }

}
