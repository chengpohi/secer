package org.bugogre.crawler.url

import org.slf4j.LoggerFactory
import java.net.URL

/**
 * Created by xiachen on 1/23/15.
 */
object UrlNormalizer {
  val LOG = LoggerFactory.getLogger(getClass.getName)
  val hasNormalizablePattern = ".*\\.?\\.?.*".r
  val relativePathRule = "/[^/]*[^/.]{1}[^/]*/\\.\\.".r
  val leadingRelativePathRule = "^(/\\.\\./)+".r
  val currentPathRule = "/\\./".r
  val adjacentSlashRule = "/{2,}".r

  def normalize(urlString: String): URL = {
    if ("".equals(urlString)) // permit empty
      return new URL(urlString)

    val url = new URL(urlString.trim)

    val protocol = url.getProtocol
    var host = url.getHost
    var port = url.getPort

    var file = url.getFile

    var changed = false

    if (!urlString.startsWith(protocol)) // protocol was lowercased
      changed = true

    if ("http".equals(protocol) || "https".equals(protocol)
      || "ftp".equals(protocol)) {

      if (host != null) {
        val newHost = host.toLowerCase // lowercase host
        if (!host.equals(newHost)) {
          host = newHost
          changed = true
        }
      }

      if (port == url.getDefaultPort) {
        // uses default port
        port = -1; // so don't specify it
        changed = true
      }

      if (file == null || "".equals(file)) {
        // add a slash
        file = "/"
        changed = true
      }

      if (url.getRef != null) {
        // remove the ref
        changed = true
      }

      // check for unnecessary use of "/../"
      val file2 = substituteUnnecessaryRelativePaths(file);

      if (!file.equals(file2)) {
        changed = true
        file = file2
      }

    }

    if (changed)
      new URL(protocol, host, port, file)
    else
      new URL(urlString)
  }

  def substituteUnnecessaryRelativePaths(file: String): String = {
    file match {
      case hasNormalizablePattern(_*) =>
      case _ => return file
    }

    var fileWorkCopy = file
    var oldLen = file.length
    var newLen = oldLen - 1

    // All substitutions will be done step by step, to ensure that certain
    // constellations will be normalized, too
    //
    // For example: "/aa/bb/../../cc/../foo.html will be normalized in the
    // following manner:
    // "/aa/bb/../../cc/../foo.html"
    // "/aa/../cc/../foo.html"
    // "/cc/../foo.html"
    // "/foo.html"
    //
    // The normalization also takes care of leading "/../", which will be
    // replaced by "/", because this is a rather a sign of bad webserver
    // configuration than of a wanted link. For example, urls like
    // "http://www.foo.com/../" should return a http 404 error instead of
    // redirecting to "http://www.foo.com".
    //
    while (oldLen != newLen) {
      // substitue first occurence of "/xx/../" by "/"
      oldLen = fileWorkCopy.length
      fileWorkCopy = relativePathRule.replaceFirstIn(fileWorkCopy, "/")
      fileWorkCopy = leadingRelativePathRule.replaceFirstIn(fileWorkCopy, "/")
      fileWorkCopy = currentPathRule.replaceFirstIn(fileWorkCopy, "/")
      fileWorkCopy = adjacentSlashRule.replaceFirstIn(fileWorkCopy, "/")

      newLen = fileWorkCopy.length()
    }

    fileWorkCopy
  }
}
