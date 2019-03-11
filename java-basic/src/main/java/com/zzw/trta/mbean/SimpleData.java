package com.zzw.trta.mbean;

import javax.management.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhaozhiwei
 * @date 2019/3/11 15:30
 */
public class SimpleData extends NotificationBroadcasterSupport implements SimpleDataMBean, NotificationFilter, NotificationListener {

    private static final long serialVersionUID = -807949226449761353L;
    private String data;

    private AtomicLong sequenceNumber = new AtomicLong();

    public SimpleData() {
        this.addNotificationListener(this, this, null);
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(String data) {
        String oldValue = this.data;
        this.data = data;
        Notification notification = new AttributeChangeNotification(this, sequenceNumber.incrementAndGet(), System.currentTimeMillis(),
                "simple data has changed from " + oldValue + " to" + data, "data", String.class.getName(), oldValue, data);
        sendNotification(notification);
    }

    @Override
    public String displayData() {
        return data;
    }

    @Override
    public boolean isNotificationEnabled(Notification notification) {

        if (notification.getClass().isAssignableFrom(AttributeChangeNotification.class)) {
            AttributeChangeNotification attributeChangeNotification = (AttributeChangeNotification) notification;
            if (("data").equals(attributeChangeNotification.getAttributeName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        AttributeChangeNotification attributeChangeNotification = (AttributeChangeNotification) notification;
        String oldValue = (String) attributeChangeNotification.getOldValue();
        String newValue = (String) attributeChangeNotification.getNewValue();
        System.out.println("simple data has changed from " + oldValue + "to " + newValue);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {

        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(new String[]{AttributeChangeNotification.ATTRIBUTE_CHANGE}, "Data Change Notification",
                        "Notify Data Change...")
        };
    }

}
