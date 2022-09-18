## High-order functions and lambdas

Kotlin functions are first-class, which means they can be stored in variables and data structures, and can be passed as
arguments to and returned from other higher-order functions. You can perform any operations on functions that are
possible for other non-function values.

### Higher-order functions

A higher-order function is a function that takes functions as parameters, or returns a function.

A good example of a higher-order function is the functional programming idiom fold for collections. It takes an initial
accumulator value and a combining function and builds its return value by consecutively combining the current
accumulator value with each collection element, replacing the accumulator value each time:

```
fun <T, R> Collection<T>.fold(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}
```

In the code above, the `combine` parameter has the function type `(R, T) -> R`, so it accepts a function that takes two
arguments of types R and T and returns a value of type R. It is invoked inside the for loop, and the return value is
then assigned to accumulator.

```
val items = listOf(1, 2, 3, 4, 5)

// Lambdas are code blocks enclosed in curly braces.
items.fold(0, { 
    // When a lambda has parameters, they go first, followed by '->'
    acc: Int, i: Int -> 
    print("acc = $acc, i = $i, ") 
    val result = acc + i
    println("result = $result")
    // The last expression in a lambda is considered the return value:
    result
})

// Parameter types in a lambda are optional if they can be inferred:
val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })

// Function references can also be used for higher-order function calls:
val product = items.fold(1, Int::times)
```

### Function types

Kotlin uses function types, such as `(Int) -> String`, for declarations that deal with functions:
val `onClick: () -> Unit = ...`.

These types have a special notation that corresponds to the signatures of the functions - their parameters and return
values:

* All function types have a parenthesized list of parameter types and a return type: (A, B) -> C denotes a type that
  represents functions that take two arguments of types A and B and return a value of type C. The list of parameter
  types may be empty, as in () -> A. The Unit return type cannot be omitted.
* Function types can optionally have an additional receiver type, which is specified before the dot in the notation: the
  type A.(B) -> C represents functions that can be called on a receiver object A with a parameter B and return a value
  C. Function literals with receiver are often used along with these types.
* Suspending functions belong to a special kind of function type that have a suspend modifier in their notation, such as
  suspend () -> Unit or suspend A.(B) -> C.

The function type notation can optionally include names for the function parameters: (x: Int, y: Int) -> Point. These
names can be used for documenting the meaning of the parameters.

To specify that a function type is nullable, use parentheses as follows: ((Int, Int) -> Int)?.

Function types can also be combined using parentheses: (Int) -> ((Int) -> Unit).

You can also give a function type an alternative name by using a type alias:

```
typealias ClickHandler = (Button, ClickEvent) -> Unit
```

### Instantiating a function type

There are several ways to obtain an instance of a function type:

* Use a code block within a function literal, in one of the following forms:
    * a lambda expression: `{ a, b -> a + b }`,
    * an anonymous function: `fun(s: String): Int { return s.toIntOrNull() ?: 0 }`

Function literals with receiver can be used as values of function types with receiver.

* Use a callable reference to an existing declaration:
    * a top-level, local, member, or extension function: `::isOdd`, `String::toInt`,
    * a top-level, member, or extension property: `List<Int>::size`,
    * a constructor: `::Regex`
* Use instances of a custom class that implements a function type as an interface:

```
class IntTransformer: (Int) -> Int {
    override operator fun invoke(x: Int): Int = TODO()
}

val intFunction: (Int) -> Int = IntTransformer()
```

The compiler can infer the function types for variables if there is enough information:

```
val a = { i: Int -> i + 1 } // The inferred type is (Int) -> Int
```

Non-literal values of function types with and without a receiver are interchangeable, so the receiver can stand in for
the first parameter, and vice versa. For instance, a value of type `(A, B) -> C` can be passed or assigned where a value
of type `A.(B) -> C` is expected, and the other way around:

```
val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }
val twoParameters: (String, Int) -> String = repeatFun // OK

fun runTransformation(f: (String, Int) -> String): String {
    return f("hello", 3)
}
val result = runTransformation(repeatFun) // OK
```

### Invoking a function type instance

A value of a function type can be invoked by using its `invoke(...)` operator: `f.invoke(x)` or just `f(x)`.

If the value has a receiver type, the receiver object should be passed as the first argument. Another way to invoke a
value of a function type with receiver is to prepend it with the receiver object, as if the value were an extension
function: 1.foo(2).

```
val stringPlus: (String, String) -> String = String::plus
val intPlus: Int.(Int) -> Int = Int::plus

println(stringPlus.invoke("<-", "->"))
println(stringPlus("Hello, ", "world!"))

println(intPlus.invoke(1, 1))
println(intPlus(1, 2))
println(2.intPlus(3)) // extension-like call
```

#### Inline functions

Sometimes it is beneficial to use inline functions, which provide flexible control flow, for higher-order functions.

### Lambda expressions and anonymous functions

Lambda expressions and anonymous functions are function literals. Function literals are functions that are not declared
but are passed immediately as an expression. Consider the following example:

```
max(strings, { a, b -> a.length < b.length })

second argument=> 

fun compare(a: String, b: String): Boolean = a.length < b.length
```

#### Lambda expression syntax

```
val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
```

* A lambda expression is always surrounded by curly braces.
* Parameter declarations in the full syntactic form go inside curly braces and have optional type annotations.
* The body goes after the ->.
* If the inferred return type of the lambda is not Unit, the last (or possibly single) expression inside the lambda body
  is treated as the return value.

#### Passing trailing lambdas

According to Kotlin convention, if the last parameter of a function is a function, then a lambda expression passed as
the corresponding argument can be placed outside the parentheses:

```
val product = items.fold(1) { acc, e -> acc * e }
```

Such syntax is also known as trailing lambda.

If the lambda is the only argument in that call, the parentheses can be omitted entirely:
```run { println("...") }```

#### it: implicit name of a single parameter

It's very common for a lambda expression to have only one parameter.

If the compiler can parse the signature without any parameters, the parameter does not need to be declared and -> can be
omitted. The parameter will be implicitly declared under the name it:

```
ints.filter { it > 0 } // this literal is of type '(it: Int) -> Boolean'
```

### Returning a value from a lambda expression

You can explicitly return a value from the lambda using the qualified return syntax. Otherwise, the value of the last
expression is implicitly returned.

Therefore, the two following snippets are equivalent:

```
ints.filter {
    val shouldFilter = it > 0
    shouldFilter
}

ints.filter {
    val shouldFilter = it > 0
    return@filter shouldFilter
}
```

