package tips.classes

fun eval2(expr: Expr2): Int =
    when (expr) {
        is Num2 -> expr.value
        is Sum2 -> eval2(expr.left) + eval2(expr.right)
    }

sealed interface Expr2
class Num2(val value: Int) : Expr2
class Sum2(val left: Expr2, val right: Expr2) : Expr2