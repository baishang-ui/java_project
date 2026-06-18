package notify.ui.render;

import com.formdev.flatlaf.FlatClientProperties;
import notify.Notifications;
import notify.ToastClientProperties;

import javax.swing.*;
import java.awt.*;

import source.Fontloader;

public class ToastNotificationPanel extends JPanel {
    protected Graphics window2;
    protected JWindow window;

    //protected JLabel labelIcon;
    protected JTextPane textPane;
    protected String textPane2 = "";
    private Notifications.Type type;
    private Font pf_b = Fontloader.CreateFonts("PingFang-Bold",13);
    public ToastNotificationPanel() {
        Init();
    }

    private void installPropertyStyle() {
        String key = getKey();
        String outlineColor = toTextColor(getDefaultColor());
        String outline = convertsKey(key, "outlineColor", outlineColor);
        putClientProperty(FlatClientProperties.STYLE, "" +
                "background:" + convertsKey(key, "background", "$Panel.background") + ";" +
                "outlineColor:" + outline + ";" +
                "effectColor:" + convertsKey(key, "effectColor", outline));
    }

    private String convertsKey(String key, String value, String defaultValue) {
        return "if($Toast." + key + "." + value + ", $Toast." + key + "." + value + ", if($Toast." + value + ", $Toast." + value + ", " + defaultValue + "))";
    }

    @Override
    public void updateUI() {
        setUI(new ToastPanelUI());
        removeDialogBackground();
    }

    private void removeDialogBackground() {
        if (window != null) {
            Color bg = getBackground();
            window.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 0));
            window.setSize(getPreferredSize());
        }
    }

    private void Init() {
        textPane = new JTextPane();
        textPane.setFont(pf_b);
        textPane.setText("Hello!\nToast Notification");
        textPane.setOpaque(false);
        textPane.setFocusable(false);
        textPane.setCursor(Cursor.getDefaultCursor());
        putClientProperty(ToastClientProperties.TOAST_COMPONENT, textPane);
    }

    public void set(Notifications.Type type, String message) {
        this.type = type;
        textPane.setText(message);
        installPropertyStyle();
    }

    public void setDialog(JWindow window) {
        this.window = window;
        removeDialogBackground();
    }

    public Color getDefaultColor() {
        if (type == Notifications.Type.SUCCESS) {
            return Color.decode("#67C23A");
        } else if (type == Notifications.Type.INFO) {
            return Color.decode("#909399");
        } else if (type == Notifications.Type.WARNING) {
            return Color.decode("#E6A23C");
        } else {
            return Color.decode("#F56C6C");
        }
    }

    private String toTextColor(Color color) {
        return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
    }

    public String getKey() {
        if (type == Notifications.Type.SUCCESS) {
            return "success";
        } else if (type == Notifications.Type.INFO) {
            return "info";
        } else if (type == Notifications.Type.WARNING) {
            return "warning";
        } else {
            return "error";
        }
    }
}
