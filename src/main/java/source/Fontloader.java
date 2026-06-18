package source;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class Fontloader {
    public static Font loadFont(String fontFileName, float fontSize){
        try
        {
            File file = new File(fontFileName);
            FileInputStream aixing = new FileInputStream(file);
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
            Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
            aixing.close();
            return dynamicFontPt;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new Font("微软雅黑", Font.PLAIN, 12);
        }
    }
    public static Font CreateFonts(String name, float size){
        String root=System.getProperty("user.dir");
        Font font = loadFont(root + "/fonts/" + name + ".ttf", size);
        return font;//返回字体
    }
}
