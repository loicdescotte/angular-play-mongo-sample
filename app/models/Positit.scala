package models

import reactivemongo.bson._
import reactivemongo.bson.DefaultBSONHandlers._
import play.modules.reactivemongo.json.BSONFormats._

case class Postit(_id: Option[BSONObjectID],
                  text: String,
                  star: Boolean = false)

object Postit {
  import play.api.libs.json.Json
  import play.api.data._
  implicit val feedFormat = Json.format[Postit]
}