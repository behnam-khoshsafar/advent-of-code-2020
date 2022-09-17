Kotlin collections contain functions for commonly used aggregate operations – operations that return a single value
based on the collection content. Most of them are well known and work the same way as they do in other languages:

* minOrNull() and maxOrNull() return the smallest and the largest element respectively. On empty collections, they
  return null.
* average() returns the average value of elements in the collection of numbers.
* sum() returns the sum of elements in the collection of numbers.
* count() returns the number of elements in a collection.

```
fun main() {
    val numbers = listOf(6, 42, 10, 4)

    println("Count: ${numbers.count()}")
    println("Max: ${numbers.maxOrNull()}")
    println("Min: ${numbers.minOrNull()}")
    println("Average: ${numbers.average()}")
    println("Sum: ${numbers.sum()}")
}
```

There are also functions for retrieving the smallest and the largest elements by certain selector function or custom
Comparator:

* maxByOrNull() and minByOrNull() take a selector function and return the element for which it returns the largest or
  the smallest value.
* maxWithOrNull() and minWithOrNull() take a Comparator object and return the largest or smallest element according to
  that Comparator.
* maxOfOrNull() and minOfOrNull() take a selector function and return the largest or the smallest return value of the
  selector itself.
* maxOfWithOrNull() and minOfWithOrNull() take a Comparator object and return the largest or smallest selector return
  value according to that Comparator.

These functions return null on empty collections. There are also alternatives – maxOf, minOf, maxOfWith, and minOfWith –
which do the same as their counterparts but throw a NoSuchElementException on empty collections.

```
val numbers = listOf(5, 42, 10, 4)
val min3Remainder = numbers.minByOrNull { it % 3 }
println(min3Remainder)

val strings = listOf("one", "two", "three", "four")
val longestString = strings.maxWithOrNull(compareBy { it.length })
println(longestString)
```

Besides regular sum(), there is an advanced summation function sumOf() that takes a selector function and returns the
sum of its application to all collection elements. Selector can return different numeric types: Int, Long, Double, UInt,
and ULong (also BigInteger and BigDecimal on the JVM).

```
val numbers = listOf(5, 42, 10, 4)
println(numbers.sumOf { it * 2 })
println(numbers.sumOf { it.toDouble() / 2 })
```

## Fold and reduce

For more specific cases, there are the functions reduce() and fold() that apply the provided operation to the collection
elements sequentially and return the accumulated result. The operation takes two arguments: the previously accumulated
value and the collection element.

The difference between the two functions is that fold() takes an initial value and uses it as the accumulated value on
the first step, whereas the first step of reduce() uses the first and the second elements as operation arguments on the
first step.

```
val numbers = listOf(5, 2, 10, 4)

val simpleSum = numbers.reduce { sum, element -> sum + element }
println(simpleSum)
val sumDoubled = numbers.fold(0) { sum, element -> sum + element * 2 }
println(sumDoubled)
```

The example above shows the difference: fold() is used for calculating the sum of doubled elements. If you pass the same
function to reduce(), it will return another result because it uses the list's first and second elements as arguments on
the first step, so the first element won't be doubled.

To apply a function to elements in the reverse order, use functions reduceRight() and foldRight(). They work in a way
similar to fold() and reduce() but start from the last element and then continue to previous. Note that when folding or
reducing right, the operation arguments change their order: first goes the element, and then the accumulated value.

You can also apply operations that take element indices as parameters. For this purpose, use functions reduceIndexed()
and foldIndexed() passing element index as the first argument of the operation.

Finally, there are functions that apply such operations to collection elements from right to left - reduceRightIndexed()
and foldRightIndexed().

```
val numbers = listOf(5, 2, 10, 4)
val sumDoubledRight = numbers.foldRight(0) { element, sum -> sum + element * 2 }
println(sumDoubledRight)


val numbers = listOf(5, 2, 10, 4)
val sumEven = numbers.foldIndexed(0) { idx, sum, element -> if (idx % 2 == 0) sum + element else sum }
println(sumEven)

val sumEvenRight = numbers.foldRightIndexed(0) { idx, element, sum -> if (idx % 2 == 0) sum + element else sum }
println(sumEvenRight)
```

All reduce operations throw an exception on empty collections. To receive null instead, use their *OrNull()
counterparts:

* reduceOrNull()
* reduceRightOrNull()
* reduceIndexedOrNull()
* reduceRightIndexedOrNull()

For cases where you want to save intermediate accumulator values, there are functions runningFold() (or its synonym
scan()) and runningReduce().

```
val numbers = listOf(0, 1, 2, 3, 4, 5)
val runningReduceSum = numbers.runningReduce { sum, item -> sum + item }
val runningFoldSum = numbers.runningFold(10) { sum, item -> sum + item }
```

