package tips.conventions

/*
You can build a range of any comparable elements.
 In Kotlin in checks are translated to the corresponding contains calls and .. to rangeTo calls:
* */
/*
val list = listOf("a", "b")
"a" in list  // list.contains("a")
"a" !in list // !list.contains("a")

date1..date2 // date1.rangeTo(date2)
* */
fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in first..last
}