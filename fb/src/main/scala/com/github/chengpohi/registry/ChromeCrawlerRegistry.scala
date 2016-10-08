package com.github.chengpohi.registry

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
trait Initialize {
  val path: String = this.getClass.getResource("/chromedriver").getPath
  System.setProperty("webdriver.chrome.driver", path)
}

object ChromeCrawlerRegistry extends Initialize {
  private val options: ChromeOptions = new ChromeOptions()
  val webDriver: WebDriver = {
    options.addArguments("--disable-notifications")
    new ChromeDriver(options)
  }
}
