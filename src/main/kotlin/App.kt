import java.awt.Color
import java.awt.Font
import javax.swing.JFrame
import javax.swing.JLabel
import java.awt.Font.PLAIN
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JTextArea


class App : JFrame("Genetic Algo"), Display{

    val bigFont = Font("serif", PLAIN, 22)
    val normalFont = Font("serif", PLAIN, 18)

    val bestPhrase: JLabel
    val allPhrases: JTextArea
    val info: JLabel

    val list = arrayListOf<String>()

    init {

        setBounds(300, 150, 800, 600)
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true

        bestPhrase = JLabel()
        bestPhrase.setBounds(20, 200, 300, 100)
        bestPhrase.font = bigFont;

        allPhrases = JTextArea()
        allPhrases.setBounds(300, 15, 550, 530)
        allPhrases.font = normalFont

        info = JLabel()
        info.setBounds(20, 320, 300, 100)
        info.font = normalFont
//        setInfo(311, 73, 200, 1)

        add(bestPhrase)
        add(allPhrases)
        add(info)

        background = Color.WHITE



        val totalPopulation = 100000
        val targetWord = "Koniec świata."
        val mutationRate = 0.05f
        val genetic = Genetic(this, targetWord, totalPopulation, mutationRate)


        val thread = Thread(Runnable {
            while(!genetic.end) {
                genetic.nextGeneration()
                Thread.sleep(3)
            }
        })

        thread.start()

//        println(genetic.createInitialPopulation(totalPopulation, targetWord.length.toLong()))


//        val initialPopulation = genetic.createInitialPopulation(totalPopulation, targetWord.length.toLong())






        // debug
        bestPhrase.addMouseListener(object: MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)

                genetic.nextGeneration()
            }
        })
    }

    override fun setInfo(totalGenerations: Int, averageFitness: Int, totalPopulation: Int, mutationRate: Float){
        info.text = "<html>total generations: $totalGenerations<br>" +
                "average fitness: $averageFitness%<br>" +
                "total population: $totalPopulation<br>" +
                "mutation rate: ${mutationRate*100}%"
    }

    override fun showPhrase(text: String) {
        allPhrases.text = ""
        list.add(text)
        if(list.size > 21)
            list.removeAt(0)

        var listInString = ""
        for (s in list.reversed()) {
            listInString+="$s\n"
        }
        allPhrases.text += "All phrases \n$listInString"
    }

}