package com.knoldus

import scala.concurrent.Future
import scala.io.StdIn

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.knoldus.database.mysql.{JdbcConnection, User}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

/**
 * Created by manjot on 6/6/18.
 */
object httpRouts {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher
      implicit val userFormat = jsonFormat2(User)



    val route =
      path("read") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, JdbcConnection.returnMysqlData))
        }
      } ~
      post {
        path("adduser") {
          entity(as[User]) { user =>
            val saved: Future[Done] = JdbcConnection.addUser(user)
            onComplete(saved) { done =>
              complete("user added")
            }
          }
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}
