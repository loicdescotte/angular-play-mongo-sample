package models

import play.api.libs.json.Json
import scala.collection.immutable.TreeMap

case class Postit(id: Long,
                  text: String,
                  star: Boolean = false)


object JsonFormats {
  import play.api.libs.json.Json
  import play.api.data._
  implicit val feedFormat = Json.format[Postit]
}