import play.api._

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    Logger.info("Starting...")
    Logger.info(s"  Mode: ${app.mode}")
    Logger.info(s"  Path: ${app.path}")
  }

  override def onStop(app: Application) {
    Logger.info("Shutting down...")
  }
}
