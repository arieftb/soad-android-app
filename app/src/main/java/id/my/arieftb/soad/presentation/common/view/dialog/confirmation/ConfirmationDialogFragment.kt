package id.my.arieftb.soad.presentation.common.view.dialog.confirmation

import android.os.Bundle
import android.view.View
import id.my.arieftb.soad.databinding.FragmentDialogConfirmationBinding
import id.my.arieftb.soad.presentation.base.page.BaseDialogFragmentImpl

class ConfirmationDialogFragment : BaseDialogFragmentImpl<FragmentDialogConfirmationBinding>(),
    ConfirmationDialogContract.View {

    private var _title: String? = null
    private var _description: String? = null
    private var _primaryButtonLabel: String? = null
    private var _secondaryButtonLabel: String? = null
    private var _onPrimaryButtonClickListener: ((dialog: ConfirmationDialogFragment) -> Unit)? =
        null
    private var _onSecondaryButtonClickListener: ((dialog: ConfirmationDialogFragment) -> Unit)? =
        null


    override fun initViewBinding(): FragmentDialogConfirmationBinding {
        return FragmentDialogConfirmationBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArgs()
        init()
    }

    override fun initArgs() {
        arguments?.let {
            _title = it.getString(EXTRA_TITLE)
            _description = it.getString(EXTRA_DESCRIPTION)
            _primaryButtonLabel = it.getString(EXTRA_PRIMARY)
            _secondaryButtonLabel = it.getString(EXTRA_SECONDARY)
        }
    }

    override fun init() {
        initTitle()
        initDescription()
        initPrimaryButton()
        initSecondaryButton()
    }

    override fun initTitle() {
        binding?.apply {
            title.text = _title
        }
    }

    override fun initDescription() {
        binding?.apply {
            description.text = _description
        }
    }

    override fun initPrimaryButton() {
        binding?.apply {
            buttonPrimary.apply {
                text = _primaryButtonLabel
                setOnClickListener {
                    _onPrimaryButtonClickListener?.invoke(this@ConfirmationDialogFragment)
                }
            }
        }
    }

    override fun initSecondaryButton() {
        binding?.apply {
            buttonSecondary.apply {
                if (_secondaryButtonLabel.isNullOrEmpty()) {
                    visibility = View.GONE
                    return
                }

                text = _secondaryButtonLabel
                setOnClickListener {
                    _onSecondaryButtonClickListener?.invoke(this@ConfirmationDialogFragment)
                }
            }
        }
    }

    override fun setOnPrimaryButtonClickListener(onClick: (dialog: ConfirmationDialogFragment) -> Unit) {
        _onPrimaryButtonClickListener = onClick
    }

    override fun setOnSecondaryButtonClickListener(onClick: ((dialog: ConfirmationDialogFragment) -> Unit)?) {
        _onSecondaryButtonClickListener = onClick
    }

    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_DESCRIPTION = "description"
        private const val EXTRA_PRIMARY = "primary"
        private const val EXTRA_SECONDARY = "secondary"

        fun navigate(
            title: String,
            description: String,
            primaryLabel: String,
            secondaryLabel: String? = null,
            onPrimaryButtonClickListener: ((dialog: ConfirmationDialogFragment) -> Unit),
            onSecondaryButtonClickListener: ((dialog: ConfirmationDialogFragment) -> Unit)? = null,
        ): ConfirmationDialogFragment {
            return ConfirmationDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TITLE, title)
                    putString(EXTRA_DESCRIPTION, description)
                    putString(EXTRA_PRIMARY, primaryLabel)
                    putString(EXTRA_SECONDARY, secondaryLabel)
                }

                setOnPrimaryButtonClickListener(onPrimaryButtonClickListener)
                setOnSecondaryButtonClickListener(onSecondaryButtonClickListener)
            }
        }
    }


}
