package id.my.arieftb.soad.presentation.base.page

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseActivityImpl<VB : ViewBinding> : AppCompatActivity(), BaseActivity<VB> {
    override var binding: VB? = null

    abstract override fun initViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initViewBinding()
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        detachBinding()
        super.onDestroy()
    }

    override fun pushFragment(fragment: Fragment, frameId: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(frameId, fragment).commit()
    }
}