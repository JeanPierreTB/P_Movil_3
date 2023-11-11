package pe.edu.ulima.pm20232.aulavirtual.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.ResetPasswordViewModel

class ResetPasswordViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ResetPasswordViewModel::class.java)) {
            return ResetPasswordViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}