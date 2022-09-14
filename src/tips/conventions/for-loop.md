The for loop iterates through anything that provides an iterator.

```
for (item in collection) print(item)

for (item: Int in ints) {
    // ...
}
```

As mentioned before, for iterates through anything that provides an iterator. This means that it:

* has a member or an extension function iterator() that returns Iterator<>:
* has a member or an extension function next()
* has a member or an extension function hasNext() that returns Boolean.
* All of these three functions need to be marked as operator.

use a range expression:

```
for (i in 1..3) {
    println(i)
}
for (i in 6 downTo 0 step 2) {
    println(i)
}
```

If you want to iterate through an array or a list with an index, you can do it this way:

```
for (i in array.indices) {
    println(array[i])
}

for ((index, value) in array.withIndex()) {
    println("the element at $index is $value")
}
```

## While loops

while and do-while loops execute their body continuously while their condition is satisfied. The difference between them
is the condition checking time:

* while checks the condition and, if it's satisfied, executes the body and then returns to the condition check.
* do-while executes the body and then checks the condition. If it's satisfied, the loop repeats. So, the body of
  do-while executes at least once regardless of the condition.

```
while (x > 0) {
    x--
}

do {
    val y = retrieveData()
} while (y != null) // y is visible here!
```

Kotlin supports traditional break and continue operators in loops.


##Ranges
```
if (i in 1..4) { // equivalent of i >= 1 && i <= 4
    print(i)
}

for (i in 4 downTo 1) print(i)

for (i in 1..8 step 2) print(i)
println()
for (i in 8 downTo 1 step 2) print(i)

for (i in 1 until 10) {       // i in 1 until 10, excluding 10
    print(i)
}

for (i in 1..9 step 3) print(i) // the last element is 7

for (i in (1..4).reversed()) print(i)

println((1..10).filter { it % 2 == 0 })
```