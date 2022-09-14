## Lambda expressions and anonymous functions

Lambda expressions and anonymous functions are function literals. Function literals are functions that are not declared
but are passed immediately as an expression. Consider the following example:

```max(strings, { a, b -> a.length < b.length })```

The function max is a higher-order function, as it takes a function value as its second argument. This second argument
is an expression that is itself a function, called a function literal, which is equivalent to the following named
function:

```fun compare(a: String, b: String): Boolean = a.length < b.length```

## Lambda expression syntax

The full syntactic form of lambda expressions is as follows:

```val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }```

* A lambda expression is always surrounded by curly braces.
* Parameter declarations in the full syntactic form go inside curly braces and have optional type annotations.
* The body goes after the ->.
* If the inferred return type of the lambda is not Unit, the last (or possibly single) expression inside the lambda body
  is treated as the return value.

If you leave all the optional annotations out, what's left looks like this:

```val sum = { x: Int, y: Int -> x + y }```

## Passing trailing lambdas

According to Kotlin convention, if the last parameter of a function is a function, then a lambda expression passed as
the corresponding argument can be placed outside the parentheses:

```val product = items.fold(1) { acc, e -> acc * e }```

## it: implicit name of a single parameter

## Returning a value from a lambda expression

You can explicitly return a value from the lambda using the qualified return syntax. Otherwise, the value of the last
expression is implicitly returned.

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

## Underscore for unused variables

If the lambda parameter is unused, you can place an underscore instead of its name: