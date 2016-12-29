package com.github.chengpohi.so

import java.io.File

import scala.util.Try
import scala.xml.Elem

/**
  * Created by xiachen on 28/12/2016.
  */

case class Post(id: Option[Int],
                postType: Option[Int],
                acceptAnswerId: Option[Int],
                createDate: String,
                score: Option[Int],
                viewCount: Option[Int],
                body: String,
                title: String,
                tags: List[String],
                ownerId: Option[Int],
                lastEditorId: Option[Int],
                lastEditorUserName: String,
                answerCount: Option[Int],
                commentCount: Option[Int],
                favoriteCount: Option[Int],
                communityOwnedDate: String
               ) {
  val HOSTNAME = "https://stackoverflow.com"

  def url: String = {
    val urlTitile = title.replaceAll("['|?]+", "").replaceAll("\\s+", "-").toLowerCase
    HOSTNAME + "/questions/" + id.getOrElse("") + "/" + urlTitile
  }
}

class SOExtractor {
  val ROW_PREFIX = "<row"

  implicit def asInt(str: String): Int = {
    Integer.parseInt(str)
  }

  implicit def optInt(str: String): Option[Int] = {
    Try(Integer.parseInt(str)).toOption
  }

  def extract(file: File): Stream[Post] = {
    scala.io.Source.fromFile(file).getLines().toStream.filter(_.trim.startsWith(ROW_PREFIX)).map(str => {
      val e = scala.xml.XML.loadString(str)
      val Id = getAttributeByName(e, "Id")
      val PostTypeId = getAttributeByName(e, "PostTypeId")
      val AcceptedAnswerId = getAttributeByName(e, "AcceptedAnswerId")
      val CreationDate = getAttributeByName(e, "CreationDate")
      val Score = getAttributeByName(e, "Score")
      val ViewCount = getAttributeByName(e, "ViewCount")
      val Body = getAttributeByName(e, "Body")
      val OwnerUserId = getAttributeByName(e, "OwnerUserId")
      val LastEditorUserId = getAttributeByName(e, "LastEditorUserId")
      val LastEditorDisplayName = getAttributeByName(e, "LastEditorDisplayName")
      val LastEditDate = getAttributeByName(e, "LastEditDate")
      val LastActivityDate = getAttributeByName(e, "LastActivityDate")
      val Title = getAttributeByName(e, "Title")
      val Tags = getAttributeByName(e, "Tags")
      val AnswerCount = getAttributeByName(e, "AnswerCount")
      val CommentCount = getAttributeByName(e, "CommentCount")
      val FavoriteCount = getAttributeByName(e, "FavoriteCount")
      val CommunityOwnedDate = getAttributeByName(e, "CommunityOwnedDate")
      Post(Id,
        PostTypeId,
        AcceptedAnswerId,
        CreationDate,
        Score,
        ViewCount,
        Body,
        Title,
        Try(Tags.replaceAll(">", " ").replaceAll("<", "").trim.split(" ").toList).getOrElse(List()),
        OwnerUserId,
        LastEditorUserId,
        LastEditorDisplayName,
        AnswerCount,
        CommentCount,
        FavoriteCount,
        CommunityOwnedDate
      )
    })
  }

  def getAttributeByName(e: Elem, name: String): String = {
    Try(e.attribute(name).get.text).getOrElse("")
  }
}

object SOExtractor {
  def apply(): SOExtractor = new SOExtractor()
}