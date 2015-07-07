package models

import slick.driver.MySQLDriver.api._
import slick.lifted.ProvenShape

case class Dog(id: Int, name: String, favoriteFood: String)

class DogsTable(tag: Tag) extends Table[Dog](tag, "Dog") {
  def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name")
  def favoriteFood = column[String]("favorite_food")
  override def * : ProvenShape[Dog] = (id, name, favoriteFood) <> (Dog.tupled, Dog.unapply)
}
