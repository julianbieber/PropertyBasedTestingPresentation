package regression

object Fibonacci {

  def oldFunction(n: Int): Long = {
    if (n <= 0)
      0
    else if (n == 1)
      1
    else
      oldFunction(n - 1) + oldFunction(n - 2)
  }

  def newFunction(n: Int): BigDecimal = {
    var a: BigDecimal = 0
    var b: BigDecimal = 1
    (0 until n).foreach { i =>
      val newB = a + b
      a = b
      b = newB
    }
    a
  }
}
