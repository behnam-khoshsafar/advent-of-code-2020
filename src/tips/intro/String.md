Kotlin has two types of string literals:

* Escaped strings
    * ```val s = "Hello, world!\n"```
* Raw strings
  ```
  val text = """
    for (c in "foo")
        print(c)"""
  ```

To remove leading whitespace from raw strings, use the trimMargin()
By default, a pipe symbol | is used as margin prefix, but you can choose another character and pass it as a parameter,
like trimMargin(">").

```
val text = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
    """.trimMargin()
```

Triple-quoted strings are not only useful for multiline strings but also for creating regex patterns as you don't need
to escape a backslash with a backslash.

```
val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"

fun getPattern(): String = """\d{2} $month \d{4}"""
```