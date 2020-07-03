package cyxTankWar.modle

import cyxTankWar.Config
import org.itheima.kotlin.game.core.Painter

class NewBorn(override val x: Int, override val y: Int) :View {
    override val height: Int = Config.block
    override val width: Int = Config.block
    private var index=0
    private var imagePath= ArrayList<String>()
    init{
        for(i in 1..8){
            imagePath.add("img/born${i}.gif")
        }
    }

    override fun draw() {
        Painter.drawImage(imagePath[(index++)%imagePath.size],x,y)
    }
}