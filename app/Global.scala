import play.api.GlobalSettings

import models._
import play.api.Play.current
import play.api.Application
import play.api.Logger


object Global extends GlobalSettings {

  override def onStart(app: Application) {
      Logger.info("init data")
      Postits.insert(Postit("Hey!", false))
      Postits.insert(Postit("TODO", true))
  }
}