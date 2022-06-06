package id.my.arieftb.soad.presentation.page.main

interface MainContract {
    interface View {
        fun init()
        fun initAppBar()

        fun showAccountPage()
        fun showHomePage()
    }
}