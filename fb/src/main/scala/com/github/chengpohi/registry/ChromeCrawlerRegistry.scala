package com.github.chengpohi.registry

import java.util.concurrent.TimeUnit

import com.github.chengpohi.config.Config
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.remote.DesiredCapabilities

import scala.collection.JavaConverters._

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
trait Initialize {
  val path: String = this.getClass.getResource("/chromedriver").getPath
  System.setProperty("webdriver.chrome.driver", path)
}

object ChromeCrawlerRegistry extends Initialize {
  val webDriver: WebDriver = {
    val mobile = Map("deviceName" -> "Google Nexus 5")
    val options: ChromeOptions = new ChromeOptions()
    options.addArguments("--disable-notifications")
    options.addArguments("test-type")
    options.addArguments("start-maximized")
    options.addArguments("user-data-dir=./temp/")
    options.setExperimentalOptions("mobileEmulation", mobile.asJava)

    val capabilities = DesiredCapabilities.chrome()
    capabilities.setCapability(ChromeOptions.CAPABILITY, options)

    val driver: ChromeDriver = new ChromeDriver(capabilities)
    driver.manage().timeouts().pageLoadTimeout(Config.MAX_LOAD_TIME, TimeUnit.SECONDS)
    driver.manage().timeouts().setScriptTimeout(Config.MAX_SCRIPT_TIME, TimeUnit.SECONDS)
    driver
  }
}
