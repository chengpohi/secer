package com.github.chengpohi.so

import java.io.File

import com.github.chengpohi.model.IndexTrait
import com.google.common.io.BaseEncoding

import scala.util.Try
import scala.xml.Elem

/**
  * Created by xiachen on 28/12/2016.
  */
case class Post(Id: Int,
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
               ) extends IndexTrait {
  private val HOSTNAME = "https://stackoverflow.com"

  override def doc: Map[String, Any] = {
    this.getClass.getDeclaredFields.toList.filter(!_.getName.equalsIgnoreCase("HOSTNAME")).map(i => i.getName -> i.get(this)).toMap
  }

  override def indexName: String = "so"

  override def indexType: String = tags.isEmpty match {
    case true => "universal"
    case false => BaseEncoding.base64().encode(tags.head.getBytes("UTF-8"))
  }

  override def id: String = Id.toString

  def url: String = {
    val urlTitle = title.replaceAll("['|?]", "").replaceAll("\\s+", "-").toLowerCase
    HOSTNAME + "/questions/" + Id + "/" + urlTitle
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
      val tags = Try(Tags.replaceAll(">", " ").replaceAll("<", "").trim.split(" ").toList).getOrElse(List())
      Post(Id,
        PostTypeId,
        AcceptedAnswerId,
        CreationDate,
        Score,
        ViewCount,
        Body,
        Title,
        tags,
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
    val res = Try(e.attribute(name).get.text).getOrElse("")
    res.isEmpty match {
      case true => null
      case false => res
    }
  }
}

object SOExtractor {
  def apply(): SOExtractor = new SOExtractor()
}
