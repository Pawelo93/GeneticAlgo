import java.util.*
import kotlin.collections.ArrayList
import kotlin.streams.asSequence

class Genetic(val totalPopulation: Int, val target: String) {

    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    init {
        val initPopulation = createInitialPopulation(totalPopulation, target.length.toLong())
        val fitnesses = calculateFitness(initPopulation)
        reproduction(fitnesses)
    }

    fun createInitialPopulation(totalPopulation: Int, length: Long): ArrayList<String> {
        val list = arrayListOf<String>()
        for (i in 0 until totalPopulation) {
            list.add(createRandomString(length))
        }
        return list
    }

    private fun createRandomString(length: Long): String {
        val result = Random().ints(length, 0, source.length)
                .asSequence()
                .map(source::get)
                .joinToString("")

        return result
    }

    fun calculateFitness(list: ArrayList<String>): ArrayList<Element> {
        val fitnessList = arrayListOf<Element>()
        var allFitness = 0f
        for (element in list) {
            val fitness = calculateFitnessForElement(element)
            allFitness += fitness
            fitnessList.add(Element(element, fitness))
        }
        val averageFitness = allFitness / list.size
        return fitnessList
    }

    private fun calculateFitnessForElement(element: String): Float {
        var points = 0
        for ((index, letter) in target.withIndex()) {
            if (element[index] == letter)
                points += 1
        }
        return points.toFloat() / target.length
    }

    fun reproduction(list: ArrayList<Element>): ArrayList<String> {
        val random = Random()
        val fullList = arrayListOf<String>()

        for (element in list) {
            println("Element fitness ${element.fitness}")
            var count = element.fitness * 10
            if(count == 0f)
                count = 1f
            for (i in 0 until count.toInt()) {
                println("HEllo")
                fullList.add(element.word)
            }
        }

        val output = arrayListOf<String>()
        for(i in 0 until totalPopulation) {
            output.add(rep(random, fullList))
        }

        return output
    }

    fun rep(random: Random, fullList: ArrayList<String>): String{
        val ranI = random.nextInt(fullList.size)
        println(ranI)
        val first = fullList[ranI]
        var second = ""
        do {
            second = fullList[random.nextInt(fullList.size)]
        } while (first == second)
        println("First $first Second $second")

        var newItem = first.substring(0, first.length / 2) + second.substring(second.length / 2, second.length)

        newItem = doMutation(random, newItem)

        println("NewItem $newItem")
        return newItem
    }

    fun doMutation(random: Random, item: String): String{
        if (random.nextInt(100) == 10) {
            println("Do mutation")
            val mutatedLetter = source[random.nextInt(source.length)]
            val randomIndex = random.nextInt(item.length)

            var newItem = ""
            for ((index, letter) in item.withIndex()) {
                if(index == randomIndex)
                    newItem += mutatedLetter
                else
                    newItem += letter
            }
            return newItem
        }
        return item
    }
}