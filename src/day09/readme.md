## Note about the difference between sublist and windowed functions

Note, however, that unlike sublist(), which creates a view of the existing list, the windowed() function builds all the
intermediate lists. Though using it improves readability, it results in some performance drawbacks. For parts of the
code that are not performance-critical, these drawbacks usually are not noticeable. On the JVM, the garbage collector is
very effective at collecting such short-lived objects. It’s nevertheless useful to know about these nuanced differences
between the two functions!

There’s also an overloaded version of the windowed() function that takes lambda as an argument describing how to
transform each window. This version doesn’t create new sublists to pass as lambda arguments. Instead, it passes
“ephemeral” sublists (somewhat similar to sublist() views) that are valid only inside the lambda. You should not store
such a sublist or allow it to escape unless you’ve made a snapshot of it:

```
fun main() {
   val list = listOf('a', 'b', 'c', 'd', 'e')
   // Intermediate lists are created:
   println(list.windowed(2).map { it.joinToString("") })

   // Lists passed to lambda are "ephemeral",
   // they are only valid inside this lambda:
   println(list.windowed(2) { it.joinToString("") })

   // You should make a copy of a window sublist
   // to store it or pass further:
   var firstWindowRef: List<Char>? = null
   var firstWindowCopy: List<Char>? = null
   list.windowed(2) {
       if (it.first() == 'a') {
           firstWindowRef = it // Don't do this!
           firstWindowCopy = it.toList()
       }
       it.joinToString("")
   }
   println("Ref: $firstWindowRef")   // [d, e]
   println("Copy: $firstWindowCopy") // [a, b]
}
```