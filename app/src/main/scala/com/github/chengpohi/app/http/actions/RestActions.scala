package com.github.chengpohi.app.http.actions

import org.jboss.netty.handler.codec.http.HttpMethod
import org.jboss.netty.handler.codec.http.HttpMethod._
/**
 * secer
 * Created by chengpohi on 3/19/16.
 */
object RestActions {
  type ACTION_TYPE = (Any, Manifest[_])

  var getActions = scala.collection.mutable.Map[String, ACTION_TYPE]()
  var postActions = scala.collection.mutable.Map[String, ACTION_TYPE]()
  var putActions = scala.collection.mutable.Map[String, ACTION_TYPE]()
  var deleteActions = scala.collection.mutable.Map[String, ACTION_TYPE]()

  def registerHandler[A](method: HttpMethod, path: String, f: A => Any)(implicit mf: Manifest[A]): Unit = {
    method match {
      case GET => getActions += path ->(f, mf)
      case PUT => putActions += path ->(f, mf)
      case DELETE => deleteActions += path ->(f, mf)
      case POST => postActions += path ->(f, mf)
      case _ =>
    }
  }
}
