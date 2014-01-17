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

  def all = db.values

  def insert(postit: Postit) = {
    postit.id = Some(db.size)
    val entry = (db.size, postit)
    db += entry
  }

  def update(id: Int, postit: Postit) = {
    val entry = (postit.id.getOrElse(-1), postit)
    db += entry
  }

  def delete(id: Int) = db -= id
}