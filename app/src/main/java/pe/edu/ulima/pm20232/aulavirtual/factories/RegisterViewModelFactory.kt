package pe.edu.ulima.pm20232.aulavirtual.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.RegisterViewModel

class RegisterViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}