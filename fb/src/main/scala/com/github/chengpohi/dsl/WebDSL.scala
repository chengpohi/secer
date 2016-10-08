package com.github.chengpohi.dsl

import com.github.chengpohi.model.User
import org.openqa.selenium.{By, WebDriver}

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
class WebDSL(env: {val webDriver: WebDriver}) extends WebClient {
  case object crawl {
  }

  case object login {
    def url(u: String) = LoginDefinition(u)
  }

  case class LoginDefinition(url: String) {
    def user(u: User) = {
      web.get(url)
      web.findElement(By.id("email")).sendKeys(u.mail)
      web.findElement(By.id("pass")).sendKeys(u.password)
    }
  }
  override val web: WebDriver = env.webDriver
}
