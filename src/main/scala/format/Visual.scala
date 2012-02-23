package lila
package format

import model._

/**
 * r bqkb r
 * p ppp pp
 * pr
 *    P p
 *    QnB
 *  PP  N
 * P    PPP
 * RN  K  R
 */
object Visual extends Format[Board] {

  private lazy val pieces = Role.all map { r ⇒ (r.forsyth, r) } toMap

  def <<(source: String): Board = {
    val lines = source.lines.toList
    val filtered = if (lines.size == 8) lines else lines drop 1 take 8
    Board(
      for {
        line ← (filtered.zipWithIndex)
        (l, y) = line
        char ← (l zipWithIndex)
        (c, x) = char
        if pieces.keySet(c toLower)
      } yield Pos(x + 1, 8 - y) -> (Color(c isUpper) - pieces(c toLower))
    )
  }

  def >>(board: Board): String = {
    for (y ← 8 to 1 by -1) yield {
      for (x ← 1 to 8) yield board(x, y).fold(_ forsyth, ' ')
    } mkString
  } map { """\s*$""".r.replaceFirstIn(_, "") } mkString "\n"
}