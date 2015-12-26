/**
  * Created by av on 21/12/15.
  */
sealed abstract class TupleOption[+S, +T] extends Product2[S, T] {
  self =>
  def isEmpty: Boolean

  def map[R, Q](f: (S, T) => (R, Q)): TupleOption[R, Q] = if (isEmpty) None
  else {
    val (fst, snd) = f(this._1, this._2)
    TupleOption(fst, snd)
  }

  def flatMap[R, Q](f: (S, T) => TupleOption[R, Q]): TupleOption[R, Q] =
    if (isEmpty) None else f(this._1, this._2)

  def filter(p: (S, T) => Boolean): TupleOption[S, T] =
    if (isEmpty || p(this._1, this._2)) this else None

  def foreach[U](f: (S, T) => U) {
    if (!isEmpty) f(this._1, this._2)
  }

  def withFilter(p: ((S, T)) => Boolean): WithFilter = new WithFilter(p)

  class WithFilter(p: ((S, T)) => Boolean) {
    def map[R, Q](f: ((S, T)) => (R, Q)): TupleOption[R, Q] = self.filter((x,y) => p(x,y)).map((x,y) => f(x, y))

    def flatMap[R, Q](f: ((S, T)) => TupleOption[R, Q]): TupleOption[R, Q] = self.filter((x,y) => p(x,y)).flatMap((x, y) => f(x, y))

    def foreach[U](f: ((S, T)) => U): Unit = self.filter((x,y) => p(x,y)).foreach((x, y) => f(x, y))

    def withFilter(q: ((S, T)) => Boolean): WithFilter = new WithFilter(x => p(x) && q(x))
  }

}

object TupleOption {
  def apply[S, T](fst: S, snd: T): TupleOption[S, T] = if (fst == null || snd == null) None else SomeTuple(fst, snd)

  def empty[S, T]: TupleOption[S, T] = None

  def main(args: Array[String]): Unit = {

    val someTuple = SomeTuple(SomeTuple(1,1), SomeTuple(2,1))
    for {
      (i, j) <- someTuple
      (a, b) <- i
      (c, d) <- j
      (x, y) = (a + b, c + d)
      if x > 0 && y > 0
    } {
      println(x)
    }
  }
}

case class SomeTuple[+S, +T](fst: S, snd: T) extends TupleOption[S, T] {
  override def _1: S = fst

  override def _2: T = snd

  override def isEmpty: Boolean = false
}

case object None extends TupleOption[Nothing, Nothing] {
  override def _1: Nothing = throw new NoSuchElementException("None._1")

  override def _2: Nothing = throw new NoSuchElementException("None._2")

  override def isEmpty: Boolean = true
}

