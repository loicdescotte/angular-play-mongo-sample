package models

import reactivemongo.bson._
import play.api.libs.json.Json
import scala.collection.immutable.TreeMap

case class Postit(text: String,
                  star: Boolean = false)


object Postit {
  import play.api.libs.json.Json
  import play.api.data._
  implicit val feedFormat = Json.format[Postit]
}