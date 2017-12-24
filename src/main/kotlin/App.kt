import java.awt.Color
import java.awt.Font
import javax.swing.JFrame
import javax.swing.JLabel
import java.awt.Font.PLAIN
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JTextArea


class App : JFrame() {

    val bigFont = Font("serif", PLAIN, 22)
    val normalFont = Font("serif", PLAIN, 18)

    val bestPhrase: JLabel
    val allPhrases: JTextArea
    val info: JLabel

    val list = arrayListOf<String>()

    init {

        setBounds(300, 150, 600, 600)
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true

        bestPhrase = JLabel()
        bestPhrase.setBounds(20, 200, 300, 100)
        bestPhrase.font = bigFont;

        allPhrases = JTextArea()
        allPhrases.setBounds(300, 15, 250, 530)
        allPhrases.font = normalFont

        info = JLabel()
        info.setBounds(20, 320, 300, 100)
        info.font = normalFont
        setInfo(311, 73, 200, 1)

        add(bestPhrase)
        add(allPhrases)
        add(info)

        background = Color.WHITE



        val totalPopulation = 2
        val targetWord = "quest"
        val genetic = Genetic(targetWord)

        println(genetic.createInitialPopulation(totalPopulation, targetWord.length.toLong()))






        // debug
        bestPhrase.addMouseListener(object: MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                addToAllPhrases("new")
            }
        })
    }

    fun setBestPhrase(text: String){
        bestPhrase.text = "<html>Best phrase: <br> $text</html>"
    }

    fun addToAllPhrases(text: String){
        allPhrases.text = ""
        list.add(text)
//        if(list.size > 20)
//            list.removeAt(0)

        var listInString = ""
        for (s in list.reversed()) {
            listInString+="$s\n"
        }
        allPhrases.text += "All phrases \n$listInString"
    }

    fun setInfo(totalGenerations: Int, averageFitness: Int, totalPopulation: Int, mutationRate: Int){
        info.text = "<html>total generations: $totalGenerations<br>" +
                "average fitness: $averageFitness%<br>" +
                "total population: $totalPopulation<br>" +
                "mutation rate: $mutationRate%"
    }
}