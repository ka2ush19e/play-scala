package dao

import javax.inject.Inject

import scala.concurrent.Future

import models.Dog
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.lifted.ProvenShape

class DogDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val Dogs = TableQuery[DogsTable]

  def list(): Future[List[Dog]] = db.run(Dogs.result).map(_.toList)

  def get(id: Int): Future[Dog] = db.run(Dogs.filter(d => d.id === id.bind).result.head)

  def insert(dog: Dog): Future[Unit] = db.run(Dogs += dog).map(_ => ())

  def update(dog: Dog): Future[Unit] = db.run(Dogs.filter(d => d.id === dog.id.get.bind).update(dog)).map(_ => ())

  def delete(id: Int): Future[Unit] = db.run(Dogs.filter(d => d.id === id.bind).delete).map(_ => ())

  private class DogsTable(tag: Tag) extends Table[Dog](tag, "Dog") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def favoriteFood = column[String]("favorite_food")
    override def * : ProvenShape[Dog] = (id.?, name, favoriteFood) <>(Dog.tupled, Dog.unapply)
  }
}
