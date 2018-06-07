package com.knoldus

import scala.concurrent.Future

import akka.Done
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import com.knoldus.database.mysql.{Number, User, UserImpl}
import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat2, _}


/**
 * Created by manjot on 7/6/18.
 */
trait RestService {
  //implicit val system = ActorSystem()
  //implicit val materializer = ActorMaterializer()
  implicit val userFormat = jsonFormat2(User)
  implicit val numberFormat = jsonFormat1(Number)
  val userImpl = new UserImpl

  val route =
    path("read") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, userImpl.returnMysqlData))
      }
    } ~
    post {
      path("adduser") {
        entity(as[User]) { user =>
          val saved: Future[Done] = userImpl.addUser(user)
          onComplete(saved) { done =>
            complete("user added")
          }
        }
      }
    } ~
    put {
      path("updateuser") {
        entity(as[User]) { user =>
          val saved: Future[Done] = userImpl.updateUser(user)
          onComplete(saved) { done =>
            println("user Updated ---> req completed")
            complete("user updated")
          }
        }
      }
    } ~
    delete {
      path("deleteuser") {
        entity(as[Number]) { id =>
          val saved: Future[Done] = userImpl.deleteUser(id)
          onComplete(saved) { done =>
            println("user deleted ---> req completed")
            complete("user deleted")
          }
        }
      }
    }



}
