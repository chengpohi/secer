import com.github.chengpohi.config.Config
import com.github.chengpohi.dsl.WebDSL
import com.github.chengpohi.registry.ChromeCrawlerRegistry

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
object FBCrawler extends WebDSL(ChromeCrawlerRegistry){
  def main(args: Array[String]): Unit = {
    login url "https://www.facebook.com/login.php" user Config.users.head
  }
}