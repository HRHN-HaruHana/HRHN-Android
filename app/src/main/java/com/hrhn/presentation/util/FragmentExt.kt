package com.hrhn.presentation.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

fun <T : Fragment> FragmentManager.replace(
    fragmentClass: Class<T>,
    containerViewId: Int,
    tag: String,
    arguments: Bundle = Bundle()
) {
    val constructor = fragmentClass.getConstructor()
    val fragment: Fragment = findFragmentByTag(tag) ?: constructor.newInstance()
    fragment.arguments = arguments

    commit {
        fragments.forEach { hide(it) }
        if (findFragmentByTag(tag) == null) {
            add(containerViewId, fragment, tag)
        }
        show(fragment)
    }
}