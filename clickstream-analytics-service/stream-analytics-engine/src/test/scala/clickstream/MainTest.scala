import org.scalatest._

class HelloSpec extends FunSuite with DiagrammedAssertions {
  test("Hello should start with H") {
    assert("Hello".startsWith("H"))
  }
}
