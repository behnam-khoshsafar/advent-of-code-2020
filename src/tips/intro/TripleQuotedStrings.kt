package tips.intro

const val question = "life, the universe, and everything"
const val answer = 42

val tripleQuotedString = """
    #question = "$question"
    #answer = $answer""".trimMargin("#")

val tripleQuotedString2 = """
    #question = "$question"
    #answer = $answer""".trimIndent()

fun main() {
    println(tripleQuotedString)
    println(tripleQuotedString2)
}