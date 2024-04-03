package indi.nightfish.potato_ip_display.http

import java.io.*
import java.net.HttpURLConnection
import java.net.URL

object HttpURLConnectionUtil {
    /**
     * Http get请求
     * @param httpUrl 连接
     * @return 响应数据
     */
    fun get(httpUrl: String?): String {
        //链接
        var connection: HttpURLConnection? = null
        var `is`: InputStream? = null
        var br: BufferedReader? = null
        val result = StringBuffer()
        try {
            //创建连接
            val url = URL(httpUrl)
            connection = url.openConnection() as HttpURLConnection
            //设置请求方式
            connection.setRequestMethod("GET")
            //设置连接超时时间
            connection.setReadTimeout(15000)
            //开始连接
            connection.connect()
            //获取响应数据
            if (connection.getResponseCode() == 200) {
                //获取返回的数据
                `is` = connection.inputStream
                if (null != `is`) {
                    br = BufferedReader(InputStreamReader(`is`, "UTF-8"))
                    var temp: String?
                    while (null != br.readLine().also { temp = it }) {
                        result.append(temp)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (null != br) {
                try {
                    br.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (null != `is`) {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //关闭远程连接
            connection!!.disconnect()
        }
        return result.toString()
    }
}

