package creational

fun main() {
    val macFactory: UIComponentFactory = MacFactory()
    val macButton = macFactory.createButton()
    macButton.render()

    val windowsFactory: UIComponentFactory = WindowsFactory()
    val windowsButton = windowsFactory.createButton()
    windowsButton.render()
}

interface Button {
    fun render()
}

class MacButton : Button {
    override fun render() = println("Rendering Mac Button")
}

class WindowsButton : Button {
    override fun render() = println("Rendering Windows Button")
}

interface UIComponentFactory {
    fun createButton(): Button
}

class MacFactory : UIComponentFactory {
    override fun createButton(): Button = MacButton()
}

class WindowsFactory : UIComponentFactory {
    override fun createButton(): Button = WindowsButton()
}