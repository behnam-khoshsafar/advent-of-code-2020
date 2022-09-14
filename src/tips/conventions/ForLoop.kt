package tips.conventions

import java.util.*

fun MyDate.followingDate(): MyDate {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth)
    val millisecondsInADay = 24 * 60 * 60 * 1000L
    val timeInMili = c.timeInMillis + millisecondsInADay
    val result = Calendar.getInstance()
    result.timeInMillis = timeInMili
    return MyDate(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DAY_OF_MONTH))
}

operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)

class DateRange(val start: MyDate, val end: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current: MyDate = start

            override fun next(): MyDate {
                if (!hasNext()) throw NoSuchElementException()
                val result = current
                current = current.followingDate()
                return result
            }

            override fun hasNext(): Boolean = current <= end
        }
    }
}

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

