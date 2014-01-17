package controllers

import models._
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.mvc.BodyParsers._
import play.api.Logger

object Application {
 
  def index = Action {
    Ok(views.html.index("ok"))
  }

  def insert = Action(parse.json) { request =>
  	Logger.info("request "+request )
  	Logger.info("request body "+request.body )
    request.body.validate[Postit].map{ 
      case (p) => Ok("Hello " + p.text + ", you're "+p.id)
    }.recoverTotal{
      e => BadRequest("Detected error:"+ JsError.toFlatJson(e))
    }
  }

  def findAll = Action { 
  	Ok(Json.toJson(Postits.all))
  }
}
