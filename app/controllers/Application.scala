package controllers

import scala.concurrent.Future

import models._
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.mvc.BodyParsers._
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

  def findAll = Action { 
  	Ok("")
  }

  def insert = Action.async(parse.json) { request =>
    request.body.validate[Postit].map { postit =>
      collection.insert(postit).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Ok(toJson(postit))
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def update(id: Int) = Action.async(parse.json) { request =>
      val cursor: Cursor[Postit] = collection.
      // find all people with name `name`
      find(Json.obj("id" -> id)).
      // perform the query and get a cursor of JsObject
      cursor[Postit]
      // gather all the JsObjects in a list
    val futurePostitList: Future[List[Postit]] = cursor.collect[List]()

    // everything's ok! Let's reply with the array
    futurePostitList.map { postit =>

      collection.save(postit).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        postit
      }
    }
  }

  def delete(id: Int) = Action { 
      Postits.delete(id)
      Ok("Delete ok")
  }

}
