package models

import play.api.libs.json.Json
import scala.collection.mutable.Map

case class Postit(text: String,
                  star: Boolean = false,
                  var id: Option[Int] = None)

object Postit  {
  implicit val formatPostit = Json.format[Postit]
}

object Postits {
  //fake db
  var db: Map[Int, Postit] = Map()
  var sequenceIndex = 1

  def all = db.values

  def insert(postit: Postit) = {
    sequenceIndex += 1
    postit.id = Some(sequenceIndex)
    val entry = (sequenceIndex, postit)
    db += entry
  }

  def update(id: Int, postit: Postit) = {
    val entry = (postit.id.getOrElse(-1), postit)
    db += entry
  }

  def delete(id: Int) = db -= id
}