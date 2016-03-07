package controllers

import play.api.mvc._
import shared.SharedMessages
import scalaj.http._
import scala.xml.XML

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(getRSSFeed))
  }
  
  def getRSSFeed: play.twirl.api.Html = {
    val response: HttpResponse[String] = Http("http://www.iltalehti.fi/rss.xml").charset("ISO-8859-1").asString
    return new play.twirl.api.Html(rssParser(response.body))
  }
  
  def rssParser (implicit x:String) : String =  {
    var title:String = ""
   (scala.xml.XML.loadString(x) \ "channel" \ "item").foreach { child => 
     title += "<b><a href="+ (child \ "link").text + "> " + (child \ "title").text+ "  </a></b>"  + "<em>" +  (child \ "pubDate").text + "</em><br />"
   }
    return title
  }

   

}
