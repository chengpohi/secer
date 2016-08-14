package com.github.chengpohi.registry

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
trait Initialize {
  val path: String = this.getClass.getResource("/chromedriver").getPath
  System.setProperty("webdriver.chrome.driver", path)
}

object ChromeCrawlerRegistry extends Initialize {
  val webDriver: WebDriver = new ChromeDriver
}
