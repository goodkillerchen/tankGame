package cyxTankWar.modle

import cyxTankWar.Business.*
import cyxTankWar.Config
import cyxTankWar.enumClass.Direction
import org.itheima.kotlin.game.core.Painter
import kotlin.random.Random

class EnemyTank(override var x: Int, override var y: Int) :Moveable,Flyable,Blockable,AutoShot,Sufferable,Destroyable {
    override val height: Int = Config.block
    override val width: Int = Config.block
    override var currentDirection: Direction=Direction.DOWN
    override var direction: Direction=currentDirection
    override val speed: Int=10
    override var blood: Int=2
    private var badDirection:Direction?=null
    private var lastShotTime=0L
    private val shotFrequency=1000
    private var lastMoveTime=0L
    private val moveFrequency=100
    override fun draw() {
        var state=when(currentDirection){
            Direction.UP->"img/enemy1U.gif"
            Direction.DOWN->"img/enemy1D.gif"
            Direction.LEFT->"img/enemy1L.gif"
            Direction.RIGHT->"img/enemy1R.gif"
        }
        Painter.drawImage(state,x,y)
    }

    override fun fly(currentDirection: Direction){
        var currentMove=System.currentTimeMillis()
        if(currentMove-lastMoveTime<moveFrequency)
            return
        lastMoveTime=currentMove
        if(badDirection==currentDirection) {
            //badDirection=null
            this.currentDirection =randomDirection(badDirection)
            direction=this.currentDirection
            return
        }
        /*if(direction!=currentDirection){
            this.currentDirection=direction
            return
        }*/
        when(currentDirection){
            Direction.RIGHT->this.x=(this.x+speed)%Config.gameWidth
            Direction.DOWN->this.y=(this.y+speed)%(Config.gameHeight)
            Direction.UP->this.y=(this.y-speed+Config.gameHeight)%Config.gameHeight
            Direction.LEFT->this.x=(this.x-speed+Config.gameWidth)%Config.gameWidth
        }
    }

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        badDirection=direction
    }

    private fun randomDirection(badDirection: Direction?):Direction{
        var rand=Random.nextInt(4)
        var direction = when(rand){
            0->Direction.UP
            1->Direction.DOWN
            2->Direction.LEFT
            3->Direction.RIGHT
            else->Direction.LEFT
        }
        return if(direction==badDirection)
            randomDirection(badDirection)
        else
            direction
    }

    override fun notifySuffer(attacker: Attackable): Array<View>? {
        if(attacker.owner is EnemyTank)
            return null
        blood-=attacker.attack
        return arrayOf(Blast(x,y))
    }

    override fun judgeDestroy() = blood<=0


    override fun autoShoot(): View? {
        var currentTime=System.currentTimeMillis()
        if(currentTime-lastShotTime<shotFrequency)
            return null
        lastShotTime=currentTime
        return Bullet(this,5,currentDirection){
                bulletWidth, bulletHeight ->
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

    override fun showDestroy(): Array<View>? {
        return null
    }
}