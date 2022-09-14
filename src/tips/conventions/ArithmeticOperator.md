Kotlin allows you to provide custom implementations for the predefined set of operators on types. These operators have
predefined symbolic representation (like + or *) and precedence. To implement an operator, provide a member function or
an extension function with a specific name for the corresponding type. This type becomes the left-hand side type for
binary operations and the argument type for the unary ones.

Unary operations
```
+a
a.unaryPlus()

-a
a.unaryMinus()

!a
a.not()
```

Arithmetic operators
```
a + b
a.plus(b)

a - b
a.minus(b)

a * b
a.times(b)

a / b
a.div(b)

a % b
a.rem(b)

a..b
a.rangeTo(b)
```

in operator
```
a in b
b.contains(a)

a !in b
!b.contains(a)
```

Indexed access operator
```
a[i]
a.get(i)

a[i, j]
a.get(i, j)

a[i_1, ..., i_n]
a.get(i_1, ..., i_n)

a[i] = b
a.set(i, b)

a[i, j] = b
a.set(i, j, b)

a[i_1, ..., i_n] = b
a.set(i_1, ..., i_n, b)
```

invoke operator
```
a()
a.invoke()

a(i)
a.invoke(i)

a(i, j)
a.invoke(i, j)

a(i_1, ..., i_n)
a.invoke(i_1, ..., i_n)
```

Augmented assignments

```
a += b
a.plusAssign(b)

a -= b
a.minusAssign(b)

a *= b
a.timesAssign(b)

a /= b
a.divAssign(b)

a %= b
a.remAssign(b)
```

Equality and inequality operators
```
a == b
a?.equals(b) ?: (b === null)

a != b
!(a?.equals(b) ?: (b === null))
```

Comparison operators
```
a > b
a.compareTo(b) > 0

a < b
a.compareTo(b) < 0

a >= b
a.compareTo(b) >= 0

a <= b
a.compareTo(b) <= 0
```



Objects with the invoke() method can be invoked as a function.

```
class Invokable {
    var numberOfInvocations: Int = 0
        private set

    operator fun invoke(): Invokable {
        numberOfInvocations++;
        return this
    }
}

fun invokeTwice(invokable: Invokable) = invokable()()
```