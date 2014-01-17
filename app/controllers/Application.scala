package controllers

import models._
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.mvc.BodyParsers._
import play.api.Logger

object Application {
 
  def index = Action {
    Ok(views.html.index())
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
      	Logger.error("Error : " + JsError.toFlatJson(e))      	
      	Logger.error("Request : " + request.body)
      	BadRequest("Bad request")
      }
    }
  }
}
