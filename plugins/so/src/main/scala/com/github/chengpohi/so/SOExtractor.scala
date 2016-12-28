package com.github.chengpohi.so

import java.io.File
import java.util.Date

import scala.util.Try
import scala.xml.Elem

/**
  * Created by xiachen on 28/12/2016.
  */

case class Post(id: Int,
                postType: Int,
                acceptAnswerId: Int,
                createDate: String,
                score: Int,
                viewCount: Int,
                body: String,
                title: String,
                tags: List[String],
                ownerId: Int,
                lastEditorId: Int,
                lastEditorUserName: String,
                answerCount: Int,
                commentCount: Int,
                favoriteCount: Int,
                communityOwnedDate: String
               )

class SOExtractor {
  val ROW_PREFIX = "<row"

  implicit def asInt(str: String): Int = {
    Integer.parseInt(str)
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
