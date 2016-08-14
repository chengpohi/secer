package com.github.chengpohi.dsl

import com.github.chengpohi.model.User
import org.openqa.selenium.{By, WebDriver}

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
class WebDSL(env: {val webDriver: WebDriver}) extends WebClient {
  case object get {
    def url(u: String) = web.get(u)
  }
  case object login {
    def user(u: User) = {
      val email = web.findElement(By.id("email"))
      val password = web.findElement(By.id("pass"))
      val login = web.findElement(By.id("loginbutton"))
      email.sendKeys(u.mail)
      password.sendKeys(u.password)
      login.click()
    }
  }
  override val web: WebDriver = env.webDriver
}
