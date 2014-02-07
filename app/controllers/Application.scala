package controllers

import scala.concurrent.Future
import models._
import play.api.libs.json.Json
import play.api.libs.json.Json._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.mvc.BodyParsers._
import reactivemongo.api._
import play.api.Logger
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.modules.reactivemongo.json.BSONFormats._
import reactivemongo.bson.BSONObjectID

object Application extends Controller with MongoController {

  def collection: JSONCollection = db.collection[JSONCollection]("postits")

  val index = {
    val result = Ok(views.html.index())
    Action(result)
  }

  def findAll = Action.async {
    val postits = collection.find(Json.obj())
      .cursor[Postit]
      .collect[List]()

    postits.map { postits =>
      Ok(toJson(postits))
    }
  }

  def insert = Action.async(parse.json) { request =>
    request.body.validate[Postit].map { postit =>
      collection.insert(postit).map { status =>
        Ok(toJson(postit))
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def update(id: String) = Action.async(parse.json) { request =>
    val text = (request.body \ "text").as[String]
    val star = (request.body \ "star").as[Boolean]
    collection.update(
      Json.obj("_id" -> BSONObjectID(id)),
      Json.obj("$set" -> Json.obj("text" -> text, "star" -> star))).map { status =>
        Ok(toJson(request.body))
      }

  }

  def delete(id: String) = Action.async {
    collection.remove(Json.obj("_id" -> BSONObjectID(id))).map { status =>
      Ok("Postit deleted")
    }
  }

}
