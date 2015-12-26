object Test {
  def test = {
    optimize {
      println(2 + 3)
      List(1, 1 + 1, 1 + 1 + 1).foreach(println)
    }
  }
}
