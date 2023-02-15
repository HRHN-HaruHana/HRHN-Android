package com.hrhn.presentation.ui.screen.storage.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hrhn.R
import com.hrhn.databinding.ActivityAddStorageBinding
import com.hrhn.domain.model.StorageItem
import com.hrhn.presentation.util.customGetSerializableExtra
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddStorageActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddStorageBinding.inflate(layoutInflater) }
    private val data: StorageItem? by lazy { intent.customGetSerializableExtra(KEY) as StorageItem? }

    @Inject
    lateinit var addEditStorageViewModelFactory: AddEditStorageViewModelFactory
    private val viewModel: AddStorageViewModel by viewModels {
        AddStorageViewModel.provideFactory(addEditStorageViewModelFactory, data!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        observeData()
    }

    private fun initViews() {
        with(binding) {
            vm = viewModel
            lifecycleOwner = this@AddStorageActivity
        }
        initToolbar()
        initHeader()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbAddStorage)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun initHeader() {
        binding.tvTitle.text = data?.let { getString(R.string.message_edit_storage_item) }
            ?: getString(R.string.message_new_storage_item)
    }

    private fun observeData() {
        viewModel.navigateEvent.observeEvent(this) {
            finish()
        }
        viewModel.message.observeEvent(this) {
            showToast(it)
        }
    }

    companion object {
        const val KEY = "KEY_STORAGE"

        fun newIntent(context: Context, item: StorageItem = StorageItem()) =
            Intent(context, AddStorageActivity::class.java).apply {
                putExtra(KEY, item)
            }
    }
}