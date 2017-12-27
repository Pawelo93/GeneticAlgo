import java.util.*
import kotlin.collections.ArrayList
import kotlin.streams.asSequence

class Genetic(val display: Display?, val target: String, val totalPopulation: Int, val mutationRate: Float) {

    var end = false

//    val source = " ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val source = ",./;'[]!@#$%^&*()_+= abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZżźćńąśłęóŻŹĆŃĄŚŁĘÓ"
    var generation = 0
    var nextGeneration: ArrayList<String>? = null

    init {
        nextGeneration = createInitialPopulation(totalPopulation, target.length.toLong())

    }

//    fun start(){
//        val elements = calculateFitness(initPopulation)
//
//        val mostFitElement = findMostFitElement(elements)
//
//        display?.showPhrase(mostFitElement.word)
//
//        nextGeneration = reproduction(elements)
//
//        val averageFitness = calculateAverageFitness(elements)
//
//        display?.setInfo(generation, averageFitness, totalPopulation, mutationRate)
//    }



    fun nextGeneration(){

        generation++

        val elements = calculateFitness(nextGeneration!!)
        nextGeneration = reproduction(elements)

        val mostFitElement = findMostFitElement(elements)
        display?.showPhrase(mostFitElement.word)

        display?.setInfo(generation, calculateAverageFitness(elements), totalPopulation, mutationRate)

        if(mostFitElement.word == target)
            end = true
    }

    private fun findMostFitElement(elements: ArrayList<Element>): Element {
        var mostFitElement: Element = elements[0]
        for (element in elements) {
            if(element.fitness > mostFitElement.fitness)
                mostFitElement = element
        }
        return mostFitElement
    }

    private fun calculateAverageFitness(elements: ArrayList<Element>): Int{
        var sumFitness = 0f
        for (element in elements) {
            sumFitness += element.fitness
        }

        return (sumFitness/elements.size * 100).toInt()
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
        for (element in list) {
            val fitness = calculateFitnessForElement(element)
            fitnessList.add(Element(element, fitness))
        }
//        val averageFitness = allFitness / list.size
        return fitnessList
    }

    fun calculateFitnessForElement(element: String): Float {
        var points = 0f
//        for ((index, letter) in target.withIndex()) {
//            if (element[index] == letter)
//                points += 1
//        }
//        points = points / target.length
//
//
//        if(points <= 0.5f) {
//            for ((index, letter) in target.withIndex()) {
//                for (char in element) {
//                    if (letter == char) {
//                        points += 0.01f
//                    }
//                }
//            }
//
//        }


        // full point for every good letter
        var neddedLetters = ""
        var notUsedLetters = ""
        for ((index, letter) in target.withIndex()) {
            if (element[index] == letter) {
                points += 1.toFloat() / target.length
            }else {
                neddedLetters += letter
                notUsedLetters += element[index]
            }
        }


        // half point if letter is nedded in sentence
//        for (neddedLetter in neddedLetters) {
//            for (letter in neddedLetters) {
//                if(neddedLetter == letter) {
//                    points += 0.5f / target.length
//                    break
//                }
//            }
//        }




//        for ((index, letter) in target.withIndex()) {
//            for ((charIndex, char) in element.withIndex()) {
////                println("Letter $letter char $char index $index charIndex $charIndex targetLength ${target.length} points $points")
//                if (char == letter && charIndex == index) {
//                    points += 1.toFloat() / target.length
//                    break
//                }else if(char == letter){
//                    points += 0.5f / target.length
//                }
//            }
//        }

        return points
    }

    var all = 0
    var withMutation = 0
    fun reproduction(list: ArrayList<Element>): ArrayList<String> {
        val random = Random()
        val fullList = arrayListOf<String>()

        for (element in list) {
            var count = element.fitness * 10
            if(count == 0f)
                count = 1f
            for (i in 0 until count.toInt()) {
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
        val first = fullList[ranI]
        var second = ""
        var i = 0
        do {
            second = fullList[random.nextInt(fullList.size)]
            i++
        } while (first == second && i < fullList.size)

        var newItem: String
        if(random.nextFloat() <= 0.5f)
            newItem = first.substring(0, first.length / 2) + second.substring(second.length / 2, second.length)
        else
            newItem = second.substring(0, second.length / 2) + first.substring(first.length / 2, first.length)

        all += 1
        newItem = doMutation(random, newItem)

        return newItem
    }

    fun doMutation(random: Random, item: String): String{
        val rand = random.nextFloat()
        if (rand <= mutationRate) {
            withMutation += 1
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