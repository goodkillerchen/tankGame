package cyxTankWar.modle

import cyxTankWar.Business.Attackable
import cyxTankWar.Business.Blockable
import cyxTankWar.Business.Destroyable
import cyxTankWar.Business.Sufferable
import cyxTankWar.Config
import org.itheima.kotlin.game.core.Painter

class Camp(override var x: Int,override var y: Int) :Blockable,Sufferable,Destroyable {
    override var blood: Int=12
    override var width: Int= Config.block*2
    override var height: Int=Config.block * 3 / 2
    override fun draw() {
        if(blood<=3){
            x=(Config.gameWidth-Config.block)/2
            y=Config.gameHeight-Config.block
            Painter.drawImage("img/symbol.gif",Config.gameWidth/2-Config.block/2,12*Config.block)
            width=Config.block
            height=Config.block
        }
        else {
            for (i in 0..(height - Config.block) step Config.block / 2) {
                Painter.drawImage("img/wall/wall.gif", x, y + i)
                Painter.drawImage("img/wall/wall.gif", x + width - Config.block / 2, y + i)
            }
            for (i in 0..(width - Config.block / 2) step Config.block / 2) {
                Painter.drawImage("img/wall/wall.gif", x + i, Config.gameWidth - height)
            }
            Painter.drawImage("img/symbol.gif", Config.gameWidth / 2 - Config.block / 2, 12 * Config.block)
        }
    }

    override fun notifySuffer(attacker: Attackable): Array<View>? {
        blood-=attacker.attack
        lateinit var array: Array<View>
        if(blood==3){
            return arrayOf(Blast((Config.gameWidth-Config.block)/2,Config.gameHeight-Config.block))
        }
        return null
    }

    override fun judgeDestroy(): Boolean = blood<=0

    override fun showDestroy(): Array<View>? {
        if(judgeDestroy())
            return arrayOf(Blast(x,y))
        return null
    }
}