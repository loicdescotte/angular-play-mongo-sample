package controllers

import models._
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.mvc.BodyParsers._
import play.api.Logger

object Application {
 
  val index = {
    val result = Ok(views.html.index())
    Action(result)
  }

  def findAll = Action { 
  	Ok(Json.toJson(Postits.all))
  }

  def insert = Action(parse.json) { request =>
    request.body.validate[Postit].map{ 
      case (postit) => {
      	Postits.insert(postit)
      	Ok("Insert ok")
      }
    }.recoverTotal{
      e => {      
        val error = JsError.toFlatJson(e)	
      	Logger.error("Error : " + error)      	
      	Logger.error("Request : " + request.body)
      	BadRequest(error)
      }
    }
  }

  def update(id: Int) = Action(parse.json) { request =>
    request.body.validate[Postit].map{ 
      case (postit) => {
        Postits.update(id, postit)
        Ok(Json.toJson(postit))
      }
    }.recoverTotal{
      e => {      
        val error = JsError.toFlatJson(e) 
        Logger.error("Error : " + error)        
        Logger.error("Request : " + request.body)
        BadRequest(error)
      }
    }
  }

  def delete(id: Int) = Action { 
      Postits.delete(id)
      Ok("Delete ok")
  }

}
