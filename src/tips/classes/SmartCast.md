In most cases, you don't need to use explicit cast operators in Kotlin because the compiler tracks the is-checks and
explicit casts for immutable values and inserts (safe) casts automatically when necessary:

```
fun demo(x: Any) {
    if (x is String) {
        print(x.length) // x is automatically cast to String
    }
}

if (x !is String) return

print(x.length) // x is automatically cast to String


// x is automatically cast to String on the right-hand side of `||`
if (x !is String || x.length == 0) return

// x is automatically cast to String on the right-hand side of `&&`
if (x is String && x.length > 0) {
    print(x.length) // x is automatically cast to String
}
```

Smart casts work for when expressions and while loops as well:

```
when (x) {
    is Int -> print(x + 1)
    is String -> print(x.length + 1)
    is IntArray -> print(x.sum())
}
```

Note that smart casts work only when the compiler can guarantee that the variable won't change between the check and the
usage.

## "Unsafe" cast operator

Usually, the cast operator throws an exception if the cast isn't possible. And so, it's called unsafe. The unsafe cast
in Kotlin is done by the infix operator as.

```
val x: String = y as String
```

Note that null cannot be cast to String, as this type is not nullable. If y is null, the code above throws an exception.
To make code like this correct for null values, use the nullable type on the right-hand side of the cast:

```val x: String? = y as String?```

## "Safe" (nullable) cast operator

To avoid exceptions, use the safe cast operator as?, which returns null on failure.

```val x: String? = y as? String```