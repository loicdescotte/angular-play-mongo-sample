package controllers

import scala.concurrent.Future

import models._
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.mvc.BodyParsers._
import reactivemongo.api._
import play.api.Logger

import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Application extends Controller with MongoController {
 
  def collection: JSONCollection = db.collection[JSONCollection]("postits")
  
  val index = {
    val result = Ok(views.html.index())
    Action(result)
  }

  def findAll = Action.async {
    val postits = collection.
      find(Json.obj()).
      // sort them by creation date
      sort(Json.obj("created" -> -1)).
      // perform the query and get a cursor of JsObject
      cursor[Postit]
      .collect[List]()
      
    postits.map { postits =>
      Ok(Json.toJson(postits))
    }
  }

  def insert = Action.async(parse.json) { request =>
    request.body.validate[Postit].map { postit =>
      collection.insert(postit).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Ok(Json.toJson(postit))
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def update(id: Int) = Action.async(parse.json) { request =>
    Future.successful(Ok(""))
  }

  def delete(id: Int) = Action {
      Ok("Delete ok")
  }

}
