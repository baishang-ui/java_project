package notify.ui.util;

import notify.Notifications;

import java.util.LinkedList;
import java.util.List;


public class NotificationHolder {
    private static List<Notifications.NotificationAnimation> lists = new LinkedList<>();
    private final Object lock = new Object();

    public int getHoldCount() {
        return lists.size();
    }
    public Notifications.NotificationAnimation getHold(Notifications.Location location) {
        synchronized (lock) {
            for (int i = 0; i < lists.size(); i++) {
                Notifications.NotificationAnimation n = lists.get(i);
                if (n.getLocation() == location) {
                    return n;
                }
            }
            return null;
        }
    }

    public void removeHold(Notifications.NotificationAnimation notificationAnimation) {
        synchronized (lock) {
            lists.remove(notificationAnimation);

        }
    }

    public void hold(Notifications.NotificationAnimation notificationAnimation) {
        synchronized (lock) {
            lists.add(notificationAnimation);

        }
    }

    public void clearHold() {
        synchronized (lock) {
            lists.clear();
        }
    }

    public void clearHold(Notifications.Location location) {
        synchronized (lock) {
            for (int i = 0; i < lists.size(); i++) {
                Notifications.NotificationAnimation n = lists.get(i);
                if (n.getLocation() == location) {
                    lists.remove(n);
                    i--;
                }
            }
        }
    }
}
