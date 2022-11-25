package utils;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static Color stringRgbToColor(String rgbString) {
        Pattern c = Pattern.compile("\\( *([0-9]+); *([0-9]+); *([0-9]+) *\\)");
        Matcher m = c.matcher(rgbString);

        if (m.matches())
        {
            return new Color(Integer.parseInt(m.group(1)),  // r
                    Integer.parseInt(m.group(2)),  // g
                    Integer.parseInt(m.group(3))); // b
        }

        return null;
    }
}
