package com.github.chengpohi.dsl

import com.github.chengpohi.model.User
import org.openqa.selenium.{By, JavascriptExecutor, WebDriver}

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
class WebDSL(env: {val webDriver: WebDriver}) extends WebClient {
  case object crawl {
  }

  case object go {
    def to(u: String): Unit = web.get(u)
  }

  case object scroll {
    def down(s: Int): Unit =
      web.asInstanceOf[JavascriptExecutor].executeScript(s"window.scrollBy(0,$s)", "")
    def up(s: Int): Unit =
      web.asInstanceOf[JavascriptExecutor].executeScript(s"window.scrollBy(0,-$s)", "")
  }

  case object login {
    def url(u: String): LoginDefinition = LoginDefinition(u)
  }

  case class LoginDefinition(url: String) {
    def user(u: User): Unit = {
      web.get(url)
      try {
        web.findElement(By.id("email")).sendKeys(u.mail)
        web.findElement(By.id("pass")).sendKeys(u.password)
      } catch {
        case e: Exception =>
      }
    }
  }
  override val web: WebDriver = env.webDriver
}
