The Kotlin standard library provides extension functions for grouping collection elements. The basic function groupBy()
takes a lambda function and returns a Map. In this map, each key is the lambda result and the corresponding value is the
List of elements on which this result is returned. This function can be used, for example, to group a list of Strings by
their first letter.

You can also call groupBy() with a second lambda argument – a value transformation function. In the result map of
groupBy() with two lambdas, the keys produced by keySelector function are mapped to the results of the value
transformation function instead of the original elements.

```
val numbers = listOf("one", "two", "three", "four", "five")

println(numbers.groupBy { it.first().uppercase() })
println(numbers.groupBy(keySelector = { it.first() }, valueTransform = { it.uppercase() }))
```

If you want to group elements and then apply an operation to all groups at one time, use the function groupingBy(). It
returns an instance of the Grouping type. The Grouping instance lets you apply operations to all groups in a lazy
manner: the groups are actually built right before the operation execution.

Namely, Grouping supports the following operations:

* eachCount() counts the elements in each group.
* fold() and reduce() perform fold and reduce operations on each group as a separate collection and return the results.
* aggregate() applies a given operation subsequently to all the elements in each group and returns the result. This is
  the generic way to perform any operations on a Grouping. Use it to implement custom operations when fold or reduce are
  not enough.