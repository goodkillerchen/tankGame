package cyxTankWar.modle

import cyxTankWar.Business.Destroyable
import cyxTankWar.Config
import org.itheima.kotlin.game.core.Painter

class Blast(override val x: Int, override val y: Int) :Destroyable {
    override val height: Int= Config.block
    override val width: Int=Config.block
    private var imagePath = arrayListOf<String>()
    private var index=0
    init {
        (1..8).forEach {
            imagePath.add("img/blast${it}.gif")
        }
    }
    override fun draw() {
        val i=index%imagePath.size
        Painter.drawImage(imagePath[i],x,y)
        index++
    }

    override fun judgeDestroy(): Boolean = index>=imagePath.size

    override fun showDestroy(): Array<View>? {
        return null
    }
}