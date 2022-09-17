The order of elements is an important aspect of certain collection types. For example, two lists of the same elements
are not equal if their elements are ordered differently.

In Kotlin, the orders of objects can be defined in several ways.

First, there is natural order. It is defined for implementations of the Comparable interface. Natural order is used for
sorting them when no other order is specified.

Most built-in types are comparable:

Numeric types use the traditional numerical order: 1 is greater than 0; -3.4f is greater than -5f, and so on.

Char and String use the lexicographical order: b is greater than a; world is greater than hello.

To define a natural order for a user-defined type, make the type an implementer of Comparable. This requires
implementing the compareTo() function. compareTo() must take another object of the same type as an argument and return
an integer value showing which object is greater:

* Positive values show that the receiver object is greater.
* Negative values show that it's less than the argument.
* Zero shows that the objects are equal.

Custom orders let you sort instances of any type in a way you like. Particularly, you can define an order for
non-comparable objects or define an order other than natural for a comparable type. To define a custom order for a type,
create a Comparator for it. Comparator contains the compare() function: it takes two instances of a class and returns
the integer result of the comparison between them. The result is interpreted in the same way as the result of a
compareTo() as is described above.

A shorter way to define a Comparator is the compareBy() function from the standard library. compareBy() takes a lambda
function that produces a Comparable value from an instance and defines the custom order as the natural order of the
produced values.

The Kotlin collections package provides functions for sorting collections in natural, custom, and even random orders. On
this page, we'll describe sorting functions that apply to read-only collections. These functions return their result as
a new collection containing the elements of the original collection in the requested order. To learn about functions for
sorting mutable collections in place, see the List-specific operations.

## Natural order

The basic functions sorted() and sortedDescending() return elements of a collection sorted into ascending and descending
sequence according to their natural order. These functions apply to collections of Comparable elements.

```
val numbers = listOf("one", "two", "three", "four")

println("Sorted ascending: ${numbers.sorted()}")
println("Sorted descending: ${numbers.sortedDescending()}")
```

## Custom orders

For sorting in custom orders or sorting non-comparable objects, there are the functions sortedBy() and
sortedByDescending(). They take a selector function that maps collection elements to Comparable values and sort the
collection in natural order of that values.

```
val numbers = listOf("one", "two", "three", "four")

val sortedNumbers = numbers.sortedBy { it.length }
println("Sorted by length ascending: $sortedNumbers")
val sortedByLast = numbers.sortedByDescending { it.last() }
println("Sorted by the last letter descending: $sortedByLast")
```

To define a custom order for the collection sorting, you can provide your own Comparator. To do this, call the
sortedWith() function passing in your Comparator. With this function, sorting strings by their length looks like this:

```
val numbers = listOf("one", "two", "three", "four")
println("Sorted by length ascending: ${numbers.sortedWith(compareBy { it.length })}")
```

## Reverse order

```
val numbers = listOf("one", "two", "three", "four")
println(numbers.reversed())
```

reversed() returns a new collection with the copies of the elements. So, if you change the original collection later,
this won't affect the previously obtained results of reversed().

Another reversing function - asReversed()

* returns a reversed view of the same collection instance, so it may be more lightweight and preferable than reversed()
  if the original list is not going to change.

```
val numbers = listOf("one", "two", "three", "four")
val reversedNumbers = numbers.asReversed()
println(reversedNumbers)
```

If the original list is mutable, all its changes reflect in its reversed views and vice versa.

```
val numbers = mutableListOf("one", "two", "three", "four")
val reversedNumbers = numbers.asReversed()
println(reversedNumbers)
numbers.add("five")
println(reversedNumbers)
```

## Random order

Finally, there is a function that returns a new List containing the collection elements in a random order - shuffled().
You can call it without arguments or with a Random object.

```
val numbers = listOf("one", "two", "three", "four")
println(numbers.shuffled())
```