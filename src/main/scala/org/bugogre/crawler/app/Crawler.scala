package ogre.bugogre.crawler.app

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils

/**
 * author: chengpohi@gmail.com
 */
object Crawler {
  def main(args: Array[String]) {
    var httpclient = new DefaultHttpClient()
    var httpget = new HttpGet("http://www.baidu.com")
    var response = httpclient.execute(httpget)
    var entity = response.getEntity
    println(response.getStatusLine  + " : " +  EntityUtils.toString(entity))
  }
}
