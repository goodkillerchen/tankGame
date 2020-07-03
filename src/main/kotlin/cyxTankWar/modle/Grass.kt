package cyxTankWar.modle

import cyxTankWar.Business.Blockable
import cyxTankWar.Config
import org.itheima.kotlin.game.core.Painter

class Grass(override val x: Int, override val y: Int):View {
    override val width = Config.block
    override val height = Config.block
    override fun draw() {
        Painter.drawImage("img/grass.gif",x,y)
    }
}