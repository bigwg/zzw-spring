package com.zzw.trta.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HttpUtils 工具类
 * 具体参数参考 http://jinnianshilongnian.iteye.com/blog/2089792
 *
 * @author litianyi
 */
public enum HttpClientUtils {
    /**
     * 实例
     */
    Instance;
    private static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
    /**
     * 连接超时
     */
    private final static int CONNECTION_TIMEOUT = 5 * 1000;
    /**
     * 读数据超时
     */
    private final static int SO_TIMEOUT = 5 * 1000;
    /**
     * 最大连接数
     */
    private final static int HTTP_MAX_CONNECTIONS = 300;
    /**
     * 从连接池获取连接的超时时间
     */
    private final static int CONNECTION_REQUEST_TIMEOUT = 500;
    /**
     * 清理线程等待时间
     */
    private final static int MONITOR_WAIT_TIMEOUT = 10 * 1000;

    private PoolingHttpClientConnectionManager connManager;
    private CloseableHttpClient httpClient;
    private RequestConfig requestConfig;
    private IdleConnectionMonitorThread monitor;

    HttpClientUtils() {
        this.connManager = new PoolingHttpClientConnectionManager();
        this.connManager.setMaxTotal(HTTP_MAX_CONNECTIONS);
        this.connManager.setDefaultMaxPerRoute(connManager.getMaxTotal());
        this.requestConfig = RequestConfig.custom()
                .setSocketTimeout(SO_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .build();
    }

    public CloseableHttpClient getHttpClient() {
        if (this.httpClient == null) {
            synchronized (Instance) {
                if (this.httpClient == null) {
                    this.httpClient = HttpClients.custom().setConnectionManager(connManager)
                            .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                            //取消重试
                            .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                            .build();

                    //启动线程，10秒钟清空一次失效连接

                    monitor = new IdleConnectionMonitorThread(connManager);
                    monitor.start();

                    // JVM 停止时 停止线程池
                    Runtime.getRuntime().addShutdownHook(new Thread() {
                        @Override
                        public void run() {
                            try {
                                if (monitor != null) {
                                    monitor.shutdown();
                                }
                                if (httpClient != null) {
                                    httpClient.close();
                                }
                                LOGGER.info("httpClient 连接池已释放完成。");
                            } catch (IOException e) {
                                LOGGER.info("httpClient 连接池释放失败！");
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
        return this.httpClient;
    }

    /**
     * get请求 {@link #doGet(String, Map, Map)}
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    /**
     * get请求 {@link #doGet(String, Map, Map)}
     *
     * @param url
     * @param param
     * @return
     */
    public static String doGet(String url, Map<String, Object> param) {
        return doGet(url, param, null);
    }

    /**
     * get请求
     *
     * @param url
     * @param param
     * @param header
     * @return
     */
    public static String doGet(String url, Map<String, Object> param, Map<String, Object> header) {
        LOGGER.info("HttpClientUtils.doGet:url={},params={},header={}", url, param, header);
        if (param != null && !param.isEmpty()) {
            url = buildParams(url, param);
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(HttpClientUtils.Instance.requestConfig);
        CloseableHttpResponse response = null;
        //设置header
        if (header != null && !header.isEmpty()) {
            header.forEach((k, v) -> httpGet.addHeader(k, String.valueOf(v)));
        }
        try {
            response = HttpClientUtils.Instance.getHttpClient().execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            LOGGER.error("HttpClientUtils.doGet.error:",e);
        } finally {
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
        return null;
    }

    /**
     * post请求 {@link #doPost(String, String, Map, Map)}
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, Map<String, Object> param) {
        return doPost(null, url, param, null);
    }

    /**
     * post请求 {@link #doPost(String, String, Map, Map)}
     *
     * @param url
     * @param param
     * @param header
     * @return
     */
    public static String doPost(String url, Map<String, Object> param, Map<String, Object> header) {
        return doPost(null, url, param, header);
    }

    /**
     * post请求 {@link #doPost(String, String, Map, Map)}
     *
     * @param contentType
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String contentType, String url, Map<String, Object> param) {
        return doPost(contentType, url, param, null);
    }

    /**
     * post请求
     *
     * @param contentType
     * @param url
     * @param param
     * @param header
     * @return
     */
    public static String doPost(String contentType, String url, Map<String, Object> param, Map<String, Object> header) {
        LOGGER.info("HttpClientUtils.doPost:url={}，contentType={}，params={}，header={}", url, contentType, param, header);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(HttpClientUtils.Instance.requestConfig);
        CloseableHttpResponse response = null;
        try {
            List<NameValuePair> parameter = new ArrayList<>();
            //设置参数
            if (param != null && !param.isEmpty()) {
                param.forEach((k, v) -> parameter.add(new BasicNameValuePair(k, String.valueOf(v))));
            }
            //设置header
            if (header != null && !header.isEmpty()) {
                header.forEach((k, v) -> httpPost.addHeader(k, String.valueOf(v)));
            }
            if (contentType == null || "".equals(contentType)) {
                httpPost.setEntity(new UrlEncodedFormEntity(parameter));
            } else {
                httpPost.addHeader("Content-Type", contentType);
                httpPost.setEntity(new StringEntity(JSON.toJSONString(param)));
            }
            response = HttpClientUtils.Instance.getHttpClient().execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            LOGGER.error("HttpClientUtils.doPost.error!",e);
        } finally {
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
        return null;
    }

    /**
     * 发送application/json请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String postJson(String url, String param) {
        LOGGER.info("HttpClientUtils.postJson:url={}，param={}", url, param);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(HttpClientUtils.Instance.requestConfig);
        CloseableHttpResponse response = null;
        try {
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(param));
            response = HttpClientUtils.Instance.getHttpClient().execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }catch (Exception e) {
            LOGGER.error("HttpClientUtils.postJson.error!",e);
        } finally {
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
        return null;
    }


    /**
     * 对URL中的请求参数UTF-8编码
     *
     * @param value 参数值
     * @return 编码的值
     */
    public static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }
    }


    /**
     * 构建URL地址
     */
    private static String buildParams(String url, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(url);
        if (url.contains("?")) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        if (!params.isEmpty()) {
            for (Map.Entry<String, Object> ent : params.entrySet()) {
                if (ent.getValue() != null && ent.getValue() instanceof Collection) {
                    Collection coll = (Collection) ent.getValue();
                    for (Object v : coll) {
                        appendParam(sb, ent.getKey(), String.valueOf(v));
                    }
                } else {
                    appendParam(sb, ent.getKey(), urlEncode(String.valueOf(ent.getValue())));
                }
            }

        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private static void appendParam(StringBuilder sb, String name, Object paramValue) {
        sb.append(name).append("=").append(paramValue == null ? "" : paramValue).append("&");
    }

    /**
     * 连接管理器清空失效连接和过长连接
     */
    static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager manager;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.manager = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    synchronized (this) {
                        wait(MONITOR_WAIT_TIMEOUT);
                        // 关闭失效连接
                        manager.closeExpiredConnections();
                        //关闭空闲超过10秒的连接
                        manager.closeIdleConnections(MONITOR_WAIT_TIMEOUT, TimeUnit.MILLISECONDS);
                        LOGGER.info("IdleConnectionMonitorThread 清理失效连接...");
                    }
                }
                LOGGER.info("IdleConnectionMonitorThread 正在退出清理线程...");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        public void shutdown() {
            this.interrupt();
            synchronized (this) {
                notifyAll();
            }
            LOGGER.info("IdleConnectionMonitorThread 正在停止 ...");
        }
    }
}
