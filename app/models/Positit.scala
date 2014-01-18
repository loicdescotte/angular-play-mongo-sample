package models

import play.api.libs.json.Json
import scala.collection.immutable.TreeMap

case class Postit(text: String,
                  star: Boolean = false,
                  var id: Option[Int] = None)

object Postit  {
  implicit val formatPostit = Json.format[Postit]
}

object Postits {
  //fake db
  var db: TreeMap[Int, Postit] = TreeMap()
  var sequenceIndex = 1

  def all = db.values

  def insert(postit: Postit) = {
    sequenceIndex += 1
    postit.id = Some(sequenceIndex)
    val entry = (sequenceIndex, postit)
    db = db + entry
  }

  def update(id: Int, postit: Postit) = {
    val entry = (postit.id.getOrElse(-1), postit)
    db = db + entry
  }

  def delete(id: Int) = {
    db = db - id
  }
}