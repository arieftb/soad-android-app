package id.my.arieftb.soad.presentation.common.view.dialog.confirmation

interface ConfirmationDialogContract {
    interface View {
        fun initArgs()
        fun init()
        fun initTitle()
        fun initDescription()
        fun initPrimaryButton()
        fun initSecondaryButton()

        fun setOnPrimaryButtonClickListener(
            onClick: (dialog: ConfirmationDialogFragment) -> Unit
        )

        fun setOnSecondaryButtonClickListener(
            onClick: ((dialog: ConfirmationDialogFragment) -> Unit)? = null
        )
    }
}
