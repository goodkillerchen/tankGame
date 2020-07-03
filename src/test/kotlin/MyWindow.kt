
import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window

class MyWindow : Window() {
    override fun onCreate() {
        //窗体创建回调
    }

    override fun onDisplay() {
        //显示刷新回
        Painter.drawImage("enemy3D.gif",0,0)
        Painter.drawColor(Color.YELLOW,100,100,200,200)
        Painter.drawText("I love you",x=150,y=150)
    }

    override fun onKeyPressed(event: KeyEvent) {
        when(event.code) {
            KeyCode.L -> Composer.play("blast.wav")
        }
        // 按键响应回调
    }

    override fun onRefresh() {
        // 耗时操作回调
    }
}
fun main(args:Array<String>){
    Application.launch(MyWindow::class.java)
}