package svgloader.loaderfunc;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.net.URL;

public class SvgIcon extends FlatSVGIcon {
    protected SvgIcon(String name, int width, int height, float scale, boolean disabled, ClassLoader classLoader, URL url) {
        super(name, width, height, scale, disabled, classLoader,url);
    }
}
