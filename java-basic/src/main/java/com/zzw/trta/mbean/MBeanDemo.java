package com.zzw.trta.mbean;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @author zhaozhiwei
 * @date 2019/3/11 16:10
 */
public class MBeanDemo {

    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        SimpleData simpleData = new SimpleData();
        ObjectName objectName = createObjectName(simpleData);
        mBeanServer.registerMBean(simpleData, objectName);
        Thread.sleep(Long.MAX_VALUE);
    }

    private static ObjectName createObjectName(Object mBean) throws MalformedObjectNameException {
        Class<?> mBeanClass = mBean.getClass();
        String packageName = mBeanClass.getPackage().getName();
        String className = mBeanClass.getSimpleName();
        return new ObjectName(packageName + ":type=" + className);
    }
}
