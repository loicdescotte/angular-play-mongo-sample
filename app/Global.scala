import play.api.GlobalSettings
import models._
import play.api.Play.current
import play.api.Application
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson.BSONDocument
import play.api.libs.json._


object Global extends GlobalSettings {

  override def onStart(app: Application) {
      Logger.info("init data")
      controllers.Application.collection.remove(Json.obj()).map{ done => 
      	controllers.Application.collection.insert(Postit(None, "Hey!", false))
      	controllers.Application.collection.insert(Postit(None, "TODO", true))
      }
  }
}