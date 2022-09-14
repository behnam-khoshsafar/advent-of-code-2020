One of the most common pitfalls in many programming languages, including Java, is that accessing a member of a null
reference will result in a null reference exception. In Java this would be the equivalent of a NullPointerException, or
an NPE for short.

The only possible causes of an NPE in Kotlin are:

* An explicit call to throw NullPointerException().
* Usage of the !! operator that is described below.
* Data inconsistency with regard to initialization, such as when:
    * An uninitialized this available in a constructor is passed and used somewhere (a "leaking this").
    * A superclass constructor calls an open member whose implementation in the derived class uses an uninitialized
      state.
* Java interoperation:
    * Attempts to access a member of a null reference of a platform type;
    * Nullability issues with generic types being used for Java interoperation. For example, a piece of Java code might
      add null into a Kotlin MutableList<String>, therefore requiring a MutableList<String?> for working with it.
    * Other issues caused by external Java code.

In Kotlin, the type system distinguishes between references that can hold null (nullable references) and those that
cannot (non-null references). For example, a regular variable of type String cannot hold null:

```
var a: String = "abc" // Regular initialization means non-null by default
a = null // compilation error
```

To allow nulls, you can declare a variable as a nullable string by writing String?:

```
var b: String? = "abc" // can be set to null
b = null // ok
print(b)
```

## Checking for null in conditions

First, you can explicitly check whether b is null, and handle the two options separately:

```
val l = if (b != null) b.length else -1
```

The compiler tracks the information about the check you performed, and allows the call to length inside the if. More
complex conditions are supported as well:

```
val b: String? = "Kotlin"
if (b != null && b.length > 0) {
    print("String of length ${b.length}")
} else {
    print("Empty string")
}
```

Note that this only works where b is immutable (meaning it is a local variable that is not modified between the check
and its usage or it is a member val that has a backing field and is not overridable), because otherwise it could be the
case that b changes to null after the check.

## Safe calls

Your second option for accessing a property on a nullable variable is using the safe call operator ?.:

```
val a = "Kotlin"
val b: String? = null
println(b?.length)
println(a?.length) // Unnecessary safe call
```

This returns b.length if b is not null, and null otherwise. The type of this expression is Int?.

Safe calls are useful in chains.

```bob?.department?.head?.name```
Such a chain returns null if any of the properties in it is null.

To perform a certain operation only for non-null values, you can use the safe call operator together with let:

```
val listWithNulls: List<String?> = listOf("Kotlin", null)
for (item in listWithNulls) {
    item?.let { println(it) } // prints Kotlin and ignores null
}
```

## Elvis operator

```val l: Int = if (b != null) b.length else -1```
```val l = b?.length ?: -1```

If the expression to the left of ?: is not null, the Elvis operator returns it, otherwise it returns the expression to
the right. Note that the expression on the right-hand side is evaluated only if the left-hand side is null.

Since throw and return are expressions in Kotlin, they can also be used on the right-hand side of the Elvis operator.
This can be handy, for example, when checking function arguments:

```
fun foo(node: Node): String? {
    val parent = node.getParent() ?: return null
    val name = node.getName() ?: throw IllegalArgumentException("name expected")
    // ...
}
```

## The !! operator

The third option is for NPE-lovers: the not-null assertion operator (!!) converts any value to a non-null type and
throws an exception if the value is null.

## Safe casts

regular casts may result in a ClassCastException if the object is not of the target type. Another option is to use safe
casts that return null if the attempt was not successful:
```val aInt: Int? = a as? Int```

## Collections of a nullable type

If you have a collection of elements of a nullable type and want to filter non-null elements, you can do so by using
filterNotNull:

```
val nullableList: List<Int?> = listOf(1, 2, null, 4)
val intList: List<Int> = nullableList.filterNotNull()
```