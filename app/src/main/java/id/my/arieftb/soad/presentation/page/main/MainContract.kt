package id.my.arieftb.soad.presentation.page.main

interface MainContract {
    interface View {
        fun init()
        fun initMenu()

        fun showAccountPage()
        fun showHomePage()
    }
}
