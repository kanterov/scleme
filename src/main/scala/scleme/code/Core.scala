package scleme.code

object Core {

  def isFixnum(a: Any) = a.isInstanceOf[Int] || a.isInstanceOf[Long]
 
  def fixnumToChar(a: Int): Char = a.toChar

  def fixnumToChar(a: Long): Char = a.toChar
  
  def fixnumToChar(a: Any): Char =
    if (a.isInstanceOf[Int]) fixnumToChar(a.asInstanceOf[Int])
    else if (a.isInstanceOf[Long]) fixnumToChar(a.asInstanceOf[Long])
    else throw new IllegalArgumentException("fixnum expected")
  
  def charToFixnum(a: Char): Int = a.toInt
  
  def charToFixnum(a: Any): Char =
	if (a.isInstanceOf[Char]) charToFixnum(a.asInstanceOf[Char])
    else throw new IllegalArgumentException("fixnum expected")
}