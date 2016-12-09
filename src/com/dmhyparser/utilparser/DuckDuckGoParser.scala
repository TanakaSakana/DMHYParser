class DuckDuckGoParser extends dParser {
  override def parse(query: String): String = null
}

abstract class dParser {
  def parse(query: String): String
}
