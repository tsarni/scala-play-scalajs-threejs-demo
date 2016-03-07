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
    val response: HttpResponse[String] = Http("http://www.iltalehti.fi/rss.xml").asString
    val latest = rssParser(response.body.toString())
    val html = new play.twirl.api.Html(latest)
    html
  }
  
  def rssParser (implicit x:String) : String =  {
    val xml = scala.xml.XML.loadString(x)
    var title = ""
   (xml\ "channel" \ "item").foreach { child => title += (child \ "title").text + "<br />" }
    title
  }

   

}
