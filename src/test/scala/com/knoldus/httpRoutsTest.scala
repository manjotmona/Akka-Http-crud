package com.knoldus

/**
 * Created by manjot on 6/6/18.
 */
//class httpRoutsTest extends WordSpec with Matchers with ScalatestRouteTest  {
//
//  "The service" should {
//
//    "return a greeting for GET requests to the root path" in {
//      // tests:
//      Get("/read") ~> httpRouts.route ~> check {
//        responseAs[String] shouldEqual "Captain on the bridge!"
//      }
//    }
//
//    "return a 'PONG!' response for GET requests to /ping" in {
//      // tests:
//      Get("/ping") ~> smallRoute ~> check {
//        responseAs[String] shouldEqual "PONG!"
//      }
//    }
//
//    "leave GET requests to other paths unhandled" in {
//      // tests:
//      Get("/kermit") ~> smallRoute ~> check {
//        handled shouldBe false
//      }
//    }
//
//    "return a MethodNotAllowed error for PUT requests to the root path" in {
//      // tests:
//      Put() ~> Route.seal(smallRoute) ~> check {
//        status shouldEqual StatusCodes.MethodNotAllowed
//        responseAs[String] shouldEqual "HTTP method not allowed, supported methods: GET"
//      }
//    }
//  }
//}
