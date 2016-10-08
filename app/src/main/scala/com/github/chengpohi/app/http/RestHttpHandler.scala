package com.github.chengpohi.app.http

import com.github.chengpohi.app.http.ResponseWriter.writeBuffer
import com.github.chengpohi.app.http.actions.RestActions._
import org.jboss.netty.channel._
import org.jboss.netty.handler.codec.http.HttpMethod._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._
import org.jboss.netty.handler.codec.http.{HttpResponseStatus, HttpMethod, HttpRequest}
import org.jboss.netty.util.CharsetUtil
import org.json4s._
import org.json4s.native.JsonMethods._

/**
 * secer
 * Created by chengpohi on 3/19/16.
 */
class RestHttpHandler extends SimpleChannelUpstreamHandler {
  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
    val httpRequest = e.getMessage.asInstanceOf[HttpRequest]
    val httpMethod: HttpMethod = httpRequest.getMethod
    val uri: String = httpRequest.getUri

    val f: (Any, Manifest[_]) = httpMethod match {
      case GET => getActions.getOrElse(uri, null)
      case POST => postActions.getOrElse(uri, null)
      case PUT => putActions.getOrElse(uri, null)
    }

    val rawData = httpRequest.getContent.toString(CharsetUtil.UTF_8)
    def eval[A] = f._1.asInstanceOf[A => String]
    implicit val mf = f._2
    implicit val formats = org.json4s.DefaultFormats

    val response = eval(parse(rawData).extract)
    writeBuffer(ctx.getChannel, response.getBytes, OK)
  }

  @throws(classOf[Exception])
  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent): Unit = {
    e.getCause.printStackTrace()
    writeBuffer(ctx.getChannel, (e.getCause.getLocalizedMessage + "Internal Exception has occurred").getBytes, BAD_REQUEST)
  }


  override def channelDisconnected(ctx: ChannelHandlerContext, e: ChannelStateEvent): Unit = {
    super.channelDisconnected(ctx, e)
  }
}

case class HttpResponse(content: String, status: HttpResponseStatus, cookie: Option[String] = None)
