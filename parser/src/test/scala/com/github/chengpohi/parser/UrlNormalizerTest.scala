package com.github.chengpohi.parser

import org.scalatest.FlatSpec

/**
 * Created by xiachen on 3/19/15.
 */
class UrlNormalizerTest extends FlatSpec {
  def normalize(path: String): String = UrlNormalizer.normalize(path).toString

  "normalize" should " remove space" in {
    assert(normalize(" http://foo.com/ ") == "http://foo.com/")
  }

  "normalize" should " parse protocol upper case to lower case" in {
    assert(normalize("HTTP://foo.com/") == "http://foo.com/")
  }

  "normalize" should " parse all upper case to lower case" in {
    assert(normalize("http://Foo.Com/index.html") == "http://foo.com/index.html")
  }

  "normalize" should " remove default 80 port" in {
    assert(normalize("http://Foo.Com:80/index.html") == "http://foo.com/index.html")
    assert(normalize("http://Foo.Com:8080/index.html") == "http://foo.com:8080/index.html")
  }

  "normalize" should " remove ref" in {
    assert(normalize("http://foo.com/foo.html#ref") == "http://foo.com/foo.html")
  }

  "normalize" should " remove one dot" in {
    assert(normalize("http://foo.com/./foo.html") == "http://foo.com/foo.html")
  }

  "normalize" should " two dot to upper path" in {
    assert(normalize("http://foo.com/aa/../foo.html") == "http://foo.com/foo.html")
    assert(normalize("http://foo.com/aa/../") == "http://foo.com/")
    assert(normalize("http://foo.com/aa/..") == "http://foo.com/")
    assert(normalize("http://foo.com/aa/bb/cc/../../foo.html") == "http://foo.com/aa/foo.html")
    assert(normalize("http://foo.com/aa/bb/../cc/dd/../ee/foo.html") == "http://foo.com/aa/cc/ee/foo.html")
    assert(normalize("http://foo.com/../foo.html") == "http://foo.com/foo.html")
    assert(normalize("http://foo.com/../aa/../foo.html") == "http://foo.com/foo.html")
    assert(normalize("http://foo.com/aa/../../foo.html") == "http://foo.com/foo.html")
    assert(normalize("http://foo.com/aa/../bb/../foo.html/../../") == "http://foo.com/")
    assert(normalize("http://foo.com/../aa/foo.html") == "http://foo.com/aa/foo.html")
    assert(normalize("http://foo.com/../aa/../foo.html") == "http://foo.com/foo.html")
    assert(normalize("http://foo.com/a..a/foo.html") == "http://foo.com/a..a/foo.html")
    assert(normalize("http://foo.com/a..a/../foo.html") == "http://foo.com/foo.html")
    assert(normalize("http://foo.com/foo.foo/../foo.html") == "http://foo.com/foo.html")

  }

  "normalize" should " repeated slash" in {
    assert(normalize("http://foo.com//aa/bb/foo.html") == "http://foo.com/aa/bb/foo.html")
    assert(normalize("http://foo.com/aa//bb/foo.html") == "http://foo.com/aa/bb/foo.html")
    assert(normalize("http://foo.com/aa/bb//foo.html") == "http://foo.com/aa/bb/foo.html")
    assert(normalize("http://foo.com//aa//bb//foo.html") == "http://foo.com/aa/bb/foo.html")
    assert(normalize("http://foo.com////aa////bb////foo.html") == "http://foo.com/aa/bb/foo.html")
  }
}
