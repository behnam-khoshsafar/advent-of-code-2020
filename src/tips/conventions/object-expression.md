## Object expressions and declarations

Sometimes you need to create an object that is a slight modification of some class, without explicitly declaring a new
subclass for it. Kotlin can handle this with object expressions and object declarations.

Object expressions create objects of anonymous classes, that is, classes that aren't explicitly declared with the class
declaration. Such classes are useful for one-time use. You can define them from scratch, inherit from existing classes,
or implement interfaces. Instances of anonymous classes are also called anonymous objects because they are defined by an
expression, not a name.

Object expressions start with the object keyword.

```
val helloWorld = object {
    val hello = "Hello"
    val world = "World"
    // object expressions extend Any, so `override` is required on `toString()`
    override fun toString() = "$hello $world"
}
```

