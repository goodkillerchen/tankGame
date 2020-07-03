package cyxTankWar.modle

import cyxTankWar.Business.*
import cyxTankWar.Config
import cyxTankWar.enumClass.Direction
import org.itheima.kotlin.game.core.Painter

class Tank(override var x: Int, override var y: Int):Moveable,Blockable,Sufferable,Destroyable{
    override val height = Config.block
    override val width = Config.block
    override var currentDirection:Direction=Direction.UP
    override val speed:Int=10
    override var blood:Int=5
    private var badDirection:Direction?=null
    override fun draw() {
        var state=when(currentDirection){
            Direction.UP->"img/p1tankU.gif"
            Direction.DOWN->"img/p1tankD.gif"
            Direction.LEFT->"img/p1tankL.gif"
            Direction.RIGHT->"img/p1tankR.gif"
        }
        Painter.drawImage(state,x,y)
    }
    fun move(direction: Direction){
        if(badDirection==direction) {
            //badDirection=null
            return
        }
        if(direction!=currentDirection){
            this.currentDirection=direction
            return
        }
        when(currentDirection){
            Direction.RIGHT->this.x=(this.x+speed)%Config.gameWidth
            Direction.DOWN->this.y=(this.y+speed)%(Config.gameHeight)
            Direction.UP->this.y=(this.y-speed+Config.gameHeight)%Config.gameHeight
            Direction.LEFT->this.x=(this.x-speed+Config.gameWidth)%Config.gameWidth
        }
    }
    override fun notifySuffer(attacker: Attackable): Array<View>? {
        blood-=attacker.attack
        return arrayOf(Blast(x,y))
    }
    /*override fun willCollision(block: Blockable): Direction? {
        var x=this.x
        var y=this.y
        when(currentDirection){
            Direction.RIGHT->x=(x+speed)%Config.gameWidth
            Direction.DOWN->y=(y+speed)%Config.gameHeight
            Direction.UP->y=(y-speed+Config.gameHeight)%Config.gameHeight
            Direction.LEFT->x=(x-speed+Config.gameWidth)%Config.gameWidth
        }

        /*var judge=when{
            block.x+block.width<=x
                ->false
            block.x>=x+width
                ->false
            block.y+block.height<=y
                ->false
            else ->block.y<y+height
        }*/
        return if(checkCollision(x,y,width,height,block.x,block.y,block.width,block.height)) currentDirection else null
    }*/

    override fun judgeDestroy(): Boolean = blood<=0

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        this.badDirection=direction
    }

    override fun showDestroy(): Array<View>? {
        return null
    }

    fun shoot():Bullet{
        return Bullet(this, speed,currentDirection) { bulletWidth, bulletHeight ->
            val tankX = this.x
            val tankY = this.y
            var bulletX: Number = 0
            var bulletY: Number = 0
            when (currentDirection) {
                Direction.UP -> {
                    //val trans = (tankX - bulletWidth) as Double
                    bulletX = tankX + (this.width - bulletWidth) / 2
                    bulletY = tankY - bulletHeight / 2
                }
                Direction.DOWN -> {
                    //val trans = (tankX - bulletWidth) as Double
                    bulletX = tankX + (this.width - bulletWidth) / 2
                    bulletY = tankY - bulletHeight / 2 + this.height
                }
                Direction.RIGHT -> {
                    bulletX = tankX + this.width - bulletWidth / 2
                    bulletY = tankY + (this.height - bulletHeight) / 2
                }
                Direction.LEFT -> {
                    bulletX = tankX - bulletWidth / 2
                    bulletY = tankY + (this.height - bulletHeight) / 2
                }
            }
            Pair(bulletX, bulletY)
        }
    }
}