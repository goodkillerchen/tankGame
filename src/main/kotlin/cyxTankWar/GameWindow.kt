package cyxTankWar

import cyxTankWar.Business.*
import cyxTankWar.enumClass.Direction
import cyxTankWar.modle.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window
import java.io.BufferedReader
import java.io.InputStreamReader

class GameWindow:Window(title = "tank war",icon="img/kt.jpg",
width= Config.gameWidth, height= Config.gameHeight) {
    private val views= arrayListOf<View>()
    private lateinit var tank:Tank
    private lateinit var camp:Camp
    private var gameOver=false
    private var tankNum=4
    private var tankOnScreen=3
    private var enemyTankBorn= arrayListOf<Pair<Int,Int>>()
    private var index=0
    private var start=false
    //private var ArrayList<>
    //creation for map
    override fun onCreate() {
        Composer.play("img/start.wav")
        //val file= File(javaClass.getResource("/map/1.map").path)
        //val lines:List<String> = file.readLines();
        val readSource=javaClass.getResourceAsStream("/map/1.map")
        val reader=BufferedReader(InputStreamReader(readSource,"utf-8"))
        val lines=reader.readLines()//read the map
        var lineNum=0;
        lines.forEach{line->
            var colNum=0
            line.toCharArray().forEach {column->
                when(column){
                    '砖'-> views.add(Wall(colNum*Config.block,lineNum*Config.block))
                    '水'-> views.add(Water(colNum*Config.block,lineNum*Config.block))
                    '草'-> views.add(Grass(colNum*Config.block,lineNum*Config.block))
                    '铁'-> views.add(Steel(colNum*Config.block,lineNum*Config.block))
                    '敌'->enemyTankBorn.add(Pair(colNum*Config.block,lineNum*Config.block))
                }
                colNum++;
            }
            lineNum++;
        }
        tank=Tank(9*Config.block,12*Config.block)
        views.add(tank)
        camp=Camp(Config.gameWidth/2-Config.block,12*Config.block)
        views.add(camp)
    }

    override fun onDisplay() {
        views.forEach { view ->
            view.draw()
        }
        if(gameOver)
            Painter.drawImage("img/over.gif",Config.gameWidth/2,Config.gameHeight/2)
        //println(views.size)
    }

    //keyboard control
    override fun onKeyPressed(event: KeyEvent) {
        if(!gameOver)
            when(event.code){
                KeyCode.ENTER->start=true
                KeyCode.W->tank.move(Direction.UP)
                KeyCode.A->tank.move(Direction.LEFT)
                KeyCode.S->tank.move(Direction.DOWN)
                KeyCode.D->tank.move(Direction.RIGHT)
                KeyCode.SPACE->views.add(tank.shoot())
            }
    }

    override fun onRefresh() {
        views.filter{it is Destroyable }.forEach {
            it as Destroyable
            if(it.judgeDestroy()) {
                views.remove(it)
                if(it is EnemyTank)
                    tankNum--
                val des=it.showDestroy()
                des?.let{
                    views.addAll(des)
                }
            }
        }
        if(gameOver)
            return
        //verify the place whether the tank can go
        views.filter { it is Moveable }.forEach{move->
            move as Moveable
            var badDirection:Direction?=null
            var badBlock:Blockable?=null
            views.filter { (it is Blockable) and (move!=it)}.forEach blockTag@{block->
                block as Blockable
                val direction=move.willCollision(block)
                direction?.let{
                    badBlock=block
                    badDirection=direction
                    return@blockTag
                }
            }
            move.notifyCollision(badDirection,badBlock)
        }

        //make the bullets fly and enemy tanks move
        views.filter{it is Flyable }.forEach{flyer->
            if(start) {
                flyer as Flyable
                flyer.fly(flyer.direction)
            }
        }


        //destroy
        views.filter{it is Attackable }.forEach {attacker->
            attacker as Attackable
            views.filter{it is Sufferable && attacker.owner!=it && attacker!=it}.forEach SufferTag@ {defender->
                defender as Sufferable
                if(attacker.isCollision(defender)){
                    attacker.notifyAttack(defender)
                    val bombView=defender.notifySuffer(attacker)
                    bombView?.let{
                        views.addAll(bombView)
                    }
                    return@SufferTag
                }
            }
        }

        //enemy tanks can shot the bullet by themselves
        views.filter{it is AutoShot }.forEach {
            if(start) {
                it as AutoShot
                val shot = it.autoShoot()
                shot?.let {
                    views.add(shot)
                }
            }
        }

        //tell whether the game finish or not
        if(views.filter{it is Camp}.isEmpty() || tankNum<=0 || views.filter{it is Tank}.isEmpty()){
            gameOver=true;
        }

        //new enemy tanks are born
        if(views.filter {it is EnemyTank}.size<tankOnScreen){
            if(tankNum-views.filter { it is EnemyTank }.size>0)
            //views.add(NewBorn(enemyTankBorn[index%tankOnScreen].first,enemyTankBorn[index%tankOnScreen].second))
            views.add(EnemyTank(enemyTankBorn[index%tankOnScreen].first,enemyTankBorn[index%tankOnScreen].second))
            index++
        }


    }

}