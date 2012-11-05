package scleme.code

object Core {

  def isFixnum(a: Any) = a.isInstanceOf[Int] || a.isInstanceOf[Long]
  
}