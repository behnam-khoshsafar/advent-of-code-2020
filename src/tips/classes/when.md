when defines a conditional expression with multiple branches. It is similar to the switch statement in C-like languages.
Its simple form looks like this.

```
when (x) {
    1 -> print("x == 1")
    2 -> print("x == 2")
    else -> {
        print("x is neither 1 nor 2")
    }
}

enum class Bit {
  ZERO, ONE
}

val numericValue = when (getRandomBit()) {
    Bit.ZERO -> 0
    Bit.ONE -> 1
    // 'else' is not required because all cases are covered
}

```

when can be used either as an expression or as a statement. If it is used as an expression, the value of the first
matching branch becomes the value of the overall expression. If it is used as a statement, the values of individual
branches are ignored. Just like with if, each branch can be a block, and its value is the value of the last expression
in the block.

If when is used as an expression, the else branch is mandatory, unless the compiler can prove that all possible cases
are covered with branch conditions, for example, with enum class entries and sealed class subtypes).

```
enum class Color {
  RED, GREEN, BLUE
}

when (getColor()) {
    Color.RED -> println("red")
    Color.GREEN -> println("green")
    Color.BLUE -> println("blue")
    // 'else' is not required because all cases are covered
}

when (getColor()) {
  Color.RED -> println("red") // no branches for GREEN and BLUE
  else -> println("not red") // 'else' is required
}
```

```
when (x) {
    0, 1 -> print("x == 0 or x == 1")
    else -> print("otherwise")
}

when (x) {
    in 1..10 -> print("x is in the range")
    in validNumbers -> print("x is valid")
    !in 10..20 -> print("x is outside the range")
    else -> print("none of the above")
}

when (x) {
    s.toInt() -> print("s encodes x")
    else -> print("s does not encode x")
}


fun hasPrefix(x: Any) = when(x) {
    is String -> x.startsWith("prefix")
    else -> false
}
```