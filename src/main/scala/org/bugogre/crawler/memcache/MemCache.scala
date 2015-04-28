package org.bugogre.crawler.memcache

import akka.actor.Actor

/**
 * Created by xiachen on 4/28/15.
 */
class MemCache extends Actor{
  override def receive: Receive = {
    case str: String =>
  }
}
