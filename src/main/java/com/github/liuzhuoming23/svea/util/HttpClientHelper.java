package com.github.liuzhuoming23.svea.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http请求类
 *
 * @author liuzhuoming
 */
public class HttpClientHelper {

    /**
     * 发送有参http get请求并获取返回值
     *
     * @param url 请求路径
     * @param params 请求参数集
     * @return 返回值字符串
     */
    public static String get(String url, Map<String, String> params) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder urlBuilder = new URIBuilder(url.trim());
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    urlBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            URI uri = urlBuilder.build();
            HttpGet httpGet = new HttpGet(uri);
            return getResponse(httpClient, httpGet, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送无参http get请求并获取返回值
     *
     * @param url 请求路径
     * @return 返回值字符串
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 发送有参http post请求并获取返回值
     *
     * @param url 请求路径
     * @param params 请求参数集
     * @return 返回值字符串
     */
    public static String post(String url, Map<String, String> params) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            if (params != null) {
                List<NameValuePair> parameters = new ArrayList<>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
                httpPost.setEntity(formEntity);
            }
            return getResponse(httpClient, httpPost, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送无参http post请求并获取返回值
     *
     * @param url 请求路径
     * @return 返回值字符串
     */
    public static String post(String url) {
        return post(url, null);
    }

    /**
     * 解析请求返回值
     *
     * @param httpClient CloseableHttpClient
     * @param request 请求信息
     * @param params 请求参数集合
     */
    private static String getResponse(CloseableHttpClient httpClient, HttpUriRequest request,
        Map<String, String> params) {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            URI uri = request.getURI();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                throw new RuntimeException(
                    response.getStatusLine().getStatusCode() + " | " + uri.getScheme() + "://" + uri
                        .getAuthority() + uri.getPath() + "?" + com.github.liuzhuoming23.svea.util.QueryParamsUtil
                        .generateParamsStringWithSortAndNotUrlEncodeAndNotNullable(params));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
