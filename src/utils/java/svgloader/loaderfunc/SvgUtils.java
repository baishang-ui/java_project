package svgloader.loaderfunc;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import source.FrameMain202209160316;
import svgloader.svgfiles.Res;
import java.awt.*;
import java.net.URL;

public class SvgUtils {
    static ClassLoader classLoader = FrameMain202209160316.class.getClassLoader();
    public static FlatSVGIcon getSvg(String svgName,Color color){
        URL svgPath = Res.class.getResource(svgName+".svg");
        SvgIcon flatSVGIcon = new SvgIcon(svgName, 1,1,2F,false,classLoader,svgPath);
        flatSVGIcon.setColorFilter(new FlatSVGIcon.ColorFilter(color1 -> color));
        return flatSVGIcon;
    }
    public static FlatSVGIcon getSvg(String svgName, Color color,int width,int height){
        URL svgPath = Res.class.getResource(svgName+".svg");

        SvgIcon flatSVGIcon = new SvgIcon(svgName, width,height,1.0F,false,classLoader,svgPath);
        flatSVGIcon.setColorFilter(new FlatSVGIcon.ColorFilter(color1 -> color));
        return flatSVGIcon;
    }
    public static FlatSVGIcon getSvg(String svgName,int width,int height) {
        URL svgPath = Res.class.getResource(svgName+".svg");
        SvgIcon flatSVGIcon = new SvgIcon(svgName, width,height,1.0F,false,classLoader,svgPath);
        return flatSVGIcon;
    }
    public static SvgIcon getSvg(String svgName) {
        URL svgPath = Res.class.getResource(svgName+".svg");
        SvgIcon flatSVGIcon = new SvgIcon(svgName, 1,1,1.0F,false,classLoader,svgPath);
        return flatSVGIcon;
    }
}
