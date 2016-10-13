import com.github.chengpohi.config.Config
import com.github.chengpohi.dsl.WebDSL
import com.github.chengpohi.registry.ChromeCrawlerRegistry

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
object FBCrawler extends WebDSL(ChromeCrawlerRegistry){
  val SLEEP_TIME: Long = 3000
  def main(args: Array[String]): Unit = {
    login url "https://www.facebook.com/" user Config.users.head
    go to "https://m.facebook.com"
    while(true) {
      Thread.sleep(SLEEP_TIME)
      println("scroll down")
      scroll down 800
    }
  }
}
