Sealed classes and interfaces represent restricted class hierarchies that provide more control over inheritance. All
direct subclasses of a sealed class are known at compile time. No other subclasses may appear outside a module within
which the sealed class is defined. For example, third-party clients can't extend your sealed class in their code. Thus,
each instance of a sealed class has a type from a limited set that is known when this class is compiled.

The same works for sealed interfaces and their implementations: once a module with a sealed interface is compiled, no
new implementations can appear.

```
sealed interface Error

sealed class IOError(): Error

class FileReadError(val file: File): IOError()
class DatabaseError(val source: DataSource): IOError()

object RuntimeError : Error
```

A sealed class is abstract by itself, it cannot be instantiated directly and can have abstract members.

Constructors of sealed classes can have one of two visibilities: protected (by default) or private:

```
sealed class IOError {
    constructor() { /*...*/ } // protected by default
    private constructor(description: String): this() { /*...*/ } // private is OK
    // public constructor(code: Int): this() {} // Error: public and internal are not allowed
}
```

Direct subclasses of sealed classes and interfaces must be declared in the same package.

