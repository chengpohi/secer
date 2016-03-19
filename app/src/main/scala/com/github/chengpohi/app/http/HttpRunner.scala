package com.github.chengpohi.app.http

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import com.typesafe.config.ConfigFactory
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.{ChannelPipeline, ChannelPipelineFactory, Channels}
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory
import org.jboss.netty.handler.codec.http.{HttpChunkAggregator, HttpRequestDecoder, HttpResponseEncoder}
import org.jboss.netty.handler.stream.ChunkedWriteHandler
import org.slf4j.LoggerFactory

trait HttpRunner {
  lazy val config = ConfigFactory.load()

  lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  val serverBootStrap = new ServerBootstrap(new NioServerSocketChannelFactory(
      Executors.newCachedThreadPool(),
      Executors.newCachedThreadPool()
    ))

  def start() = {
    this.registerPath()

    serverBootStrap.setPipelineFactory(new ChannelPipelineFactory() {
      override def getPipeline: ChannelPipeline = {
        val pipeline: ChannelPipeline = Channels.pipeline()
        pipeline.addLast("decoder", new HttpRequestDecoder())
        pipeline.addLast("aggregator", new HttpChunkAggregator(65536))
        pipeline.addLast("encoder", new HttpResponseEncoder())
        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler())

        pipeline.addLast("handler", new RestHttpHandler())
        pipeline
      }
    })

    serverBootStrap.setOption("child.reuseAddress", true)
    serverBootStrap.setOption("child.tcpNoDelay", true)
    serverBootStrap.setOption("child.keepAlive", true)

    // Bind and start to accept incoming connections.
    serverBootStrap.bind(new InetSocketAddress(config.getInt("http.port")))
    LOG.info("Server Started, IP: " + config.getString("http.interface") + ", Port: " + config.getInt("http.port"))
  }

  def shutdown(): Unit = {
    serverBootStrap.shutdown()
  }

  def registerPath(): Unit = ???
}
